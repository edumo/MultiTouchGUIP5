package org.edumo.gui.behaviour;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.edumo.gui.AbstractGUIModifier;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.HIDEventListener;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonImagePortView;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class DraggableDecorator extends AbstractGUIModifier implements HIDEventListener {

	protected PVector posDraged = new PVector();
	protected PVector incDraged = new PVector();
	protected PVector posInitDrag = new PVector();

	protected String action;

	protected float walkedWay = 0;

	Map<Integer, TouchPointer> pointers = new HashMap<Integer, TouchPointer>();

	public void init(GUIComponent component) {
		this.component = component;
	}

	@Override
	public ActionEvent hidDragged(TouchPointer touche) {

		try {
			Iterator<TouchPointer> values = pointers.values().iterator();

			if (!values.hasNext())
				return null;

			// if (pointers.size() == 1) {

			TouchPointer p1 = values.next();

			if (p1.id == touche.id) {
				PVector dif = PVector.sub(touche.getScreen(), p1.getScreen());
				component.addPosition(dif);
				walkedWay += dif.mag();
				// actualizamos mi pos en funci�n de la diferencia y
				// actualizamos el map
				pointers.put(touche.id, touche);
				String compoundAction = "dragged::" + component;
				// return new ActionEvent(compoundAction, this);
			}
			// }else{
			// walkedWay = 0;
			// }
			//
		} catch (Exception e) {
			System.err.println("Error al dragar con behavviour draggable");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		if (!active)
			return null;

		TouchPointer removeObject = pointers.remove(touche.id);

		if (pointers.isEmpty()) {
			if (component instanceof AbstractButton) {
				((AbstractButton) component).pressed = false;
			}
		}

		if (removeObject != null) {
			PVector dif = PVector.sub(posInitDrag, touche.getScreen());
			float threshold = 5;
			if (dif.mag() < threshold && walkedWay < threshold) {
				if (component instanceof HIDEventListener) {
					HIDEventListener listener = (HIDEventListener) component;
					ActionEvent actionEvent = listener.hidPressed(touche);
					if (actionEvent == null || listener instanceof ButtonImagePortView) {
						actionEvent = listener.hidReleased(touche);
						if (actionEvent != null && listener instanceof ButtonImagePortView) {
							// modificación para poder asocir acciones al
							// espacio de iammgen
							ButtonImagePortView buttonImagePortView = (ButtonImagePortView) listener;
							PVector positionClicked = PVector.sub(touche.getScreen(), buttonImagePortView.realPos1);
							// ESTA ROTACIÓN LA HACEMOS PARA TRANSFORMAR EL
							// CLICK ENEL ESPACIO DE COORDENADAS DEL BOTON
							PVector direction = PVector.sub(buttonImagePortView.realPos2, buttonImagePortView.realPos1);
							float myrot = PVector.angleBetween(direction, new PVector(1, 0));

							positionClicked.rotate(myrot);

							String action = buttonImagePortView.findAction(PApplet.abs(positionClicked.x));
							// System.out.println("action"+action+" posClick
							// "+positionClicked);
							actionEvent.setAction(action);
							return actionEvent;
						} else {
							return actionEvent;
						}
					} else {
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
			if (component instanceof AbstractButton) {
				((AbstractButton) component).pressed = false;
			}
			// return new ActionEvent("selectDragImage", this);
			return new ActionEvent("pressedPoseido", this);
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