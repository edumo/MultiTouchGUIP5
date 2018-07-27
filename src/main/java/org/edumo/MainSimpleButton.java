package org.edumo;

import java.util.HashMap;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonImage;

import processing.core.PApplet;

public class MainSimpleButton extends MTGuiP5PApplet {

	public void setup() {

		// size(displayWidth, displayHeight, OPENGL);

		
		mtContext = new BaseApp(this, g);
		frameRate(60);
		mtContext.contentManager.imgContainer = new HashMap<>();
		initGUI();
	}

	private void initGUI() {

		window = new Window(mtContext);

		AbstractButton butonText = window.getWindowManager().addTextButton(g,
				"button1Name", "button1Action", width / 2, height / 2, 36,
				CENTER);

		ButtonImage buttonImage = window.getWindowManager().addButton(
				"botonImagen", width / 2, height / 3, "keyblank.jpg",
				"keyblank.jpg");

	}

	public void draw() {

		background(100);
		mtContext.drawDebugPointers(g);
		window.drawUndecorated(g);

	}

	protected void doAction(ActionEvent action) {
		if (action != null)
			println("action  " + action.getAction());
	}
	
	@Override
	public void settings() {
		size(1024, 768, P3D);
	}
	
	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainSimpleButton" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
