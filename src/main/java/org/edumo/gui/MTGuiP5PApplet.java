package org.edumo.gui;

import org.edumo.content.BaseApp;
import org.edumo.touch.TouchPointer;

import TUIO.TuioCursor;
import TUIO.TuioTime;
import processing.core.PApplet;

public abstract class MTGuiP5PApplet extends PApplet {

	protected BaseApp mtContext;

	protected Window window;

	protected float scale = 1.0f;

	public float getScale() {
		return scale;
	}

	@Override
	public void scale(float s) {
		this.scale = 1.0f/s;
		super.scale(s);
	}

	protected void checkMouse() {
		if (mtContext.ignoreMouseIfTUIO)
			mtContext.ignoreMouse = true;
	}

	public void refresh(TuioTime time, Integer port ){
		
		
	}
	
	public void addTuioCursor(TuioCursor tcur, Integer port) {
		checkMouse();
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur, scale, port);
			ActionEvent action = window.hidPressed(touchPointer);
			doAction(action);
		}
	}
	
	public void addTuioCursor(TuioCursor tcur) {

		checkMouse();
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur, scale);
			ActionEvent action = window.hidPressed(touchPointer);
			doAction(action);
		}

	}

	public void updateTuioCursor(TuioCursor tcur,Integer port) {
		checkMouse();
		checkMouse();
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur, scale, port);
			ActionEvent action = window.hidDragged(touchPointer);
			doAction(action);
		}
	}
	
	public void updateTuioCursor(TuioCursor tcur) {
		checkMouse();
		checkMouse();
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur, scale);
			ActionEvent action = window.hidDragged(touchPointer);
			doAction(action);
		}
	}

	abstract protected void doAction(ActionEvent action);

	public void removeTuioCursor(TuioCursor tcur,Integer port) {
		checkMouse();
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur, scale, port);
			ActionEvent action = window.hidReleased(touchPointer);
			doAction(action);
		}	
	}
	
	public void removeTuioCursor(TuioCursor tcur) {

		checkMouse();

		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur, scale);
			ActionEvent action = window.hidReleased(touchPointer);
			doAction(action);
		}
	}

	@Override
	public void mousePressed() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this, scale);
			ActionEvent action = window.hidPressed(touchPointer);
			doAction(action);
		}
	}

	@Override
	public void mouseReleased() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this, scale);
			ActionEvent action = window.hidReleased(touchPointer);
			doAction(action);
		}
	}

	@Override
	public void mouseDragged() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this, scale);
			ActionEvent action = window.hidDragged(touchPointer);
			doAction(action);
		}
	}

	public void mouseOver() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this, scale);
			ActionEvent action = window.hidMoved(touchPointer);
			doAction(action);
		}
	}
}
