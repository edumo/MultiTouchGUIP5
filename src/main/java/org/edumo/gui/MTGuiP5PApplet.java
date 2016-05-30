package org.edumo.gui;

import org.edumo.content.BaseApp;
import org.edumo.touch.TouchPointer;

import TUIO.TuioCursor;
import processing.core.PApplet;

public abstract class MTGuiP5PApplet extends PApplet {

	protected BaseApp mtContext;

	protected Window window;

	protected void checkMouse() {
		if (mtContext.ignoreMouseIfTUIO)
			mtContext.ignoreMouse = true;
	}

	public void addTuioCursor(TuioCursor tcur) {

		checkMouse();
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur);
			ActionEvent action = window.hidPressed(touchPointer);
			// doAction(action);
		}

	}

	public void updateTuioCursor(TuioCursor tcur) {

		checkMouse();
		checkMouse();
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur);
			ActionEvent action = window.hidDragged(touchPointer);
			doAction(action);
		}
	}

	abstract protected void doAction(ActionEvent action);

	public void removeTuioCursor(TuioCursor tcur) {

		checkMouse();

		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter.tuioToTouchPointer(g, tcur);
			ActionEvent action = window.hidReleased(touchPointer);
			doAction(action);
		}
	}

	@Override
	public void mousePressed() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this);
			ActionEvent action = window.hidPressed(touchPointer);
			doAction(action);
		}
	}

	@Override
	public void mouseReleased() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this);
			ActionEvent action = window.hidReleased(touchPointer);
			doAction(action);
		}
	}

	@Override
	public void mouseDragged() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this);
			ActionEvent action = window.hidDragged(touchPointer);
			doAction(action);
		}
	}

	public void mouseOver() {
		if (!mtContext.ignoreMouse) {
			TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g, this);
			ActionEvent action = window.hidMoved(touchPointer);
			doAction(action);
		}
	}
}
