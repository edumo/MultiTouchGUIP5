package org.edumo.touch;

import processing.core.PVector;

public class TouchPointer {

	public float x, y, acc, vel;

	public int id;
	public long millisStartTouch;

	public TouchPointer last;

	private Integer w, h;

	public TouchPointer(int id, float x, float y, long millisStart) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
		this.millisStartTouch = millisStart;
	}

	public void init(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public Integer getW() {
		return w;
	}

	public Integer getH() {
		return h;
	}

	public TouchPointer() {
		super();
	}

	public int getScreenX(int width) {
		return (int) (x * width);
	}

	public int getScreenY(int height) {
		return (int) (y * height);
	}

	public float getScreenX() {
		return (x * w);
	}

	public float getScreenY() {
		return (y * h);
	}

	public PVector getScreen() {
		return new PVector(getScreenX(), getScreenY());
	}

	public TouchPointer get() {
		TouchPointer pointer = new TouchPointer(id, x, y, millisStartTouch);
		pointer.w = w;
		pointer.h = h;
		pointer.last = last;
		return pointer;
	}

}
