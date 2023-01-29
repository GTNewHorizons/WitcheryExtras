package alkalus.main.core.util;

import org.apache.logging.log4j.LogManager;

import alkalus.main.core.WitcheryExtras;

public class Logger {

    public static final org.apache.logging.log4j.Logger modLogger = Logger.makeLogger();

    public static org.apache.logging.log4j.Logger makeLogger() {
        final org.apache.logging.log4j.Logger loggers = LogManager.getLogger(WitcheryExtras.NAME);
        return loggers;
    }

    public static final org.apache.logging.log4j.Logger getLogger() {
        return modLogger;
    }

    public void INFO(final String s) {
        modLogger.info(s);
    }

    public void WARNING(final String s) {
        modLogger.warn(s);
    }

    public void ERROR(final String s) {
        modLogger.fatal(s);
    }

    public static void REFLECTION(String string) {
        modLogger.info("[Reflection] " + string);
    }

    public static void ASM(String string) {
        modLogger.info("[ASM] " + string);
    }
}
