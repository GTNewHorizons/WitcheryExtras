package alkalus.main.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import alkalus.main.asm.transformer.ClassTransformer_Witchery_NEIWitcheryConfig;

public class WE_CORE_Handler implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        // Fix Bad NEI Handling, by patching it out entirely.
        if (transformedName.equals("com.emoniph.witchery.integration.NEIWitcheryConfig") && AsmConfig.enablePatchNEI) {
            return new ClassTransformer_Witchery_NEIWitcheryConfig(
                    transformedName,
                    basicClass,
                    WE_CORE_FMLLoadingPlugin.isIsObf()).getWriter().toByteArray();
            // return AsmUtils.getClassBytes("com.emoniph.witchery.integration.NEIWitcheryConfig");
        }

        return basicClass;
    }
}
