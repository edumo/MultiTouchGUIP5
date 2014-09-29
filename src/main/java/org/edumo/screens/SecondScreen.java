package org.edumo.screens;

import org.edumo.gui.GUIManager;
import org.edumo.gui.ScreenComponent;
import org.edumo.gui.button.ButonText;

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

	public void init(PGraphics canvas) {

		guiManager = new GUIManager();

		ButonText butonText = guiManager.addTextButton(canvas, "button1Name",
				"button1Action", canvas.width / 2, canvas.height / 2, 36,
				PApplet.CENTER);

		components.add(butonText);
	}

}
