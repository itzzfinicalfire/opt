package net.fabricmc.fabric.api.client.event.lifecycle.v1;

import net.minecraft.client.MinecraftClient;

public final class ClientTickEvents {
    private ClientTickEvents() {}

    public static final EndTick END_CLIENT_TICK = new EndTick();

    public static class EndTick {
        public void register(EndTickHandler handler) {
            // No-op stub
        }
    }

    @FunctionalInterface
    public interface EndTickHandler {
        void onEndTick(MinecraftClient client);
    }
}
