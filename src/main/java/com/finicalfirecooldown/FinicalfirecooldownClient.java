package com.finicalfirecooldown;

import com.finicalfirecooldown.benchmark.PerformanceBenchmark;
import com.finicalfirecooldown.config.ConfigManager;
import com.finicalfirecooldown.config.FinicalfirecooldownConfig;
import com.finicalfirecooldown.performance.PerformanceController;
import com.finicalfirecooldown.ui.PerformanceHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the client-side mod. The implementation is intentionally
 * defensive: it applies a low-end preset on launch and then hands off control to
 * {@link PerformanceController} for dynamic scaling based on the player's FPS
 * and hardware constraints.
 */
public class FinicalfirecooldownClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("finicalfirecooldown");
    private final ConfigManager configManager = new ConfigManager();
    private PerformanceController performanceController;
    private PerformanceBenchmark benchmark;

    @Override
    public void onInitializeClient() {
        FinicalfirecooldownConfig config = configManager.load();
        performanceController = new PerformanceController(config, configManager);
        benchmark = new PerformanceBenchmark();

        // Apply presets once at startup to minimize load spikes on low-end PCs.
        performanceController.applyLowEndPreset();
        LOGGER.info("Finicalfirecooldown optimizations applied for better FPS.");

        HudRenderCallback.EVENT.register(new PerformanceHudOverlay(performanceController, benchmark, config));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            performanceController.onClientTick(client);
            benchmark.onTick(client);
        });

        // Minimal compatibility check logging. The code does not hard depend on these mods
        // but surfaces their presence for debugging clarity.
        checkOptionalMods();

        // Friendly in-chat reminder for new players that presets are active.
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> benchmark.trackMessage(message.getString()));
    }

    private void checkOptionalMods() {
        if (FabricLoader.getInstance().isModLoaded("sodium")) {
            LOGGER.info("Sodium detected: advanced rendering optimizations will cooperate with it.");
        }
        if (FabricLoader.getInstance().isModLoaded("lithium")) {
            LOGGER.info("Lithium detected: entity and AI optimizations will remain compatible.");
        }
        if (FabricLoader.getInstance().isModLoaded("phosphor")) {
            LOGGER.info("Phosphor detected: lighting tweaks will respect existing enhancements.");
        }
    }
}
