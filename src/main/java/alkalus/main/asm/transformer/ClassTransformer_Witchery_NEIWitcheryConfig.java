package alkalus.main.asm.transformer;

import static org.objectweb.asm.Opcodes.ASM5;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ClassTransformer_Witchery_NEIWitcheryConfig extends BaseTransformer {

	private boolean didLoadDummy = false;
	
	public ClassTransformer_Witchery_NEIWitcheryConfig(String transformedName, byte[] basicClass, boolean isObf) {
		super(transformedName, basicClass, isObf);
		getReader().accept(new InterfaceAdaptor(getWriter()), 0);
		if (!didLoadDummy) {
			init();
		}
	}

	public boolean injectMethod(String aMethodName, ClassWriter cw) {	
		log("Patching Success.");	
		return true;
	}

	@Override
	public String[] getMethodNamesToStrip() {
		return new String[] {
			"loadConfig",
		};
	}

	@Override
	public String[] getMethodDescriptorsToStrip() {
		return null;
	}

	@Override
	public String getTransformerName() {
		return "NEI Fix";
	}

	@Override
	protected boolean runInjectorMultipleTimes() {
		return false;
	}
	
	public class InterfaceAdaptor extends ClassVisitor {
		
		public InterfaceAdaptor(ClassVisitor cv) {
			super(ASM5, cv);
			this.cv = cv;
		}


		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			if (interfaces != null && interfaces.length > 0) {
				String[] aNewInterfaces = new String[interfaces.length > 1 ? interfaces.length-1 : 1];
				int aIndex = 0;
				for (String s : interfaces) {
					if (!s.contains("IConfigureNEI")) {
						aNewInterfaces[aIndex++] = s;						
					}
					else {
						log("Found Interface "+s+" | Removing");							
					}
				}
			}
			else if (interfaces == null || interfaces.length == 0) {
				log("Found 0 Interfaces | Dummy Class Load success.");	
				didLoadDummy = true;
			}
			super.visit(version, access, name, signature, superName, interfaces);
		}
	}

}
