package alkalus.main.asm.transformer;

import static org.objectweb.asm.Opcodes.ACC_PROTECTED;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.RETURN;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class ClassTransformer_Witchery_TileEntityPoppetShelf extends BaseTransformer {

    public ClassTransformer_Witchery_TileEntityPoppetShelf(String transformedName, byte[] basicClass, boolean isObf) {
        super(transformedName, basicClass, isObf);
        init();
    }

    public boolean injectMethod(String aMethodName, ClassWriter cw) {
        boolean[] aResults = new boolean[2];
        aResults[0] = inject_initiate();
        aResults[1] = inject_invalidate();
        for (boolean b : aResults) {
            if (!b) {
                log("Patching failed.");
                return false;
            }
        }
        log("Patching Success.");
        return true;
    }

    // com.emoniph.witchery.blocks.BlockPoppetShelf.TileEntityPoppetShelf

    private boolean inject_initiate() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PROTECTED, "initiate", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(138, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "com/emoniph/witchery/blocks/TileEntityBase", "initiate", "()V", false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(139, l1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/PoppetShelfUtils",
                "initiate",
                "(Lcom/emoniph/witchery/blocks/BlockPoppetShelf$TileEntityPoppetShelf;)V",
                false);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(140, l2);
        mv.visitInsn(RETURN);
        Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable(
                "this", "Lcom/emoniph/witchery/blocks/BlockPoppetShelf$TileEntityPoppetShelf;", null, l0, l3, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return true;
    }

    private boolean inject_invalidate() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PUBLIC, "invalidate", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(155, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/PoppetShelfUtils",
                "invalidate",
                "(Lcom/emoniph/witchery/blocks/BlockPoppetShelf$TileEntityPoppetShelf;)V",
                false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(156, l1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "com/emoniph/witchery/blocks/TileEntityBase", "invalidate", "()V", false);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(157, l2);
        mv.visitInsn(RETURN);
        Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable(
                "this", "Lcom/emoniph/witchery/blocks/BlockPoppetShelf$TileEntityPoppetShelf;", null, l0, l3, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return true;
    }

    @Override
    public String[] getMethodNamesToStrip() {
        return new String[] {
            "initiate", "invalidate",
        };
    }

    @Override
    public String[] getMethodDescriptorsToStrip() {
        return null;
    }

    @Override
    public String getTransformerName() {
        return "Poppet Shelf Chunkloading";
    }

    @Override
    protected boolean runInjectorMultipleTimes() {
        return false;
    }
}
