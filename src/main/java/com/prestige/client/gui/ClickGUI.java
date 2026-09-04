package com.prestige.client.gui;

import com.prestige.client.PrestigeClient;
import com.prestige.client.module.Module;
import com.prestige.client.module.ModuleCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Professional Click GUI for module management
 */
public class ClickGUI extends Screen {
	private static final MinecraftClient client = MinecraftClient.getInstance();
	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 400;
	
	private int x = 20;
	private int y = 20;
	private int scrollOffset = 0;
	private ModuleCategory selectedCategory = ModuleCategory.CRYSTAL;
	private List<ModulePanel> modulePanels = new ArrayList<>();
	private boolean dragging = false;
	private int dragX = 0;
	private int dragY = 0;

	public ClickGUI() {
		super(Text.literal("Prestige Client"));
		refreshModules();
	}

	/**
	 * Refresh module list
	 */
	private void refreshModules() {
		modulePanels.clear();
		var modules = PrestigeClient.MODULE_MANAGER.getModulesByCategory(selectedCategory);
		
		int panelY = y + 30;
		for (Module module : modules) {
			ModulePanel panel = new ModulePanel(module, x + 10, panelY, WINDOW_WIDTH - 20, 20);
			modulePanels.add(panel);
			panelY += 25;
		}
	}

	/**
	 * Change selected category
	 */
	public void selectCategory(ModuleCategory category) {
		this.selectedCategory = category;
		this.scrollOffset = 0;
		refreshModules();
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, mouseX, mouseY, delta);
		
		// Draw window background
		context.fill(x, y, x + WINDOW_WIDTH, y + WINDOW_HEIGHT, 0xFF1a1a1a);
		
		// Draw window border
		context.fill(x, y, x + WINDOW_WIDTH, y + 2, 0xFF00d4ff);
		
		// Draw title
		context.drawTextWithBackground(
			client.textRenderer,
			Text.literal("Prestige Client"),
			x + 10,
			y + 8,
			0xFFFFFF,
			0x00000000
		);
		
		// Draw category tabs
		drawCategoryTabs(context, mouseX, mouseY);
		
		// Draw module list
		drawModuleList(context, mouseX, mouseY);
	}

	private void drawCategoryTabs(DrawContext context, int mouseX, int mouseY) {
		int tabX = x + 10;
		int tabY = y + 25;
		
		for (ModuleCategory category : ModuleCategory.values()) {
			int width = 40;
			int color = category == selectedCategory ? 0xFF00d4ff : 0xFF666666;
			
			context.fill(tabX, tabY, tabX + width, tabY + 15, color);
			context.drawTextWithBackground(
				client.textRenderer,
				Text.literal(category.getDisplayName().substring(0, Math.min(4, category.getDisplayName().length()))),
				tabX + 5,
				tabY + 3,
				0xFFFFFF,
				0x00000000
			);
			
			tabX += width + 2;
		}
	}

	private void drawModuleList(DrawContext context, int mouseX, int mouseY) {
		int panelY = y + 45;
		
		for (ModulePanel panel : modulePanels) {
			panel.setY(panelY);
			panel.render(context, mouseX, mouseY);
			panelY += 25;
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		this.scrollOffset += (int) verticalAmount * 10;
		refreshModules();
		return true;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		// Check category clicks
		int tabX = x + 10;
		int tabY = y + 25;
		
		for (ModuleCategory category : ModuleCategory.values()) {
			int width = 40;
			if (mouseX >= tabX && mouseX < tabX + width && mouseY >= tabY && mouseY < tabY + 15) {
				selectCategory(category);
				return true;
			}
			tabX += width + 2;
		}
		
		// Check module clicks
		for (ModulePanel panel : modulePanels) {
			if (panel.mouseClicked(mouseX, mouseY, button)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void close() {
		this.client.setScreen(null);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	/**
	 * Show the GUI
	 */
	public void show() {
		if (client.currentScreen == this) {
			client.setScreen(null);
		} else {
			client.setScreen(this);
		}
	}

	/**
	 * Check if GUI is visible
	 */
	public boolean isVisible() {
		return client.currentScreen == this;
	}
}
