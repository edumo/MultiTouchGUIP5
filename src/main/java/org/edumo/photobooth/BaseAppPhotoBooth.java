package org.edumo.photobooth;

import org.edumo.content.BaseApp;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Capture;

public class BaseAppPhotoBooth extends BaseApp {

	public Capture cam = null;

	public PImage lastPhoto = null;

	public BaseAppPhotoBooth(PApplet parent, PGraphics canvas) {
		super(parent, canvas);
	}

	public void initCamera() {
		String[] cameras = Capture.list();
		if (cameras == null) {
			// println("Failed to retrieve the list of available cameras, will try the default...");
			cam = new Capture(contentManager.parent, 640, 480);
		}
		if (cameras.length == 0) {
			// println("There are no cameras available for capture.");
			contentManager.parent.exit();
		} else {
			// println("Available cameras:");
			for (int i = 0; i < cameras.length; i++) {
				contentManager.parent.println(cameras[i]);
			}

			// The camera can be initialized directly using an element
			// from the array returned by list():
			cam = new Capture(contentManager.parent, cameras[0]);
			// Or, the settings can be defined based on the text in the list
			// cam = new Capture(this, 640, 480, "Built-in iSight", 30);

			// Start capturing the images from the camera

		}
	}

}
