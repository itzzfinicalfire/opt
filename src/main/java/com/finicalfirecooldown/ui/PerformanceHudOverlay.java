package com.finicalfirecooldown.ui;

import com.finicalfirecooldown.benchmark.PerformanceBenchmark;
import com.finicalfirecooldown.config.FinicalfirecooldownConfig;
import com.finicalfirecooldown.performance.PerformanceController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

/**
 * Heads-up overlay that surfaces current performance mode, FPS, and a simple
 * benchmarking indicator. Everything here is lightweight text rendering to
 * avoid extra GPU/CPU cost.
 */
public class PerformanceHudOverlay implements HudRenderCallback {
    private final PerformanceController controller;
    private final PerformanceBenchmark benchmark;
    private final FinicalfirecooldownConfig config;

    public PerformanceHudOverlay(PerformanceController controller, PerformanceBenchmark benchmark, FinicalfirecooldownConfig config) {
        this.controller = controller;
        this.benchmark = benchmark;
        this.config = config;
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (!config.enableHud) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer renderer = client.textRenderer;

        int fps = client.getCurrentFps();
        int y = 4;
        drawContext.drawText(renderer, Text.literal("Finicalfirecooldown: " + config.performanceMode.name()), 4, y, 0x00FF88, true);
        y += 10;
        drawContext.drawText(renderer, Text.literal("FPS: " + fps + " (min " + benchmark.getMinFps() + ", max " + benchmark.getMaxFps() + ")"), 4, y, 0xFFFFFF, true);
        y += 10;
        drawContext.drawText(renderer, Text.literal(String.format("Avg FPS: %.1f", benchmark.getAverageFps())), 4, y, 0xFFFFFF, true);
        y += 10;
        drawContext.drawText(renderer, Text.literal("Dynamic scaling: " + (config.dynamicResolutionScaling ? "ON" : "OFF")), 4, y, 0xAAAAAA, true);
        y += 10;
        drawContext.drawText(renderer, Text.literal("Modules: " + config.modules), 4, y, 0xAAAAAA, true);
    }
}
