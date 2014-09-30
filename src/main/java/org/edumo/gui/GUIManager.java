package org.edumo.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.edumo.content.ContentManager;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.DragableImage;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Gestor de propagacin de eventos
 * 
 * @author edumo
 * 
 */

public class GUIManager {

	private List<HIDEventListener> listeners = new ArrayList<HIDEventListener>();

	private Logger logger = Logger.getLogger(this.getClass());
	
	private ContentManager contentManager;

	public GUIManager(ContentManager contentManager) {
		super();
		this.contentManager = contentManager;
	}

	public void addListener(HIDEventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(HIDEventListener listener) {
		listeners.remove(listener);
	}

	public ActionEvent pressed(TouchPointer touchPointer) {
		return pressed(listeners, touchPointer);
	}

	public ActionEvent pressed(List<HIDEventListener> listeners,
			TouchPointer touchPointer) {

		for (int i = listeners.size() - 1; i >= 0; i--) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					String action = button.hidPressed(touchPointer);
					if (action != null) {
						// System.out.println("accion " + action);
						return new ActionEvent(action, (Component) button);
					}
				} catch (Exception e) {
					logger.error(
							"error al manejar el pressed en "
									+ button.getClass() + " " + e, e);
				}
			}
		}

		return null;
	}

	public ActionEvent drag(TouchPointer touchPointer) {
		return drag(listeners, touchPointer);
	}

	public ActionEvent drag(List<HIDEventListener> listeners,
			TouchPointer touchPointer) {
		for (int i = 0; i < listeners.size(); i++) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					String action = button.hidDragged(touchPointer);
					if (action != null) {
						return new ActionEvent(action, (Component) button);
					}
				} catch (Exception e) {
					logger.error(
							"error al manejar el drag en " + button.getClass()
									+ " " + e, e);
				}
			}
		}
		return null;
	}

	public ActionEvent release(TouchPointer touchPointer) {
		return release(listeners, touchPointer);
	}

	public ActionEvent release(List<HIDEventListener> listeners,
			TouchPointer touchPointer) {
		ActionEvent action = null;
		for (int i = 0; i < listeners.size(); i++) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					String actionTemp = button.hidReleased(touchPointer);
					if (actionTemp != null) {
						action = new ActionEvent(actionTemp, (Component) button);
					}
				} catch (Exception e) {
					logger.error(
							"error al manejar el released en "
									+ button.getClass() + " " + e, e);
				}
			}
		}

		return action;
	}

	public List<ActionEvent> drawComponentes(List<Component> components,
			PGraphics canvas) {

		List<ActionEvent> actions = new ArrayList<ActionEvent>();

		for (int i = 0; i < components.size(); i++) {
			Component component = components.get(i);
			String action = null;

			if (component.isRendered())
				action = component.draw(canvas);

			if (action != null) {
				ActionEvent actionEvent = new ActionEvent(action, component);
				actions.add(actionEvent);
			}
		}

		return actions;
	}

	public ButtonText addTextButton(PGraphics canvas, String nombre,
			String action, int x, int y, int textSize, int textAlign) {

		ButtonText textButon = new ButtonText();
		textButon.init(canvas, nombre, action, new PVector(x, y), textSize,
				255, textAlign);
		addListener(textButon);
		return textButon;
	}
	
	public ButtonImage addButton(String action, int x, int y, String imgPath,
			String pressedImgPath) {

		PImage img = contentManager.loadImage(imgPath);
		PImage pressedImg = contentManager.loadImage(pressedImgPath);
		int size = img.width / 2;
		ButtonImage button = new ButtonImage();
		button.init(action, img, pressedImg, new PVector(x, y), size,
				PApplet.CENTER);

		addListener(button);

		return button;
	}
	
	public DragableImage addDraggableImage(String action, int x, int y, String imgPath) {

		PImage img = contentManager.loadImage(imgPath);
		int size = img.width / 2;
		DragableImage button = new DragableImage();
		button.init(action, img, new PVector(x, y), size,
				PApplet.CENTER);

		addListener(button);

		return button;
	}

}
