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

	protected boolean dragabble = false;

	protected boolean movable = false;

	protected PVector posDraged = new PVector();
	protected PVector incDraged = new PVector();
	protected PVector posInitDrag = new PVector();
	protected PVector minDraggedAbsPos;
	protected PVector maxDraggedAbsPos;
	
	public abstract String draw(PGraphics canvas);

	public abstract boolean isOver(PVector pos);

	public void setDraggable() {
		dragabble = true;
	}

	public void setMoveable() {
		movable = true;
	}

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
			if (dragabble) {
				posDraged = new PVector();
				posInitDrag = touche.getScreen();
			}

			if (movable) {
				posDraged = pos.get();
				posInitDrag = PVector.sub(touche.getScreen(), pos);
			}
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

			if (dragabble) {
				incDraged.x = posDraged.x;
				incDraged.y = posDraged.y;

				posDraged = PVector.sub(touche.getScreen(), posInitDrag);

				incDraged.sub(posDraged);

				ret = action;
			}

			if (movable) {
				incDraged.x = posDraged.x;
				incDraged.y = posDraged.y;
				posDraged = PVector.sub(touche.getScreen(), posInitDrag);
				if(minDraggedAbsPos!=null){
					if(posDraged.x < minDraggedAbsPos.x)
						posDraged.x = minDraggedAbsPos.x;
					if(posDraged.y < minDraggedAbsPos.y)
						posDraged.y = minDraggedAbsPos.y;
				}
				if(maxDraggedAbsPos!=null){
					if(posDraged.x > maxDraggedAbsPos.x)
						posDraged.x = maxDraggedAbsPos.x;
					if(posDraged.y > maxDraggedAbsPos.y)
						posDraged.y = maxDraggedAbsPos.y;
				}
				pos = posDraged.get();
				incDraged.sub(posDraged);
				ret = action;
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
	
	public PVector getMinDraggedAbsPos() {
		return minDraggedAbsPos;
	}

	public void setMinDraggedAbsPos(PVector minDraggedPos) {
		this.minDraggedAbsPos = minDraggedPos;
	}

	public PVector getMaxDraggedAbsPos() {
		return maxDraggedAbsPos;
	}

	public void setMaxDraggedAbsPos(PVector maxDraggedPos) {
		this.maxDraggedAbsPos = maxDraggedPos;
	}

}
