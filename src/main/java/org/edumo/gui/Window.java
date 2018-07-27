package org.edumo.gui;

import org.edumo.content.BaseApp;
import org.edumo.gui.button.ButtonImage;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Window extends GUIComponent implements HIDEventListener {

	protected BaseApp mtContext;

	public WindowManager getWindowManager() {
		return windowManager;
	}

	public String drawUndecorated(PGraphics canvas) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);

		windowManager.drawComponentes(components, canvas);

		canvas.popMatrix();

		return null;
	}

	public void startWindow() {

	}

	public Window(BaseApp contextApp) {
		delegateConstructor(contextApp);
		windowManager = new WindowManager(contextApp.contentManager, this);
		init(contextApp);
	}

	public Window(BaseApp contextApp, boolean withInit) {
		delegateConstructor(contextApp);
		windowManager = new WindowManager(contextApp.contentManager, this);
		if (withInit)
			init(contextApp);

	}

	public ActionEvent doAction(ActionEvent action) {
		return null;
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

	public ActionEvent hidMoved(TouchPointer touche) {
		return windowManager.hidMoved(touche);
	}

	public void init(BaseApp contextApp) {
		this.mtContext = contextApp;
		this.parent = contextApp.parent;
	}

	protected void delegateConstructor(BaseApp contextApp) {

	}

	public void setTopDraw(GUIComponent component) {
		components.remove(component);
		components.add(component);
	}

}
