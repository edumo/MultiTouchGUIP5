package org.edumo.screens;

import org.edumo.content.BaseApp;
import org.edumo.gui.WindowManager;
import org.edumo.gui.Window;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonText;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;

public class SecondScreen extends Window {

	public SecondScreen(BaseApp contextApp) {
		super(contextApp);
	}

	@Override
	public String drawUndecorated(PGraphics canvas) {
		updateRealPos(canvas);
		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);
		canvas.fill(50);
		canvas.noStroke();
		canvas.rect(0, 0, canvas.width, canvas.height);
		windowManager.drawComponentes(components, canvas);
		canvas.popMatrix();
		return null;
	}

	public void init(BaseApp contextApp) {

		super.init(contextApp);
		ButtonText butonText = windowManager.addTextButton(contextApp.canvas,
				"button1Action", contextApp.canvas.width / 2,
				contextApp.canvas.height / 2, 36, PApplet.CENTER);
		butonText.setWidth(400);
		butonText.setRectBoxColor(mtContext.parent.color(100,20,20));
		components.add(butonText);
	}

}
