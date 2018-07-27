package org.edumo;

import java.util.HashMap;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.ButtonImagePortView;
import org.edumo.gui.button.ButtonImagePortViewVertical;
import org.edumo.gui.button.ButtonSlider;

import processing.core.PApplet;
import processing.core.PVector;

public class MainSimpleButtonViewPort extends MTGuiP5PApplet {

	ButtonImagePortView buttonSlider;
	private ButtonImagePortViewVertical buttonSliderVertical;

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

		buttonSlider = window.getWindowManager().addButtonPortView("botonImagen", width / 2, height / 3, "menu.jpg",
				"menu.jpg", CORNER);
		
		buttonSliderVertical = window.getWindowManager().addButtonPortViewVertical("botonImagen", width / 2, height / 3, "texto-test.jpg",
				"menu.jpg", CORNER);
		
		
		
		window.getWindowManager().addDraggable(buttonSlider);
		window.getWindowManager().addDraggable(buttonSliderVertical);

	}

	public void draw() {

		
		buttonSlider.setMargin(0, 300);
		buttonSlider.setResizeOnDraw(new PVector(1920, 148));
		buttonSlider.setPosition(-width / 2, height - 254);
		buttonSlider.setSizeViewPort(1.1f);
		
		buttonSliderVertical.setMargin(0, -200);
		buttonSliderVertical.setResizeOnDraw(new PVector(548, 300));
		buttonSliderVertical.setPosition(width / 2, 0);
		buttonSliderVertical.setSizeViewPort(1.1f);

		background(100);
		mtContext.drawDebugPointers(g);
		window.drawUndecorated(g);

	}

	protected void doAction(ActionEvent action) {
		if (action != null)
			println("action  " + action.getAction());
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainSimpleButtonViewPort" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
