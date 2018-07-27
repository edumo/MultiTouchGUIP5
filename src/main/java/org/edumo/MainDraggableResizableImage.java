package org.edumo;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;

import processing.core.PApplet;
import processing.core.PVector;

public class MainDraggableResizableImage extends MTGuiP5PApplet {

	public void setup() {
		size(1024, 768, OPENGL);
		smooth(4);
		mtContext = new BaseApp(this, g);
		frameRate(60);
		initGUI();
		mtContext.ignoreMouseIfTUIO = true;
	}

	private void initGUI() {

		window = new Window(mtContext);

		GUIComponent dragableImage = window.getWindowManager().addButton("", width / 2, height / 2, "keyblank.jpg",
				"keyblank.jpg", PApplet.CENTER);
		dragableImage.setResizeOnDraw(new PVector(200, 200));

		window.getWindowManager().addDraggable(dragableImage);
		window.getWindowManager().addResizable(dragableImage);
	}

	public void draw() {

		background(0);
		mtContext.drawDebugPointers(g);
		window.drawUndecorated(g);

	}

	protected void doAction(ActionEvent action) {
		if (action != null)
			println("action " + action.getAction());
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainDraggableResizableImage" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
