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

	// A reference to the canvas (we need to know the fill color, the strokeWidth...)
	public PGraphics canvas;

	public TuioProcessing[] tuioClients;
	
	public int port1 = 3333;
	
	public TUIOConverter tuioConverter;

	public int MAX_TUIOS_PROCESSED = 100;

	public Properties properties;

	public boolean ignoreMouse = false;
	public boolean ignoreMouseIfTUIO = false;

	public BaseApp(PApplet parent, PGraphics canvas) {
		tuioConverter = new TUIOConverter();
		tuioClients = new TuioProcessing[1];
		tuioClients[0] = new TuioProcessing(parent, port1);
		tuioConverter.init(tuioClients, this);
		createContentManager(parent);
		this.parent = parent;
		this.canvas = canvas;
	}
	
	protected void createContentManager(PApplet parent) {
		contentManager = new ContentManager(parent);
	}

	public void drawDebugPointers(PGraphics canvas) {
		List<TouchPointer> touches = tuioConverter.getPointers(canvas, parent);

		for (int i = 0; i < touches.size(); i++) {
			TouchPointer pointer = touches.get(i);

			canvas.pushStyle();
			canvas.strokeWeight(5);
			canvas.stroke(255, 192, 192);
			canvas.fill(255, 192, 192);
			canvas.ellipse(pointer.getScreenX(canvas.width), pointer.getScreenY(canvas.height), 15, 15);
			canvas.text("id:"+pointer.id, pointer.getScreenX(canvas.width), pointer.getScreenY(canvas.height));
			canvas.popStyle();
		}
	}

	public int getPropertyInt(String key) {
		Object object = properties.get(key);
		if (object != null) {
			String string = object.toString();
			int ret = Integer.parseInt(string);
			return ret;
		} else {
			System.out.println("SE SOLICITO PROPIEDAD QUE NO EXISTE CON CADENA '" + key + "'");
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
			System.out.println("SE SOLICITO PROPIEDAD QUE NO EXISTE CON CADENA '" + key + "'");
		}
		return null;
	}

	public PImage loadImage(String string) {
		return contentManager.loadImage(string);
	}
}
