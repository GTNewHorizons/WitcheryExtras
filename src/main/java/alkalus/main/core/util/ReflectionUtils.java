package alkalus.main.core.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

    private static final Map<String, Class<?>> mCachedClasses = new HashMap<>();
    private static final Map<String, Field> mCachedFields = new HashMap<>();

    /**
     * Returns a cached {@link Class} object.
     *
     * @param aClassCanonicalName - The canonical name of the underlying class.
     * @return - Valid, {@link Class} object, or {@link null}.
     */
    public static Class<?> getClass(String aClassCanonicalName) {
        if (aClassCanonicalName == null || aClassCanonicalName.isEmpty()) {
            return null;
        }
        Class<?> clazz = mCachedClasses.get(aClassCanonicalName);
        if (clazz == null) {
            try {
                clazz = Class.forName(aClassCanonicalName);
                mCachedClasses.put(clazz.getCanonicalName(), clazz);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        return clazz;
    }

    /**
     * Returns a cached {@link Field} object.
     *
     * @param aClass     - Class containing the Method.
     * @param aFieldName - Field name in {@link String} form.
     * @return - Valid, non-final, {@link Field} object, or {@link null}.
     */
    public static Field getField(final Class<?> aClass, final String aFieldName) {
        if (aClass == null || aFieldName == null || aFieldName.isEmpty()) {
            return null;
        }
        Field field = mCachedFields.get(aClass.getName() + "." + aFieldName);
        if (field == null) {
            try {
                field = aClass.getDeclaredField(aFieldName);
                field.setAccessible(true);
                mCachedFields.put(aClass.getName() + "." + aFieldName, field);
            } catch (NoSuchFieldException e) {
                return null;
            }
        }
        return field;
    }

    /**
     * Returns a cached {@link Field} object.
     *
     * @param aInstance  - {@link Object} to get the field instance from.
     * @param aFieldName - Field name in {@link String} form.
     * @return - Valid, non-final, {@link Field} object, or {@link null}.
     */
    public static Object getField(final Object aInstance, final String aFieldName) {
        try {
            return getField(aInstance.getClass(), aFieldName).get(aInstance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

    public static Object getFieldValue(Field field, Object instance) {
        try {
            return field.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }
}
