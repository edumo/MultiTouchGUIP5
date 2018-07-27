package org.edumo.photobooth.scenes;

import org.edumo.content.ContentManager;
import org.edumo.content.BaseApp;
import org.edumo.gui.WindowManager;
import org.edumo.gui.Window;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonCapture;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.decorator.RectDecorator;
import org.edumo.photobooth.BaseAppPhotoBooth;
import org.edumo.photobooth.WindowPhotoBooth;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class PhotoScene extends WindowPhotoBooth {

	int countDown = -1;
	int countDownStart = 3;

	PGraphics photo;

	public PhotoScene(BaseAppPhotoBooth contextApp) {
		super(contextApp);
	}

	ButtonCapture buttonCapture;

	@Override
	public String drawUndecorated(PGraphics canvas) {
		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);
		updateRealPos(canvas);
		canvas.fill(10);
		canvas.noStroke();
		canvas.rect(0, 0, canvas.width, canvas.height);

		windowManager.drawComponentes(components, canvas);

		canvas.fill(255);

		if (countDown > 0) {
			int now = parent.millis() - countDown;
			int seg = (int) (now / 1000f);

			if (seg > countDownStart) {
				// HERE WE TAKE THE PHOTO
				if (photo == null) {
					photo = parent.createGraphics(buttonCapture.capture.width,
							buttonCapture.capture.height, PApplet.P2D);
				}
				// disparamos
				countDown = -1;
				photo.beginDraw();
				photo.image(buttonCapture.capture, 0, 0, photo.width,
						photo.height);
				photo.endDraw();
				mtContext.lastPhoto = photo;
			} else {
				canvas.text("CUENTA ATRAS " + (countDownStart - seg), 500, 500);
			}
		}

		if (photo != null) {
			canvas.imageMode(PApplet.CORNER);
			canvas.image(photo, 0, 0, 100, 100);
		}

		canvas.popMatrix();

		return null;
	}

	public void init(BaseAppPhotoBooth contextApp) {

		// ButtonText butonText = windowManager.addTextButton(contextApp.canvas,
		// "button1Name",
		// "button1Action", contextApp.canvas.width / 2,
		// contextApp.canvas.height / 2, 36, PApplet.CENTER);
		// butonText.setWidth(400);
		// butonText.setRectBoxColor(100);
		this.mtContext = contextApp;
		if (buttonCapture == null) {
			buttonCapture = getWindowManager().addButtonCapture("",
					contextApp.canvas.width / 2, contextApp.canvas.height / 2,
					false, PApplet.CENTER, components, mtContext.cam);

			components.add(buttonCapture);

			ButtonImage buttonImage = getWindowManager().addButton("takePhoto",
					contextApp.canvas.width - 100,
					contextApp.canvas.height / 2, "keyblank.jpg",
					"keyblank.jpg");

			buttonImage.setText("Take!", 18, PApplet.CENTER);
		}

		parent = contextApp.parent;

	}

	public void startCountDown() {
		countDown = parent.millis();
		photo = null;
	}

}
