package org.edumo.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.edumo.content.ContentManager;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.CheckBoxImage;
import org.edumo.gui.decorator.DraggableGuiComponent;
import org.edumo.gui.decorator.ResizableGuiComponent;
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

public class WindowManager implements HIDEventListener {

	private List<HIDEventListener> listeners = new ArrayList<HIDEventListener>();

	private ContentManager contentManager;
	private Window window;

	public WindowManager(ContentManager contentManager, Window window) {
		super();
		this.contentManager = contentManager;
		this.window = window;
	}

	public void addListener(HIDEventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(HIDEventListener listener) {
		listeners.remove(listener);
	}

	public ActionEvent hidPressed(TouchPointer touchPointer) {
		return pressed(listeners, touchPointer);
	}

	public ActionEvent pressed(List<HIDEventListener> actionEvent, TouchPointer touchPointer) {
		ActionEvent action = null;
		for (int i = listeners.size() - 1; i >= 0; i--) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					action = button.hidPressed(touchPointer);
					if (action != null)
						break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return action;
	}

	public ActionEvent hidDragged(TouchPointer touchPointer) {
		return drag(listeners, touchPointer);
	}

	public ActionEvent drag(List<HIDEventListener> listeners, TouchPointer touchPointer) {
		ActionEvent action = null;
		for (int i = 0; i < listeners.size(); i++) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					action = button.hidDragged(touchPointer);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return action;
	}

	public ActionEvent hidReleased(TouchPointer touchPointer) {
		return release(listeners, touchPointer);
	}

	public ActionEvent release(List<HIDEventListener> listeners, TouchPointer touchPointer) {
		ActionEvent action = null;
		for (int i = 0; i < listeners.size(); i++) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					ActionEvent tempaction = button.hidReleased(touchPointer);
					if (tempaction != null) {
						action = tempaction;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return action;
	}

	public ActionEvent hidMoved(TouchPointer touchPointer) {
		return release(listeners, touchPointer);
	}

	public ActionEvent over(List<HIDEventListener> listeners, TouchPointer touchPointer) {
		ActionEvent action = null;
		for (int i = 0; i < listeners.size(); i++) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					ActionEvent tempaction = button.hidMoved(touchPointer);
					if (tempaction != null) {
						action = tempaction;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return action;
	}

	public List<ActionEvent> drawComponentes(List<GUIComponent> components, PGraphics canvas) {

		List<ActionEvent> actions = new ArrayList<ActionEvent>();

		for (int i = 0; i < components.size(); i++) {
			GUIComponent component = components.get(i);
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

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	// --------------------------------------------------------------------------------------------
	// Add components...
	public void addComponent(GUIComponent c) {
		window.components.add(c);
		c.setWindowManager(this);
	}
	
	public void removeComponent(GUIComponent c) {
		window.components.remove(c);
		c.setWindowManager(this);
	}

	public void addComponent(GUIComponent c, List<GUIComponent> components) {
		components.add(c);
		c.setWindowManager(this);
	}

	// --------------------------------------------------------------------------------------------
	// Buttons
	public ButtonImage addButton(String action, int x, int y, String imgPath, String pressedImgPath) {
		return addButton(action, x, y, imgPath, pressedImgPath, true, PApplet.CENTER);
	}

	public ButtonImage addButton(String action, int x, int y, String imgPath, String pressedImgPath,
			boolean insertComponent, int align, List<GUIComponent> componentsTemp) {

		PImage img = contentManager.loadImage(imgPath);
		PImage pressedImg = contentManager.loadImage(pressedImgPath);
		int size = img.width / 2;
		ButtonImage button = new ButtonImage();
		button.init(action, img, pressedImg, new PVector(x, y), size, align);

		addListener(button);
		if (insertComponent)
			addComponent(button, componentsTemp);

		return button;
	}

	public ButtonImage addButton(String action, int x, int y, String imgPath, String pressedImgPath, int align) {
		return addButton(action, x, y, imgPath, pressedImgPath, true, align);
	}

	public ButtonImage addButton(String action, int x, int y, String imgPath, String pressedImgPath,
			boolean insertComponent, int align) {

		return addButton(action, x, y, imgPath, pressedImgPath, insertComponent, align, window.components);
	}

	public CheckBoxImage addCheckBox(String action, int x, int y, String imgPath, String pressedImgPath,
			String checkImgPath, boolean insertComponent, int align) {

		PImage img = contentManager.loadImage(imgPath);
		PImage pressedImg = contentManager.loadImage(pressedImgPath);
		PImage checkedImg = contentManager.loadImage(checkImgPath);

		int size = img.width / 2;
		CheckBoxImage button = new CheckBoxImage();
		button.init(action, img, pressedImg, checkedImg, new PVector(x, y), size, align);

		addListener(button);
		if (insertComponent)
			addComponent(button);

		return button;
	}

	// --------------------------------------------------------------------------------------------
	// Text buttons
	public ButtonText addTextButton(PGraphics canvas, String nombre, int x, int y, int textSize, int textAlign) {

		return addTextButton(canvas, nombre, nombre, x, y, textSize, textAlign);
	}

	public ButtonText addTextButton(PGraphics canvas, String nombre, String action, int x, int y, int textSize,
			int textAlign) {
		return addTextButton(canvas, nombre, action, x, y, textSize, textAlign, window.components);
	}

	public ButtonText addTextButton(PGraphics canvas, String nombre, String action, int x, int y, int textSize,
			int textAlign, List<GUIComponent> components) {

		ButtonText textButon = new ButtonText();
		textButon.init(canvas, nombre, action, new PVector(x, y), textSize, 255, textAlign);

		addListener(textButon);
		addComponent(textButon, components);

		return textButon;
	}

	public DraggableGuiComponent addDraggable(String action, GUIComponent component) {

		DraggableGuiComponent draggableGuiComponent = new DraggableGuiComponent();
		draggableGuiComponent.init(component);

		if (component instanceof HIDEventListener)
			removeListener((HIDEventListener) component);
		
		removeComponent(component);

		addListener(draggableGuiComponent);
		addComponent(draggableGuiComponent);

		return draggableGuiComponent;
	}
	
	public ResizableGuiComponent addResizable(String action, GUIComponent component) {

		ResizableGuiComponent resizableGuiComponent = new ResizableGuiComponent();
		resizableGuiComponent.init(component);

		if (component instanceof HIDEventListener)
			removeListener((HIDEventListener) component);
		
		removeComponent(component);

		addListener(resizableGuiComponent);
		addComponent(resizableGuiComponent);

		return resizableGuiComponent;
	}


}
