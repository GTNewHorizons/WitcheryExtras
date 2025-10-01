package alkalus.main.mixins.late.witchery;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.emoniph.witchery.item.ItemGeneral;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleExpandedSlots;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(ItemGeneral.class)
public abstract class ItemGeneralMixin implements IBaubleExpanded {

    @Unique
    private static final String[] witcheryExtras$rowanKeyBaubleTypes = { BaubleExpandedSlots.amuletType,
            BaubleExpandedSlots.beltType, BaubleExpandedSlots.charmType };

    @Final
    @Shadow(remap = false)
    public ItemGeneral.SubItem itemDoorKey;

    @Final
    @Shadow(remap = false)
    public ItemGeneral.SubItem itemDoorKeyring;

    @SideOnly(Side.CLIENT)
    @WrapMethod(method = "addInformation")
    public void witcheryExtras$addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip,
            boolean expanded, Operation<Void> original) {
        original.call(stack, player, tooltip, expanded);
        if (itemDoorKey.isMatch(stack) || itemDoorKeyring.isMatch(stack)) {
            BaubleItemHelper.addSlotInformation(tooltip, witcheryExtras$rowanKeyBaubleTypes);
        }
    }

    @WrapMethod(method = "onItemRightClick")
    public ItemStack witcheryExtras$onItemRightClick(ItemStack stack, World world, EntityPlayer player,
            Operation<ItemStack> original) {
        return itemDoorKey.isMatch(stack) || itemDoorKeyring.isMatch(stack)
                ? BaubleItemHelper.onBaubleRightClick(stack, world, player)
                : original.call(stack, world, player);
    }

    // Baubles interface methods
    @Unique
    @Override
    public String[] getBaubleTypes(ItemStack stack) {
        return itemDoorKey.isMatch(stack) || itemDoorKeyring.isMatch(stack) ? witcheryExtras$rowanKeyBaubleTypes : null;
    }

    @Unique
    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return null;
    }

    @Unique
    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

    @Unique
    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

    @Unique
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

    @Unique
    @Override
    public boolean canEquip(ItemStack stack, EntityLivingBase player) {
        return itemDoorKey.isMatch(stack) || itemDoorKeyring.isMatch(stack);
    }

    @Unique
    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        return itemDoorKey.isMatch(stack) || itemDoorKeyring.isMatch(stack);
    }
}
