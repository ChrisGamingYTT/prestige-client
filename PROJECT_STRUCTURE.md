# Prestige Client - Project Structure

## Directory Layout

```
prestige-client/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/prestige/client/
│       │       ├── PrestigeClient.java          # Main mod entry point
│       │       ├── event/
│       │       │   ├── Event.java               # Base event class
│       │       │   ├── EventBus.java            # Event management system
│       │       │   └── events/
│       │       │       ├── UpdateEvent.java     # Tick update event
│       │       │       ├── RenderEvent.java     # Render event
│       │       │       ├── KeyPressEvent.java   # Keyboard input
│       │       │       ├── AttackEntityEvent.java
│       │       │       └── InteractBlockEvent.java
│       │       ├── module/
│       │       │   ├── Module.java              # Base module class
│       │       │   └── ModuleCategory.java      # Module categories enum
│       │       ├── modules/
│       │       │   ├── crystal/
│       │       │   │   ├── CrystalAura.java
│       │       │   │   ├── CrystalAttack.java
│       │       │   │   ├── CrystalSpeed.java
│       │       │   │   ├── CrystalESP.java
│       │       │   │   └── CrystalDefense.java
│       │       │   ├── combat/
│       │       │   │   ├── KillAura.java
│       │       │   │   ├── Rotation.java
│       │       │   │   ├── TargetAssist.java
│       │       │   │   └── AutoClicker.java
│       │       │   ├── defense/
│       │       │   │   ├── AutoHeal.java
│       │       │   │   ├── Velocity.java
│       │       │   │   ├── AntiVoid.java
│       │       │   │   └── NoFall.java
│       │       │   ├── movement/
│       │       │   │   ├── Sprint.java
│       │       │   │   ├── Strafe.java
│       │       │   │   ├── BunnyHop.java
│       │       │   │   ├── FastBridge.java
│       │       │   │   └── AutoWalk.java
│       │       │   ├── render/
│       │       │   │   ├── PlayerESP.java
│       │       │   │   ├── CrystalRender.java
│       │       │   │   ├── Tracers.java
│       │       │   │   ├── Nametags.java
│       │       │   │   └── FullBright.java
│       │       │   ├── awareness/
│       │       │   │   ├── CombatHUD.java
│       │       │   │   ├── Radar.java
│       │       │   │   ├── EntityAlert.java
│       │       │   │   └── Notifications.java
│       │       │   └── utility/
│       │       │       ├── Coordinates.java
│       │       │       ├── AutoDrop.java
│       │       │       └── AutoTool.java
│       │       ├── manager/
│       │       │   ├── ModuleManager.java      # Module registration & retrieval
│       │       │   ├── EventManager.java       # Event listener setup
│       │       │   └── ConfigManager.java      # Configuration I/O
│       │       ├── setting/
│       │       │   └── Setting.java            # Module setting wrapper
│       │       ├── gui/
│       │       │   ├── ClickGUI.java           # Main GUI screen
│       │       │   └── ModulePanel.java        # Individual module panel
│       │       ├── util/
│       │       │   ├── RotationUtil.java       # Rotation calculations
│       │       │   ├── TargetUtil.java         # Target finding & filtering
│       │       │   └── CrystalUtil.java        # Crystal detection & placement
│       │       └── mixin/
│       │           ├── ClientPlayerMixin.java
│       │           ├── GameRendererMixin.java
│       │           └── WorldRendererMixin.java
│       └── resources/
│           ├── fabric.mod.json                # Fabric mod metadata
│           └── prestige.mixins.json           # Mixin configuration
├── build.gradle                               # Gradle build config
├── gradle.properties                          # Gradle properties
├── .gitignore
├── README.md                                  # Main documentation
├── PROJECT_STRUCTURE.md                       # This file
├── settings.example.json                      # Example configuration
└── LICENSE

```

## Core Systems

### Event System (`event/`)
- **EventBus**: Central event dispatcher
- **Event**: Base event class for all events
- **Events**: Specific event types (Update, Render, Input, etc.)

### Module System (`module/`)
- **Module**: Abstract base class for all modules
- **ModuleCategory**: Enum for organizing modules
- **Modules**: 30+ concrete module implementations

