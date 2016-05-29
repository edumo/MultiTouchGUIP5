package org.edumo.gui.button;

import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.HIDEventListener;
import org.edumo.touch.TouchPointer;

import processing.core.PGraphics;
import processing.core.PVector;

public abstract class AbstractButton extends GUIComponent implements
		HIDEventListener {

	protected boolean pressed;
	protected boolean moved;
	protected Integer tempIdTouch;
	protected String action;
	protected boolean actionOnPressed = false;
	
	//TODO mal, pero es lo que hay :/
	int imageMode = -1;

	public abstract String drawUndecorated(PGraphics canvas);

	public String getAction() {
		return action;
	}

	public ActionEvent hidPressed(TouchPointer touche) {

		PVector touchPos = touche.getScreen();

		if (!active)
			return null;

		if (isOver(touchPos)) {
			pressed = true;
			tempIdTouch = touche.id;
			if(actionOnPressed){
				return new ActionEvent("pressed:" + action, this);
			}else{
				return null;
			}
		}

		return null;
	}

	public ActionEvent hidReleased(TouchPointer touche) {

		if (!active)
			return null;

		if (tempIdTouch != null && touche.id == tempIdTouch) {
			// pulsamos y soltamos el mismo botn
			pressed = false;
			tempIdTouch = null;
			if (isOver(touche.getScreen())) {
				return new ActionEvent(action, this);
			}
		} else {
			if (isOver(touche.getScreen())) {
				pressed = false;
			}
		}

		return null;
	}

	public ActionEvent hidDragged(TouchPointer touche) {

		String action = null;

		if (!active)
			return null;

		if (tempIdTouch != null && touche.id == tempIdTouch) {

			PVector touchPos = touche.getScreen();
			if (!isOver(touchPos)) {
				pressed = false;
			} else {
				pressed = true;
			}

		} else {
			PVector touchPos = touche.getScreen();
			if (isOver(touchPos)) {
				pressed = true;
			} else {
				pressed = false;
			}
		}
		if (action != null)
			return new ActionEvent(action, this);
		else
			return null;
	}
	
	@Override
	public ActionEvent hidMoved(TouchPointer touche) {
		String action = null;

		if (!active)
			return null;

		if (tempIdTouch != null && touche.id == tempIdTouch) {

			PVector touchPos = touche.getScreen();
			if (!isOver(touchPos)) {
				moved = false;
			} else {
				moved = true;
			}

		} else {
			PVector touchPos = touche.getScreen();
			if (isOver(touchPos)) {
				moved = true;
			} else {
				moved = false;
			}
		}
		if (action != null)
			return new ActionEvent(action, this);
		else
			return null;
	}

	protected void init(String action, PVector pos) {
		this.pos = pos;
		this.action = action;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void hide() {
		setRenderedAndActive(false);
	}

	public void show() {
		setRenderedAndActive(true);
	}

}
