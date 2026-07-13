package alkalus.main.compat.efr;

import net.minecraft.entity.player.EntityPlayer;

import com.emoniph.witchery.common.ExtendedPlayer;
import com.emoniph.witchery.util.TransformCreature;

import ganymedes01.etfuturum.pose.IPlayerPose;

public class PlayerPose {

    static public class WolfForm implements IPlayerPose {

        @Override
        public float getPriority() {
            return 4;
        }

        @Override
        public float getHeight() {
            return 0.8F;
        }

        @Override
        public float getEyeHeight() {
            return 0.8F * 0.92F;
        }

        @Override
        public boolean canApply(EntityPlayer player) {
            return ExtendedPlayer.get(player).getCreatureType() == TransformCreature.WOLF;
        }

        @Override
        public boolean canFallback(EntityPlayer player) {
            return ExtendedPlayer.get(player).getCreatureType() == TransformCreature.WOLF;
        }
    }

    static public class BatForm implements IPlayerPose {

        @Override
        public float getPriority() {
            return 4;
        }

        @Override
        public float getWidth() {
            return 0.3F;
        }

        @Override
        public float getHeight() {
            return 0.6F;
        }

        @Override
        public float getEyeHeight() {
            return 0.6F * 0.8F;
        }

        @Override
        public boolean canApply(EntityPlayer player) {
            return ExtendedPlayer.get(player).getCreatureType() == TransformCreature.BAT;
        }

        @Override
        public boolean canFallback(EntityPlayer player) {
            return ExtendedPlayer.get(player).getCreatureType() == TransformCreature.BAT;
        }
    }

    static public class ToadForm implements IPlayerPose {

        @Override
        public float getPriority() {
            return 4;
        }

        @Override
        public float getWidth() {
            return 0.3F;
        }

        @Override
        public float getHeight() {
            return 0.5F;
        }

        @Override
        public float getEyeHeight() {
            return 0.5F * 0.92F;
        }

        @Override
        public boolean canApply(EntityPlayer player) {
            return ExtendedPlayer.get(player).getCreatureType() == TransformCreature.TOAD;
        }

        @Override
        public boolean canFallback(EntityPlayer player) {
            return ExtendedPlayer.get(player).getCreatureType() == TransformCreature.TOAD;
        }
    }

    static public IPlayerPose BAT = new BatForm();
    static public IPlayerPose WOLF = new WolfForm();
    static public IPlayerPose TOAD = new ToadForm();
}
