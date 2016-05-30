package org.edumo.gui;

import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

import org.edumo.gui.decorator.Decorator;
import org.edumo.gui.decorator.ResizableDecorator;

import de.looksgood.ani.Ani;
import de.looksgood.ani.easing.Easing;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class GUIComponent {

	protected WindowManager windowManager;
	protected PApplet parent;
	protected PVector pos = new PVector();
	protected PVector posTarget = new PVector();
	protected PVector realPos = null; // posicin real en la pantalla
	protected float width;
	protected float height;
	protected List<GUIComponent> components = new ArrayList<GUIComponent>();
	protected boolean rendered = true;
	protected boolean active = true;

	protected PVector resizeOnDraw = null;
	protected PVector resizeOnDrawTarget = null;

	protected float rotation = 0;
	protected float targetRotation = 0;

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setResizeOnDraw(PVector resizeOnDraw) {
		this.width = (int) resizeOnDraw.x;
		this.height = (int) resizeOnDraw.y;
		this.resizeOnDraw = resizeOnDraw;
		resizeOnDrawTarget = new PVector(resizeOnDraw.x, resizeOnDraw.y);
	}

	public PVector getResizeOnDraw() {
		return resizeOnDraw;
	}

	// ------------------------------------------------------------------------
	// Abstract methods
	public abstract String drawUndecorated(PGraphics canvas);

	// ------------------------------------------------------------------------
	// Protected methods

	public boolean isOver(PVector pos) {
		if (this.realPos != null && pos.x > this.realPos.x && pos.x < this.realPos.x + getWidth()
				&& pos.y > this.realPos.y && pos.y < this.realPos.y + getHeight()) {
			// System.out.println("SI--" + this.pos.dist(pos));
			return true;
		}
		return false;
	}

	/**
	 * Because we can be contained by other componentes with translate, I've to
	 * update my real position in the screen
	 * 
	 * @param canvas
	 */

	protected void updateRealPos(PGraphics canvas) {
		realPos = new PVector(canvas.screenX(pos.x, pos.y, pos.z), canvas.screenY(pos.x, pos.y, pos.z), pos.z);
		rotation = rotation + (targetRotation - rotation) * 0.1f;

		resizeOnDraw.x = resizeOnDraw.x + (resizeOnDrawTarget.x - resizeOnDraw.x) * 0.1f;
		resizeOnDraw.y = resizeOnDraw.y + (resizeOnDrawTarget.y - resizeOnDraw.y) * 0.1f;

		pos.x = pos.x + (posTarget.x - pos.x) * 0.33f;
		pos.y = pos.y + (posTarget.y - pos.y) * 0.33f;
		
		// PVector temp = resizeOnDrawTarget.get();
		// temp.sub(resizeOnDraw);
		// temp.mult(0.1f);
		// resizeOnDraw.add(temp);
	}

	// ------------------------------------------------------------------------
	// Public methods
	public void setWindowManager(WindowManager windowManager) {
		this.windowManager = windowManager;
	}

	public String draw(PGraphics canvas) {
		// Decorator management
		return drawUndecorated(canvas);
	}

	// protected Logger logger = Logger.getLogger(this.getClass());

	public void add(GUIComponent component) {
		components.add(component);
	}

	public void add(int index, GUIComponent component) {
		components.add(index, component);
	}

	public void remove(GUIComponent component) {
		components.remove(component);
	}

	public Ani animate(int x, int y, float time) {
		return animate(x, y, time, 0);
	}

	public Ani animate(int x, int y, float time, float delay) {
		Ani ani = null;
		try {
			Ani.to(pos, time, delay, "x", x);
			ani = Ani.to(pos, time, delay, "y", y);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ani;
	}

	public Ani animate(int x, int y, float time, float delay, Easing easing) {
		Ani ani = null;
		try {
			Ani.to(pos, time, delay, "x", x);
			ani = Ani.to(pos, time, delay, "y", y, easing);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ani;
	}

	public void animate(int x, int y, float time, Easing easing) {
		try {
			Ani.to(pos, time, "x", x, easing);
			Ani.to(pos, time, "y", y, easing);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPosition(float x, float y) {
		this.pos.x = x;
		this.pos.y = y;
		posTarget.x = x;
		posTarget.y = y;
	}

	public void setPosition(PVector pos) {
		this.pos = pos;
		posTarget.x = pos.x;
		posTarget.y = pos.y;
	}

	public PVector getPosition() {
		return pos;
	}

	public List<GUIComponent> getComponents() {
		return components;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRenderedAndActive(boolean rendered) {
		setRendered(rendered);
		setActive(rendered);
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
		for (int i = 0; i < components.size(); i++) {
			GUIComponent component = components.get(i);
			component.setRendered(rendered);
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		for (int i = 0; i < components.size(); i++) {
			GUIComponent component = components.get(i);
			component.setActive(active);
		}
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public PVector getRealPos() {
		return realPos;
	}

	public void setOrder(float zOffset) {
		pos.z = zOffset;
	}

	public void addRotation(float dif) {
		targetRotation += dif;
	}

	public void addResizeOnDraw(float x, float y) {
		resizeOnDrawTarget.add(x, y, 0);
	}

	public void addPosition(PVector addPos) {
		posTarget.add(addPos);
	}

}
