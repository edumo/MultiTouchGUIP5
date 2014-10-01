package org.edumo.gui.button;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.edumo.gui.ActionEvent;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class DragableImage extends ButtonText {

	protected int size;
	public PImage img;

	protected int imageMode;
	protected PVector resizeOnDraw = new PVector(1, 1);

	protected PVector posDraged = new PVector();
	protected PVector incDraged = new PVector();
	protected PVector posInitDrag = new PVector();

	Map<Integer, TouchPointer> pointers = new HashMap<Integer, TouchPointer>();

	public void setResizeOnDraw(PVector resizeOnDraw) {
		this.resizeOnDraw = resizeOnDraw;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void init(String action, PImage img, PVector pos, int size) {
		init(action, img, pos, size, PApplet.CENTER);
	}

	public void init(String action, PImage img, PVector pos, int size,
			int imageMode) {

		this.imageMode = imageMode;
		this.img = img;
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
			canvas.pushStyle();
			canvas.tint(100, 100, 100);
			drawImage(canvas, img);
			canvas.popStyle();
		}

		canvas.popMatrix();

		// System.out.println(pointers.size());

		super.draw(canvas);

		return null;

	}

	private void drawImage(PGraphics canvas, PImage img) {

		if (resizeOnDraw == null) {
			canvas.image(img, pos.x, pos.y);
		} else {
			canvas.image(img, pos.x, pos.y, img.width * resizeOnDraw.x,
					img.height * resizeOnDraw.y);
		}

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
			// }
			// else if (resizeOnDraw != null) {
			// size = (int) resizeOnDraw.mag() / 2;
			// if (this.realPos != null) {
			// PVector newPos = realPos.get();
			// newPos.add(size / 2, size / 2, 0);
			// if (newPos.dist(pos) < size) {
			// return true;
			// }
			// } else {
			// // System.out.println("----" + this.pos.dist(pos));
			// }
		} else {
			size = this.size;
			if (imageMode == PApplet.CENTER
					&& this.realPos != null
					&& pos.x + img.width * resizeOnDraw.x / 2 > this.realPos.x
					&& pos.x + img.width * resizeOnDraw.x / 2 < this.realPos.x
							+ img.width * resizeOnDraw.x
					&& pos.y + img.height * resizeOnDraw.x / 2 > this.realPos.y
					&& pos.y + img.height * resizeOnDraw.x / 2 < this.realPos.y
							+ img.height * resizeOnDraw.x) {
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

	@Override
	public ActionEvent hidDragged(TouchPointer touche) {
		if (pointers.isEmpty())
			return null;

		if (pointers.size() == 1) {
			TouchPointer p1 = pointers.values().iterator().next();
			if (p1.id == touche.id) {
				PVector dif = PVector.sub(touche.getScreen(), p1.getScreen());
				pos.add(dif);
				// actualizamos mi pos en funci�n de la diferencia y
				// actualizamos el map
				pointers.put(touche.id, touche);
			}
		}

		if (pointers.size() >= 2) {
			Iterator<TouchPointer> pointsActive = pointers.values().iterator();

			TouchPointer activePointer = null;
			TouchPointer activePointerOld = null;
			// buscamos el primer implicado
			while (pointsActive.hasNext()) {
				TouchPointer touchPointer = (TouchPointer) pointsActive.next();
				if (touchPointer.id == touche.id) {
					// Se mueve uno de los dos
					// primero zoom vemos distancia anterior y la nueva
					activePointer = touche;
					activePointerOld = touchPointer;
				}
			}
			// buscamos alg�n segundo implicado
			TouchPointer activePointer2 = null;
			pointsActive = pointers.values().iterator();
			if (activePointer != null) {
				while (pointsActive.hasNext()) {
					TouchPointer touchPointer = (TouchPointer) pointsActive
							.next();
					if (touchPointer.id != activePointer.id) {
						// Se mueve uno de los dos
						// primero zoom vemos distancia anterior y la nueva
						activePointer2 = touchPointer;
					}
				}

				PVector tpv1 = activePointer.getScreen();
				PVector tpv1Old = activePointerOld.getScreen();
				PVector tpv2 = activePointer2.getScreen();

				// tenemos los dos analizamos su distancia actual y la nueva
				float distX = PApplet.dist(tpv1.x, tpv1.y, tpv2.x, tpv2.y);
				float distXOld = PApplet.dist(tpv1Old.x, tpv1Old.y, tpv2.x,
						tpv2.y);
				distX /= img.width;
				distXOld /= img.width;
				resizeOnDraw.y += distX - distXOld;
				resizeOnDraw.x += distX - distXOld;

				pointers.put(touche.id, touche);
			}
		}

		return null;
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		PVector touchPos = touche.getScreen();

		if (!active)
			return null;

		TouchPointer removeObject = pointers.remove(touche.id);

		return null;

	}

	@Override
	public ActionEvent hidPressed(TouchPointer touche) {
		PVector touchPos = touche.getScreen();

		if (!active)
			return null;

		if (isOver(touchPos)) {
			pointers.put(touche.id, touche);
		}

		return null;
	}

}