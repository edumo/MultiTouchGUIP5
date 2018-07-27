package org.edumo.gui.decorator;

import org.edumo.gui.AbstractGUIModifier;
import org.edumo.gui.GUIComponent;

import processing.core.PGraphics;
import processing.core.PVector;

public class RectDecorator extends AbstractGUIModifier {

	private int strokeColor = 255;
	private int strokeWeight = 2;

	public int getStrokeWeight() {
		return strokeWeight;
	}

	public void setStrokeWeight(int strokeWeight) {
		this.strokeWeight = strokeWeight;
	}

	public RectDecorator( int strokeColor ) {
		super();
		this.strokeColor = strokeColor;
	}

	@Override
	public String draw(PGraphics canvas) {
		
		// First, we need to draw only the component internal graphics
		String message = component.drawUndecorated( canvas );
		
		// Then we draw the decorator
		canvas.noFill();
		canvas.stroke(strokeColor);
		canvas.strokeWeight(strokeWeight);
		canvas.rect(component.getPosition().x, component.getPosition().y,
				component.getWidth(), component.getHeight());
		return message;
	}

	public int getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	@Override
	public PVector getRealPos() {
		return component.getRealPos();
	}

	@Override
	public void setPosition(float x, float y) {
		component.setPosition(x, y);
	}

	@Override
	public String drawUndecorated(PGraphics canvas) {
		return null;
	}

}
