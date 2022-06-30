package engine.core;

import org.apache.logging.log4j.LogManager;

public class Logger {

    private org.apache.logging.log4j.Logger mLogger;

    private Logger(final String moduleName) {
        mLogger = LogManager.getLogger(moduleName);
    }

    /**
     * Create a Logger object given the module name.
     *
     * @param moduleName module name.
     */
    public static Logger create(final String moduleName) {
        return new Logger(moduleName);
    }

    /**
     * Logs a message with `trace` level.
     *
     * @param message message to be logged.
     */
    public void trace(final String message) {
        mLogger.trace(message);
    }

    /**
     * Logs a message with `debug` level.
     *
     * @param message message to be logged.
     */
    public void debug(final String message) {
        mLogger.debug(message);
    }

    /**
     * Logs a message with `info` level.
     *
     * @param message message to be logged.
     */
    public void info(final String message) {
        mLogger.info(message);
    }

    /**
     * Logs a message with `warning` level.
     *
     * @param message message to be logged.
     */
    public void warn(final String message) {
        mLogger.warn(message);
    }

    /**
     * Logs a message with `error` level.
     *
     * @param message message to be logged.
     */
    public void error(final String message) {
        mLogger.error(message);
    }
}
