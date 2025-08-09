package alkalus.main.mixins.late.witchery;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.Constants;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.brewing.ItemBrew;
import com.emoniph.witchery.item.ItemBiomeNote;
import com.emoniph.witchery.item.ItemBook;

@SuppressWarnings("UnusedMixin")
@Mixin(ItemBrew.class)
public class ItemBrewMixin {

    @Inject(method = "addInformation", at = @At("TAIL"))
    private void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean expanded,
        CallbackInfo ci) {
        if (stack == null || !(stack.getItem() instanceof ItemBrew)) return;

        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) return;

        String type = tag.getString("BrewInfo");
        if (type == null || !type.contains("Shifting Seasons")) return;

        if (!tag.hasKey("Items")) return;
        NBTTagList items = tag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); i++) {
            ItemStack pageStack = ItemStack.loadItemStackFromNBT(items.getCompoundTagAt(i));
            if (pageStack != null && pageStack.getItem() instanceof ItemBiomeNote) {
                BiomeGenBase biome = ItemBook.getSelectedBiome(pageStack.getItemDamage());
                if (biome != null) {
                    tooltip.add(biome.biomeName);
                    return;
                }
            }
        }
    }
}
