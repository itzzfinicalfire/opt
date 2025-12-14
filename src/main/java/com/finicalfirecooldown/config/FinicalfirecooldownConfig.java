package com.finicalfirecooldown.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration backing bean. All fields have safe defaults tuned for low-end
 * machines; values are persisted via {@link ConfigManager}.
 */
public class FinicalfirecooldownConfig {
    public enum PerformanceMode {
        LOW, MEDIUM, HIGH
    }

    public PerformanceMode performanceMode = PerformanceMode.LOW;
    public int lowEndViewDistance = 6;
    public int highEndViewDistance = 10;
    public int fpsTarget = 60;
    public int fpsFloor = 30;
    public boolean dynamicResolutionScaling = true;
    public boolean toggleVsync = false;
    public boolean enableEntityCulling = true;
    public boolean enableChunkCulling = true;
    public boolean enableAiThrottling = true;
    public boolean enableLogging = true;
    public boolean enableHud = true;
    public boolean warnOnOverclock = true;
    public Map<String, Boolean> modules = defaultModules();

    private Map<String, Boolean> defaultModules() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("rendering", true);
        map.put("entities", true);
        map.put("ai", true);
        map.put("network", true);
        map.put("background", true);
        return map;
    }
}
