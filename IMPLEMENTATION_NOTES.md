# Prestige Client - Implementation Notes

## Overview
This document provides technical implementation details and guidance for extending Prestige Client.

## Architecture Overview

### Three-Tier Design

1. **Module Layer** - Individual features
2. **Manager Layer** - Orchestration
3. **Utility Layer** - Shared algorithms

### Data Flow

```
Event → EventBus → Module.onEvent() → Action/State Change
```

## Core Components

### 1. Event System

**Location**: `com.prestige.client.event`

The event system uses a simple pub-sub pattern:
- `EventBus` maintains listeners per event type
- Modules subscribe in constructor
- Events post synchronously each tick

**Key Events**:
- `UpdateEvent` - Game tick (20Hz)
- `RenderEvent` - Frame render (60Hz)
- `KeyPressEvent` - Keyboard input

**Adding Custom Events**:
```java
public class MyEvent extends Event {
    private final Object data;
    
    public MyEvent(Object data) {
        this.data = data;
    }
    
    public Object getData() { return data; }
}
```

**Posting Events**:
```java
EventBus.post(new MyEvent(data));
```

**Subscribing**:
```java
EventBus.subscribe(MyEvent.class, event -> {
    // Handle event
});
```

### 2. Module System

**Location**: `com.prestige.client.module`

Each module is a self-contained feature with:
- Settings (configurable parameters)
- Enable/disable lifecycle
- Event subscriptions
- State management

**Base Module Structure**:
```java
public class MyModule extends Module {
    private Setting<Integer> range = new Setting<>("Range", "...", 10);
    
    public MyModule() {
        super("MyModule", "Description", ModuleCategory.COMBAT);
        registerSetting(range);
        EventBus.subscribe(UpdateEvent.class, this::onUpdate);
    }
    
    @Override
    public void onEnable() {
        // Initialization
    }
    
    @Override
    public void onDisable() {
        // Cleanup
    }
    
    private void onUpdate(Event event) {
        if (!isEnabled()) return;
        // Implementation
    }
}
```

**Settings**:
- Type-safe: `Setting<T>`
- Range support: `setRange(min, max)`
- Automatic GUI rendering
- JSON serialization

### 3. Manager System

**Location**: `com.prestige.client.manager`

#### ModuleManager
Manages module lifecycle:
- Registration of all modules
- Retrieval by name/category
- Enable/disable tracking

```java
// Get module by name
Module mod = PrestigeClient.MODULE_MANAGER.getModule("CrystalAura");

// Get all modules in category
List<Module> combat = PrestigeClient.MODULE_MANAGER
    .getModulesByCategory(ModuleCategory.COMBAT);

// Get enabled modules
List<Module> active = PrestigeClient.MODULE_MANAGER.getEnabledModules();
```

#### EventManager
Integrates Fabric API events:
- Client tick events
- Key binding registration
- Event listener setup

#### ConfigManager
Loads/saves JSON configuration:
```java
// Load from disk
CONFIG_MANAGER.loadConfig();

// Get value
Integer value = CONFIG_MANAGER.get("key", 10);

// Set value
CONFIG_MANAGER.set("key", 20);

// Save to disk
CONFIG_MANAGER.saveConfig();
```

### 4. Utility System

**Location**: `com.prestige.client.util`

Reusable algorithms for:
- Rotation calculations
- Target finding
- Crystal detection

#### RotationUtil
```java
// Calculate angles to entity
float[] angles = RotationUtil.calculateAngles(entity);
float yaw = angles[0];
float pitch = angles[1];

// Smooth rotation
float newYaw = RotationUtil.smooth(currentYaw, targetYaw, speed);

// Check if close
boolean close = RotationUtil.isRotationClose(currentYaw, currentPitch, 
    targetYaw, targetPitch, tolerance);
```

