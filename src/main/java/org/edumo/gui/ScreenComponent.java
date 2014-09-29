package org.edumo.gui;

import processing.core.PGraphics;

public abstract class ScreenComponent extends Component{

	protected GUIManager guiManager;

	public GUIManager getGuiManager() {
		return guiManager;
	}

	public abstract void init(PGraphics canvas);
}
