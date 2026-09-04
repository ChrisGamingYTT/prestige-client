package com.prestige.client.manager;

import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import com.prestige.client.modules.crystal.*;
import com.prestige.client.modules.combat.*;
import com.prestige.client.modules.defense.*;
import com.prestige.client.modules.movement.*;
import com.prestige.client.modules.render.*;
import com.prestige.client.modules.awareness.*;
import com.prestige.client.modules.utility.*;
import java.util.*;

/**
 * Manages all modules in the client
 */
public class ModuleManager {
	private final List<Module> modules = new ArrayList<>();
	private final Map<String, Module> modulesByName = new HashMap<>();
	private final Map<ModuleCategory, List<Module>> modulesByCategory = new HashMap<>();

	public ModuleManager() {
		// Initialize category map
		for (ModuleCategory category : ModuleCategory.values()) {
			modulesByCategory.put(category, new ArrayList<>());
		}
	}

	/**
	 * Register all modules
	 */
	public void registerModules() {
		// Crystal modules
		registerModule(new CrystalAura());
		registerModule(new CrystalAttack());
		registerModule(new CrystalSpeed());
		registerModule(new CrystalESP());
		registerModule(new CrystalDefense());

		// Combat modules
		registerModule(new KillAura());
		registerModule(new Rotation());
		registerModule(new TargetAssist());
		registerModule(new AutoClicker());

		// Defense modules
		registerModule(new AutoHeal());
		registerModule(new Velocity());
		registerModule(new AntiVoid());
		registerModule(new NoFall());

		// Movement modules
		registerModule(new Sprint());
		registerModule(new Strafe());
		registerModule(new BunnyHop());
		registerModule(new FastBridge());
		registerModule(new AutoWalk());

		// Render modules
		registerModule(new PlayerESP());
		registerModule(new CrystalRender());
		registerModule(new Tracers());
		registerModule(new Nametags());
		registerModule(new FullBright());

		// Awareness modules
		registerModule(new CombatHUD());
		registerModule(new Radar());
		registerModule(new EntityAlert());
		registerModule(new Notifications());

		// Utility modules
		registerModule(new Coordinates());
		registerModule(new AutoDrop());
		registerModule(new AutoTool());
	}

	/**
	 * Register a single module
	 */
	public void registerModule(Module module) {
		modules.add(module);
		modulesByName.put(module.getName().toLowerCase(), module);
		modulesByCategory.get(module.getCategory()).add(module);
	}

	/**
	 * Get a module by name
	 */
	public Module getModule(String name) {
		return modulesByName.get(name.toLowerCase());
	}

	/**
	 * Get all modules
	 */
	public List<Module> getModules() {
		return new ArrayList<>(modules);
	}

	/**
	 * Get modules by category
	 */
	public List<Module> getModulesByCategory(ModuleCategory category) {
		return new ArrayList<>(modulesByCategory.get(category));
	}

	/**
	 * Get all enabled modules
	 */
	public List<Module> getEnabledModules() {
		List<Module> enabled = new ArrayList<>();
		for (Module module : modules) {
			if (module.isEnabled()) {
				enabled.add(module);
			}
		}
		return enabled;
	}
}
