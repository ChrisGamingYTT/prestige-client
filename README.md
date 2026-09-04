# Prestige Client - Crystal PvP Minecraft Mod

A professional Fabric-based utility client for Minecraft 1.20.x, optimized for Crystal PvP gameplay.

## Features

Prestige Client includes **30+ modules** across 7 categories, specifically designed for competitive Crystal PvP combat.

### Crystal Modules
- **CrystalAura** - Automatically place End Crystals on nearby players with smart placement detection
- **CrystalAttack** - Auto-attack placed crystals with configurable speed
- **CrystalSpeed** - Speed boost while placing/attacking crystals
- **CrystalESP** - Highlight crystals with health, distance, and explosion radius
- **CrystalDefense** - Predict and avoid incoming crystal damage

### Combat Modules
- **KillAura** - Auto-attack nearby players with configurable range and CPS
- **Rotation** - Smooth head rotation system with customizable speed
- **TargetAssist** - Intelligent target selection and switching
- **AutoClicker** - Automatic mouse clicking with CPS control

### Defense Modules
- **AutoHeal** - Automatically eat gapples when health drops
- **Velocity** - Reduce knockback from hits and crystals
- **AntiVoid** - Prevent falling into the void with jump/block placement
- **NoFall** - Eliminate fall damage

### Movement Modules
- **Sprint** - Auto-sprint in configured directions
- **Strafe** - Enhanced air movement control for evasion
- **BunnyHop** - Momentum-based jumping for speed
- **FastBridge** - Quick bridge building for escape
- **AutoWalk** - Continue moving in selected direction

### Render Modules
- **PlayerESP** - Highlight players with health and distance info
- **CrystalRender** - Custom crystal visualization with explosion radius
- **Tracers** - Draw lines to players and crystals
- **Nametags** - Enhanced nametag rendering with health info
- **FullBright** - Brightness enhancement for low-light areas

### Awareness Modules
- **CombatHUD** - Display combat information (target, nearby players, health)
- **Radar** - Minimap showing nearby entity positions
- **EntityAlert** - Alert when players target you
- **Notifications** - Chat and visual alerts for important events

### Utility Modules
- **Coordinates** - Always display current coordinates and biome
- **AutoDrop** - Automatically drop unwanted items
- **AutoTool** - Smart tool switching for mining/combat

## Installation

### Requirements
- Java 21 or higher
- Minecraft 1.20.1
- Fabric Loader 0.14.21 or higher
- Fabric API 0.90.7 for MC 1.20.1

### Setup

1. **Clone or download the project**
```bash
git clone https://github.com/prestige/prestige-client.git
cd prestige-client
```

2. **Build the mod**
```bash
./gradlew build
```

3. **Locate the JAR file**
- Built JAR: `build/libs/prestige-client-1.0.0.jar`

4. **Install the mod**
- Copy the JAR to your Minecraft `mods/` folder
- (Windows): `%appdata%/.minecraft/mods/`
- (macOS): `~/Library/Application Support/minecraft/mods/`
- (Linux): `~/.minecraft/mods/`

5. **Launch Minecraft with Fabric**
- Ensure you have Fabric Loader installed
- Create a Fabric profile in the Minecraft launcher
- Launch and enjoy!

## Usage

### Opening the GUI
- Default keybind: **RIGHT SHIFT** (configurable)
- Click module names to toggle on/off
- Right-click to expand settings
- Adjust sliders for precise tuning

### Module Categories
Navigate through categories using the tab buttons at the top of the GUI:
- **CRYSTAL** - Crystal-specific utilities
- **COMBAT** - Combat enhancements
- **DEFENSE** - Defensive modules
- **MOVEMENT** - Movement assistance
- **RENDER** - Visual enhancements
- **AWARENESS** - Information displays
- **UTILITY** - Helper functions

### Configuration
Settings are automatically saved to: `config/prestige-client/settings.json`

Each module has configurable parameters:
- Range values
- Speed multipliers
- Rotation angles
- Click rates (CPS)
- Delays and intervals

### Keybinds
Important default keybinds:
- **Right Shift** - Toggle GUI
- **Scroll Wheel** - Navigate settings in GUI
- **Left Click** - Toggle module
- **Right Click** - Expand module settings

## Module Details

### Crystal Aura (CRYSTAL)
Places End Crystals on nearby players for burst damage.

**Settings:**
- `Range` - Detection range (3-12 blocks)
- `Delay` - Ticks between placements (0-10)
- `Rotate` - Rotate to face placed crystals
- `Swing` - Show swing animation
- `Rotation Speed` - Speed of rotation (1-180°/tick)

**Tips:**
- Higher rotation speed = more obvious to other players
- Lower delay = faster placement but higher server load
- Disable swing for less visible placements

### Kill Aura (COMBAT)
Automatically attacks nearby players.

**Settings:**
- `Range` - Attack range (3-10 blocks)
- `CPS` - Clicks per second (1-30)
- `Rotate` - Rotate to target before attacking
- `Swing` - Show attack swing animation
- `Rotation Speed` - Rotation smoothness

**Tips:**
- Higher CPS = faster attack speed but may look suspicious
- Smooth rotation with moderate speed looks more natural
- Combine with TargetAssist for better targeting

