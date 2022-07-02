package alkalus.main.core.types;

import alkalus.main.core.util.ReflectionUtils;
import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.predictions.PredictionManager;
import java.util.Hashtable;

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
        } catch (Throwable t) {
        }
        return new Hashtable<Integer, Prediction>();
    }
}
