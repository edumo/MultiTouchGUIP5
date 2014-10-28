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

public class ButtonText extends AbstractButton {

	private String label = null;
	private String placeholder = null;
	private boolean renderPlaceHolder = true;
	private boolean withRectBox = false;
	private int rectBoxColor = -1;
	private int textColor = 0;
	private int textSize = 12;
	private PFont font;
	private PVector textOffset = new PVector();
	private int maxLetters = 0;
	private PGraphics canvas;
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

	public int getMaxLetters() {
		return maxLetters;
	}

	public void setMaxLetters(int maxLetters) {
		this.maxLetters = maxLetters;
	}

	public boolean isRenderPlaceHolder() {
		return renderPlaceHolder;
	}

	public void setRenderPlaceHolder(boolean renderPlaceHolder) {
		this.renderPlaceHolder = renderPlaceHolder;
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

	public boolean isWithRectBox() {
		return withRectBox;
	}

	public void setWithRectBox(boolean withRectBox) {
		this.withRectBox = withRectBox;
	}

	public int getRectBoxColor() {
		return rectBoxColor;
	}

	public void setRectBoxColor(int rectBoxColor) {
		this.rectBoxColor = rectBoxColor;
		setWithRectBox(true);
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
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

	public void init(PGraphics canvas, String name, String action, PVector pos,
			int textSize, int textColor) {
		init(canvas, name, action, pos, textSize, textColor, PApplet.CENTER);
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
		this.canvas = cavnas;
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
	public String drawUndecorated(PGraphics canvas) {

		if (label == null)
			return null;

		updateRealPos(canvas);
		canvas.pushMatrix();
		canvas.pushStyle();
		canvas.translate(pos.x, pos.y);

		if (withRectBox) {
			// if (rectBoxColor > -1)
			if (rectBoxColor != 255)
				canvas.fill(rectBoxColor, 100);
			else
				canvas.fill(rectBoxColor);

			canvas.rectMode(PApplet.CENTER);
			canvas.rect(0, 0, getWidth(), getHeight());
		}
		canvas.noFill();
		canvas.strokeWeight(2);
		canvas.stroke(255, 0, 0);

		canvas.textAlign(textAlign);
		if (pressed) {
			canvas.textSize(textSize /** 1.1f */
			);
		} else {
			canvas.fill(textColor);
			canvas.textSize(textSize);
		}

		if (font != null) {
			canvas.textFont(font, textSize);
		}

		// canvas.noFill();
		// if (textAlign == PApplet.CENTER)
		// canvas.rect(-width / 2, -height / 2, width, height);
		// else
		// canvas.rect(0, 0, width, height);

		// if (textAlign == PApplet.CENTER)
		// canvas.text(label, width / 2 + xOffset, height / 2 + textSize / 4
		// + yOffset);
		// else
		// canvas.text(label, xOffset, textSize + yOffset);

		// if (textAlign == PApplet.CENTER)
		// canvas.text(label, width / 2, height / 2 + textSize / 4);
		// else

		String tempLabel = null;
		if (label != null && !label.equals("")) {
			tempLabel = label;
		} else {
			if (placeholder != null && renderPlaceHolder) {
				tempLabel = placeholder;
			} else {
				tempLabel = label;
			}
		}

		// Draw label
		canvas.pushMatrix();

		// Apply offset if needed
		canvas.translate(textOffset.x, textOffset.y);

		if (imageMode == PApplet.CORNER) {
			if (textAlign == PApplet.CENTER) {
				canvas.text(tempLabel, width / 2, height / 2);
			} else {
				canvas.text(tempLabel, 0, height / 2);
			}

		} else {
			if (textAlign == PApplet.CENTER) {

				canvas.text(tempLabel, 0, textSize / 2);
			} else {
				canvas.text(tempLabel, -width / 2 + 10, textSize / 2);
			}
		}

		realTextWidth = canvas.textWidth(label);

		canvas.popMatrix();

		// canvas.textAlign(PApplet.CORNER);
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
		if (textAlign == PApplet.CENTER) {
			if (realPos != null && pos.x > realPos.x - width / 2
					&& pos.x < realPos.x + width) {
				if (pos.y > realPos.y - height / 2
						&& pos.y < realPos.y + height / 2) {
					over = true;
				}
			}
		} else {
			if (realPos != null && pos.x > realPos.x - width / 2
					&& pos.x < realPos.x + width) {
				if (pos.y + height / 2 > realPos.y
						&& pos.y < realPos.y + height / 2) {
					over = true;
				}
			}
		}
		return over;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		String lastLAbel = this.label;
		if (label != null)
			this.label = label;

		if (canvas != null) {
			canvas.textSize(textSize);
			float w = canvas.textWidth(label);
			float w2 = canvas.textWidth(lastLAbel);
			if (w > width-textSize/2 && w > w2) {
				System.out.println("tenemos un problema no cabe");
				this.label = lastLAbel;
			}
		}
		// chekeamos si cabe
	}

	public void setText(String label, int textSize, int align) {
		setLabel(label);
		setTextSize(textSize);
		setTextAlign(align);
	}

	public void setText(String label, int textSize, int align, int color) {
		setTextColor(color);
		setText(label, textSize, align);
	}

	public void setTextOffset(float x, float y) {
		textOffset.x = x;
		textOffset.y = y;
	}

}
