package org.edumo.touch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PGraphics;
import TUIO.TuioCursor;
import TUIO.TuioPoint;
import TUIO.TuioProcessing;

public class TUIOConverter {

	float cursor_size = 15;
	float object_size = 60;
	float scale_factor = 1;

	long lastPressedStart = 0;

	TuioProcessing tuioClient;

	// Map<>
	Map<Integer, TouchPointer> grifos; // ID , Contador

	public void init(TuioProcessing tuioClient) {
		this.tuioClient = tuioClient;
	}

	public List<TouchPointer> getPointers(PGraphics canvas, PApplet parent) {

		float cur_size = cursor_size * scale_factor;

		List<TouchPointer> pointers = new ArrayList<TouchPointer>();
		Vector tuioCursorList = tuioClient.getTuioCursors();
		for (int i = 0; i < tuioCursorList.size(); i++) {
			TuioCursor tcur = (TuioCursor) tuioCursorList.elementAt(i);
			TouchPointer pointer = tuioToTouchPointer(canvas, tcur);

			pointers.add(pointer);

		}

		if (parent.mousePressed) {
			
			if (lastPressedStart == 0) {
				lastPressedStart = parent.millis();
			}
			
			TouchPointer pointer = mouseToPointer(canvas, parent);

			pointers.add(pointer);
			
		} else {
			
			lastPressedStart = 0;
			
		}

		return pointers;
	}

	public TouchPointer mouseToPointer(PGraphics canvas, PApplet parent) {
		TouchPointer pointer = new TouchPointer(-1, parent.mouseX
				/ (float) canvas.width, parent.mouseY
				/ (float) canvas.height, lastPressedStart);

		TouchPointer pointerLast = new TouchPointer(-1, parent.pmouseX
				/ (float) canvas.width, parent.pmouseY
				/ (float) canvas.height, lastPressedStart);
		
		pointer.init(canvas.width, canvas.height);
		
		pointer.last = pointerLast;
		return pointer;
	}

	public TouchPointer tuioToTouchPointer(PGraphics canvas, TuioCursor tcur) {
		Vector pointList = tcur.getPath();

		TouchPointer pointer = new TouchPointer(tcur.getCursorID(),
				tcur.getX(), tcur.getY(), tcur.getStartTime()
						.getTotalMilliseconds());

		// aquí inicializamos con el tamaño para simplificar
		pointer.init(canvas.width, canvas.height);

		if (tcur.getPath().size() > 1) {
			TuioPoint lastPoint = tcur.getPath().get(1);
			TouchPointer pointerBefore = new TouchPointer(tcur.getCursorID(),
					lastPoint.getX(), lastPoint.getY(), tcur.getStartTime()
							.getTotalMilliseconds());
			pointer.last = pointerBefore;
		}

		pointer.vel = tcur.getMotionSpeed();
		pointer.acc = tcur.getMotionAccel();
		return pointer;
	}

	public void drawBpTouches(PGraphics canvas, PApplet parent) {

		List<TouchPointer> pointers = getPointers(canvas, parent);

		canvas.noFill();
		canvas.stroke(255, 0, 0);
		canvas.strokeWeight(3);

		for (int i = 0; i < pointers.size(); i++) {
			TouchPointer touchPointer = pointers.get(i);
			canvas.ellipse(touchPointer.getScreenX(canvas.width),
					touchPointer.getScreenY(canvas.height), 30, 30);
			canvas.text("" + touchPointer.millisStartTouch,
					touchPointer.getScreenX(canvas.width) + 20,
					touchPointer.getScreenY(canvas.height));
		}
	}

	public void drawTUIO(PGraphics canvas) {

		float cur_size = cursor_size * scale_factor;

		Vector tuioCursorList = tuioClient.getTuioCursors();
		for (int i = 0; i < tuioCursorList.size(); i++) {
			TuioCursor tcur = (TuioCursor) tuioCursorList.elementAt(i);
			Vector pointList = tcur.getPath();

			if (pointList.size() > 0) {
				canvas.stroke(0, 0, 255);
				TuioPoint start_point = (TuioPoint) pointList.firstElement();

				;
				for (int j = 0; j < pointList.size(); j++) {
					TuioPoint end_point = (TuioPoint) pointList.elementAt(j);
					canvas.line(start_point.getScreenX(canvas.width),
							start_point.getScreenY(canvas.height),
							end_point.getScreenX(canvas.width),
							end_point.getScreenY(canvas.height));
					start_point = end_point;
				}

			}
		}
	}

}
