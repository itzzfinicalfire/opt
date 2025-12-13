package com.finicalfirecooldown.performance;

import com.finicalfirecooldown.FinicalfirecooldownClient;
import com.finicalfirecooldown.config.ConfigManager;
import com.finicalfirecooldown.config.FinicalfirecooldownConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.util.Window;

/**
 * Applies the core performance strategy: a low-end preset on launch and a
 * feedback loop that gently scales quality up or down depending on real-time
 * FPS. The logic intentionally avoids aggressive oscillations to prevent visual
 * stutter.
 */
public class PerformanceController {
    private final FinicalfirecooldownConfig config;
    private final ConfigManager configManager;
    private int tickCounter = 0;
    private float lastScale = 1.0f;
    private boolean warnedOverclock = false;

    public PerformanceController(FinicalfirecooldownConfig config, ConfigManager configManager) {
        this.config = config;
        this.configManager = configManager;
    }

    public void onClientTick(MinecraftClient client) {
        if (client == null || client.isPaused()) {
            return;
        }
        tickCounter++;
        if (tickCounter % 20 != 0) {
            return; // adjust roughly once per second to avoid thrashing
        }

        int fps = client.getCurrentFps();
        GameOptions options = client.options;

        if (config.modules.getOrDefault("rendering", true)) {
            dynamicRenderAdjustments(options, fps);
        }
        if (config.modules.getOrDefault("entities", true)) {
            applyEntityBudget(options, fps);
        }
        if (config.modules.getOrDefault("ai", true)) {
            throttleAiIfNeeded(fps);
        }
        if (config.warnOnOverclock && fps > 120 && !warnedOverclock) {
            FinicalfirecooldownClient.LOGGER.warn("FPS above 120 detected. Consider capping for thermals.");
            warnedOverclock = true;
        }
    }

    /**
     * Applies the starting preset focused on low-end hardware.
     */
    public void applyLowEndPreset() {
        MinecraftClient client = MinecraftClient.getInstance();
        GameOptions options = client.options;
        options.getViewDistance().setValue(Math.min(config.lowEndViewDistance, 6));
        options.getGraphicsMode().setValue(GraphicsMode.FAST);
        options.getParticles().setValue(ParticlesMode.MINIMAL);
        options.getCloudRenderMode().setValue(GameOptions.CloudRenderMode.OFF);
        options.getVsync().setValue(config.toggleVsync ? false : options.getVsync().getValue());
        options.getFpsLimit().setValue(config.fpsTarget);
        options.getEntityDistanceScaling().setValue(0.5D);
        options.getAo().setValue(0);
        options.write();
    }

    private void dynamicRenderAdjustments(GameOptions options, int fps) {
        int floor = config.fpsFloor;
        int target = config.fpsTarget;
        int currentView = options.getViewDistance().getValue();
        int desiredView = currentView;

        if (fps < floor) {
            desiredView = Math.max(4, currentView - 2);
            options.getParticles().setValue(ParticlesMode.MINIMAL);
            options.getCloudRenderMode().setValue(GameOptions.CloudRenderMode.OFF);
            options.getGraphicsMode().setValue(GraphicsMode.FAST);
            options.getEntityDistanceScaling().setValue(0.5D);
            toggleResolution(options, fps, 0.85f);
        } else if (fps < target) {
            desiredView = Math.max(5, currentView - 1);
            toggleResolution(options, fps, 0.95f);
        } else {
            desiredView = Math.min(config.highEndViewDistance, currentView + 1);
            toggleResolution(options, fps, 1.05f);
            options.getParticles().setValue(ParticlesMode.DECREASED);
        }

        if (desiredView != currentView) {
            options.getViewDistance().setValue(desiredView);
        }
        options.write();
    }

    private void toggleResolution(GameOptions options, int fps, float scaleStep) {
        if (!config.dynamicResolutionScaling) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = client.getWindow();
        double scale = window.getScaleFactor();
        if (fps < config.fpsFloor) {
            scale = Math.max(0.75f, scale * scaleStep);
        } else if (fps > config.fpsTarget && scale < window.calculateScaleFactor(client.options.getGuiScale().getValue(), client.forcesUnicodeFont())) {
            scale = Math.min(scale * scaleStep, 2.0f);
        }
        if (Math.abs(scale - lastScale) > 0.05f) {
            window.setScaleFactor(scale);
            lastScale = (float) scale;
        }
    }

    private void applyEntityBudget(GameOptions options, int fps) {
        double baseScale = fps < config.fpsFloor ? 0.5D : fps < config.fpsTarget ? 0.75D : 1.0D;
        options.getEntityDistanceScaling().setValue(baseScale);
        options.write();
    }

    private void throttleAiIfNeeded(int fps) {
        // AI throttling hooks would normally live server-side. For client safety we
        // simply log intent so pack makers know where to extend the mod.
        if (fps < config.fpsFloor && config.enableLogging) {
            FinicalfirecooldownClient.LOGGER.debug("AI throttling hint: consider Lithium/AI-reduction compat modules.");
        }
    }

    public int getTickCounter() {
        return tickCounter;
    }
}
