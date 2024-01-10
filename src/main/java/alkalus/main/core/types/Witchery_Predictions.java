package alkalus.main.core.types;

import java.util.Hashtable;

import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.predictions.PredictionManager;

import alkalus.main.core.util.ReflectionUtils;

public class Witchery_Predictions {

    @SuppressWarnings("unchecked")
    public static synchronized Hashtable<Integer, Prediction> getPredictions() {
        Object f = ReflectionUtils.getField(PredictionManager.instance(), "predictions");
        Hashtable<Integer, Prediction> registry;
        try {
            if (f != null) {
                registry = (Hashtable<Integer, Prediction>) f;
                if (registry != null) {
                    return registry;
                }
            }
        } catch (Throwable t) {}
        return new Hashtable<Integer, Prediction>();
    }
}