#### TargetUtil
```java
// Get nearby players
List<PlayerEntity> players = TargetUtil.getNearbyPlayers(range);

// Get nearest player
Optional<PlayerEntity> target = TargetUtil.getNearestPlayer(range);

// Get weakest player
Optional<PlayerEntity> weak = TargetUtil.getLowestHealthPlayer(range);

// Check if in view
boolean visible = TargetUtil.isInView(entity);
```

#### CrystalUtil
```java
// Find nearby crystals
List<EndCrystalEntity> crystals = CrystalUtil.getNearByCrystals(range);

// Find valid placements
List<BlockPos> spots = CrystalUtil.findValidPlacements(pos, radius);

// Check if valid placement
boolean valid = CrystalUtil.isValidCrystalPlacement(blockPos);

// Calculate damage
float damage = CrystalUtil.calculateDamage(crystalPos, player);
```

### 5. GUI System

**Location**: `com.prestige.client.gui`

#### ClickGUI
Main GUI window:
- Category tabs for organization
- Module list with toggles
- Setting sliders and inputs
- Drag support

**Features**:
- Modern dark theme
- Responsive layout
- Quick category switching
- Scrollable module list

#### ModulePanel
Individual module widget:
- Toggle on/off
- Expand settings
- Visual feedback
- Settings sliders

## Implementation Patterns

### Pattern 1: Event-Driven Modules

**When to use**: Features that react to game events

```java
public class EventDrivenModule extends Module {
    public EventDrivenModule() {
        super("EventDriven", "...", ModuleCategory.COMBAT);
        EventBus.subscribe(UpdateEvent.class, this::onTick);
    }
    
    private void onTick(Event event) {
        if (!isEnabled()) return;
        
        // React to tick
        performAction();
    }
}
```

### Pattern 2: Polling Modules

**When to use**: Continuous monitoring

```java
public class PollingModule extends Module {
    private int ticks = 0;
    
    public PollingModule() {
        super("Polling", "...", ModuleCategory.COMBAT);
        EventBus.subscribe(UpdateEvent.class, this::onUpdate);
    }
    
    private void onUpdate(Event event) {
        if (!isEnabled()) return;
        
        ticks++;
        if (ticks >= frequency) {
            checkCondition();
            ticks = 0;
        }
    }
}
```

### Pattern 3: State Management

**When to use**: Modules with complex state

```java
public class StatefulModule extends Module {
    private enum State { IDLE, ACTIVE, COOLDOWN }
    
    private State state = State.IDLE;
    private int cooldownTicks = 0;
    
    public StatefulModule() {
        super("Stateful", "...", ModuleCategory.COMBAT);
        EventBus.subscribe(UpdateEvent.class, this::onUpdate);
    }
    
    private void onUpdate(Event event) {
        if (!isEnabled()) return;
        
        switch (state) {
            case IDLE -> checkForActivation();
            case ACTIVE -> performAction();
            case COOLDOWN -> {
                cooldownTicks--;
                if (cooldownTicks <= 0) state = State.IDLE;
            }
        }
    }
}
```

## Common Tasks

### Task 1: Create Combat Module

1. **Create class**:
```java
public class MyAura extends Module {
    private Setting<Double> range = new Setting<>("Range", "...", 5.0);
    private Setting<Integer> cps = new Setting<>("CPS", "...", 15);
    private int ticksSinceAttack = 0;
    
    public MyAura() {
        super("MyAura", "Auto-attack", ModuleCategory.COMBAT);
        registerSetting(range);
        registerSetting(cps);
        EventBus.subscribe(UpdateEvent.class, this::onUpdate);
    }
    
    private void onUpdate(Event event) {
        if (!isEnabled()) return;
        ticksSinceAttack++;
        
        // Find target
        var target = TargetUtil.getNearestPlayer(range.getValue());
        if (target.isEmpty()) return;
        
        // Attack logic
        int delay = 20 / cps.getValue();
        if (ticksSinceAttack >= delay) {
            performAttack(target.get());
            ticksSinceAttack = 0;
        }
    }
    
    private void performAttack(PlayerEntity target) {
        // Implementation
    }
}
```

