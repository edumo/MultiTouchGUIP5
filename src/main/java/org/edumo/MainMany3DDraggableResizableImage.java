package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;

import processing.core.PApplet;
import processing.core.PVector;

public class MainMany3DDraggableResizableImage extends MTGuiP5PApplet {

	List<GUIComponent> componentes = new ArrayList<>();
	
	public void setup() {
		size(1600, 900, OPENGL);
		smooth(4);
		mtContext = new BaseApp(this, g);
		frameRate(60);
		initGUI();
		mtContext.ignoreMouseIfTUIO = true;
	}

	private void initGUI() {

		window = new Window(mtContext);

		for (int i = 0; i < 10; i++) {
			GUIComponent dragableImage = window.getWindowManager().addButton("action", width / 2, height / 2, "keyblank.jpg",
					"keyblank.jpg", PApplet.CENTER);
			componentes.add(dragableImage);
			dragableImage.setPosition(new PVector(random(width), random(height),random(1000)));
			dragableImage.setResizeOnDraw(new PVector(200, 200));

			window.getWindowManager().addDraggable(dragableImage);
			window.getWindowManager().addResizable(dragableImage);
		}
	}

	public void draw() {

		background(0);
		mtContext.drawDebugPointers(g);
		window.drawUndecorated(g);
		
		for(GUIComponent c:componentes){
			
		}
	}

	protected void doAction(ActionEvent action) {
		if (action != null)
			println("action " + action.getAction());
		
		if(action != null && action.getAction().trim().equals("action"))
			window.setTopDraw(action.getComponent());
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainMany3DDraggableResizableImage" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
