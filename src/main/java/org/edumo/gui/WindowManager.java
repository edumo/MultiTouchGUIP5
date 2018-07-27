package org.edumo.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.edumo.content.ContentManager;
import org.edumo.gui.behaviour.DraggableDecorator;
import org.edumo.gui.behaviour.PlayableDecorator;
import org.edumo.gui.behaviour.ResizableDecorator;
import org.edumo.gui.button.AbstractButton;
import org.edumo.gui.button.ButtonCapture;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.button.ButtonVideo;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.ButtonImagePortView;
import org.edumo.gui.button.ButtonImagePortViewVertical;
import org.edumo.gui.button.ButtonSlider;
import org.edumo.gui.button.CheckBoxImage;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.video.Capture;
import processing.video.Movie;

/**
 * Gestor de propagacin de eventos
 * 
 * @author edumo
 * 
 */

public class WindowManager implements HIDEventListener {

	private List<HIDEventListener> listeners = new ArrayList<HIDEventListener>();

	public ContentManager contentManager;
	private Window window;

	public WindowManager(ContentManager contentManager, Window window) {
		super();
		this.contentManager = contentManager;
		this.window = window;
	}

	public void addListener(HIDEventListener listener) {
		listeners.add(listener);
	}

	public void addListener(int index, HIDEventListener listener) {
		listeners.add(index, listener);
	}

	public void removeListener(HIDEventListener listener) {
		listeners.remove(listener);
	}

	public ActionEvent hidPressed(TouchPointer touchPointer) {
		return pressed(listeners, touchPointer);
	}

