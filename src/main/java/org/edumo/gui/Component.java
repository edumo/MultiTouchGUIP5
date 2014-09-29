package org.edumo.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.looksgood.ani.Ani;
import de.looksgood.ani.easing.Easing;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class Component {

	protected PVector pos = new PVector();

	// posicin real en la pantalla
	protected PVector realPos = null;

	protected int width;
	protected int height;

	protected List<Component> components = new ArrayList<Component>();

	public abstract String draw(PGraphics canvas);

	protected PApplet parent;

	protected Logger logger = Logger.getLogger(this.getClass());

	protected boolean rendered = true;

	protected boolean active = true;

	public void add(Component component) {
		components.add(component);
	}

	public void add(int index, Component component) {
		components.add(index, component);
	}

	public void remove(Component component) {
		components.remove(component);
	}

	public void animate(int x, int y, float time) {
		try {
			Ani.to(pos, time, "x", x);
			Ani.to(pos, time, "y", y);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	}

	public PVector getPos() {
		return pos;
	}

	public List<Component> getComponents() {
		return components;
	}

	/**
	 * Because we can be contained by other componentes with translate, I've to
	 * update my real position in the screen
	 * 
	 * @param canvas
	 */

	protected void updateRealPos(PGraphics canvas) {
		realPos = new PVector(canvas.screenX(pos.x, pos.y), canvas.screenY(
				pos.x, pos.y));
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
		for (int i = 0; i < components.size(); i++) {
			Component component = components.get(i);
			component.setRendered(rendered);
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		for (int i = 0; i < components.size(); i++) {
			Component component = components.get(i);
			component.setActive(active);
		}

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public PVector getRealPos() {
		return realPos;
	}
}
