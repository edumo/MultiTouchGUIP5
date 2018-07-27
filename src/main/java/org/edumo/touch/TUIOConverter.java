package org.edumo.touch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.edumo.content.BaseApp;
import org.edumo.gui.MTGuiP5PApplet;

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

	TuioProcessing[] tuioClients;
	
	// Map<>
	Map<Integer, TouchPointer> grifos; // ID , Contador

	BaseApp mtContext;

	public void init(TuioProcessing[] tuioClients,  BaseApp mtContext) {
		this.tuioClients = tuioClients;
		this.mtContext = mtContext;
	}

	public List<TouchPointer> getPointers(PGraphics canvas, PApplet parent) {

		MTGuiP5PApplet guiP5PApplet = (MTGuiP5PApplet) parent;

		float cur_size = cursor_size * scale_factor;

		List<TouchPointer> pointers = new ArrayList<TouchPointer>();
		for(int t = 0; t < tuioClients.length; t++){
			ArrayList<TuioCursor> tuioCursorList = tuioClients[t].getTuioCursorList();
			for (int i = 0; i < tuioCursorList.size(); i++) {
				TuioCursor tcur = tuioCursorList.get(i);
				TouchPointer pointer = tuioToTouchPointer(canvas, tcur, guiP5PApplet.getScale(),tuioClients[t].port);
				pointers.add(pointer);
			}			
		}
		
		if (!mtContext.ignoreMouse) {
			if (parent.mousePressed) {

				if (lastPressedStart == 0) {
					lastPressedStart = parent.millis();
				}

				TouchPointer pointer = mouseToPointer(canvas, parent, guiP5PApplet.getScale());

				pointers.add(pointer);

			} else {

				lastPressedStart = 0;

			}
		}

		return pointers;
	}

	public TouchPointer mouseToPointer(PGraphics canvas, PApplet parent, float scale) {
		TouchPointer pointer = new TouchPointer(-1, parent.mouseX / (float) canvas.width * scale,
				(parent.mouseY / (float) canvas.height) * scale, lastPressedStart);

		TouchPointer pointerLast = new TouchPointer(-1, parent.pmouseX / (float) canvas.width * scale,
				(parent.pmouseY / (float) canvas.height) * scale, lastPressedStart);

		pointer.init(canvas.width, canvas.height);
		pointerLast.init(canvas.width, canvas.height);

		pointer.last = pointerLast;
		return pointer;
	}

	public TouchPointer tuioToTouchPointer(PGraphics canvas, TuioCursor tcur, float scale) {
		ArrayList<TuioPoint> pointList = tcur.getPath();

		TouchPointer pointer = new TouchPointer(tcur.getCursorID(), tcur.getX(), tcur.getY(),
				tcur.getStartTime().getTotalMilliseconds());

		// aquí inicializamos con el tamaño para simplificar
		pointer.init(canvas.width, canvas.height);

		if (tcur.getPath().size() > 1) {
			TuioPoint lastPoint = tcur.getPath().get(1);
			TouchPointer pointerBefore = new TouchPointer(tcur.getCursorID(), lastPoint.getX() * scale,
					lastPoint.getY() * scale, tcur.getStartTime().getTotalMilliseconds());
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
			canvas.ellipse(touchPointer.getScreenX(canvas.width), touchPointer.getScreenY(canvas.height), 30, 30);
			canvas.text("" + touchPointer.millisStartTouch, touchPointer.getScreenX(canvas.width) + 20,
					touchPointer.getScreenY(canvas.height));
		}
	}

	public void drawTUIO(PGraphics canvas, PApplet parent) {

		MTGuiP5PApplet guiP5PApplet = (MTGuiP5PApplet) parent;
		float cur_size = cursor_size * scale_factor;
		for(int t = 0; t < tuioClients.length; t++){
			ArrayList<TuioCursor> tuioCursorList = tuioClients[t].getTuioCursorList();
			for (int i = 0; i < tuioCursorList.size(); i++) {
				TuioCursor tcur = tuioCursorList.get(i);
				TouchPointer pointer = tuioToTouchPointer(canvas, tcur, guiP5PApplet.getScale(),tuioClients[t].port);
				canvas.noFill();
				canvas.stroke(1);
				canvas.ellipse(pointer.x, pointer.y, 10, 10);
				canvas.text(pointer.id, pointer.x+7, pointer.y+7);			
			}			
		}
	}

	public TouchPointer tuioToTouchPointer(PGraphics canvas, TuioCursor tcur, float scale, int port) {
		ArrayList<TuioPoint> pointList = tcur.getPath();

		int cursorID = 0;
		float x = 0, y = 0;
		TouchPointer pointer;
		
		if(port==tuioClients[0].port){
			
			x = tcur.getX()/2 + 0.5f;
			y = tcur.getY()/2 + 0.5f;
			cursorID = tcur.getCursorID();
			
		} else if(port==tuioClients[1].port){
			
			x = tcur.getX()/2;
			y = tcur.getY()/2+0.5f;
			cursorID = tcur.getCursorID()+10;
			
		} else if(port == tuioClients[2].port) {
			
			x = 0.5f - tcur.getX()/2;
			y = 0.5f - tcur.getY()/2;
			cursorID = tcur.getCursorID()+20;
			
		} else if(port == tuioClients[3].port) {
			
			x = 1.0f - tcur.getX()/2;
			y = 0.5f - tcur.getY()/2;
			cursorID = tcur.getCursorID()+30;
			
		}
		
		pointer = new TouchPointer(cursorID, x, y,
				tcur.getStartTime().getTotalMilliseconds());

		// aquí inicializamos con el tamaño para simplificar
		pointer.init(canvas.width, canvas.height);

		if (tcur.getPath().size() > 1) {
			TuioPoint lastPoint = tcur.getPath().get(1);
			TouchPointer pointerBefore = new TouchPointer((int) cursorID, lastPoint.getX() * scale,
					lastPoint.getY() * scale, tcur.getStartTime().getTotalMilliseconds());
			pointer.last = pointerBefore;
		}

		pointer.vel = tcur.getMotionSpeed();
		pointer.acc = tcur.getMotionAccel();
		//mtContext.parent.println("cursorID"+cursorID);
		return pointer;
	}

}
