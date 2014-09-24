package org.edumo.gui.decorator;

import java.util.ArrayList;
import java.util.List;

import org.edumo.gui.Component;


import processing.core.PGraphics;
import processing.core.PVector;

public abstract class Decorator extends Component {
	
	protected Component component;

	public Decorator(Component componente) {
		this.component = componente;
	}

	public String draw(PGraphics canvas) {
		return component.draw(canvas);
	}

	@Override
	public List<Component> getComponents() {
		List<Component> ret = new ArrayList<Component>();
		ret.add(component);
		return ret;
	}

	@Override
	public PVector getPos() {
		return component.getPos();
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
	
}