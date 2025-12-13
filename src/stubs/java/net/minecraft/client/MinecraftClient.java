package net.minecraft.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.GameOptions;

public class MinecraftClient {
    private static final MinecraftClient INSTANCE = new MinecraftClient();

    public final GameOptions options = new GameOptions();
    public final TextRenderer textRenderer = new TextRenderer();

    public static MinecraftClient getInstance() { return INSTANCE; }

    public boolean isPaused() { return false; }

    public int getCurrentFps() { return 60; }

    public void onResolutionChanged() { }
}
