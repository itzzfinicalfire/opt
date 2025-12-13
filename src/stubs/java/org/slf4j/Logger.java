package org.slf4j;

/** Minimal logger that prints to stdout. */
public interface Logger {
    default void info(String msg) { System.out.println(msg); }
    default void warn(String msg) { System.out.println(msg); }
    default void debug(String msg) { System.out.println(msg); }
    default void error(String msg) { System.err.println(msg); }
}
