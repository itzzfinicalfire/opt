# Finicalfirecooldown

A Fabric-based optimization mod that ships with aggressive low-end presets,
dynamic FPS-aware scaling, and compatibility guidance for Sodium, Lithium, and
Phosphor. The mod focuses on quick wins for laptops and older desktops while
remaining modular for advanced users. The default build now targets Minecraft
**1.21.4** with Java 21, while remaining configurable for earlier releases.

## Features

- **Low-end preset on startup**: forces fast graphics, minimal particles, clouds
off, 60 FPS cap, and trims view distance to 4–6 chunks for predictable loads.
- **Dynamic performance scaling**: monitors FPS each second and raises/lowers
view distance, entity distance, V-Sync, and simple resolution scaling in
response to your hardware.
- **Entity and AI budget hints**: scales entity render distance and logs
throttling hints for packs that include Lithium or similar AI reducers.
- **Overclock warning**: warns when FPS exceeds 120 so you can lower thermals.
- **HUD feedback**: small overlay showing FPS, averages, and current performance
mode without costly graphics.
- **Benchmark snapshot**: lightweight tracker of min/avg/max FPS while the mod is
active to compare before/after behavior.
- **Compatibility notes**: detects Sodium/Lithium/Phosphor for safer co-existence
and exposes module toggles for rendering, entities, AI, network, and background
systems.

## Project layout

```
- build.gradle / settings.gradle : Fabric Loom configuration for 1.21.4 (Java 21)
- src/main/java/com/finicalfirecooldown : Mod sources
- src/main/resources/fabric.mod.json : Loader metadata
```

## Building

This repository uses Fabric Loom. Install a recent Gradle (8.x) and run:

```bash
gradle build
```

Artifacts appear in `build/libs/Finicalfirecooldown-<version>.jar`.

## Installation

1. Install Fabric Loader (0.16.7+ recommended) and Fabric API matching your
   target game version.
2. Drop the built JAR into the `mods/` directory.
3. (Optional) Add Sodium, Lithium, or Phosphor to gain low-level improvements.
4. Launch the game; a startup log entry `Finicalfirecooldown optimizations
   applied for better FPS` confirms the mod is active.

## Configuration

A JSON config file `config/finicalfirecooldown.json` is created on first run.
Key options:

- `performanceMode`: `LOW`, `MEDIUM`, `HIGH` presets for quick tuning.
- `fpsTarget` / `fpsFloor`: thresholds used by the dynamic scaler.
- `dynamicResolutionScaling`: enables the built-in GUI scaling nudge when FPS
  dips below the floor.
- `modules`: toggle individual categories (rendering, entities, ai, network,
  background) to match other optimization mods.
- `warnOnOverclock`: prompt when FPS climbs above 120.
- `enableHud`: toggle the lightweight overlay.

## Version compatibility

The code targets **Minecraft 1.21.4** with Java 21. To retarget earlier
releases (1.16–1.20.1):

- Set `minecraft_version`, `fabric_api_version`, and `fabric_loader_version`
  in `gradle.properties` (or via `-P` overrides) to the desired release pair
  and align the Fabric Loader version.
- If building for Java 8/16/17 (older versions), adjust the `java_version`
  property in `gradle.properties` and any APIs that moved between releases.
  Most logic here uses stable `GameOptions` getters that exist back to 1.16
  with minimal renaming.
- Re-run `gradle build` to produce the versioned JAR.

## Recommended system presets

- **Low-end** (integrated graphics / 4–8GB RAM): use defaults, render distance 4–6,
  V-Sync off, dynamic scaling on.
- **Mid-range** (GTX 1060+/RX 580+): raise `fpsTarget` to 90, set view distance to
  10, enable V-Sync if tearing is noticeable.
- **High-end**: set `performanceMode` to `HIGH` and disable module caps; the mod
  will slowly raise view distance and particle density when FPS is stable.

## Compatibility and coexistence

- **Sodium**: detected automatically; rendering tweaks avoid conflicting GL
  state changes and focus on options-level knobs.
- **Lithium**: AI/physics optimizations remain additive. The mod logs hints
  instead of forcing server-side changes when Lithium is present.
- **Phosphor**: lighting remains untouched; clouds and ambient occlusion settings
  are adjusted via options only.
- For other mods, disable categories in `modules` that overlap with their
  features to avoid redundant work.

## Benchmarking

The HUD exposes min/avg/max FPS observed during a session. For a repeatable
check:

1. Join a world, wait 30–60 seconds with the mod enabled.
2. Note the HUD averages, then toggle the mod (or switch to HIGH mode) and
   compare values.

## Future work

- Server-side hooks to slow redstone ticks and chunk generation frequency.
- Optional Vulkan-specific toggles when Fabric exposes a Vulkan backend.
- UI settings screen for in-game module toggles.

## License

MIT
