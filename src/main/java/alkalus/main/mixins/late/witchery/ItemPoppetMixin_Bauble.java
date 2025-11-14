package alkalus.main.mixins.late.witchery;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.item.ItemPoppet;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.expanded.BaubleExpandedSlots;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(ItemPoppet.class)
public abstract class ItemPoppetMixin_Bauble implements IBaubleExpanded {

    @Unique
    private static final String[] witcheryExtras$poppetBaubleTypes = { BaubleExpandedSlots.charmType };

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType earthPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType waterPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType firePoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType foodPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType toolPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType deathPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType antiVoodooPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType vampiricPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType poppetProtectionPoppet;

    @Final
    @Shadow(remap = false)
    public ItemPoppet.PoppetType armorPoppet;

    @SideOnly(Side.CLIENT)
    @Inject(method = "addInformation", at = @At(value = "TAIL"))
    public void witcheryExtras$addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip,
            boolean expanded, CallbackInfo ci) {
        if (witcheryExtras$isValidBauble(stack)) {
            BaubleItemHelper.addSlotInformation(tooltip, witcheryExtras$poppetBaubleTypes);
        }
    }

    @WrapMethod(method = "onItemRightClick")
    public ItemStack witcheryExtras$onItemRightClick(ItemStack stack, World world, EntityPlayer player,
            Operation<ItemStack> original) {
        return witcheryExtras$isValidBauble(stack) ? BaubleItemHelper.onBaubleRightClick(stack, world, player)
                : original.call(stack, world, player);
    }

    @WrapMethod(method = "findBoundPoppetInInventory", remap = false)
    private static ItemStack witcheryExtras$findBoundPoppetInInventory(Item item, int damage, EntityPlayer player,
            IInventory inventory, int foundItemDamage, boolean allIndices, boolean onlyBoosted,
            Operation<ItemStack> original) {
        // Prioritize player inventory over baubles when checking for poppets
        ItemStack originalResult = original
                .call(item, damage, player, inventory, foundItemDamage, allIndices, onlyBoosted);
        if (originalResult != null) return originalResult;

        IInventory baubles = BaublesApi.getBaubles(player);
        if (baubles == null) return null;

        return original.call(item, damage, player, baubles, foundItemDamage, allIndices, onlyBoosted);
    }

    @Unique
    private boolean witcheryExtras$isValidBauble(final ItemStack stack) {
        return earthPoppet.isMatch(stack) || waterPoppet.isMatch(stack)
                || firePoppet.isMatch(stack)
                || foodPoppet.isMatch(stack)
                || toolPoppet.isMatch(stack)
                || deathPoppet.isMatch(stack)
                || antiVoodooPoppet.isMatch(stack)
                || vampiricPoppet.isMatch(stack)
                || poppetProtectionPoppet.isMatch(stack)
                || armorPoppet.isMatch(stack);
    }

    // Baubles interface methods
    @Unique
    @Override
    public String[] getBaubleTypes(ItemStack itemstack) {
        return witcheryExtras$isValidBauble(itemstack) ? witcheryExtras$poppetBaubleTypes : null;
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
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return witcheryExtras$isValidBauble(itemstack);
    }

    @Unique
    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