### AutoHeal (DEFENSE)
Automatically eats gapples when health drops.

**Settings:**
- `Health Threshold` - Health level to start healing (1-20)
- `Delay` - Ticks between heals (1-40)
- `Smart Eat` - Only eat when safe (not in combat)

**Tips:**
- Lower threshold = less food waste, higher risk of death
- Higher delay = slower healing, more natural appearance

### Rotation (COMBAT)
Smooth rotation system for aiming.

**Settings:**
- `Speed` - Rotation speed in degrees/tick (1-180)
- `Smooth` - Use smooth interpolation
- `Silent` - Hide rotation from other players
- `Tolerance` - Angle tolerance in degrees (1-30)

**Tips:**
- Speed of 45-90° looks most natural
- Lower tolerance = more precise aiming
- Silent mode has performance cost

### CombatHUD (AWARENESS)
Displays real-time combat information.

**Displays:**
- Current target and health
- Nearby player list with distances
- Your current health and hunger
- Active threat indicators

**Settings:**
- `Target Info` - Show current target details
- `Nearby Players` - Show player list
- `Health` - Show health bar
- `Hunger` - Show hunger level
- `Max Players` - Maximum players to display (1-20)

## Advanced Features

### Rotation Smoothing
The rotation system uses interpolation for smooth, natural-looking movement:
- Prevents instant snapping to targets
- Adjustable speed for realistic gameplay
- Works with all targeting modules

### Crystal Placement Detection
CrystalAura automatically detects valid crystal placement spots:
- Only places on obsidian/bedrock blocks
- Checks for proper height clearance
- Validates against existing blocks/entities
- Prioritizes closest valid position

### Damage Prediction
CrystalDefense and CombatHUD show estimated damage:
- Calculates crystal explosion damage
- Accounts for distance and armor
- Predicts incoming damage
- Alerts before dangerous situations

### Target Switching
TargetAssist provides intelligent target selection:
- Lowest health targeting
- Nearest player targeting
- Automatic target switching on death
- Respects team indicators

## Performance

### System Requirements
- CPU: Minimal (client-side processing)
- RAM: <150MB additional usage
- GPU: Minimal impact (efficient rendering)
- Network: Optimized for low-latency servers

### Optimization Tips
1. Disable ESP modules if performance is low
2. Reduce rendering range on slower computers
3. Disable particle effects if needed
4. Use packet mode for NoFall (more efficient)
5. Adjust rotation speed to balance smoothness/performance

## Security & Fairness

⚠️ **Important Notice:**
- This mod is designed for **local testing and practice**
- Use only on servers that permit client modifications
- Some servers may ban users of utility clients
- Always check server rules before using
- The mod does not modify gameplay packets (mostly visual)

### Safe Usage
- Test in single-player first
- Know your server's rules
- Don't use on servers that prohibit mods
- Consider using on private servers only

## Configuration Files

Configuration is stored in:
```
.minecraft/config/prestige-client/settings.json
```

Manual editing is supported. Structure:
```json
{
  "module_name": {
    "enabled": true,
    "setting_name": "value"
  }
}
```

## Troubleshooting

### Mod won't load
1. Ensure Fabric Loader is installed
2. Check Java version (need Java 21+)
3. Verify Minecraft version is 1.20.1
4. Look for error logs in `logs/latest.log`

### GUI won't open
1. Check if Fabric API is installed
2. Ensure mod JAR is in correct mods folder
3. Try pressing **Right Shift** multiple times
4. Restart Minecraft completely

### Modules not working
1. Verify module is enabled (blue indicator)
2. Check setting values are in valid range
3. Look at chat for error messages
4. Check if feature requires specific conditions

### Performance issues
1. Disable non-essential modules
2. Reduce rendering distance
3. Lower screen resolution
4. Close other applications

## API & Custom Modules

Prestige Client supports custom modules. To create one:

1. Extend `Module` class
2. Register settings with `registerSetting()`
3. Implement `onEnable()` / `onDisable()`
4. Subscribe to events with `EventBus.subscribe()`
5. Register in `ModuleManager.registerModules()`

Example:
```java
public class MyModule extends Module {
    private Setting<Integer> range = new Setting<>("Range", "Detection range", 10);
    
    public MyModule() {
        super("MyModule", "Description", ModuleCategory.COMBAT);
        registerSetting(range);
        EventBus.subscribe(UpdateEvent.class, this::onUpdate);
    }
    
    private void onUpdate(Event event) {
        if (!isEnabled()) return;
        // Your code here
    }
}
```

## Contributing

Contributions welcome! Areas for improvement:
- Additional modules
- Performance optimizations
- Bug fixes
- Translation support
- Documentation improvements

## License

MIT License - See LICENSE file for details

## Credits

- Developed for Crystal PvP enthusiasts
- Built with Fabric API
- Inspired by professional client mods

## Support

For issues, suggestions, or questions:
- Check the documentation first
- Review existing issues
- Provide detailed error logs
- Include Minecraft version and mods list

## Changelog

### Version 1.0.0 (Initial Release)
- 30+ modules across 7 categories
- Professional Click GUI
- Event-based module system
- Configuration management
- Performance optimized

---

**Enjoy competitive Crystal PvP with Prestige Client!** 🎮⚔️

Remember: Play fair, respect server rules, and have fun!
