package org.edumo.content;

import java.util.List;
import java.util.Properties;

import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import TUIO.TuioProcessing;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class BaseApp {

	public ContentManager contentManager;

	public PApplet parent;

	public PGraphics canvas;

	public TuioProcessing tuioClient;

	public TUIOConverter tuioConverter;

	public int MAX_TUIOS_PROCESSED = 5;

	public Properties properties;

	public BaseApp(PApplet parent, PGraphics canvas) {

		tuioConverter = new TUIOConverter();
		tuioClient = new TuioProcessing(parent,3333);
		tuioConverter.init(tuioClient);
		contentManager = new ContentManager(parent);
		this.parent = parent;
		this.canvas = canvas;

	}

	public void drawDebugPointers(PGraphics canvas) {
		List<TouchPointer> touches = tuioConverter.getPointers(canvas, parent);

		for (int i = 0; i < touches.size(); i++) {
			TouchPointer pointer = touches.get(i);

			canvas.stroke(192, 192, 192);
			canvas.fill(192, 192, 192);
			canvas.ellipse(pointer.getScreenX(canvas.width),
					pointer.getScreenY(canvas.height), 5, 5);
		}
	}

	public int getPropertyInt(String key) {
		Object object = properties.get(key);
		if (object != null) {
			String string = object.toString();
			int ret = Integer.parseInt(string);
			return ret;
		} else {
			System.out
					.println("SE SOLICITO PROPIEDAD QUE NO EXISTE CON CADENA '"
							+ key + "'");
		}
		return 0;
	}

	public String getPropertyString(String key) {
		Object object = properties.get(key);
		if (object != null) {
			String string = object.toString();
			String homeUser = System.getProperty("user.home");
			string = string.replace("::user.home::", homeUser);
			return string;
		} else {
			System.out
					.println("SE SOLICITO PROPIEDAD QUE NO EXISTE CON CADENA '"
							+ key + "'");
		}
		return null;
	}

	public PImage loadImage(String string) {
		return contentManager.loadImage(string);
	}
}
