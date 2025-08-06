package alkalus.main.asm;

import java.io.File;

import net.minecraft.launchwrapper.IClassTransformer;

import alkalus.main.asm.transformer.ClassTransformer_Witchery_NEIWitcheryConfig;
import alkalus.main.asm.transformer.ClassTransformer_Witchery_TileEntityWitchesOven;

@SuppressWarnings("static-access")
public class WE_CORE_Handler implements IClassTransformer {

    public static final AsmConfig mConfig;

    static {
        mConfig = new AsmConfig(new File("config/WitcheryExtras/asm.cfg"));
        System.out
                .println("[Witchery++][ASM] Asm Config Location: " + mConfig.config.getConfigFile().getAbsolutePath());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        // Fix Bad NEI Handling, by patching it out entirely.
        if (transformedName.equals("com.emoniph.witchery.integration.NEIWitcheryConfig") && mConfig.enablePatchNEI) {
            return new ClassTransformer_Witchery_NEIWitcheryConfig(
                    transformedName,
                    basicClass,
                    WE_CORE_FMLLoadingPlugin.isIsObf()).getWriter().toByteArray();
            // return AsmUtils.getClassBytes("com.emoniph.witchery.integration.NEIWitcheryConfig");
        }

        // Patch witches oven to support recipe maps.
        if (transformedName.equals("com.emoniph.witchery.blocks.BlockWitchesOven$TileEntityWitchesOven")) {
            return new ClassTransformer_Witchery_TileEntityWitchesOven(
                    transformedName,
                    basicClass,
                    WE_CORE_FMLLoadingPlugin.isIsObf()).getWriter().toByteArray();
        }

        return basicClass;
    }
}
