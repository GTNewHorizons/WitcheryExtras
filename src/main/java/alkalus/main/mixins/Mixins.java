package alkalus.main.mixins;

import javax.annotation.Nonnull;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import alkalus.main.config.AsmConfig;

public enum Mixins implements IMixins {

    // spotless:off
    FIX_SHAPESHIFT_SYNC(new MixinBuilder("Correct sync offset when shapeshift")
            .addClientMixins("minecraft.NetHandlerPlayClientMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.EARLY)),
    FIX_SHAPESHIFT_STANCE_VALIDATION(new MixinBuilder("Correct stance limit when shapeshift")
            .addCommonMixins("minecraft.NetHandlerPlayServerMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.EARLY)),
    FIX_IS_ENTITY_INSIDE_OPAQUE_BLOCK(new MixinBuilder("Fix suffocation check for resized entities")
            .addCommonMixins("minecraft.EntityMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.EARLY)),
    FIX_POSITON_RESIZING_EYE_HEIGHT(new MixinBuilder("Fix player eye height positioning during resizing")
            .addCommonMixins("witchery.PotionResizingMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.LATE)),
    FIX_SHAPESHIFT_CAMERA(new MixinBuilder("Correct camera offset when shapeshift")
            .addClientMixins("minecraft.EntityRendererMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.EARLY)),
    WITCHERY_OFFSET_CAPTURE(new MixinBuilder("Skips the original view shifting logic")
            .addClientMixins("witchery.PlayerRenderMixin")
            .addRequiredMod(TargetedMod.WITCHERY)
            .setPhase(Phase.LATE)),
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
    WITCHERY_BAUBLES_COMPAT(new MixinBuilder()
            .addCommonMixins(
                    "witchery.BlockWitchDoorMixin_Bauble",
                    "witchery.ItemGeneralMixin_Bauble",
                    "witchery.ItemPoppetMixin_Bauble")
            .addRequiredMod(TargetedMod.WITCHERY)
            .addRequiredMod(TargetedMod.BAUBLES_EXPANDED)
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
