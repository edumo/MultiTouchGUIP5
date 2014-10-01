package org.edumo.content;

import java.util.List;

import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import TUIO.TuioProcessing;
import processing.core.PApplet;
import processing.core.PGraphics;

public class MTContext {

	public ContentManager contentManager;

	public PApplet parent;

	public PGraphics canvas;

	public TuioProcessing tuioClient;

	public TUIOConverter tuioConverter;

	public int MAX_TUIOS_PROCESSED = 5;

	public MTContext(PApplet parent, PGraphics canvas) {

		tuioConverter = new TUIOConverter();
		tuioClient = new TuioProcessing(parent);
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
}
