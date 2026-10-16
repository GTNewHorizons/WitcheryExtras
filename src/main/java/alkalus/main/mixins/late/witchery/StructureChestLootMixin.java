package alkalus.main.mixins.late.witchery;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import com.emoniph.witchery.worldgen.ComponentShack;
import com.emoniph.witchery.worldgen.ComponentVillageApothecary;
import com.emoniph.witchery.worldgen.ComponentVillageBookShop;
import com.emoniph.witchery.worldgen.ComponentVillageKeep;
import com.emoniph.witchery.worldgen.ComponentVillageWatchTower;

import alkalus.main.core.util.ChestLootHandler;

@SuppressWarnings("UnusedMixin")
@Mixin(
        value = { ComponentShack.class, ComponentVillageApothecary.class, ComponentVillageBookShop.class,
                ComponentVillageKeep.class, ComponentVillageWatchTower.class },
        remap = false)
public class StructureChestLootMixin {

    // The chest generation method is generateStructureChestContents in dev and func_74879_a in prod.
    // Only the one that exists in the current environment matches.
    @ModifyArgs(
            method = { "addComponentParts", "func_74875_a" },
            at = @At(value = "INVOKE", target = "generateStructureChestContents"),
            require = 0)
    private void witcheryextras$useChestGenHooksDev(Args args) {
        witcheryextras$useChestGenHooks(args);
    }

    @ModifyArgs(
            method = { "addComponentParts", "func_74875_a" },
            at = @At(value = "INVOKE", target = "func_74879_a"),
            require = 0)
    private void witcheryextras$useChestGenHooksSrg(Args args) {
        witcheryextras$useChestGenHooks(args);
    }

    // Args: 2 = random, 6 = loot table, 7 = item count
    private void witcheryextras$useChestGenHooks(Args args) {
        final Random random = args.get(2);
        args.set(6, ChestLootHandler.getContents(args.get(6), random));
        args.set(7, ChestLootHandler.getCount(getClass(), args.<Integer>get(7), random));
    }
}
