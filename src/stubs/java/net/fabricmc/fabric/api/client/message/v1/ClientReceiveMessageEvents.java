package net.fabricmc.fabric.api.client.message.v1;

public final class ClientReceiveMessageEvents {
    private ClientReceiveMessageEvents() {}

    public static final Game GAME = new Game();

    public static class Game {
        public void register(GameMessageHandler handler) {
            // No-op stub
        }
    }

    @FunctionalInterface
    public interface GameMessageHandler {
        void onMessage(net.minecraft.text.Text message, boolean overlay);
    }
}
