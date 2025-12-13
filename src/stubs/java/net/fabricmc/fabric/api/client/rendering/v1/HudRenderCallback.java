package net.fabricmc.fabric.api.client.rendering.v1;

import net.minecraft.client.gui.DrawContext;

@FunctionalInterface
public interface HudRenderCallback {
    void onHudRender(DrawContext drawContext, float tickDelta);

    Event EVENT = new Event();

    class Event {
        public void register(HudRenderCallback callback) {
            // No-op stub
        }
    }
}
