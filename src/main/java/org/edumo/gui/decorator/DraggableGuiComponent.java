package org.edumo.gui.decorator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.HIDEventListener;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class DraggableGuiComponent extends Decorator implements HIDEventListener{

	protected PVector posDraged = new PVector();
	protected PVector incDraged = new PVector();
	protected PVector posInitDrag = new PVector();
	
	protected String action;
	
	protected float walkedWay = 0; 

	Map<Integer, TouchPointer> pointers = new HashMap<Integer, TouchPointer>();
	
	public void init(GUIComponent component){
		this.component = component;
	}
	
	@Override
	public ActionEvent hidDragged(TouchPointer touche) {
		if (pointers.isEmpty())
			return null;

		if (pointers.size() == 1) {
			TouchPointer p1 = pointers.values().iterator().next();
			if (p1.id == touche.id) {
				PVector dif = PVector.sub(touche.getScreen(), p1.getScreen());
				component.addPosition(dif);
				walkedWay += dif.mag();
				// actualizamos mi pos en funci�n de la diferencia y
				// actualizamos el map
				pointers.put(touche.id, touche);
				String compoundAction = "dragged::" + component;
				return new ActionEvent(compoundAction, this);
			}
		}else{
			walkedWay = 0;
		}
		
		return null;
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		if (!active)
			return null;

		TouchPointer removeObject = pointers.remove(touche.id);
		if(removeObject != null){
			PVector dif = PVector.sub(posInitDrag, touche.getScreen());
			float threshold = 2;
			if(dif.mag()<threshold && walkedWay < threshold){
				if(component instanceof HIDEventListener){
					HIDEventListener listener = (HIDEventListener)component;
					ActionEvent actionEvent = listener.hidPressed(touche);
					if(actionEvent == null){
						return listener.hidReleased(touche);
					}else{
						listener.hidReleased(touche);
						return actionEvent;
					}
				}
			}
		}

		return null;
	}

	@Override
	public ActionEvent hidPressed(TouchPointer touche) {
		PVector touchPos = touche.getScreen();

		if (!active)
			return null;

		if (component.isOver(touchPos)) {
			posInitDrag = touchPos;
			pointers.put(touche.id, touche);
			walkedWay = 0;
			//return new ActionEvent("selectDragImage", this);
			return null;
		}

		return null;
	}

	public void forceUpdateRealPos(PGraphics canvas) {
		updateRealPos(canvas);
	}

	@Override
	public ActionEvent hidMoved(TouchPointer touche) {
		return null;
	}

	@Override
	public String drawUndecorated(PGraphics canvas) {
		return component.drawUndecorated(canvas);
	}

}