package org.edumo.gui.decorator;

import org.edumo.gui.GUIComponent;

import processing.core.PGraphics;
import processing.core.PVector;

public class RectDecorator extends Decorator {

	private int strokeColor = 255;
	private int strokeWeight = 2;

	public int getStrokeWeight() {
		return strokeWeight;
	}

	public void setStrokeWeight(int strokeWeight) {
		this.strokeWeight = strokeWeight;
	}

	public RectDecorator(GUIComponent componente, int strokeColor) {
		super(componente);
		this.strokeColor = strokeColor;
	}

	@Override
	public String draw(PGraphics canvas) {
		super.draw(canvas);
		canvas.noFill();
		canvas.stroke(strokeColor);
		canvas.strokeWeight(strokeWeight);
		canvas.rect(component.getPos().x, component.getPos().y,
				component.getWidth(), component.getHeight());
		return null;
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

}
