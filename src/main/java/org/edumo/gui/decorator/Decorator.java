package org.edumo.gui.decorator;

import java.util.ArrayList;
import java.util.List;

import org.edumo.gui.GUIComponent;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class Decorator extends GUIComponent {
	
	protected GUIComponent component;

	@Override
	public List<GUIComponent> getComponents() {
		List<GUIComponent> ret = new ArrayList<GUIComponent>();
		ret.add(component);
		return ret;
	}

	@Override
	public PVector getPosition() {
		return component.getPosition();
	}

	public GUIComponent getComponent() {
		return component;
	}

	public void setComponent(GUIComponent component) throws Exception {
		this.component = component;
	}
	
	public void setParent( PApplet parent )
	{
		super.parent = parent;
		
	}
	
}