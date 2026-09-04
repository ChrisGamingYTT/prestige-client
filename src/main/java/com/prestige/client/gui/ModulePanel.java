package com.prestige.client.gui;

import com.prestige.client.module.Module;
import com.prestige.client.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Individual module panel in the GUI
 */
public class ModulePanel {
	private static final MinecraftClient client = MinecraftClient.getInstance();
	
	private final Module module;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean expanded = false;

	public ModulePanel(Module module, int x, int y, int width, int height) {
		this.module = module;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void render(DrawContext context, int mouseX, int mouseY) {
		// Draw module name
		int color = module.isEnabled() ? 0xFF00d4ff : 0xFF666666;
		context.fill(x, y, x + width, y + height, 0xFF2a2a2a);
		
		// Draw border
		context.fill(x, y, x + width, y + 1, color);
		
		// Draw toggle indicator
		if (module.isEnabled()) {
			context.fill(x + 2, y + 4, x + 10, y + height - 4, 0xFF00d4ff);
		}
		
		// Draw module name
		context.drawTextWithBackground(
			client.textRenderer,
			Text.literal(module.getName()),
			x + 12,
			y + 6,
			0xFFFFFF,
			0x00000000
		);

		// Draw expanded arrow
		String arrow = expanded ? "▼" : "▶";
		if (!module.getSettings().isEmpty()) {
			context.drawTextWithBackground(
				client.textRenderer,
				Text.literal(arrow),
				x + width - 20,
				y + 6,
				0xFFFFFF,
				0x00000000
			);
		}

		// Draw settings if expanded
		if (expanded) {
			drawSettings(context, mouseX, mouseY);
		}
	}

	private void drawSettings(DrawContext context, int mouseX, int mouseY) {
		List<Setting<?>> settings = module.getSettings();
		int settingY = y + height + 2;

		for (Setting<?> setting : settings) {
			context.fill(x, settingY, x + width, settingY + 15, 0xFF1a1a1a);
			
			// Draw setting name
			context.drawTextWithBackground(
				client.textRenderer,
				Text.literal(setting.getName()),
				x + 10,
				settingY + 3,
				0xFFCCCCCC,
				0x00000000
			);

			// Draw value
			String value = setting.getValue().toString();
			context.drawTextWithBackground(
				client.textRenderer,
				Text.literal(value),
				x + width - 30,
				settingY + 3,
				0xFF888888,
				0x00000000
			);

			settingY += 15;
		}
	}

	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height) {
			if (button == 0) {
				module.toggle();
				return true;
			} else if (button == 1) {
				expanded = !expanded;
				return true;
			}
		}
		return false;
	}

	public Module getModule() {
		return module;
	}

	public int getHeight() {
		int height = this.height;
		if (expanded) {
			height += module.getSettings().size() * 15 + 2;
		}
		return height;
	}
}
