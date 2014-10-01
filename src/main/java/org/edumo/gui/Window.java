package org.edumo.gui;

import org.edumo.content.MTContext;
import org.edumo.touch.TouchPointer;

import processing.core.PGraphics;

public class Window extends GUIComponent implements HIDEventListener {

	protected WindowManager windowManager;

	public WindowManager getGuiManager() {
		return windowManager;
	}

	public String draw(PGraphics canvas) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);

		windowManager.drawComponentes(components, canvas);

		canvas.popMatrix();

		return null;
	}

	public Window(MTContext contextApp) {

		windowManager = new WindowManager(contextApp.contentManager, this);
	}

	public ActionEvent hidPressed(TouchPointer touche) {
		return windowManager.hidPressed(touche);
	}

	public ActionEvent hidReleased(TouchPointer touche) {
		return windowManager.hidReleased(touche);
	}

	public ActionEvent hidDragged(TouchPointer touche) {
		return windowManager.hidDragged(touche);
	}
}
