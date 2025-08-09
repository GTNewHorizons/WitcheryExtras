package alkalus.main.core.util;

import org.apache.logging.log4j.LogManager;

import alkalus.main.core.WitcheryExtras;

public class Logger {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(WitcheryExtras.NAME);

    public static org.apache.logging.log4j.Logger getLogger() {
        return logger;
    }

    public static void INFO(final String s) {
        logger.info(s);
    }

    public static void WARNING(final String s) {
        logger.warn(s);
    }

    public static void ERROR(final String s) {
        logger.fatal(s);
    }

    public static void REFLECTION(String string) {
        logger.info("[Reflection] " + string);
    }

    public static void ASM(String string) {
        logger.info("[ASM] " + string);
    }
}
