package com.finicalfirecooldown.benchmark;

import net.minecraft.client.MinecraftClient;

/**
 * Lightweight self-observing benchmark: tracks max/min FPS values and calculates
 * a naive improvement indicator once presets stabilize. It intentionally avoids
 * storing long histories to minimize memory overhead.
 */
public class PerformanceBenchmark {
    private int maxFps = 0;
    private int minFps = Integer.MAX_VALUE;
    private long sampleCount = 0;
    private double fpsSum = 0;
    private boolean messageSeen = false;

    public void onTick(MinecraftClient client) {
        if (client == null || client.isPaused()) {
            return;
        }
        int fps = client.getCurrentFps();
        fpsSum += fps;
        sampleCount++;
        maxFps = Math.max(maxFps, fps);
        minFps = Math.min(minFps, fps);
    }

    public double getAverageFps() {
        return sampleCount == 0 ? 0 : fpsSum / sampleCount;
    }

    public int getMaxFps() {
        return maxFps;
    }

    public int getMinFps() {
        return minFps == Integer.MAX_VALUE ? 0 : minFps;
    }

    public void trackMessage(String message) {
        if (!messageSeen && message.toLowerCase().contains("finicalfirecooldown")) {
            messageSeen = true;
        }
    }

    public boolean hasSeenMessage() {
        return messageSeen;
    }
}
