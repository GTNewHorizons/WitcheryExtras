package alkalus.main.asm;

import net.minecraft.launchwrapper.IClassTransformer;

public class WE_CORE_Handler implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        return basicClass;
    }
}
