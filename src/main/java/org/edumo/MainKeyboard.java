package org.edumo;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;
import org.edumo.gui.keyboard.KeyboardComponent;

import processing.core.PApplet;

public class MainKeyboard extends MTGuiP5PApplet {

	public void setup() {

		
		
		mtContext = new BaseApp(this,g);
		frameRate(60);
		initGUI();
	}

	private void initGUI() {

		window = new Window(mtContext);
		KeyboardComponent keyboardComponent = new KeyboardComponent();
		String[][] chars = {
				{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "'", "@",
						KeyboardComponent.DELETE },
				{ "q", "w", "e", "r", "t", "y", "u", "i", "o", "p" },
				{ "a", "s", "d", "f", "g", "h", "j", "k", "l", "�", "�" },
				{ "z", "x", "c", "v", "b", "n", "m", /* ",", */"-", "_", "." },
				{ KeyboardComponent.SPACE } };

		String[] imgs = { "keyblank.jpg", "keyblank.jpg", "keyblank.jpg","keyblank.jpg" };

		keyboardComponent
				.init(this, window.getWindowManager(), chars, 50, "kb33", imgs);
		keyboardComponent.setPosition(100, 200);
		
		window.add(keyboardComponent);
	}

	public void draw() {

		background(0);
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

		String[] appletArgs = new String[] { "org.edumo.MainKeyboard" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