	public ActionEvent pressed(List<HIDEventListener> actionEvent,
			TouchPointer touchPointer) {
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

	public ActionEvent drag(List<HIDEventListener> listeners,
			TouchPointer touchPointer) {
		ActionEvent action = null;
		for (int i = 0; i < listeners.size(); i++) {
			HIDEventListener button = listeners.get(i);
			if (button.isActive()) {
				try {
					action = button.hidDragged(touchPointer);
					if (action != null) {
						break;
					}
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

	public ActionEvent release(List<HIDEventListener> listeners,
			TouchPointer touchPointer) {
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

	public ActionEvent over(List<HIDEventListener> listeners,
			TouchPointer touchPointer) {
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

	public List<ActionEvent> drawComponentes(List<GUIComponent> components,
			PGraphics canvas) {

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

	public List<ActionEvent> drawComponentes(List<GUIComponent> components,
			PGraphics canvas, int tint) {

		List<ActionEvent> actions = new ArrayList<ActionEvent>();

		for (int i = 0; i < components.size(); i++) {
			GUIComponent component = components.get(i);
			String action = null;

			if (component.isRendered()) {
				float t = PApplet.map(component.pos.z, -100.0f, 150.0f, 0f,
						255f);
				action = component.draw(canvas, 255/* (int) t */);
			}
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

	public ButtonImage addButton(String action, int x, int y, String imgPath,
			String pressedImgPath, int align) {
		return addButton(action, x, y, imgPath, pressedImgPath, true, align);
	}

	public ButtonImage addButton(String action, int x, int y, String imgPath,
			String pressedImgPath, boolean insertComponent, int align) {

		return addButton(action, x, y, imgPath, pressedImgPath,
				insertComponent, align, window.components);
	}

	// --------------------------------------------------------------------------------------------
	// Buttons
	public ButtonImage addButton(String action, int x, int y, String imgPath,
			String pressedImgPath) {
		return addButton(action, x, y, imgPath, pressedImgPath, true,
				PApplet.CENTER);
	}

	public ButtonImage addButton(String action, int x, int y, String imgPath,
			String pressedImgPath, boolean insertComponent, int align,
			List<GUIComponent> componentsTemp) {

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

	public ButtonVideo addButtonVideo(String action, int x, int y,
			String imgPath, boolean insertComponent, int align) {

		return addButtonVideo(action, x, y, imgPath, insertComponent, align,
				window.components);
	}

	// --------------------------------------------------------------------------------------------
	// Buttons ButtonVideo
	public ButtonVideo addButtonVideo(String action, int x, int y,
			String imgPath) {
		return addButtonVideo(action, x, y, imgPath, true, PApplet.CENTER);
	}

	public ButtonCapture addButtonCapture(String action, int x, int y,
			boolean insertComponent, int align,
			List<GUIComponent> componentsTemp,Capture cam) {

		ButtonCapture button = new ButtonCapture();
		button.init(action, null, null, new PVector(x, y), 33, align);
		button.setSize(800, 600);
		addListener(button);
		if (insertComponent)
			addComponent(button, componentsTemp);

		if (cam != null) {
			cam.start();
			button.setCapture(cam);
		}

		return button;

	}

	public ButtonVideo addButtonVideo(String action, int x, int y,
			String imgPath, boolean insertComponent, int align,
			List<GUIComponent> componentsTemp) {

		Movie img = contentManager.loadVideo(imgPath);
		img.play();
		img.jump(img.duration() * 0.33f);

		// img.jump(0);
		img.volume(0);
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		img.pause();
		PImage thmb = img.get();

		thmb.loadPixels();

		int nRepetitions = 0;
		while (true) {
			float brightness = 0.0f;
			for (int i = 0; i < thmb.width; i++) {
				for (int j = 0; j < thmb.height; j++) {
					brightness += contentManager.parent.brightness(thmb.get(i,
							j));
				}
			}
			brightness = brightness / (float) (thmb.width * thmb.height);

			if (brightness < 1.0f) {
				img.play();
				img.jump(contentManager.parent.random(img.duration()));

				// img.jump(0);
				img.volume(0);
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				img.pause();
				thmb = img.get();
				thmb.loadPixels();
			} else {
				break;
			}

			nRepetitions++;
			if (nRepetitions > 50)
				break;
		}

		/*
		 * int p1 = thmb.get((int) (thmb.width * 0.2f), (int) (thmb.height *
		 * 0.2f)); int p2 = thmb.get((int) (thmb.width * 0.3f), (int)
		 * (thmb.height * 0.3f)); int p3 = thmb.get((int) (thmb.width * 0.4f),
		 * (int) (thmb.height * 0.4f)); int p4 = thmb.get((int) (thmb.width *
		 * 0.8f), (int) (thmb.height * 0.8f));
		 * 
		 * float avg = (contentManager.parent.brightness(p1) +
		 * contentManager.parent.brightness(p2) +
		 * contentManager.parent.brightness(p3) +
		 * contentManager.parent.brightness(p4)) / 4;
		 * 
		 * if (avg < 60) { img.play(); img.jump(img.duration() * 0.53f);
		 * 
		 * try { Thread.sleep(5); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 * 
		 * img.pause(); thmb = img.get(); } else {
		 * System.out.println("EL BRILLO FUE avg " + avg); }
		 */
		int size = img.width / 2;
		ButtonVideo button = new ButtonVideo();
		button.init(action, thmb, thmb, new PVector(x, y), size, align);

		addListener(button);
		if (insertComponent)
			addComponent(button, componentsTemp);

		button.setMovie(img);
		button.setThumbnail(thmb);

		return button;
	}

	public ButtonSlider addButtonSlider(String action, int x, int y,
			String imgPath, String pressedImgPath, int align) {
		return addButtonSlider(action, x, y, imgPath, pressedImgPath, true,
				align);
	}

	public ButtonSlider addButtonSlider(String action, int x, int y,
			String imgPath, String pressedImgPath, boolean insertComponent,
			int align) {

		return addButtonSlider(action, x, y, imgPath, pressedImgPath,
				insertComponent, align, window.components);
	}

	public ButtonSlider addButtonSlider(String action, int x, int y,
			String imgPath, String pressedImgPath, boolean insertComponent,
			int align, List<GUIComponent> componentsTemp) {

		PImage img = contentManager.loadImage(imgPath);
		PImage pressedImg = contentManager.loadImage(pressedImgPath);
		int size = img.width / 2;
		ButtonSlider button = new ButtonSlider();
		button.init(action, img, pressedImg, new PVector(x, y), size, align);

		addListener(button);
		if (insertComponent)
			addComponent(button, componentsTemp);

		return button;
	}

	public ButtonImagePortView addButtonPortView(String action, int x, int y,
			String imgPath, String pressedImgPath, int align) {
		return addButtonPortView(action, x, y, imgPath, pressedImgPath, true,
				align);
	}

	public ButtonImagePortView addButtonPortView(String action, int x, int y,
			String imgPath, String pressedImgPath, boolean insertComponent,
			int align) {

		return addButtonPortView(action, x, y, imgPath, pressedImgPath,
				insertComponent, align, window.components);
	}

	public ButtonImagePortView addButtonPortView(String action, int x, int y,
			String imgPath, String pressedImgPath, boolean insertComponent,
			int align, List<GUIComponent> componentsTemp) {

		PImage img = contentManager.loadImage(imgPath);
		PImage pressedImg = contentManager.loadImage(pressedImgPath);
		int size = img.width / 2;
		ButtonImagePortView button = new ButtonImagePortView();
		button.init(action, img, pressedImg, new PVector(x, y), size, align);

		addListener(button);
		if (insertComponent)
			addComponent(button, componentsTemp);

		return button;
	}

	public ButtonImagePortViewVertical addButtonPortViewVertical(String action,
			int x, int y, String imgPath, String pressedImgPath, int align) {
		return addButtonPortViewVertical(action, x, y, imgPath, pressedImgPath,
				true, align);
	}

	public ButtonImagePortViewVertical addButtonPortViewVertical(String action,
			int x, int y, String imgPath, String pressedImgPath,
			boolean insertComponent, int align) {

		return addButtonPortViewVertical(action, x, y, imgPath, pressedImgPath,
				insertComponent, align, window.components);
	}

	public ButtonImagePortViewVertical addButtonPortViewVertical(String action,
			int x, int y, String imgPath, String pressedImgPath,
			boolean insertComponent, int align,
			List<GUIComponent> componentsTemp) {

		PImage img = contentManager.loadImage(imgPath);
		PImage pressedImg = contentManager.loadImage(pressedImgPath);
		int size = img.width / 2;
		ButtonImagePortViewVertical button = new ButtonImagePortViewVertical();
		button.init(action, img, pressedImg, new PVector(x, y), size, align);

		addListener(button);
		if (insertComponent)
			addComponent(button, componentsTemp);

		return button;
	}

	public CheckBoxImage addCheckBox(String action, int x, int y,
			String imgPath, String pressedImgPath, String checkImgPath,
			boolean insertComponent, int align) {

		PImage img = contentManager.loadImage(imgPath);
		PImage pressedImg = contentManager.loadImage(pressedImgPath);
		PImage checkedImg = contentManager.loadImage(checkImgPath);

		int size = img.width / 2;
		CheckBoxImage button = new CheckBoxImage();
		button.init(action, img, pressedImg, checkedImg, new PVector(x, y),
				size, align);

		addListener(button);
		if (insertComponent)
			addComponent(button);

		return button;
	}

	// --------------------------------------------------------------------------------------------
	// Text buttons
	public ButtonText addTextButton(PGraphics canvas, String nombre, int x,
			int y, int textSize, int textAlign) {

		return addTextButton(canvas, nombre, nombre, x, y, textSize, textAlign);
	}

	public ButtonText addTextButton(PGraphics canvas, String nombre,
			String action, int x, int y, int textSize, int textAlign) {
		return addTextButton(canvas, nombre, action, x, y, textSize, textAlign,
				window.components);
	}

	public ButtonText addTextButton(PGraphics canvas, String nombre,
			String action, int x, int y, int textSize, int textAlign,
			List<GUIComponent> components) {

		ButtonText textButon = new ButtonText();
		textButon.init(canvas, nombre, action, new PVector(x, y), textSize,
				255, textAlign);

		addListener(textButon);
		addComponent(textButon, components);

		return textButon;
	}

	public void removeDraggable(DraggableDecorator decorator) {
		removeListener(decorator);
	}

	public DraggableDecorator addDraggable(GUIComponent component) {

		DraggableDecorator draggableGuiComponent = new DraggableDecorator();
		draggableGuiComponent.init(component);

		// Remove events to this decorated component
		if (component instanceof HIDEventListener)
			removeListener((HIDEventListener) component);

		// removeComponent(component);

		// Now, we want to start to capture events
		addListener(draggableGuiComponent);
		// addComponent(draggableGuiComponent);

		return draggableGuiComponent;
	}

	public ResizableDecorator addResizable(GUIComponent component) {

		ResizableDecorator resizableGuiComponent = new ResizableDecorator();
		resizableGuiComponent.init(component);

		if (component instanceof HIDEventListener)
			removeListener((HIDEventListener) component);

		// removeComponent(component);

		addListener(resizableGuiComponent);
		// addComponent(resizableGuiComponent);

		return resizableGuiComponent;
	}

	public PlayableDecorator addPlayable(GUIComponent component) {

		PlayableDecorator playableDecorator = new PlayableDecorator();
		playableDecorator.init(component);

		if (component instanceof HIDEventListener)
			removeListener((HIDEventListener) component);

		// removeComponent(component);

		addListener(playableDecorator);
		// addComponent(resizableGuiComponent);

		return playableDecorator;
	}

}
