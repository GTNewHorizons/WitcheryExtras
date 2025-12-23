package alkalus.main.mixins;

import javax.annotation.Nonnull;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import alkalus.main.config.AsmConfig;

public enum Mixins implements IMixins {

    // spotless:off
    WITCHERY(new MixinBuilder()
            .addCommonMixins(
                    "witchery.TileEntityPoppetShelfMixin",
                    "witchery.TileEntityWitchesOvenMixin",
                    "witchery.TileEntitySpinningWheelMixin",
                    "witchery.SpinningRecipeMixin",
                    "witchery.ShockwaveTaskMixin",
                    "witchery.TileEntityDistilleryMixin",
                    "witchery.ItemGeneral$3$1Mixin",
                    "witchery.BlockAltarMixin")
            .addClientMixins("witchery.ItemBrewMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.LATE)),
    WITCHERY_NEI_CONFIG(new MixinBuilder()
            .addCommonMixins("witchery.NEIWitcheryConfigMixin")
            .setApplyIf(()-> AsmConfig.enablePatchNEI)
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.LATE)),
    WITCHERY_DISABLE_CONTAINER_BLOCKS(new MixinBuilder()
            .addCommonMixins("witchery.BlockBaseContainerMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.LATE)
            // configs handle individual blocks to disable
            ),
        ;
    // spotless:on

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return this.builder;
    }
}
