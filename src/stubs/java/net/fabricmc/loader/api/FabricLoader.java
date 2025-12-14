package net.fabricmc.loader.api;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FabricLoader {
    private static final FabricLoader INSTANCE = new FabricLoader();

    private FabricLoader() {}

    public static FabricLoader getInstance() {
        return INSTANCE;
    }

    public boolean isModLoaded(String id) {
        return false;
    }

    public Path getConfigDir() {
        return Paths.get("./config");
    }
}
