package org.edumo.gui;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class AbstractGUIModifier extends GUIComponent {
	
	protected GUIComponent component;

	/* Commented on 2 June 2016
	@Override
	public List<GUIComponent> getComponents() {
		List<GUIComponent> ret = new ArrayList<GUIComponent>();
		ret.add(component);
		return ret;
	}
	 */
	
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