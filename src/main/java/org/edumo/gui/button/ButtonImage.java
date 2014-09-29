package org.edumo.gui.button;


import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class ButtonImage extends ButtonText {

	int size;
	public PImage img;
	public PImage pressedImg;

	int imageMode;
	private PVector resizeOnDraw = null;

	public void setResizeOnDraw(PVector resizeOnDraw) {
		this.resizeOnDraw = resizeOnDraw;
	}

	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}



	public void init(String action, PImage img, PImage pressedImg, PVector pos,
			int size) {
		init(action, img, pressedImg, pos, size, PApplet.CENTER);
	}

	public void init(String action, PImage img, PImage pressedImg, PVector pos,
			int size, int imageMode) {

		this.imageMode = imageMode;
		this.img = img;
		this.pressedImg = pressedImg;
		this.size = -1;
		super.init(action, pos);
	}

	public String draw(PGraphics canvas) {
		
		if (!rendered)
			return null;

		canvas.pushMatrix();
		canvas.imageMode(imageMode);
		updateRealPos(canvas);
		
		if (!pressed) {
			drawImage(canvas, img);
		} else {
			if (pressedImg == img) {
				canvas.pushStyle();
				canvas.tint(100, 100, 100);
				drawImage(canvas, pressedImg);
				canvas.popStyle();
			} else {
				drawImage(canvas, pressedImg);
			}
		}
		
		canvas.popMatrix();
		
		super.draw(canvas);
		
		return null;

	}

	private void drawImage(PGraphics canvas, PImage img) {

		if (resizeOnDraw == null) {
			canvas.image(img, pos.x, pos.y);
		} else {
			canvas.image(img, pos.x, pos.y, resizeOnDraw.x, resizeOnDraw.y);
		}

		// if (movable) {
		// canvas.popMatrix();
		// }
	}

	public boolean isOver(PVector pos) {

		int size;
		if (this.size > 0) {
			if (this.realPos != null) {
				PVector newPos = realPos.get();
				if (newPos.dist(pos) < this.size) {
					return true;
				}
			} else {
				// System.out.println("----" + this.pos.dist(pos));
			}
		} else if (resizeOnDraw != null) {
			size = (int) resizeOnDraw.mag() / 2;
			if (this.realPos != null) {
				PVector newPos = realPos.get();
				newPos.add(size / 2, size / 2, 0);
				if (newPos.dist(pos) < size) {
					return true;
				}
			} else {
				// System.out.println("----" + this.pos.dist(pos));
			}
		} else {
			size = this.size;
			if (imageMode == PApplet.CENTER && this.realPos != null
					&& pos.x + img.width / 2 > this.realPos.x
					&& pos.x + img.width / 2 < this.realPos.x + img.width
					&& pos.y + img.height / 2 > this.realPos.y
					&& pos.y + img.height / 2 < this.realPos.y + img.height) {
				return true;
			}

			if (imageMode == PApplet.CORNER && this.realPos != null
					&& pos.x > this.realPos.x
					&& pos.x < this.realPos.x + img.width
					&& pos.y > this.realPos.y
					&& pos.y < this.realPos.y + img.height) {
				System.out.println("SI--" + this.pos.dist(pos));
				return true;
			} else {
				// System.out.println("----" + this.pos.dist(pos));
			}
		}

		return false;
	}

}