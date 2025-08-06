package alkalus.main.mixins;

import javax.annotation.Nonnull;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

public enum Mixins implements IMixins {

    // spotless:off
    WITCHERY(new MixinBuilder()
            .addCommonMixins(
                    "witchery.TileEntityPoppetShelfMixin",
                    "witchery.TileEntitySpinningWheelMixin",
                    "witchery.SpinningRecipeMixin",
                    "witchery.ShockwaveTaskMixin",
                    "witchery.TileEntityDistilleryMixin",
                    "witchery.ItemGeneral$3$1Mixin",
                    "witchery.BlockAltarMixin")
            .addClientMixins("witchery.ItemBrewMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.LATE));
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
