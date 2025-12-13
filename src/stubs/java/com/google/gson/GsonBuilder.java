package com.google.gson;

/** Minimal stub of GsonBuilder to allow offline compilation. */
public class GsonBuilder {
    public GsonBuilder setPrettyPrinting() {
        return this;
    }

    public Gson create() {
        return new Gson();
    }
}
