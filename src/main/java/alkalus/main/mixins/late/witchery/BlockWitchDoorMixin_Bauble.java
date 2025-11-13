package alkalus.main.mixins.late.witchery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.blocks.BlockWitchDoor;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import baubles.api.BaublesApi;

@Mixin(BlockWitchDoor.class)
public abstract class BlockWitchDoorMixin_Bauble {

    @WrapMethod(method = "hasKeyForDoor", remap = false)
    private boolean witcheryExtras$hasKeyForDoor(World world, int x, int y, int z, EntityPlayer player,
            Operation<Boolean> original) {

        /* func_150012_g == getFullMetadata with MCP mappings.
         * We use it to get the Y coord of the bottom door block, which has
         * the NBT data we want, even if the player clicks on the top block.
         */
        final int doorBaseY = (((BlockWitchDoor) (Object) this).func_150012_g(world, x, y, z) & 8) == 0 ? y : y - 1;

        IInventory baubles = BaublesApi.getBaubles(player);
        for (int slot = 0; slot < baubles.getSizeInventory(); slot++) {
            ItemStack bauble = baubles.getStackInSlot(slot);

            if (Witchery.Items.GENERIC.itemDoorKey.isMatch(bauble)) {
                if (witcheryExtras$keyNBTMatchesDoor(
                        bauble.getTagCompound(),
                        x,
                        doorBaseY,
                        z,
                        world.provider.dimensionId))
                    return true;
                continue;
            }

            if (Witchery.Items.GENERIC.itemDoorKeyring.isMatch(bauble)) {
                NBTTagCompound nbt = bauble.getTagCompound();
                if (nbt == null) continue;

                NBTTagList keyList = nbt.getTagList("doorKeys", 10);
                if (keyList == null) continue;
                for (int key = 0; key < keyList.tagCount(); key++) {
                    if (witcheryExtras$keyNBTMatchesDoor(
                            keyList.getCompoundTagAt(key),
                            x,
                            doorBaseY,
                            z,
                            world.provider.dimensionId))
                        return true;
                }
            }
        }

        return original.call(world, x, y, z, player);
    }

    @Unique
    private static boolean witcheryExtras$keyNBTMatchesDoor(final NBTTagCompound nbt, final int x, final int y,
            final int z, final int dimension) {
        if (nbt == null || !nbt.hasKey("doorX") || !nbt.hasKey("doorY") || !nbt.hasKey("doorZ")) return false;
        if (nbt.hasKey("doorD") && nbt.getInteger("doorD") != dimension) return false;

        return nbt.getInteger("doorX") == x && nbt.getInteger("doorY") == y && nbt.getInteger("doorZ") == z;
    }
}
