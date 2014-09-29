package org.edumo.gui.button;


import org.edumo.gui.Component;
import org.edumo.gui.HIDEventListener;
import org.edumo.touch.TouchPointer;

import processing.core.PGraphics;
import processing.core.PVector;

public abstract class AbstractButton extends Component implements
		HIDEventListener {

	protected boolean pressed;
	protected Integer tempIdTouch;
	protected String action;

	public abstract String draw(PGraphics canvas);

	public abstract boolean isOver(PVector pos);

	public String getAction() {
		return action;
	}

	public String hidPressed(TouchPointer touche) {
		
		PVector touchPos = touche.getScreen();
		
		if(!active)
			return null;
		
		if (isOver(touchPos)) {
			pressed = true;
			tempIdTouch = touche.id;
			return "pressed:" + action;
		}

		return null;
	}

	public String hidReleased(TouchPointer touche) {
		
		if(!active)
			return null;
		
		if (tempIdTouch != null && touche.id == tempIdTouch) {
			// pulsamos y soltamos el mismo botn
			pressed = false;
			tempIdTouch = null;
			if (isOver(touche.getScreen())) {
				return action;
			}
		} else {
			if (isOver(touche.getScreen())) {
				pressed = false;
			}
		}

		return null;
	}

	public String hidDragged(TouchPointer touche) {

		String ret = null;

		if(!active)
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
		return ret;
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

}
