package org.edumo.gui.decorator;

import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.HIDEventListener;
import org.edumo.gui.WindowManager;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.DraggableGuiComponent;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PGL;

public class ResizableDecorator extends Decorator implements HIDEventListener {

	private int strokeColor = 255;
	private int strokeWeight = 2;

	PVector sizeOffset = new PVector(0,0);
	
	PVector posResizableArea = new PVector();
	PVector resizableSize = new PVector(40, 40);
	PVector resizableHandlerOffset = new PVector(0, 0);
	boolean resizableAreaIsPressed = false;
	boolean resizableAreaIsOver = false;
	
	
	PImage tiradorImg = null;
	

	public int getStrokeWeight() {
		return strokeWeight;
	}

	public void setStrokeWeight(int strokeWeight) {
		this.strokeWeight = strokeWeight;
	}

	public ResizableDecorator(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	public void setComponent(GUIComponent component) throws Exception {
		this.component = component;
		if (component instanceof ButtonImage) {
			ButtonImage buttonImage = (ButtonImage) component;
			buttonImage.setResizeOnDraw(new PVector(buttonImage.getWidth(),
					buttonImage.getHeight()));
		} else {
			throw new Exception("ahora solo se manejar ButtonImage");
		}
	}

	public String drawUndecorated(PGraphics canvas) {

		//canvas.ortho();
		//canvas.hint(PApplet.ENABLE_DEPTH_SORT);
		//canvas.hint(PApplet.DISABLE_DEPTH_SORT);
		//canvas.hint(PApplet.ENABLE_DEPTH_MASK);
		//canvas.hint(PApplet.DISABLE_DEPTH_MASK);
		//canvas.hint(PApplet. ENABLE_DEPTH_TEST);
		//canvas.hint(PApplet. DISABLE_DEPTH_TEST);
		
		// component.drawUndecorated(canvas);
		//------------------------------------------------------------------------
		// Draw component image (an small hack to avoid image size errors...)
		DraggableGuiComponent c = (DraggableGuiComponent) component;
		c.forceUpdateRealPos(canvas);
		c.drawUndecorated(canvas);


		//------------------------------------------------------------------------
		// Dibujamos el decorator
		canvas.noFill();
		canvas.stroke(strokeColor);
		canvas.strokeWeight(strokeWeight);

		//------------------------------------------------------------------------
		// Dibujamos la bounding box
		int x = (int) component.getRealPos().x;
		int y = (int) component.getRealPos().y;
		int w = (int) component.getWidth() + (int)sizeOffset.x;
		int h = (int) component.getHeight() + (int)sizeOffset.y;


		if (resizableAreaIsPressed)
			canvas.stroke(255, 0, 0);
		else
			canvas.stroke(255, 255, 255);
		
		canvas.rectMode(PApplet.CENTER);
		canvas.strokeWeight(0);
		canvas.rect(x, y, w, h, 4, 4, 4, 4);
		// Hack para evitar falta de línea superior
		canvas.line(x - w / 2 + 4, y - h / 2, x + w / 2 - 2, y - h / 2);
		canvas.strokeWeight(1);

		//------------------------------------------------------------------------
		// Tirador de la esquina
		posResizableArea.x = component.getRealPos().x + component.getWidth()
				/ 2 + resizableSize.x / 2 + resizableHandlerOffset.x;
		posResizableArea.y = component.getRealPos().y + component.getHeight()
				/ 2 + resizableSize.y / 2 + resizableHandlerOffset.y;

		if (tiradorImg == null) {
			tiradorImg = parent.loadImage("images/btns_elementos/lupa_2.png");
		} else {
			canvas.tint(255, 0, 0, 255);
			canvas.pushMatrix();
			//canvas.translate(resizableHandlerOffset.x, resizableHandlerOffset.y);
			canvas.image(tiradorImg, posResizableArea.x, posResizableArea.y,
					resizableSize.x, resizableSize.y);
			canvas.popMatrix();
			canvas.noTint();
		}
		
		/*
		// Debug zOrder
		canvas.fill(255,0,0);
		canvas.text(component.getRealPos().z,posResizableArea.x, posResizableArea.y-40);
		canvas.fill(255,255,255);
		*/
		return null;
	}

	public void setResizableHandlerOffset(float x, float y) {
		resizableHandlerOffset.x = x;
		resizableHandlerOffset.y = y;
	}

	public void setSizeOffset(float x, float y) {
		sizeOffset.x = x;
		sizeOffset.y = y;
	}
	
	public boolean isOver(PVector pos, int w, int h, PVector realPos) {
		if (realPos != null && pos.x + w / 2 > realPos.x
				&& pos.x + w / 2 < realPos.x + w && pos.y + h / 2 > realPos.y
				&& pos.y + h / 2 < realPos.y + h) {
			return true;
		}

		// if (imageMode == PApplet.CORNER && this.realPos != null
		// && pos.x > this.realPos.x && pos.x < this.realPos.x + w
		// && pos.y > this.realPos.y && pos.y < this.realPos.y + h) {
		// //System.out.println("SI--" + this.pos.dist(pos));
		// return true;
		// }
		return false;
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
	public void setWindowManager(WindowManager windowManager) {
		super.setWindowManager(windowManager);

		// Queremos que esta clase pueda escuchar los eventos que recibe el
		// bot�n de zoom...
		windowManager.addListener(this);

	}

	// El interfaz HIDListener nos da los siguientes m�todos
	@Override
	public ActionEvent hidPressed(TouchPointer touche) {
		if (isOver(posResizableArea, (int) resizableSize.x,
				(int) resizableSize.y, touche.getScreen())) {
			resizableAreaIsPressed = true;
		}
		return null;
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		resizableAreaIsPressed = false;
		return null;
	}

	@Override
	public ActionEvent hidDragged(TouchPointer touche) {

		PVector toucheLastVector = touche.last.getScreen();
		if (toucheLastVector != null && resizableAreaIsPressed) {
			PVector sub = PVector.sub(touche.getScreen(),
					touche.last.getScreen());
			int newWidth = (int) (component.getWidth() + sub.x * 2);
			int newHeight = (int) (component.getHeight() + sub.y * 2);

			if (newWidth >= 10 && newHeight >= 10) {
				component.setWidth(newWidth);
				component.setHeight(newHeight);

				touche.x = touche.getScreenX();
				touche.y = touche.getScreenY();
				touche.last.x = touche.getScreenX();
				touche.last.y = touche.getScreenY();
			} else {
				resizableAreaIsPressed = false;
			}
		}

		return null;
	}

	@Override
	public ActionEvent hidMoved(TouchPointer touche) {
		if (touche != null) {
			if (isOver(posResizableArea, (int) resizableSize.x,
					(int) resizableSize.y, touche.getScreen()))
				resizableAreaIsOver = true;
			else
				resizableAreaIsOver = false;
			return null;
		} else {
			return null;
		}
	}

}
