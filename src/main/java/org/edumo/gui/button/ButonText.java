package org.edumo.gui.button;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 * Clase para el renderizado de botones de texto
 * 
 * @author edumo
 * 
 */

public class ButonText extends AbstractButton {

	private String label = null;
	private int textColor = 0;
	private int textSize = 12;
	private PFont font;

	// private int xOffset = 0;
	// private int yOffset = 0;

	private float padding = .1f;// 15%

	// public int getxOffset() {
	// return xOffset;
	// }
	//
	// public void setxOffset(int xOffset) {
	// this.xOffset = xOffset;
	// }
	//
	// public int getyOffset() {
	// return yOffset;
	// }
	//
	// public void setyOffset(int yOffset) {
	// this.yOffset = yOffset;
	// }

	public PFont getFont() {
		return font;
	}

	public void setFont(PFont font) {
		this.font = font;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public int getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(int textAlign) {
		this.textAlign = textAlign;
	}

	public void setRealTextWidth(float realTextWidth) {
		this.realTextWidth = realTextWidth;
	}

	float realTextWidth = 0;

	int textAlign = 0;

	public float getRealTextWidth() {
		return realTextWidth;
	}

	public void init(PGraphics cavnas, String name, String action, PVector pos,
			int textSize, int textColor) {
		init(cavnas, name, action, pos, textSize, textColor, PApplet.CENTER);
	}

	/**
	 * init del botn meidante el tamao de un texto
	 * 
	 * @param cavnas
	 * @param name
	 * @param action
	 * @param pos
	 * @param text
	 * @param textSize
	 */

	public void init(PGraphics cavnas, String name, String action, PVector pos,
			int textSize, int textColor, int textAlign) {

		this.textColor = textColor;
		label = name;
		cavnas.textSize(textSize);
		width = (int) cavnas.textWidth(label);
		height = textSize;
		this.textSize = textSize;
		super.init(action, pos);
		this.textAlign = textAlign;
	}

	public void init(String name, String action, PVector pos, int w, int h,
			int textSize) {
		init(name, action, pos, w, h, textSize, PApplet.CENTER);
	}

	/**
	 * init del botn
	 * 
	 * @param cavnas
	 * @param name
	 * @param action
	 * @param pos
	 * @param text
	 * @param textSize
	 */

	public void init(String name, String action, PVector pos, int w, int h,
			int textSize, int textAlign) {

		label = name;
		this.width = w;
		this.textSize = textSize;
		this.height = h;
		this.textAlign = textAlign;
		super.init(action, pos);
	}

	/**
	 * este init permite crear un botn sin texto, es decir un area clicable
	 * vacia
	 * 
	 * @param cavnas
	 * @param width
	 * @param action
	 * @param pos
	 * @param text
	 * @param textSize
	 */

	public void init(PGraphics cavnas, int width, String action, PVector pos,
			int textSize) {

		label = "";
		this.width = width;
		this.height = textSize;
		this.textSize = textSize;
		super.init(action, pos);
	}

	@Override
	public String draw(PGraphics canvas) {

		if (label == null)
			return null;

		updateRealPos(canvas);
		canvas.pushMatrix();
		canvas.pushStyle();
		canvas.translate(pos.x, pos.y);
		canvas.noFill();
		canvas.strokeWeight(2);
		canvas.stroke(255, 0, 0);

		canvas.textAlign(textAlign);
		if (pressed) {
			// canvas.fill(255,0,0);
			canvas.textSize(textSize * 1.1f);
		} else {
			canvas.fill(textColor);
			canvas.textSize(textSize);
		}

		if (font != null) {
			canvas.textFont(font, textSize);
		}

		// if (textAlign == PApplet.CENTER)
		// canvas.text(label, width / 2 + xOffset, height / 2 + textSize / 4
		// + yOffset);
		// else
		// canvas.text(label, xOffset, textSize + yOffset);

		if (textAlign == PApplet.CENTER)
			canvas.text(label, width / 2, height / 2 + textSize / 4);
		else
			canvas.text(label, 0, textSize);

		realTextWidth = canvas.textWidth(label);
		canvas.textAlign(PApplet.CORNER);
		canvas.popMatrix();
		canvas.popStyle();

		return null;
	}

	@Override
	public boolean isOver(PVector pos) {
		boolean over = false;
		if (pos == null) {
			return false;
		}
		if (realPos != null && pos.x > realPos.x && pos.x < realPos.x + width) {
			if (pos.y > realPos.y && pos.y < realPos.y + height) {
				over = true;
			}
		}
		return over;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		if (label != null)
			this.label = label;
	}

}
