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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.item.ItemGeneral;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleExpandedSlots;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(ItemGeneral.class)
public abstract class ItemGeneralMixin_Bauble implements IBaubleExpanded {

    @Unique
    private static final String[] witcheryExtras$charmOfDisruptedDreamsBaubleTypes = { BaubleExpandedSlots.charmType };

    @Unique
    private static final String[] witcheryExtras$rowanKeyBaubleTypes = { BaubleExpandedSlots.amuletType,
            BaubleExpandedSlots.beltType, BaubleExpandedSlots.charmType };

    @Final
    @Shadow(remap = false)
    // Internal name for the Charm of Fanciful Thinking
    public ItemGeneral.SubItem itemCharmOfDisruptedDreams;

    @Final
    @Shadow(remap = false)
    public ItemGeneral.SubItem itemDoorKey;

    @Final
    @Shadow(remap = false)
    public ItemGeneral.SubItem itemDoorKeyring;

    @SideOnly(Side.CLIENT)
    @Inject(method = "addInformation", at = @At(value = "TAIL"))
    public void witcheryExtras$addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip,
            boolean expanded, CallbackInfo ci) {
        if (isRowanKeyType(stack)) {
            BaubleItemHelper.addSlotInformation(tooltip, witcheryExtras$rowanKeyBaubleTypes);
        } else if (isCharmOfFancifulThinking(stack)) {
            BaubleItemHelper.addSlotInformation(tooltip, witcheryExtras$charmOfDisruptedDreamsBaubleTypes);
        }
    }

    @WrapMethod(method = "onItemRightClick")
    public ItemStack witcheryExtras$onItemRightClick(ItemStack stack, World world, EntityPlayer player,
            Operation<ItemStack> original) {
        return isBauble(stack)
                // No need to call the original method because all of the subtypes
                // of this item that can be worn as baubles have no right-click functionality.
                ? BaubleItemHelper.onBaubleRightClick(stack, world, player)
                : original.call(stack, world, player);
    }

    // Baubles interface methods
    @Unique
    @Override
    public String[] getBaubleTypes(ItemStack stack) {
        if (isCharmOfFancifulThinking(stack)) {
            return witcheryExtras$charmOfDisruptedDreamsBaubleTypes;
        }

        if (isRowanKeyType(stack)) {
            return witcheryExtras$rowanKeyBaubleTypes;
        }

        return null;
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
        return isBauble(stack);
    }

    @Unique
    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    @Unique
    private boolean isBauble(ItemStack stack) {
        return isCharmOfFancifulThinking(stack) || isRowanKeyType(stack);
    }

    @Unique
    private boolean isCharmOfFancifulThinking(ItemStack stack) {
        return itemCharmOfDisruptedDreams.isMatch(stack);
    }

    @Unique
    private boolean isRowanKeyType(ItemStack stack) {
        return itemDoorKey.isMatch(stack) || itemDoorKeyring.isMatch(stack);
    }
}
