package org.edumo.screens;

import org.edumo.content.ContentManager;
import org.edumo.content.ContextApp;
import org.edumo.gui.WindowManager;
import org.edumo.gui.Window;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.decorator.RectDecorator;

import processing.core.PApplet;
import processing.core.PGraphics;


public class Home extends Window {

	@Override
	public String draw(PGraphics canvas) {
		
		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);
		
		windowManager.drawComponentes(components, canvas);
		
		canvas.popMatrix();
		
		return null;
	}

	public void init(ContextApp contextApp){
		
		windowManager = new WindowManager(new ContentManager(contextApp.parent));
		
		ButtonText butonText = windowManager.addTextButton(contextApp.canvas, "button1Name",
				"button1Action", contextApp.canvas.width / 2, contextApp.canvas.height / 2, 36, PApplet.CENTER);
		
		components.add(butonText);
	}
	
}
