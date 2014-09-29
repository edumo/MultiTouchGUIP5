package org.edumo.screens;

import org.edumo.content.ContextApp;
import org.edumo.gui.GUIManager;
import org.edumo.gui.ScreenComponent;
import org.edumo.gui.button.ButtonText;

import processing.core.PApplet;
import processing.core.PGraphics;

public class SecondScreen extends ScreenComponent {

	@Override
	public String draw(PGraphics canvas) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);

		guiManager.drawComponentes(components, canvas);
		canvas.popMatrix();
		return null;
	}

	public void init(ContextApp contextApp) {

		guiManager = new GUIManager(contextApp.contentManager);

		ButtonText butonText = guiManager.addTextButton(contextApp.canvas, "button1Name",
				"button1Action", contextApp.canvas.width / 2, contextApp.canvas.height / 2, 36,
				PApplet.CENTER);

		components.add(butonText);
	}

}
