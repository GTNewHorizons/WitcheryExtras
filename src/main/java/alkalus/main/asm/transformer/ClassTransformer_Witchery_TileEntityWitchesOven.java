package alkalus.main.asm.transformer;

import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DRETURN;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class ClassTransformer_Witchery_TileEntityWitchesOven extends BaseTransformer {

    private final String mItemStack;

    public ClassTransformer_Witchery_TileEntityWitchesOven(String transformedName, byte[] basicClass, boolean isObf) {
        super(transformedName, basicClass, isObf);
        mItemStack = mObf ? "add" : "net/minecraft/item/ItemStack";
        log("Patching Obfuscated classes? " + mObf);
        init();
    }

    public boolean injectMethod(String aMethodName, ClassWriter cw) {
        boolean[] aResults = new boolean[10];
        aResults[0] = inject_updateEntity();
        aResults[1] = inject_canSmelt();
        aResults[2] = inject_smeltItem();
        aResults[3] = inject_getFumeFunnels();
        aResults[4] = inject_isFumeFunnel();
        aResults[5] = inject_getFumeFunnelsChance();
        aResults[6] = inject_getFumeFunnelChance();
        aResults[7] = inject_getCookTime();
        aResults[8] = inject_generateByProduct();
        aResults[9] = inject_createByProduct();
        for (boolean b : aResults) {
            if (!b) {
                log("Patching failed.");
                return false;
            }
        }
        log("Patching Success.");
        return true;
    }

    private boolean inject_updateEntity() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PUBLIC, "updateEntity", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(151, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "updateEntity",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;)V",
                false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(152, l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l2,
                0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return true;
    }

    private boolean inject_canSmelt() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "canSmelt", "()Z", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(155, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "canSmelt",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;)Z",
                false);
        mv.visitInsn(IRETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l1,
                0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return true;
    }

    private boolean inject_smeltItem() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PUBLIC, "smeltItem", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(159, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "smeltItem",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;)V",
                false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(160, l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l2,
                0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return true;
    }

    private boolean inject_getFumeFunnels() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "getFumeFunnels", "()I", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(163, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "getFumeFunnels",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;)I",
                false);
        mv.visitInsn(IRETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l1,
                0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return true;
    }

    private boolean inject_isFumeFunnel() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "isFumeFunnel", "(IIII)Z", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(167, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitVarInsn(ILOAD, 2);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitVarInsn(ILOAD, 4);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "isFumeFunnel",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;IIII)Z",
                false);
        mv.visitInsn(IRETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l1,
                0);
        mv.visitLocalVariable("xCoord", "I", null, l0, l1, 1);
        mv.visitLocalVariable("yCoord", "I", null, l0, l1, 2);
        mv.visitLocalVariable("zCoord", "I", null, l0, l1, 3);
        mv.visitLocalVariable("meta", "I", null, l0, l1, 4);
        mv.visitMaxs(5, 5);
        mv.visitEnd();

        return true;
    }

    private boolean inject_getFumeFunnelsChance() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "getFumeFunnelsChance", "()D", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(171, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "getFumeFunnelsChance",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;)D",
                false);
        mv.visitInsn(DRETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l1,
                0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();

        return true;
    }

    private boolean inject_getFumeFunnelChance() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "getFumeFunnelChance", "(IIII)D", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(175, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(
                GETFIELD,
                "com/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven",
                "xCoord",
                "I");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(
                GETFIELD,
                "com/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven",
                "yCoord",
                "I");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(
                GETFIELD,
                "com/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven",
                "zCoord",
                "I");
        mv.visitVarInsn(ILOAD, 4);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "getFumeFunnelChance",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;IIII)D",
                false);
        mv.visitInsn(DRETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l1,
                0);
        mv.visitLocalVariable("x", "I", null, l0, l1, 1);
        mv.visitLocalVariable("y", "I", null, l0, l1, 2);
        mv.visitLocalVariable("z", "I", null, l0, l1, 3);
        mv.visitLocalVariable("meta", "I", null, l0, l1, 4);
        mv.visitMaxs(5, 5);
        mv.visitEnd();

        return true;
    }

    private boolean inject_getCookTime() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "getCookTime", "()I", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(178, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "getCookTime",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;)I",
                false);
        mv.visitInsn(IRETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l1,
                0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return true;
    }

    private boolean inject_generateByProduct() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "generateByProduct", "(L" + mItemStack + ";)V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(182, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "generateByProduct",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;L" + mItemStack + ";)V",
                false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(183, l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l2,
                0);
        mv.visitLocalVariable("itemstack", "L" + mItemStack + ";", null, l0, l2, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        return true;
    }

    private boolean inject_createByProduct() {
        MethodVisitor mv;

        mv = getWriter().visitMethod(ACC_PRIVATE, "createByProduct", "(L" + mItemStack + ";)V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(186, l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(
                INVOKESTATIC,
                "alkalus/main/core/util/WitchesOvenUtils",
                "createByProduct",
                "(Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;L" + mItemStack + ";)V",
                false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(187, l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable(
                "this",
                "Lcom/emoniph/witchery/blocks/BlockWitchesOven$TileEntityWitchesOven;",
                null,
                l0,
                l2,
                0);
        mv.visitLocalVariable("byProduct", "L" + mItemStack + ";", null, l0, l2, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        return true;
    }

    @Override
    public String[] getMethodNamesToStrip() {
        return new String[] { "getCookTime", "updateEntity", "canSmelt", "smeltItem", "getFumeFunnels", "isFumeFunnel",
                "getFumeFunnelsChance", "getFumeFunnelChance", "getCookTime", "generateByProduct", "createByProduct", };
    }

    @Override
    public String[] getMethodDescriptorsToStrip() {
        return null;
    }

    @Override
    public String getTransformerName() {
        return "Witches Oven Fix";
    }

    @Override
    protected boolean runInjectorMultipleTimes() {
        return false;
    }
}
