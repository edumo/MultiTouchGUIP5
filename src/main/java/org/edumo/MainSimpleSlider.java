package org.edumo;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.ButtonSlider;

import processing.core.PApplet;
import processing.core.PVector;

public class MainSimpleSlider extends MTGuiP5PApplet {

	ButtonSlider buttonSlider;

	public void setup() {

		size(1024, 768, OPENGL);
		mtContext = new BaseApp(this, g);
		frameRate(60);
		initGUI();
	}

	private void initGUI() {

		window = new Window(mtContext);

		AbstractButton butonText = window.getWindowManager().addTextButton(g, "button1Name", "button1Action", width / 2,
				height / 2, 36, CENTER);

		buttonSlider = window.getWindowManager().addButtonSlider("botonImagen", width / 2, height / 3, "menu.jpg",
				"menu.jpg", CORNER);

	}

	public void draw() {

		buttonSlider.setResizeOnDraw(new PVector(1920, 54));
		buttonSlider.setPosition(-width / 2, height - 54);

		background(0);
		mtContext.drawDebugPointers(g);
		window.drawUndecorated(g);

	}

	protected void doAction(ActionEvent action) {
		if (action != null)
			println("action  " + action.getAction());
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainSimpleSlider" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
