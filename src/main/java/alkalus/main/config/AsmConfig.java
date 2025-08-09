package alkalus.main.config;

import java.io.File;
import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class AsmConfig {

    public static Configuration config;

    public static boolean enablePatchNEI;
    public static boolean allowPoppetShelfChunkLoading;

    // Predictions
    public static int chancePredictionSpawnBabaYagaGood;
    public static int chancePredictionSpawnBabaYagaBad;
    public static int chancePredictionExtraCoal;
    public static int chancePredictionSpawnEnt;
    public static int chancePredictionStumbleAndFall;
    public static int chancePredictionGetWet;
    public static int chancePredictionExtraIron;
    public static int chancePredictionSpawnZombie;
    public static int chancePredictionSpawnProtectiveAnimal;
    public static int chancePredictionSpawnSkeleton;
    public static int chancePredictionSpawnBuriedTreasure;
    public static int chancePredictionTeleportNether;
    public static int chancePredictionSpawnFriendlyWolf;
    public static int chancePredictionFindShinies;
    public static int chancePredictionVillagerLove;

    static {
        config = new Configuration(new File("config/WitcheryExtras/asm.cfg"));
        syncConfig(true);
    }

    public static void syncConfig(boolean load) {
        ArrayList<String> propOrder = new ArrayList<>();
        ArrayList<String> propOrderDebug = new ArrayList<>();
        try {
            if (!config.isChild && load) {
                config.load();
            }
            Property prop;

            // Fixes NEI handling
            prop = config.get("general", "enablePatchNEI", true);
            prop.comment = "Patch NEI for good recipe support.";
            prop.setLanguageKey("enablePatchNEI")
                .setRequiresMcRestart(true);
            enablePatchNEI = prop.getBoolean(true);
            propOrder.add(prop.getName());

            // Toggles Chunk Loading for Poppet Shelves
            prop = config.get("general", "allowPoppetShelfChunkLoading", true);
            prop.comment = "Enables Chunk Loading by Poppet Shelves.";
            prop.setLanguageKey("allowPoppetShelfChunkLoading")
                .setRequiresMcRestart(true);
            allowPoppetShelfChunkLoading = prop.getBoolean(true);
            propOrder.add(prop.getName());

            /*
             * Predictions
             */

            // Adjusts chances for Baba Yaga spawns
            prop = config.get("predictions", "chancePredictionSpawnBabaYagaGood", 2);
            prop.comment = "Adjusts weight for 'Good' Baba Yaga spawns";
            prop.setLanguageKey("chancePredictionSpawnBabaYagaGood");
            chancePredictionSpawnBabaYagaGood = prop.getInt(2);
            propOrder.add(prop.getName());

            prop = config.get("predictions", "chancePredictionSpawnBabaYagaBad", 2);
            prop.comment = "Adjusts weight for 'Bad' Baba Yaga spawns";
            prop.setLanguageKey("chancePredictionSpawnBabaYagaBad");
            chancePredictionSpawnBabaYagaBad = prop.getInt(2);
            propOrder.add(prop.getName());

            // Extra coal
            prop = config.get("predictions", "chancePredictionExtraCoal", 13);
            prop.comment = "Adjusts weight for Extra Coal Drops";
            prop.setLanguageKey("chancePredictionExtraCoal");
            chancePredictionExtraCoal = prop.getInt(13);
            propOrder.add(prop.getName());

            // Spawn Ent
            prop = config.get("predictions", "chancePredictionSpawnEnt", 3);
            prop.comment = "Adjusts weight for Ent Spawn";
            prop.setLanguageKey("chancePredictionSpawnEnt");
            chancePredictionSpawnEnt = prop.getInt(3);
            propOrder.add(prop.getName());

            // Create Pit
            prop = config.get("predictions", "chancePredictionStumbleAndFall", 13);
            prop.comment = "Adjusts weight for 'Stumble & Fall'";
            prop.setLanguageKey("chancePredictionStumbleAndFall");
            chancePredictionStumbleAndFall = prop.getInt(13);
            propOrder.add(prop.getName());

            // Get Wet
            prop = config.get("predictions", "chancePredictionGetWet", 13);
            prop.comment = "Adjusts weight for 'Get Wet'";
            prop.setLanguageKey("chancePredictionGetWet");
            chancePredictionGetWet = prop.getInt(13);
            propOrder.add(prop.getName());

            // Extra Iron
            prop = config.get("predictions", "chancePredictionExtraIron", 8);
            prop.comment = "Adjusts weight for Extra Iron Drops";
            prop.setLanguageKey("chancePredictionExtraIron");
            chancePredictionExtraIron = prop.getInt(8);
            propOrder.add(prop.getName());

            // Spawn Zombie
            prop = config.get("predictions", "chancePredictionSpawnZombie", 13);
            prop.comment = "Adjusts weight for Zombie Spawn";
            prop.setLanguageKey("chancePredictionSpawnZombie");
            chancePredictionSpawnZombie = prop.getInt(13);
            propOrder.add(prop.getName());

            // Spawn Wolf/Owl Friend
            prop = config.get("predictions", "chancePredictionSpawnProtectiveAnimal", 13);
            prop.comment = "Adjusts weight for Friendly Animal Spawn";
            prop.setLanguageKey("chancePredictionSpawnProtectiveAnimal");
            chancePredictionSpawnProtectiveAnimal = prop.getInt(13);
            propOrder.add(prop.getName());

            // Spawn Skeleton
            prop = config.get("predictions", "chancePredictionSpawnSkeleton", 13);
            prop.comment = "Adjusts weight for Skeleton Spawn";
            prop.setLanguageKey("chancePredictionSpawnSkeleton");
            chancePredictionSpawnSkeleton = prop.getInt(13);
            propOrder.add(prop.getName());

            // Spawn Treasure
            prop = config.get("predictions", "chancePredictionSpawnBuriedTreasure", 2);
            prop.comment = "Adjusts weight for Buried Treasure spawn";
            prop.setLanguageKey("chancePredictionSpawnBuriedTreasure");
            chancePredictionSpawnBuriedTreasure = prop.getInt(2);
            propOrder.add(prop.getName());

            // Nether Teleport
            prop = config.get("predictions", "chancePredictionTeleportNether", 3);
            prop.comment = "Adjusts weight for Nether Teleport";
            prop.setLanguageKey("chancePredictionTeleportNether");
            chancePredictionTeleportNether = prop.getInt(3);
            propOrder.add(prop.getName());

            // Spawn Wolf Friend
            prop = config.get("predictions", "chancePredictionSpawnFriendlyWolf", 3);
            prop.comment = "Adjusts weight for ";
            prop.setLanguageKey("chancePredictionSpawnFriendlyWolf");
            chancePredictionSpawnFriendlyWolf = prop.getInt(3);
            propOrder.add(prop.getName());

            // Find Shinies (Emerald/Diamond)
            prop = config.get("predictions", "chancePredictionFindShinies", 3);
            prop.comment = "Adjusts weight for Bonus Diamond/Emerald drop from stone";
            prop.setLanguageKey("chancePredictionFindShinies");
            chancePredictionFindShinies = prop.getInt(3);
            propOrder.add(prop.getName());

            // Villager Love
            prop = config.get("predictions", "chancePredictionVillagerLove", 2);
            prop.comment = "Adjusts weight for Villager + Player lovemaking event";
            prop.setLanguageKey("chancePredictionVillagerLove");
            chancePredictionVillagerLove = prop.getInt(2);
            propOrder.add(prop.getName());

            config.setCategoryPropertyOrder("general", propOrder);
            config.setCategoryPropertyOrder("predictions", propOrder);
            config.setCategoryPropertyOrder("debug", propOrderDebug);
            if (config.hasChanged()) {
                config.save();
            }

        } catch (Exception var3) {
            FMLLog.log(Level.ERROR, var3, "Witchery++ ASM had a problem loading it's config", new Object[0]);
        }
    }
}
