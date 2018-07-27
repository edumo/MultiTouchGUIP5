package org.edumo.gui.behaviour;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.edumo.gui.AbstractGUIModifier;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.HIDEventListener;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.ButtonImagePortView;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.video.Movie;

public class PlayableDecorator extends AbstractGUIModifier implements HIDEventListener {

	boolean play = false;

	public void init(GUIComponent component) {
		this.component = component;
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		if (!active)
			return null;

		if (component instanceof HIDEventListener) {
			HIDEventListener listener = (HIDEventListener) component;
			ActionEvent actionEvent = listener.hidPressed(touche);
			if (actionEvent == null) {
				actionEvent = listener.hidReleased(touche);
				
				GUIComponent comp = component;
				
				if(component instanceof DraggableDecorator){
					DraggableDecorator decorator = (DraggableDecorator)component;
					comp = decorator.getComponent();
				}
				
				if (actionEvent != null && comp instanceof ButtonImage) {
					ButtonImage buttonImage = (ButtonImage) comp;
					if (buttonImage.img instanceof Movie) {
						Movie movie = (Movie) buttonImage.img;
						if (play) {
							movie.pause();
							play = false;
						} else {
							movie.play();
							play = true;
						}
					}
					// modificación para poder asocir acciones al espacio de
					// iammgen
					// System.out.println("action"+action+" posClick
					// "+positionClicked);
					return actionEvent;
				} else {
					return actionEvent;
				}
			} else {
				listener.hidReleased(touche);
				return actionEvent;
			}
		}

		return null;
	}

	@Override
	public ActionEvent hidDragged(TouchPointer touche) {
		if (component instanceof HIDEventListener) {
			HIDEventListener listener = (HIDEventListener) component;
			ActionEvent actionEvent = listener.hidDragged(touche);
			return actionEvent;
		}
		return null;
	}

	@Override
	public ActionEvent hidPressed(TouchPointer touche) {
		if (component instanceof HIDEventListener) {
			HIDEventListener listener = (HIDEventListener) component;
			ActionEvent actionEvent = listener.hidPressed(touche);
			return actionEvent;
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