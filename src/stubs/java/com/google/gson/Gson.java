package com.google.gson;

import java.io.Reader;
import java.io.Writer;

/** Minimal stub of Gson to allow offline compilation. */
public class Gson {
    public <T> T fromJson(Reader reader, Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void toJson(Object src, Writer writer) {
        // No-op stub.
    }
}
