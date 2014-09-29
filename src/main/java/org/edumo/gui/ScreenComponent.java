package org.edumo.gui;

import org.edumo.content.ContextApp;

import processing.core.PGraphics;

public abstract class ScreenComponent extends Component{

	protected GUIManager guiManager;

	public GUIManager getGuiManager() {
		return guiManager;
	}

	public abstract void init(ContextApp contextApp);
}
