package org.edumo.gui.button;

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

public class DraggableGuiComponent extends GUIComponent implements HIDEventListener{

	protected PVector posDraged = new PVector();
	protected PVector incDraged = new PVector();
	protected PVector posInitDrag = new PVector();
	
	protected GUIComponent component;
	
	protected String action;

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
				component.getPosition().add(dif);
				// actualizamos mi pos en funci�n de la diferencia y
				// actualizamos el map
				pointers.put(touche.id, touche);
				String compoundAction = "dragged::" + action;
				return new ActionEvent(compoundAction, this);
			}
		}

		/*if (pointers.size() >= 2) {
			Iterator<TouchPointer> pointsActive = pointers.values().iterator();

			TouchPointer activePointer = null;
			TouchPointer activePointerOld = null;
			// buscamos el primer implicado
			while (pointsActive.hasNext()) {
				TouchPointer touchPointer = (TouchPointer) pointsActive.next();
				if (touchPointer.id == touche.id) {
					// Se mueve uno de los dos
					// primero zoom vemos distancia anterior y la nueva
					activePointer = touche;
					activePointerOld = touchPointer;
				}
			}
			// buscamos alg�n segundo implicado
			TouchPointer activePointer2 = null;
			pointsActive = pointers.values().iterator();
			if (activePointer != null) {
				while (pointsActive.hasNext()) {
					TouchPointer touchPointer = (TouchPointer) pointsActive
							.next();
					if (touchPointer.id != activePointer.id) {
						// Se mueve uno de los dos
						// primero zoom vemos distancia anterior y la nueva
						activePointer2 = touchPointer;
					}
				}

				PVector tpv1 = activePointer.getScreen();
				PVector tpv1Old = activePointerOld.getScreen();
				PVector tpv2 = activePointer2.getScreen();

				// tenemos los dos analizamos su distancia actual y la nueva
				float distX = PApplet.dist(tpv1.x, tpv1.y, tpv2.x, tpv2.y);
				float distXOld = PApplet.dist(tpv1Old.x, tpv1Old.y, tpv2.x,
						tpv2.y);
				distX /= img.width;
				distXOld /= img.width;
				component.getWidth() + distX - distXOld;//resizeOnDraw.y += distX - distXOld;
				resizeOnDraw.x += distX - distXOld;

				pointers.put(touche.id, touche);
			}
		}*/

		return null;
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		PVector touchPos = touche.getScreen();

		if (!active)
			return null;

		TouchPointer removeObject = pointers.remove(touche.id);

		return null;

	}

	@Override
	public ActionEvent hidPressed(TouchPointer touche) {
		PVector touchPos = touche.getScreen();

		if (!active)
			return null;

		if (component.isOver(touchPos)) {
			pointers.put(touche.id, touche);
			return new ActionEvent("selectDragImage", this);
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