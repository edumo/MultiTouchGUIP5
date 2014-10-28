package org.edumo.screens;

import org.edumo.content.ContentManager;
import org.edumo.content.BaseApp;
import org.edumo.gui.WindowManager;
import org.edumo.gui.Window;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.decorator.RectDecorator;

import processing.core.PApplet;
import processing.core.PGraphics;


public class Home extends Window {

	public Home(BaseApp contextApp) {
		super(contextApp);
	}

	@Override
	public String drawUndecorated(PGraphics canvas) {
		
		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);
		
		windowManager.drawComponentes(components, canvas);
		
		canvas.popMatrix();
		
		return null;
	}

	public void init(BaseApp contextApp){
		
		ButtonText butonText = windowManager.addTextButton(contextApp.canvas, "button1Name",
				"button1Action", contextApp.canvas.width / 2, contextApp.canvas.height / 2, 36, PApplet.CENTER);
		butonText.setWidth(400);
		butonText.setRectBoxColor(100);
		
		
		components.add(butonText);
	}
	
}
