package net.minecraft.client.option;

public class GameOptions {
    public enum CloudRenderMode { OFF, FAST, FANCY }

    public static class Option<T> {
        private T value;
        public Option(T value) { this.value = value; }
        public T getValue() { return value; }
        public void setValue(T value) { this.value = value; }
    }

    private final Option<Integer> viewDistance = new Option<>(8);
    private final Option<GraphicsMode> graphicsMode = new Option<>(GraphicsMode.FAST);
    private final Option<ParticlesMode> particles = new Option<>(ParticlesMode.MINIMAL);
    private final Option<CloudRenderMode> cloudRenderMode = new Option<>(CloudRenderMode.OFF);
    private final Option<Boolean> vsync = new Option<>(false);
    private final Option<Integer> fpsLimit = new Option<>(60);
    private final Option<Double> entityDistanceScaling = new Option<>(1.0d);
    private final Option<Integer> ao = new Option<>(0);
    private final Option<Integer> guiScale = new Option<>(2);

    public Option<Integer> getViewDistance() { return viewDistance; }
    public Option<GraphicsMode> getGraphicsMode() { return graphicsMode; }
    public Option<ParticlesMode> getParticles() { return particles; }
    public Option<CloudRenderMode> getCloudRenderMode() { return cloudRenderMode; }
    public Option<Boolean> getVsync() { return vsync; }
    public Option<Integer> getFpsLimit() { return fpsLimit; }
    public Option<Double> getEntityDistanceScaling() { return entityDistanceScaling; }
    public Option<Integer> getAo() { return ao; }
    public Option<Integer> getGuiScale() { return guiScale; }

    public void write() {
        // No-op stub for persisting options
    }
}
