package org.edumo.gui.button;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class ButtonImage extends ButtonText {

	protected int alpha = 255;
	protected int size;

	public PImage img;
	public PImage pressedImg;
	public boolean pastilla = false;

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
		super.posTarget.set(pos);
	}

	public String drawUndecorated(PGraphics canvas, PImage img) {
		if (!rendered)
			return null;

		// canvas.pushMatrix();
		canvas.imageMode(imageMode);

		if (!pressed) {
			canvas.pushStyle();
			canvas.tint(255, alpha);
			drawImage(canvas, img);
			canvas.popStyle();
		} else {
			if (pressedImg == img) {
				canvas.pushStyle();
				canvas.tint(255, alpha);
				drawImage(canvas, img);
				canvas.popStyle();
			} else {
				canvas.pushStyle();
				canvas.tint(0, alpha);
				drawImage(canvas, img);
				canvas.popStyle();
			}
		}

		super.drawUndecorated(canvas);

		return null;

	}

	public String drawUndecorated(PGraphics canvas) {

		return drawUndecorated(canvas, img);
	}

	public void addResizeOnDraw(float x, float y) {

		super.addResizeOnDraw(x, y);

		if (resizeOnDrawTarget.x < img.width * 0.4f) {
			resizeOnDrawTarget.x = img.width * 0.4f;
			resizeOnDrawTarget.y = img.height * 0.4f;
		}

		if (resizeOnDrawTarget.x > img.width * 1.f) {
			resizeOnDrawTarget.x = img.width * 1.f;
			resizeOnDrawTarget.y = img.height * 1.f;
		}

	}

	public String drawUndecorated(PGraphics canvas, int tint) {

		if (!rendered)
			return null;

		// canvas.pushMatrix();
		canvas.imageMode(imageMode);
		updateRealPos(canvas);

		if (!pressed) {
			canvas.pushStyle();
			canvas.tint(tint, alpha);
			drawImage(canvas, img, tint);
			canvas.popStyle();
		} else {
			if (pressedImg == img) {
				canvas.pushStyle();
				canvas.tint(tint, alpha);
				drawImage(canvas, pressedImg, tint);
				canvas.popStyle();
			} else {
				canvas.pushStyle();
				canvas.tint(0, alpha);
				drawImage(canvas, pressedImg, tint);
				canvas.popStyle();
			}
		}
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
		updateRealPos(canvas);
		canvas.pushStyle();
		if (pastilla ) {
			canvas.tint(255, alpha);
			canvas.rectMode(PApplet.CENTER);
			canvas.noStroke();
			canvas.rect(0, 0, getWidth(), getHeight());
		}
		if (resizeOnDraw == null) {
			canvas.image(img, 0, 0);
		} else {
			canvas.image(img, 0, 0, resizeOnDraw.x, resizeOnDraw.y);
		}
		canvas.fill(255, 0, 0);
		// canvas.text("c.rot:"+rotation, 0,0);
		// canvas.text("c.auxRot:"+rotationaUxiliar, 0,30);

		canvas.popStyle();
		canvas.popMatrix();
	}

	protected void drawImage(PGraphics canvas, PImage img, int tint) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y, pos.z);
		canvas.rotate(rotation);

		updateRealPos(canvas);
		canvas.pushStyle();
		canvas.tint(tint, alpha);
		if (resizeOnDraw == null) {
			canvas.image(img, 0, 0);
		} else {
			canvas.image(img, 0, 0, resizeOnDraw.x, resizeOnDraw.y);
		}
		canvas.fill(255, 0, 0);
		// canvas.text(action, 0,0);
		canvas.popStyle();
		canvas.popMatrix();
	}

	public boolean inPolyCheck(PVector v, PVector[] p) {
		float a = 0;
		for (int i = 0; i < p.length - 1; ++i) {
			PVector v1 = p[i].get();
			PVector v2 = p[i + 1].get();
			a += vAtan2cent180(v, v1, v2);
		}
		PVector v1 = p[p.length - 1].get();
		PVector v2 = p[0].get();
		a += vAtan2cent180(v, v1, v2);
		// if (a < 0.001) println(degrees(a));

		if (PApplet.abs(PApplet.abs(a) - PApplet.TWO_PI) < 0.01)
			return true;
		else
			return false;
	}

	float vAtan2cent180(PVector cent, PVector v2, PVector v1) {
		PVector vA = v1.get();
		PVector vB = v2.get();
		vA.sub(cent);
		vB.sub(cent);
		vB.mult(-1);
		float ang = PApplet.atan2(vB.x, vB.y) - PApplet.atan2(vA.x, vA.y);
		if (ang < 0)
			ang = PApplet.TWO_PI + ang;
		ang -= PApplet.PI;
		return ang;
	}

	@Override
	public boolean isOver(PVector pos) {
		boolean over = false;

		PVector[] pp = { realPos1, realPos2, realPos3, realPos4 };// new
		// PVector[4];
		if (realPos1 == null) {
			// System.out.println("MALOOOO, TENEMOS REAL POS SIN ACTUALIZAR;
			// TIENES QUE LLAMAR EN EL DRAW A updateRealPos();");
			return false;
		}

		if (pos == null) {
			return false;
		}

		if (this instanceof ButtonImagePortViewVertical) {
			boolean alse = false;
		}

		if (textAlign == PApplet.CENTER)
			if (realPos1 != null) {

				return inPolyCheck(pos, pp);
			}
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
			// if (isOverParams(pos, resizeOnDraw.x, resizeOnDraw.y)) {
			// return true;
			// }
			return inPolyCheck(pos, pp);
		} else {
			size = this.size;
			// if (isOverParams(pos, img.width, img.height)) {
			// return true;
			// } else {
			// // System.out.println("----" + this.pos.dist(pos));
			// }
			return inPolyCheck(pos, pp);
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

	public Ani animate(int destAlpha, float time, float delay) {
		Ani ani = null;
		try {
			ani = Ani.to(this, time, "alpha", destAlpha);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ani;
	}

	public Ani animate(float scale, float time, float delay) {
		Ani ani = null;
		try {
			Ani.to(resizeOnDraw, time, "x", resizeOnDraw.x * scale);
			Ani.to(resizeOnDraw, time, "y", resizeOnDraw.y * scale);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ani;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
}