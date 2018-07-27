package org.edumo.gui.button;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class ButtonImagePortView extends ButtonImage {

	public PVector viewportPosition = new PVector();
	public PVector viewportPositionTarget = new PVector();
	public PVector margin = new PVector();

	protected int sizeViewPort = 400;

	// ESTO LO METEMOS PARA MAPEAR ACCIONES

	List<Float> positions;

	public List<Float> getPositions() {
		return positions;
	}

	public List<String> getActions() {
		return actions;
	}

	List<String> actions;
	public boolean isOverPlygon = false;

	public void setMargin(int marginLeft, int marginRight) {
		this.margin.set(marginLeft, marginRight);
	}

	public void setSizeViewPort(float normValue) {
		this.sizeViewPort = (int) (resizeOnDraw.x * normValue);
	}
	
	@Override
	public boolean isOver(PVector pos) {
		boolean over = false;
		if (pos == null) {
			return false;
		}

		if (isOverPlygon)
			if (realPos1 != null) {
				PVector[] pp = { realPos1, realPos2, realPos3, realPos4 };// new
																			// PVector[4];
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

	@Override
	protected void updateRealPos(PGraphics canvas) {

		super.updateRealPos(canvas);
		
		realPos1 = new PVector(canvas.screenX(0, 0, 0), canvas.screenY(0, 0, 0),
				canvas.screenZ(0,0, 0));
		
		realPos2 = new PVector(canvas.screenX(0+getWidth(), 0, 0), canvas.screenY(0+getWidth(), 0, 0),
				canvas.screenZ(0+getWidth(), 0, 0));
		
		realPos3 = new PVector(canvas.screenX(0+getWidth(), getHeight(), 0), canvas.screenY(0+getWidth(), getHeight(), 0),
				canvas.screenZ(0+getWidth(), getHeight(), 0));
		
		realPos4 = new PVector(canvas.screenX(0, getHeight(), 0), canvas.screenY(0, getHeight(), 0),
				canvas.screenZ(0, getHeight(), 0));

		viewportPosition.x = viewportPosition.x + (viewportPositionTarget.x - viewportPosition.x) * 0.1f;

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
			canvas.image(img, 0, 0, (int) resizeOnDraw.x, (int) resizeOnDraw.y, (int) -viewportPosition.x, 0,
					(int) -viewportPosition.x + sizeViewPort, (int) img.height);
		}

		canvas.popMatrix();
	}

	public void addPosition(PVector addPos) {
		
		PVector direction = PVector.sub(realPos2, realPos1);
		float myrot = PVector.angleBetween(direction, new PVector(1, 0));
		
		addPos.rotate(myrot);
		viewportPositionTarget.add(addPos);
		
		if (viewportPositionTarget.x > margin.x)
			viewportPositionTarget.x = margin.x;
		float limitX = -img.width + margin.y;
		// System.out.println("limitX"+limitX+" pos:"+viewportPositionTarget.x);
		if (viewportPositionTarget.x < limitX)
			viewportPositionTarget.x = limitX;

	}

	public float getViewPortPosition() {
		return viewportPosition.x / getWidth();
	}
	
	public float setViewPortPosition(float value) {
		viewportPositionTarget.x  = value*  getWidth();
		return viewportPosition.x  = value*  getWidth();
	}

	public void setActionMappings(List<Float> positions, List<String> actions) {
		this.positions = positions;
		this.actions = actions;
	}

	public String findAction(float pos) {
			
		String action = null;
		pos += -viewportPosition.x;
		if (positions != null)
			for (int i = 0; i < positions.size(); i++) {
				Float element = positions.get(i);
				if (pos < element) {
					action = actions.get(i);
					break;
				}
			}
		else{
			System.out.println("buscamos una acción en slider de imagen y tenemos positions a null");
		}
		return action;
	}
}
