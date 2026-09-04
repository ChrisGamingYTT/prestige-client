# Prestige Client - Build Instructions

## Quick Start

### Prerequisites
- **Java**: Java 21 or higher ([download](https://www.oracle.com/java/technologies/downloads/#java21))
- **Gradle**: 7.6+ (included via wrapper)
- **Git**: For cloning (optional)

### Verify Prerequisites
```powershell
# Check Java version
java -version

# Should output something like:
# java version "21.0.0" or higher
```

## Building the Mod

### Option 1: Using PowerShell (Windows)

1. **Navigate to project directory**:
```powershell
cd "d:\AI APPS\CLAclient"
```

2. **Build the project**:
```powershell
./gradlew build
```

3. **Locate the JAR**:
```powershell
# The built JAR will be at:
ls build/libs/prestige-client-*.jar
```

### Option 2: Using Command Prompt (Windows)

1. **Navigate to project directory**:
```cmd
cd d:\AI APPS\CLAclient
```

2. **Build the project**:
```cmd
gradlew build
```

3. **Find the JAR**:
```cmd
dir build\libs\prestige-client-*.jar
```

### Option 3: Using Gradle Tasks

```bash
# Clean and rebuild
./gradlew clean build

# Build without tests
./gradlew build -x test

# Build with verbose output
./gradlew build --info
```

## Installation

### Step 1: Install Fabric Loader

1. Go to [fabricmc.net](https://fabricmc.net/use)
2. Download Fabric Installer for Minecraft 1.20.1
3. Run the installer
4. Select "Create new profile" or use existing
5. Click "Install"

### Step 2: Install Fabric API

1. Download Fabric API for 1.20.1 from [CurseForge](https://www.curseforge.com/minecraft/mods/fabric-api)
2. Save as `fabric-api-0.90.7+1.20.1.jar`

### Step 3: Install Prestige Client

1. After building (see above), find the JAR at:
   ```
   d:\AI APPS\CLAclient\build\libs\prestige-client-1.0.0.jar
   ```

2. Copy to Minecraft mods folder:
   ```
   %appdata%\.minecraft\mods\
   ```

3. Ensure both JAR files are in mods folder:
   - `fabric-api-0.90.7+1.20.1.jar`
   - `prestige-client-1.0.0.jar`

### Step 4: Launch Minecraft

1. Open Minecraft Launcher
2. Select the Fabric profile
3. Click Play
4. If mod loads, you'll see "Prestige Client" in logs

## Development Setup

### IDE Setup (IntelliJ IDEA)

1. **Open project**:
   - File → Open
   - Select `d:\AI APPS\CLAclient` folder

2. **Import Gradle project**:
   - IntelliJ usually detects `build.gradle` automatically
   - If not: File → New → Module from Existing Sources

3. **Configure JDK**:
   - File → Project Structure
   - Set Project SDK to Java 21
   - Set language level to 21

4. **Generate run configurations**:
   ```bash
   ./gradlew idea
   ```

5. **Run in development**:
   - Look for "runClient" in Gradle tasks
   - Double-click to run

### IDE Setup (Eclipse)

1. **Import project**:
   - File → Import
   - Gradle → Existing Gradle Project
   - Select project folder

2. **Build**:
   - Right-click project → Run As → Gradle build
   - Enter task: `build`

### IDE Setup (VS Code)

1. **Install extensions**:
   - Extension Pack for Java
   - Gradle for Java

2. **Open folder**:
   - File → Open Folder
   - Select `d:\AI APPS\CLAclient`

3. **Run build**:
   - Terminal → New Terminal
   - Run: `./gradlew build`

## Building in Development Mode

### Run Client with Mod

```bash
./gradlew runClient
```

This will:
1. Download Minecraft server
2. Generate mappings
3. Start Minecraft with your mod loaded
4. Allow you to test in real-time

### Re-run after code changes

```bash
./gradlew runClient
```

The Gradle daemon will recompile only changed files.

## Troubleshooting Builds

### Error: "Java 21 not found"

**Solution**:
```bash
# Set JAVA_HOME to Java 21 installation
# Windows PowerShell:
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21.0.0"

# Windows CMD:
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.0

# Verify
java -version
```

### Error: "Could not find or load main class"

**Solution**:
```bash
# Clean and rebuild
./gradlew clean build

# Or try with daemon reset
./gradlew --stop
./gradlew build
```

### Error: "Mapping files not found"

**Solution**:
```bash
# Download mappings manually
./gradlew fetchMappings

# Then rebuild
./gradlew build
```

### Build succeeds but mod doesn't load

**Solution**:
1. Verify Fabric Loader version: 0.14.21+
2. Verify Fabric API version: 0.90.7+1.20.1
3. Check `fabric.mod.json` exists and is valid
4. Check logs for errors: `logs/latest.log`

### Out of memory during build

**Solution**:
```bash
# Increase Gradle memory
# Add to gradle.properties:
org.gradle.jvmargs=-Xmx2G

# Or set environment variable:
set GRADLE_OPTS=-Xmx2G
```

## Building for Distribution

### Create Release Build

```bash
# Clean build
./gradlew clean build

# Output will be at:
# build/libs/prestige-client-1.0.0.jar
```

### Version Update

To change version:

1. Edit `gradle.properties`:
```properties
mod_version=1.1.0
```

2. Rebuild:
```bash
./gradlew clean build
```

### Signing JAR (Optional)

For security, you can sign the JAR:

```bash
# Use jarsigner (included with Java)
jarsigner -keystore keystore.jks build/libs/prestige-client-1.0.0.jar mykey
```

## Continuous Integration

### GitHub Actions Example

```yaml
name: Build

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Gradle
        run: ./gradlew build
      - name: Upload JAR
        uses: actions/upload-artifact@v3
        with:
          name: prestige-client
          path: build/libs/prestige-client-*.jar
```

## Build Structure

### Output Files

After building, you'll find:

```
build/
├── libs/
│   ├── prestige-client-1.0.0.jar          ← Use this file
│   ├── prestige-client-1.0.0-sources.jar  ← Source code
│   └── prestige-client-1.0.0-dev.jar      ← Development JAR
├── classes/
│   └── ... (compiled class files)
└── ... (other build artifacts)
```

### Important Files

- **prestige-client-1.0.0.jar** - The mod file (use this)
- **build/resources/main/fabric.mod.json** - Mod metadata
- **build/resources/main/prestige.mixins.json** - Mixin config

## Gradle Tasks Reference

### Common Tasks

```bash
./gradlew build           # Full build
./gradlew clean           # Clean build files
./gradlew runClient       # Run with mod
./gradlew genSourcesWithVineflower  # Generate sources
./gradlew idea           # Generate IntelliJ config
./gradlew eclipse        # Generate Eclipse config
./gradlew tasks          # List all available tasks
```

### Custom Tasks

```bash
./gradlew remapJar       # Remap JAR to SRG names
./gradlew sourcesJar     # Build sources JAR
./gradlew javadoc        # Generate Javadoc
```

## Performance Tips

### Speed up builds

1. **Use Gradle Daemon**:
```bash
# Enabled by default, disable with:
./gradlew --no-daemon build
```

2. **Parallel builds**:
```bash
# Add to gradle.properties:
org.gradle.parallel=true
org.gradle.workers.max=8
```

3. **Incremental compilation**:
```bash
# Add to gradle.properties:
org.gradle.caching=true
```

## Next Steps

After building:

1. **Test the mod**:
   - Place JAR in mods folder
   - Launch Minecraft
   - Verify mod loads (check logs)
   - Open GUI with Right Shift

2. **Customize**:
   - Edit module code
   - Add new features
   - Rebuild: `./gradlew build`

3. **Distribute**:
   - Copy JAR from `build/libs/`
   - Share with others
   - Include README.md

## Support

### Getting Help

1. **Check logs**: `%appdata%\.minecraft\logs\latest.log`
2. **Review errors**: Look for "Prestige Client" entries
3. **Test with minimal setup**: Just mod + Fabric API
4. **Try different Java version**: Ensure Java 21

### Common Commands

```bash
# Full diagnostic build
./gradlew clean build --info --stacktrace

# Check Java version in use
./gradlew javaVersion

# Reset Gradle cache
./gradlew --stop
./gradlew --reset-gradle-cache
```

---

Happy building! For detailed technical information, see IMPLEMENTATION_NOTES.md
