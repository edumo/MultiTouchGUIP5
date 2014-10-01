package org.edumo.gui.decorator;

import java.util.ArrayList;
import java.util.List;

import org.edumo.gui.GUIComponent;


import processing.core.PGraphics;
import processing.core.PVector;

public abstract class Decorator extends GUIComponent {
	
	protected GUIComponent component;

	public Decorator(GUIComponent componente) {
		this.component = componente;
	}

	public String draw(PGraphics canvas) {
		return component.draw(canvas);
	}

	@Override
	public List<GUIComponent> getComponents() {
		List<GUIComponent> ret = new ArrayList<GUIComponent>();
		ret.add(component);
		return ret;
	}

	@Override
	public PVector getPos() {
		return component.getPos();
	}

	public GUIComponent getComponent() {
		return component;
	}

	public void setComponent(GUIComponent component) {
		this.component = component;
	}
	
}