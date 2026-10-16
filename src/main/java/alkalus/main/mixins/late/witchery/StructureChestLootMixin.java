package alkalus.main.mixins.late.witchery;

import java.util.Random;

import net.minecraft.util.WeightedRandomChestContent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.emoniph.witchery.worldgen.ComponentShack;
import com.emoniph.witchery.worldgen.ComponentVillageApothecary;
import com.emoniph.witchery.worldgen.ComponentVillageBookShop;
import com.emoniph.witchery.worldgen.ComponentVillageKeep;
import com.emoniph.witchery.worldgen.ComponentVillageWatchTower;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import alkalus.main.core.util.ChestLootHandler;

@SuppressWarnings("UnusedMixin")
@Mixin(
        value = { ComponentShack.class, ComponentVillageApothecary.class, ComponentVillageBookShop.class,
                ComponentVillageKeep.class, ComponentVillageWatchTower.class },
        remap = false)
public class StructureChestLootMixin {

    @Definition(
            id = "contents",
            field = {
                    "Lcom/emoniph/witchery/worldgen/ComponentShack;shackChestContents:[Lnet/minecraft/util/WeightedRandomChestContent;",
                    "Lcom/emoniph/witchery/worldgen/ComponentVillageApothecary;villageApothecaryChestContents:[Lnet/minecraft/util/WeightedRandomChestContent;",
                    "Lcom/emoniph/witchery/worldgen/ComponentVillageBookShop;bookshopChestContents:[Lnet/minecraft/util/WeightedRandomChestContent;",
                    "Lcom/emoniph/witchery/worldgen/ComponentVillageKeep;villageTowerChestContents:[Lnet/minecraft/util/WeightedRandomChestContent;",
                    "Lcom/emoniph/witchery/worldgen/ComponentVillageWatchTower;villageTowerChestContents:[Lnet/minecraft/util/WeightedRandomChestContent;" })
    @Expression("contents")
    @ModifyExpressionValue(
            method = { "addComponentParts", "func_74875_a" },
            at = @At("MIXINEXTRAS:EXPRESSION"),
            require = 1)
    private WeightedRandomChestContent[] witcheryextras$useChestGenHooks(WeightedRandomChestContent[] original,
            @Local(argsOnly = true) Random random) {
        return ChestLootHandler.getContents(original, random);
    }
}
