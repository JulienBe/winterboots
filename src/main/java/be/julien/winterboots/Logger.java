package be.julien.winterboots;

import org.slf4j.LoggerFactory;

public interface Logger {

    default void logInfo(String message) {
        LoggerFactory.getLogger(getLoggingClass()).info(message);
    }

    default void logError(Object... objects) {
        if (objects != null) {
            for (Object o : objects) {
                LoggerFactory.getLogger(getLoggingClass()).error(o.toString());
            }
        }
    }

    default Class<?> getLoggingClass() {
        return getClass();
    }

}