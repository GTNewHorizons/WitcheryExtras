package alkalus.main.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

    private static final Map<String, Class<?>> mCachedClasses = new HashMap<>();
    private static final Map<String, CachedField> mCachedFields = new HashMap<>();

    private static class CachedField {

        private final boolean STATIC;
        private final Field FIELD;

        public CachedField(Field aField, boolean isStatic) {
            FIELD = aField;
            STATIC = isStatic;
        }

        public Field get() {
            return FIELD;
        }

        public boolean type() {
            return STATIC;
        }
    }

    private static boolean cacheClass(Class<?> aClass) {
        if (aClass == null) {
            return false;
        }
        Class<?> y = mCachedClasses.get(aClass.getCanonicalName());
        if (y == null) {
            mCachedClasses.put(aClass.getCanonicalName(), aClass);
            return true;
        }
        return false;
    }

    private static boolean cacheField(Class<?> aClass, Field aField) {
        if (aField == null) {
            return false;
        }
        boolean isStatic = Modifier.isStatic(aField.getModifiers());
        CachedField y = mCachedFields.get(aClass.getName() + "." + aField.getName());
        if (y == null) {
            mCachedFields.put(aClass.getName() + "." + aField.getName(), new CachedField(aField, isStatic));
            return true;
        }
        return false;
    }

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
        Class<?> y = mCachedClasses.get(aClassCanonicalName);
        if (y == null) {
            y = getClass_Internal(aClassCanonicalName);
            if (y != null) {
                Logger.REFLECTION("Caching Class: " + aClassCanonicalName);
                cacheClass(y);
            }
        }
        return y;
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
        CachedField y = mCachedFields.get(aClass.getName() + "." + aFieldName);
        if (y == null) {
            Field u;
            try {
                u = getField_Internal(aClass, aFieldName);
                if (u != null) {
                    Logger.REFLECTION("Caching Field '" + aFieldName + "' from " + aClass.getName());
                    cacheField(aClass, u);
                    return u;
                }
            } catch (NoSuchFieldException e) {}
            return null;

        } else {
            return y.get();
        }
    }

    /**
     * Returns a cached {@link Field} object.
     *
     * @param aInstance  - {@link Object} to get the field instance from.
     * @param aFieldName - Field name in {@link String} form.
     * @return - Valid, non-final, {@link Field} object, or {@link null}.
     */
    public static <T> T getField(final Object aInstance, final String aFieldName) {
        try {
            return (T) getField(aInstance.getClass(), aFieldName).get(aInstance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

    /*
     * Utility Functions
     */

    public static boolean doesClassExist(final String classname) {
        return isClassPresent(classname);
    }

    public static void makeFieldAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    public static void makeMethodAccessible(final Method field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    private static Field getField_Internal(final Class<?> clazz, final String fieldName) throws NoSuchFieldException {
        try {
            Logger.REFLECTION("Field: Internal Lookup: " + fieldName);
            Field k = clazz.getDeclaredField(fieldName);
            makeFieldAccessible(k);
            // Logger.REFLECTION("Got Field from Class. "+fieldName+" did exist within "+clazz.getCanonicalName()+".");
            return k;
        } catch (final NoSuchFieldException e) {
            Logger.REFLECTION("Field: Internal Lookup Failed: " + fieldName);
            final Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) {
                Logger.REFLECTION("Unable to find field '" + fieldName + "'");
                // Logger.REFLECTION("Failed to get Field from Class. "+fieldName+" does not existing within
                // "+clazz.getCanonicalName()+".");
                throw e;
            }
            Logger.REFLECTION("Method: Recursion Lookup: " + fieldName + " - Checking in " + superClass.getName());
            // Logger.REFLECTION("Failed to get Field from Class. "+fieldName+" does not existing within
            // "+clazz.getCanonicalName()+". Trying super class.");
            return getField_Internal(superClass, fieldName);
        }
    }

    /**
     * if (isPresent("com.optionaldependency.DependencyClass")) || This block will never execute when the dependency is
     * not present. There is therefore no more risk of code throwing NoClassDefFoundException.
     */
    private static boolean isClassPresent(final String className) {
        try {
            Class.forName(className);
            return true;
        } catch (final Throwable ex) {
            // Class or one of its dependencies is not present...
            return false;
        }
    }

    private static Method getMethod_Internal(Class<?> aClass, String aMethodName, Class<?>... aTypes) {
        Method m = null;
        try {
            Logger.REFLECTION("Method: Internal Lookup: " + aMethodName);
            m = aClass.getDeclaredMethod(aMethodName, aTypes);
            m.setAccessible(true);
            int modifiers = m.getModifiers();
            Field modifierField = m.getClass().getDeclaredField("modifiers");
            modifiers = modifiers & ~Modifier.FINAL;
            modifierField.setAccessible(true);
            modifierField.setInt(m, modifiers);
        } catch (Throwable t) {
            Logger.REFLECTION("Method: Internal Lookup Failed: " + aMethodName);
            try {
                m = getMethodRecursively(aClass, aMethodName);
            } catch (NoSuchMethodException e) {
                Logger.REFLECTION("Unable to find method '" + aMethodName + "'");
                e.printStackTrace();
                dumpClassInfo(aClass);
            }
        }
        return m;
    }

    private static Constructor<?> getConstructor_Internal(Class<?> aClass, Class<?>... aTypes) {
        Constructor<?> c = null;
        try {
            Logger.REFLECTION("Constructor: Internal Lookup: " + aClass.getName());
            c = aClass.getDeclaredConstructor(aTypes);
            c.setAccessible(true);
            int modifiers = c.getModifiers();
            Field modifierField = c.getClass().getDeclaredField("modifiers");
            modifiers = modifiers & ~Modifier.FINAL;
            modifierField.setAccessible(true);
            modifierField.setInt(c, modifiers);
        } catch (Throwable t) {
            Logger.REFLECTION("Constructor: Internal Lookup Failed: " + aClass.getName());
            try {
                c = getConstructorRecursively(aClass, aTypes);
            } catch (Exception e) {
                Logger.REFLECTION("Unable to find method '" + aClass.getName() + "'");
                e.printStackTrace();
                dumpClassInfo(aClass);
            }
        }
        return c;
    }

    private static Constructor<?> getConstructorRecursively(Class<?> aClass, Class<?>... aTypes) throws Exception {
        try {
            Logger.REFLECTION("Constructor: Recursion Lookup: " + aClass.getName());
            Constructor<?> c = aClass.getConstructor(aTypes);
            c.setAccessible(true);
            int modifiers = c.getModifiers();
            Field modifierField = c.getClass().getDeclaredField("modifiers");
            modifiers = modifiers & ~Modifier.FINAL;
            modifierField.setAccessible(true);
            modifierField.setInt(c, modifiers);
            return c;
        } catch (final NoSuchMethodException | IllegalArgumentException | IllegalAccessException e) {
            final Class<?> superClass = aClass.getSuperclass();
            if (superClass == null || superClass == Object.class) {
                throw e;
            }
            return getConstructor_Internal(superClass, aTypes);
        }
    }

    private static Method getMethodRecursively(final Class<?> clazz, final String aMethodName)
            throws NoSuchMethodException {
        try {
            Logger.REFLECTION("Method: Recursion Lookup: " + aMethodName);
            Method k = clazz.getDeclaredMethod(aMethodName);
            makeMethodAccessible(k);
            return k;
        } catch (final NoSuchMethodException e) {
            final Class<?> superClass = clazz.getSuperclass();
            if (superClass == null || superClass == Object.class) {
                throw e;
            }
            return getMethod_Internal(superClass, aMethodName);
        }
    }

    private static void dumpClassInfo(Class<?> aClass) {
        Logger.REFLECTION(
                "We ran into an error processing reflection in " + aClass.getName()
                        + ", dumping all data for debugging.");
        // Get the methods
        Method[] methods = aClass.getDeclaredMethods();
        Field[] fields = aClass.getDeclaredFields();
        Constructor<?>[] consts = aClass.getDeclaredConstructors();

        Logger.REFLECTION("Dumping all Methods.");
        for (Method method : methods) {
            System.out.println(method.getName() + " | " + getDataStringFromArray(method.getParameterTypes()));
        }
        Logger.REFLECTION("Dumping all Fields.");
        for (Field f : fields) {
            System.out.println(f.getName());
        }
        Logger.REFLECTION("Dumping all Constructors.");
        for (Constructor<?> c : consts) {
            System.out.println(
                    c.getName() + " | "
                            + c.getParameterCount()
                            + " | "
                            + getDataStringFromArray(c.getParameterTypes()));
        }
    }

    private static Class<?> getNonPublicClass(final String className) {
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (final ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // full package name --------^^^^^^^^^^
        // or simpler without Class.forName:
        // Class<package1.A> c = package1.A.class;

        if (null != c) {
            // In our case we need to use
            Constructor<?> constructor = null;
            try {
                constructor = c.getDeclaredConstructor();
            } catch (NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // note: getConstructor() can return only public constructors
            // so we needed to search for any Declared constructor

            // now we need to make this constructor accessible
            if (null != constructor) {
                constructor.setAccessible(true); // ABRACADABRA!

                try {
                    final Object o = constructor.newInstance();
                    return (Class<?>) o;
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static Class<?> getClass_Internal(String string) {
        Class<?> aClass = null;
        if (ReflectionUtils.doesClassExist(string)) {
            try {
                aClass = Class.forName(string);
            } catch (ClassNotFoundException e) {
                aClass = getNonPublicClass(string);
            }
        }

        if (aClass == null) {
            StringBuilder aClassName = new StringBuilder();
            Logger.REFLECTION("Splitting " + string + " to try look for hidden classes.");
            String[] aData = string.split("\\.");
            Logger.REFLECTION("Obtained " + aData.length + " pieces.");
            for (int i = 0; i < (aData.length - 1); i++) {
                aClassName.append((i > 0) ? "." + aData[i] : aData[i]);
                Logger.REFLECTION("Building: " + aClassName);
            }
            Logger.REFLECTION("Trying to search '" + aClassName + "' for inner classes.");
            Class<?> clazz = ReflectionUtils.getClass(aClassName.toString());

            Class<?>[] y = clazz.getDeclaredClasses();
            if (y.length == 0) {
                Logger.REFLECTION("No hidden inner classes found.");
                return null;
            } else {
                boolean found = false;
                for (Class<?> h : y) {
                    Logger.REFLECTION("Found hidden inner class: " + h.getCanonicalName());
                    if (h.getSimpleName().equalsIgnoreCase(aData[aData.length - 1])) {
                        Logger.REFLECTION(
                                "Found correct class. [" + aData[aData.length - 1]
                                        + "] Caching at correct location: "
                                        + string);
                        Logger.REFLECTION("Found at location: " + h.getCanonicalName());
                        ReflectionUtils.mCachedClasses.put(string, h);
                        aClass = h;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return null;
                }
            }
        }
        return aClass;
    }

    public static Object getFieldValue(Field field, Object instance) {
        try {
            return field.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException e) {}
        return null;
    }

    private static <V> String getDataStringFromArray(V[] parameterTypes) {
        if (parameterTypes == null || parameterTypes.length == 0) {
            return "empty/null";
        } else {
            StringBuilder aData = new StringBuilder();
            for (V y : parameterTypes) {
                if (y != null) {
                    aData.append(", ").append(y);
                }
            }
            return aData.toString();
        }
    }
}
