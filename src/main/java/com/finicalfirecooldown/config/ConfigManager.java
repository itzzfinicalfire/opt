package com.finicalfirecooldown.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Simple JSON backed configuration. The file is intentionally small so it can
 * be safely read during startup without causing additional GC pressure.
 */
public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "finicalfirecooldown.json";

    public FinicalfirecooldownConfig load() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = configDir.resolve(FILE_NAME);
        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file)) {
                return GSON.fromJson(reader, FinicalfirecooldownConfig.class);
            } catch (IOException | JsonSyntaxException ex) {
                // Fall back to defaults on any issue and write a fresh copy.
                FinicalfirecooldownConfig config = new FinicalfirecooldownConfig();
                save(config);
                return config;
            }
        }
        FinicalfirecooldownConfig config = new FinicalfirecooldownConfig();
        save(config);
        return config;
    }

    public void save(FinicalfirecooldownConfig config) {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = configDir.resolve(FILE_NAME);
        try {
            Files.createDirectories(configDir);
            try (Writer writer = Files.newBufferedWriter(file)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException ex) {
            // Logging omitted here to avoid recursive logging initialization issues.
            ex.printStackTrace();
        }
    }
}