### Manager System (`manager/`)
- **ModuleManager**: Registers and retrieves modules, manages categories
- **EventManager**: Integrates with Fabric API events
- **ConfigManager**: Loads/saves JSON configuration

### Utility System (`util/`)
- **RotationUtil**: Smooth rotation calculations, angle normalization
- **TargetUtil**: Player detection, filtering, distance checks
- **CrystalUtil**: Crystal detection, placement validation, damage calculation

### GUI System (`gui/`)
- **ClickGUI**: Main GUI window with category tabs
- **ModulePanel**: Individual module toggle and settings display

## Design Patterns

### 1. Event-Driven Architecture
Modules subscribe to events and respond asynchronously:
```java
EventBus.subscribe(UpdateEvent.class, this::onUpdate);
```

### 2. Module Pattern
Each module is self-contained with:
- Settings management
- Enable/disable hooks
- Event listeners

### 3. Manager Pattern
Centralized management of:
- Modules (registration, retrieval, categorization)
- Events (subscription, dispatch)
- Configuration (loading, saving)

### 4. Utility Pattern
Static utility classes for:
- Rotation calculations
- Target detection
- Crystal mechanics

## Build System

### Gradle Configuration
- **Build Tool**: Gradle with Fabric Loom
- **Java Version**: 21
- **Dependencies**:
  - Fabric API 0.90.7+
  - Fabric Loader 0.14.21+
  - Minecraft 1.20.1

### Build Output
- **Location**: `build/libs/prestige-client-1.0.0.jar`
- **Size**: ~500KB (varies with dependencies)

## Configuration System

### File Location
```
.minecraft/config/prestige-client/settings.json
```

### Structure
```json
{
  "ModuleName": {
    "enabled": true,
    "SettingName": value
  }
}
```

## Adding New Features

### Adding a New Module

1. Create new class in appropriate category:
```java
public class MyModule extends Module {
    public MyModule() {
        super("MyModule", "Description", ModuleCategory.COMBAT);
        registerSetting(new Setting<>("Range", "Range", 10));
    }
}
```

2. Register in `ModuleManager.registerModules()`:
```java
registerModule(new MyModule());
```

3. Module automatically appears in GUI

### Adding New Events

1. Create event class extending `Event`:
```java
public class MyEvent extends Event {
    // Event data
}
```

2. Post event from appropriate location:
```java
EventBus.post(new MyEvent());
```

3. Subscribe in modules:
```java
EventBus.subscribe(MyEvent.class, this::onMyEvent);
```

### Adding Utility Functions

1. Create static method in `RotationUtil`, `TargetUtil`, or `CrystalUtil`
2. Use across modules
3. Keep utilities focused and reusable

## Performance Considerations

### Module Updates
- Update events fire once per tick (20Hz)
- Modules should complete quickly (<1ms)
- Heavy calculations deferred to async tasks

### Rendering
- Render events fire every frame (60fps)
- Use batch rendering for efficiency
- Minimize draw calls

### Memory Usage
- Target <150MB additional RAM
- Module lazy-loading not needed (low count)
- Configuration cached in memory

## Testing

### Local Testing
1. Run gradle task: `./gradlew runClient`
2. Client starts with mod loaded
3. Open GUI with Right Shift
4. Enable modules and test features

### Debug Mode
Enable via gradle property:
```properties
minecraft.debug=true
```

## Common Tasks

### Build Mod
```bash
./gradlew build
```

### Run in Development
```bash
./gradlew runClient
```

### Clean Build
```bash
./gradlew clean build
```

### Update Dependencies
```bash
./gradlew fetchMappings
```

## Troubleshooting

### Compilation Errors
1. Check Java version: `java -version`
2. Verify Gradle cache: `./gradlew clean`
3. Update IDE mappings: `./gradlew genSourcesWithVineflower`

### Mod Not Loading
1. Check fabric.mod.json is valid
2. Verify all dependencies installed
3. Check logs for error messages

### GUI Not Rendering
1. Ensure ClickGUI instantiated in PrestigeClient
2. Check keybinding registered
3. Verify screen not being overridden

---

For detailed information on specific systems, see README.md