2. **Register in ModuleManager**:
```java
registerModule(new MyAura());
```

3. **Module appears in GUI immediately**

### Task 2: Add Rendering

1. **Listen to RenderEvent**:
```java
EventBus.subscribe(RenderEvent.class, this::onRender);
```

2. **Implement rendering**:
```java
private void onRender(Event event) {
    RenderEvent render = (RenderEvent) event;
    // Use DrawContext for rendering
}
```

### Task 3: Access Configuration

1. **Get value**:
```java
Integer freq = PrestigeClient.CONFIG_MANAGER.get("frequency", 20);
```

2. **Set value**:
```java
PrestigeClient.CONFIG_MANAGER.set("frequency", 25);
PrestigeClient.CONFIG_MANAGER.saveConfig();
```

## Performance Optimization

### 1. Reduce Event Processing
```java
private int skipTicks = 0;

private void onUpdate(Event event) {
    skipTicks++;
    if (skipTicks % 5 != 0) return; // Process every 5th tick
    
    // Heavy operation
}
```

### 2. Cache Results
```java
private List<PlayerEntity> cachedPlayers;
private int cacheAge = 0;

private List<PlayerEntity> getPlayers() {
    if (cacheAge++ > 5) {
        cachedPlayers = TargetUtil.getNearbyPlayers(range);
        cacheAge = 0;
    }
    return cachedPlayers;
}
```

### 3. Early Exit
```java
private void onUpdate(Event event) {
    if (!isEnabled()) return; // Don't process if disabled
    if (client.player == null) return; // Null check
    if (client.world == null) return; // Null check
    
    // Main logic
}
```

### 4. Limit Search Radius
```java
// ❌ Bad
var allPlayers = TargetUtil.getNearbyPlayers(256.0);

// ✅ Good
var nearbyPlayers = TargetUtil.getNearbyPlayers(32.0);
```

## Debugging

### 1. Logging
```java
PrestigeClient.LOGGER.info("Message");
PrestigeClient.LOGGER.error("Error", exception);
```

### 2. Chat Messages
```java
client.inGameHud.getChatHud().addMessage(Text.literal("Debug"));
```

### 3. Console Output
```java
System.out.println("Debug: " + value);
```

## Best Practices

### 1. Module Design
- Single responsibility
- Clear, descriptive names
- Comprehensive settings
- Proper enable/disable hooks

### 2. Settings
- Provide sensible defaults
- Include min/max ranges
- Clear descriptions
- Meaningful names

### 3. Event Handling
- Always check `isEnabled()` first
- Null check client/world
- Avoid blocking operations
- Clean up on disable

### 4. Performance
- Cache where possible
- Limit search radius
- Batch operations
- Profile critical sections

### 5. Code Quality
- Meaningful variable names
- Clear comments
- Proper error handling
- Consistent formatting

## Extending Prestige Client

### 1. Custom Modules
Create in `modules/<category>/` following existing patterns

### 2. Custom Events
Create in `event/events/` and post from appropriate hooks

### 3. Custom Utilities
Create in `util/` for shared algorithms

### 4. Custom GUI Elements
Create in `gui/` for custom screens

### 5. Custom Settings Types
Extend `Setting<T>` for specific types

## Common Issues & Solutions

### Issue: Module not appearing in GUI
**Solution**: Ensure registered in `ModuleManager.registerModules()`

### Issue: Settings not saving
**Solution**: Call `ConfigManager.saveConfig()` after changes

### Issue: Event not firing
**Solution**: Check event is posted from correct location, verify subscription

### Issue: Performance drops
**Solution**: Profile with JFR, reduce update frequency, cache results

### Issue: Null pointer exceptions
**Solution**: Add null checks for client, world, player

---

For questions or contributions, refer to README.md and PROJECT_STRUCTURE.md
