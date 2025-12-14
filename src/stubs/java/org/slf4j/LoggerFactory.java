package org.slf4j;

/** Minimal LoggerFactory. */
public final class LoggerFactory {
    private LoggerFactory() {}

    public static Logger getLogger(String name) {
        return new Logger() {};
    }
}
