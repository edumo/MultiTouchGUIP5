package org.edumo.gui;

import org.edumo.content.ContextApp;

import processing.core.PGraphics;

public abstract class Window extends GUIComponent{

	protected WindowManager windowManager;

	public WindowManager getGuiManager() {
		return windowManager;
	}

	public abstract void init(ContextApp contextApp);
}
