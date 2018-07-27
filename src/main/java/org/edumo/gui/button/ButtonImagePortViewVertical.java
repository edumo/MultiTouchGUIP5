package org.edumo.gui.button;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class ButtonImagePortViewVertical extends ButtonImage {

	public PVector viewportPosition = new PVector();
	public PVector viewportPositionTarget = new PVector();
	public PVector margin = new PVector();

	protected int sizeViewPort = 400;

	public PVector fakeResize;

	// ESTO LO METEMOS PARA MAPEAR ACCIONES

	public PVector getFakeResize() {
		return fakeResize;
	}

	@Override
	public float getWidth() {
		if (fakeResize == null)
			return super.getWidth();
		else
			return fakeResize.x;
	}

	@Override
	public float getHeight() {
		if (fakeResize == null)
			return super.getHeight();
		else
			return fakeResize.y;
	}

	public void setFakeResize(PVector fakeResize) {
		this.fakeResize = fakeResize;
	}

	List<Float> positions;

	public List<Float> getPositions() {
		return positions;
	}

	public List<String> getActions() {
		return actions;
	}

	List<String> actions;

	public void setMargin(int marginLeft, int marginRight) {
		this.margin.set(marginLeft, marginRight);
	}

	public void setSizeViewPort(float normValue) {
		this.sizeViewPort = (int) (resizeOnDraw.y * normValue);
	}

	@Override
	protected void updateRealPos(PGraphics canvas) {

		super.updateRealPos(canvas);

		viewportPosition.y = viewportPosition.y + (viewportPositionTarget.y - viewportPosition.y) * 0.1f;

	}

	protected void drawImage(PGraphics canvas, PImage img) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y, pos.z);
		canvas.rotate(rotation);
		updateRealPos(canvas);
		if (getResizeOnDraw() == null) {
			// canvas.image(img, 0,0);
			System.out.println("soy el viewPort y necesito un resize on draw");
		} else {
			// canvas.image(img, 0,0, (int)resizeOnDraw.x, (int)resizeOnDraw.y);
			canvas.image(img, 0, 0, (int) resizeOnDraw.x, (int) resizeOnDraw.y, 0, (int) -viewportPosition.y,
					(int) img.width, (int) -viewportPosition.y + sizeViewPort);
		}

		canvas.popMatrix();
	}

	public void addPosition(PVector addPos) {
		PVector direction = PVector.sub(realPos2, realPos1);
		float myrot = PVector.angleBetween(direction, new PVector(1, 0));

		addPos.rotate(myrot);
		viewportPositionTarget.add(addPos);

		if (viewportPositionTarget.y > margin.x)
			viewportPositionTarget.y = margin.x;
		float limitX = -img.width + margin.y;
		// System.out.println("limitX"+limitX+" pos:"+viewportPositionTarget.x);
		if (viewportPositionTarget.y < limitX)
			viewportPositionTarget.y = limitX;
		// viewportPositionTarget.add(addPos);
		// if (viewportPositionTarget.y > margin.x)
		// viewportPositionTarget.y = margin.x;
		// float limitY = margin.y;
		// // System.out.println("limitX"+limitX+"
		// pos:"+viewportPositionTarget.x);
		// if (viewportPositionTarget.y < limitY)
		// viewportPositionTarget.y = limitY;
		//
		// System.out.println(viewportPositionTarget);

	}

	public float getViewPortPosition() {
		return viewportPosition.y / resizeOnDraw.y;
	}

	public void setActionMappings(List<Float> positions, List<String> actions) {
		this.positions = positions;
		this.actions = actions;
	}

	public String findAction(float pos) {
		String action = null;
		pos += -viewportPosition.y;
		if (positions != null)
			for (int i = 0; i < positions.size(); i++) {
				Float element = positions.get(i);
				if (pos < element) {
					action = actions.get(i);
					break;
				}
			}
		else {
			System.out.println("buscamos una acción en slider de imagen y tenemos positions a null");
		}
		return action;
	}

}
