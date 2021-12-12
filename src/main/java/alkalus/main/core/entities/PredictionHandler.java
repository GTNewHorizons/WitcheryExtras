package alkalus.main.core.entities;

import java.lang.reflect.Field;

import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.predictions.PredictionAlwaysForced;
import com.emoniph.witchery.predictions.PredictionArrow;
import com.emoniph.witchery.predictions.PredictionBuriedTreasure;
import com.emoniph.witchery.predictions.PredictionFall;
import com.emoniph.witchery.predictions.PredictionFallInLove;
import com.emoniph.witchery.predictions.PredictionFight;
import com.emoniph.witchery.predictions.PredictionMultiMine;
import com.emoniph.witchery.predictions.PredictionNetherTrip;
import com.emoniph.witchery.predictions.PredictionRescue;
import com.emoniph.witchery.predictions.PredictionWet;

import alkalus.main.api.RecipeManager;
import alkalus.main.asm.AsmConfig;
import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.util.ReflectionUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class PredictionHandler {

	// Base Prediction
	private static final Field mSelfFulfillmentProbabilityPerSec;

	// Always Forced Prediction
	private static final Field mRegularFulfillmentDurationInTicks;
	private static final Field mRegularFulfillmentProbability;

	// Rescue Prediction
	private static final Field mRescueEntityClass;

	// Buried Treasure
	private static final Field mChestGenHook;

	// Multimine
	private static final Field mBlock;
	private static final Field mItem;
	private static final Field mMinExtra;
	private static final Field mMaxExtra;

	// Fight
	private static final Field mFightEntityClass;
	private static final Field mBindTameable;


	static {
		mSelfFulfillmentProbabilityPerSec = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.Prediction"), "selfFulfillmentProbabilityPerSec");
		mRegularFulfillmentDurationInTicks = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionAlwaysForced"), "regularFulfillmentDurationInTicks");
		mRegularFulfillmentProbability = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionAlwaysForced"), "regularFulfillmentProbability");
		mRescueEntityClass = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionRescue"), "entityClass");
		mChestGenHook = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionBuriedTreasure"), "chestGenHook");
		mBlock = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionMultiMine"), "block");
		mItem = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionMultiMine"), "itemPrototype");
		mMinExtra = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionMultiMine"), "minExtra");
		mMaxExtra = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionMultiMine"), "maxExtra");
		mFightEntityClass = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionFight"), "entityClass");
		mBindTameable = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.PredictionFight"), "bindTameable");
	}

	public static void adjustPredictions() {
		int[] aNewValues = new int[] {
				AsmConfig.chancePredictionSpawnZombie,
				AsmConfig.chancePredictionSpawnSkeleton,
				AsmConfig.chancePredictionSpawnEnt,
				AsmConfig.chancePredictionStumbleAndFall,
				AsmConfig.chancePredictionExtraIron,
				AsmConfig.chancePredictionFindShinies,
				AsmConfig.chancePredictionFindShinies,
				AsmConfig.chancePredictionSpawnBuriedTreasure,
				AsmConfig.chancePredictionVillagerLove,
				AsmConfig.chancePredictionSpawnBabaYagaBad,
				AsmConfig.chancePredictionSpawnBabaYagaGood,
				AsmConfig.chancePredictionSpawnFriendlyWolf,
				AsmConfig.chancePredictionSpawnProtectiveAnimal,
				AsmConfig.chancePredictionSpawnProtectiveAnimal,
				AsmConfig.chancePredictionGetWet,
				AsmConfig.chancePredictionTeleportNether,
				AsmConfig.chancePredictionExtraCoal,	
		};
		for (int i=0;i<18;i++) {
			Prediction aOld = RecipeManager.Predictions.getPrediction(i);
			if (aOld != null) {
				if (aOld.itemWeight != aNewValues[i-1]) {
					RecipeManager.Predictions.remove(aOld);
					WitcheryExtras.log(0, "Adjusting weight of "+aOld.getTranslationKey()+" from "+aOld.itemWeight+" to "+aNewValues[i-1]+".");
					RecipeManager.Predictions.add(generateNewPrediction(aOld, aNewValues[i-1]));
				}
			}
		}
	}


	private static Prediction generateNewPrediction(Prediction aOldPrediction, int aNewWeight) {
		Prediction aNewPrediction = null;
		int predictionID = aOldPrediction.predictionID;
		String translationKey = aOldPrediction.getTranslationKey();
		double selfFulfillmentProbabilityPerSec = (double) ReflectionUtils.getFieldValue(mSelfFulfillmentProbabilityPerSec, aOldPrediction);
		if (aOldPrediction instanceof PredictionAlwaysForced) {
			final int regularFulfillmentDurationInTicks = (int) ReflectionUtils.getFieldValue(mRegularFulfillmentDurationInTicks, aOldPrediction);
			final double regularFulfillmentProbability = (double) ReflectionUtils.getFieldValue(mRegularFulfillmentProbability, aOldPrediction);
			if (aOldPrediction instanceof PredictionMultiMine) {
				final Block block = (Block) ReflectionUtils.getFieldValue(mBlock, aOldPrediction);
				final ItemStack itemPrototype = (ItemStack) ReflectionUtils.getFieldValue(mItem, aOldPrediction);
				final int minExtra = (int) ReflectionUtils.getFieldValue(mMinExtra, aOldPrediction);
				final int maxExtra = (int) ReflectionUtils.getFieldValue(mMaxExtra, aOldPrediction);
				aNewPrediction = new PredictionMultiMine(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey, regularFulfillmentDurationInTicks, regularFulfillmentProbability, block, itemPrototype, minExtra, maxExtra);
			}
			else if (aOldPrediction instanceof PredictionBuriedTreasure) {
				final String chestGenHook = (String) ReflectionUtils.getFieldValue(mChestGenHook, aOldPrediction);
				aNewPrediction = new PredictionBuriedTreasure(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey, regularFulfillmentDurationInTicks, regularFulfillmentProbability, chestGenHook);
			}
			else if (aOldPrediction instanceof PredictionFallInLove) {
				aNewPrediction = new PredictionFallInLove(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey, regularFulfillmentDurationInTicks, regularFulfillmentProbability);
			}
			else if (aOldPrediction instanceof PredictionRescue) {
				Class aEntityClass = (Class) ReflectionUtils.getFieldValue(mRescueEntityClass, aOldPrediction);
				aNewPrediction = new PredictionRescue(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey, regularFulfillmentDurationInTicks, regularFulfillmentProbability, aEntityClass);
			}
		}		
		else if (aOldPrediction instanceof PredictionFight) {
			Class aEntityClass = (Class) ReflectionUtils.getFieldValue(mFightEntityClass, aOldPrediction);
			boolean aBindTameable = (boolean) ReflectionUtils.getFieldValue(mBindTameable, aOldPrediction);
			aNewPrediction = new PredictionFight(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey, aEntityClass, aBindTameable);
		}
		else if (aOldPrediction instanceof PredictionArrow) {
			aNewPrediction = new PredictionArrow(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey);
		}
		else if (aOldPrediction instanceof PredictionFall) {
			aNewPrediction = new PredictionFall(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey);
		}		
		else if (aOldPrediction instanceof PredictionWet) {
			aNewPrediction = new PredictionWet(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey);
		}
		else if (aOldPrediction instanceof PredictionNetherTrip) {
			aNewPrediction = new PredictionNetherTrip(predictionID, aNewWeight, selfFulfillmentProbabilityPerSec, translationKey);
		}

		return aNewPrediction;
	}

}
