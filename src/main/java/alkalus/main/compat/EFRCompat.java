package alkalus.main.compat;

import net.minecraftforge.common.MinecraftForge;

import alkalus.main.compat.efr.PlayerPose;
import alkalus.main.compat.efr.PlayerScaleEventListener;
import ganymedes01.etfuturum.pose.PlayerPoseManager;

public class EFRCompat {

    public static boolean isPoseSystemEnabled() {
        return PlayerPoseManager.isEnabled();
    }

    public static void registerToPoseSystem() {
        PlayerPoseManager.register(PlayerPose.BAT);
        PlayerPoseManager.register(PlayerPose.WOLF);
        PlayerPoseManager.register(PlayerPose.TOAD);
        MinecraftForge.EVENT_BUS.register(new PlayerScaleEventListener());
    }
}
