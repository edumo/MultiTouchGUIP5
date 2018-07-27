package org.edumo.gui.button;

import org.edumo.gui.ActionEvent;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class ButtonSlider extends ButtonText {

	int size;
	public PImage img;

	public PImage pressedImg;
	
	public float sliderPos = 0;
	public float sliderPosTarget = 0;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PImage getImg() {
		return img;
	}

	public void init(String action, PImage img, PImage pressedImg, PVector pos, int size) {
		init(action, img, pressedImg, pos, size, PApplet.CENTER);
	}

	public void init(String action, PImage img, PImage pressedImg, PVector pos, int size, int imageMode) {

		this.imageMode = imageMode;
		this.img = img;
		this.width = img.width;
		this.height = img.height;
		this.pressedImg = pressedImg;
		this.size = -1;
		super.init(action, pos);
		this.action = "slider-drag";
	}
	
	@Override
	protected void updateRealPos(PGraphics canvas) {
		sliderPos = sliderPos + (sliderPosTarget - sliderPos) * 0.33f;
		super.updateRealPos(canvas);
	}

	public String drawUndecorated(PGraphics canvas) {

		if (!rendered)
			return null;

		// canvas.pushMatrix();
		canvas.imageMode(imageMode);
		
		updateRealPos(canvas);
		

		// if (!pressed) {
		drawImage(canvas, img);
		
		
		// } else {
		// if (pressedImg == img) {
		// canvas.pushStyle();
		// canvas.tint(100, 100, 100);
		// drawImage(canvas, pressedImg);
		//
		// canvas.popStyle();
		// } else {
		// drawImage(canvas, pressedImg);
		// }
		// }
		// canvas.fill(255,0,0);
		// canvas.pushMatrix();
		// canvas.translate(realPos.x, realPos.y, 0);
		// canvas.box(10, 20,20);
		// canvas.noTint();
		// canvas.popMatrix();

		// canvas.popMatrix();

		super.drawUndecorated(canvas);

		return null;

	}

	protected void drawImage(PGraphics canvas, PImage img) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y, pos.z);
		canvas.rotate(rotation);
		if (resizeOnDraw == null) {
			canvas.image(img, 0, 0);
		} else {
			canvas.image(img, 0, 0, resizeOnDraw.x, resizeOnDraw.y);
		}

		canvas.fill(255,0,0);
		canvas.rect(sliderPosTarget,-10,20,20);
		
		canvas.popMatrix();
	}
	
	@Override
	public ActionEvent hidDragged(TouchPointer touche) {
		ActionEvent actionEvent = super.hidDragged(touche);
		if(pressed){
			PVector touchPos = touche.getScreen();
			PVector relativePos = PVector.sub(realPos, touchPos);
			sliderPosTarget = -relativePos.x*2;
		}
		return actionEvent;
	}
	
//	@Override
//	public ActionEvent hidPressed(TouchPointer touche) {
//		ActionEvent actionEvent = super.hidPressed(touche);
//		if(pressed){
//			PVector touchPos = touche.getScreen();
//			PVector relativePos = PVector.sub(realPos, touchPos);
//			sliderPos = -relativePos.x*2;
//		}
//		return actionEvent;
//	}

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
			if (isOverParams(pos, resizeOnDraw.x, resizeOnDraw.y)) {
				return true;
			}
		} else {
			size = this.size;
			if (isOverParams(pos, img.width, img.height)) {
				return true;
			} else {
				// System.out.println("----" + this.pos.dist(pos));
			}
		}

		return false;
	}

	public boolean isOverParams(PVector pos, float w, float h) {
		if (imageMode == PApplet.CENTER && this.realPos != null && pos.x + w / 2 > this.realPos.x
				&& pos.x + w / 2 < this.realPos.x + w && pos.y + h / 2 > this.realPos.y
				&& pos.y + h / 2 < this.realPos.y + h) {
			return true;
		}

		if (imageMode == PApplet.CORNER && this.realPos != null && pos.x > this.realPos.x && pos.x < this.realPos.x + w
				&& pos.y > this.realPos.y && pos.y < this.realPos.y + h) {
			// System.out.println("SI--" + this.pos.dist(pos));
			return true;
		}
		return false;
	}

	@Override
	public float getWidth() {
		if (resizeOnDraw == null)
			return img.width;
		else
			return (int) resizeOnDraw.x;
	}

	@Override
	public float getHeight() {
		if (resizeOnDraw == null)
			return img.height;
		else
			return (int) resizeOnDraw.y;
	}

	@Override
	public void setWidth(float width) {
		if (resizeOnDraw == null) {
			resizeOnDraw = new PVector();
		}
		resizeOnDraw.x = width;
		super.setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		if (resizeOnDraw == null) {
			resizeOnDraw = new PVector();
		}

		resizeOnDraw.y = height;
		super.setHeight(height);
	}

	public void setAction(String string) {
		this.action = string;
	}

}