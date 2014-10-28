package org.edumo.gui.button;

import org.edumo.gui.ActionEvent;
import org.edumo.touch.TouchPointer;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class CheckBoxImage extends ButtonImage {

	PImage checkedImg;

	boolean checked = false;

	public void init(String action, PImage img, PImage pressedImg,
			PImage checkImage, PVector pos, int size, int imageMode) {

		this.checkedImg = checkImage;
		super.init(action, img, pressedImg, pos, size);
		actionOnPressed = true;
	}

	@Override
	protected void drawImage(PGraphics canvas, PImage img) {
		if (!checked) {
			super.drawImage(canvas, img);
		} else {
			super.drawImage(canvas, checkedImg);
		}
	}

	@Override
	public ActionEvent hidPressed(TouchPointer touche) {
		ActionEvent actionEvent = super.hidPressed(touche);
		if (actionEvent != null) {
			// me han pulsado
			checked = !checked;
		}
		return actionEvent;
	}

	public PImage getCheckedImg() {
		return checkedImg;
	}

	public void setCheckedImg(PImage checkedImg) {
		this.checkedImg = checkedImg;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void change() {
		if(isChecked()){
			setChecked(false);
		}else{
			setChecked(true);
		}
	}

}
