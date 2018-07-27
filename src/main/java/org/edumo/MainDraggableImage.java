	package org.edumo;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;

import processing.core.PApplet;

public class MainDraggableImage extends MTGuiP5PApplet {

	public void setup() {
		mtContext = new BaseApp(this, g);
		frameRate(60);
		initGUI();
	}

	private void initGUI() {

		window = new Window(mtContext);

		GUIComponent dragableImage = window.getWindowManager().addButton("", width / 2, height / 2, "keyblank.jpg","keyblank.jpg");
		window.getWindowManager().addDraggable(dragableImage);
	}

	public void draw() {

		background(0);
		mtContext.drawDebugPointers(g);

		window.drawUndecorated( g);

	}

	protected void doAction(ActionEvent action) {
		if (action != null)
			println("action " + action.getAction());
	}

	@Override
	public void settings() {
		size(1024, 768, P3D);
	}
	
	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainDraggableImage" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
