package alkalus.main.asm.transformer;

import static org.objectweb.asm.Opcodes.*;

import cpw.mods.fml.relauncher.FMLRelaunchLog;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class BaseTransformer {

    protected final boolean mObf;
    protected boolean isValid = false;
    private final ClassReader reader;
    private final ClassWriter writer;

    public BaseTransformer(String transformedName, byte[] basicClass, boolean isObf) {
        ClassReader aTempReader = null;
        ClassWriter aTempWriter = null;
        mObf = isObf;
        log("Transforming " + transformedName);
        aTempReader = new ClassReader(basicClass);
        aTempWriter = new ClassWriter(aTempReader, ClassWriter.COMPUTE_FRAMES);
        reader = aTempReader;
        writer = aTempWriter;
    }

    protected final void init() {
        reader.accept(new MethodAdaptor(writer), 0);
        if (runInjectorMultipleTimes()) {
            for (String methodName : getMethodNamesToStrip()) {
                injectMethod(methodName, writer);
            }
        } else {
            injectMethod("NONE", writer);
        }
        if (reader != null && writer != null) {
            isValid = true;
        } else {
            isValid = false;
        }
        log("Valid? " + isValid + ".");
    }

    protected abstract boolean runInjectorMultipleTimes();

    public final boolean isValidTransformer() {
        return isValid;
    }

    public final ClassReader getReader() {
        return reader;
    }

    public final ClassWriter getWriter() {
        return writer;
    }

    public abstract boolean injectMethod(String aMethodName, ClassWriter cw);

    public abstract String[] getMethodNamesToStrip();

    public abstract String[] getMethodDescriptorsToStrip();

    public abstract String getTransformerName();

    protected void log(String s) {
        FMLRelaunchLog.log("[Witchery++ ASM] " + getTransformerName(), Level.INFO, s);
    }

    public class MethodAdaptor extends ClassVisitor {

        public MethodAdaptor(ClassVisitor cv) {
            super(ASM5, cv);
            this.cv = cv;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor;
            boolean found = false;
            String[] aNames = getMethodNamesToStrip();
            String[] aDesc = getMethodDescriptorsToStrip();
            boolean checkDesc = aDesc != null && aDesc.length > 0;

            for (int i = 0; i < getMethodNamesToStrip().length; i++) {
                if (checkDesc) {
                    if (desc.equals(aDesc[i])) {
                        if (name.equals(aNames[i])) {
                            found = true;
                            break;
                        }
                    }
                } else {
                    if (name.equals(aNames[i])) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            } else {
                methodVisitor = null;
            }
            if (found) {
                log("Found method " + name + ", removing. "
                        + (checkDesc
                                ? "Using matching method desc. '" + checkDesc + "'"
                                : "Did not compare method desc."));
            }
            return methodVisitor;
        }
    }
}
