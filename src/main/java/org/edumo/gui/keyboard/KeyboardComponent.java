package org.edumo.gui.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.WindowManager;
import org.edumo.gui.HIDEventListener;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.decorator.RectDecorator;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class KeyboardComponent extends GUIComponent implements HIDEventListener {

	private WindowManager guiManager;
	private String action;
	private List<HIDEventListener> listeners = new ArrayList<>();

	public static String SPACE = "ESPACIOCAD2145";
	public static String DELETE = "DELETECAD2147";

	private long nextDelete = 0;
	private ButtonText comaB;
	private ButtonText puntoB;

	public void init(PApplet parent, WindowManager guiManager,
			String[][] chars, int keySize, String action) {

		this.guiManager = guiManager;

		this.action = action;

		int posXCero = 0;
		int posX = 0;
		int posY = 0;

		for (int i = 0; i < chars.length; i++) {
			String[] line = chars[i];
			for (int j = 0; j < line.length; j++) {
				int keySizeW = keySize;
				String string = line[j].toUpperCase();
				if (string.equals(SPACE)) {
					string = " ";
					keySizeW = keySize * 5;
				} else if (string.equals(DELETE)) {
					string = "<--";
					keySizeW = keySize * 2;
				}
				ButtonText butonText = new ButtonText();
				butonText.init(string, action + "-" + chars[i][j], new PVector(
						posX, posY), keySizeW, keySize, 22);
				RectDecorator roundedRectDecorator = new RectDecorator(
						butonText, 255);
				components.add(roundedRectDecorator);
				listeners.add(butonText);

				if (string.equals(".")) {
					puntoB = butonText;
				}
				if (string.equals(",")) {
					comaB = butonText;
				}

				posX += 65;
			}
			posXCero += keySize / 3;
			posX = posXCero;
			posY += 65;
		}
	}

	/**
	 * Método de inicialización de teclado
	 * 
	 * @param parent
	 * @param guiManager
	 * @param chars
	 * @param keySize
	 * @param action
	 * @param imgs
	 *            Ha de recibir un array de mínimo 3 posiciones, con tamaño 1
	 *            (tecla) 2 (borrar) 3 (espacio). Si se reciben más posiciones
	 *            se alternarán entre las teclas de tamaño uno
	 */

	public void init(PApplet parent, WindowManager guiManager,
			String[][] chars, int keySize, String action, String[] imgs) {

		this.guiManager = guiManager;

		this.action = action;

		int posXCero = 0;
		int posX = 0;
		int posY = 0;

		int imageIndex = 0;

		for (int i = 0; i < chars.length; i++) {
			String[] line = chars[i];
			ButtonImage butonImage = null;
			for (int j = 0; j < line.length; j++) {

				String string = line[j].toUpperCase();
				String imgPath = imgs[imageIndex];

				if (string.equals(SPACE)) {
					string = " ";
					imgPath = imgs[imgs.length - 1];
					posX += 102;
				} else if (string.equals(DELETE)) {
					string = "<--";
					imgPath = imgs[imgs.length - 2];
					posX += 26;
				}

				butonImage = guiManager.addButton(action + "-" + chars[i][j],
						posX, posY, imgPath, imgPath,false);
				butonImage.setLabel(string);
				butonImage.setTextSize(22);
				butonImage.setTextAlign(PApplet.CENTER);
				imageIndex++;

				if (imageIndex >= imgs.length - 2) {
					imageIndex = 0;
				}

				// if (string.equals(".")) {
				// butonImage.setyOffset(10);
				// }
				//
				// if (string.equals(",")) {
				// butonImage.setyOffset(7);
				// }
				//
				// if (string.equals("-")) {
				// butonImage.setyOffset(-7);
				// butonImage.setLabel("_");
				// }
				//
				// if (string.equals("_")) {
				// butonImage.setyOffset(4);
				// }

				components.add(butonImage);

				posX += butonImage.img.width + 1;
			}
			posXCero += keySize / 3;
			posX = posXCero;
			posY += butonImage.img.height;
		}
	}

	@Override
	public ActionEvent hidPressed(TouchPointer touche) {
		String action = guiManager.pressed(listeners, touche).getAction();
		if (action != null)
			return new ActionEvent(action, this);
		else
			return null;
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		String action = guiManager.release(listeners, touche).getAction();
		if (action != null)
			return new ActionEvent(action, this);
		else
			return null;
	}

	@Override
	public ActionEvent hidDragged(TouchPointer touche) {
		String action = guiManager.drag(listeners, touche).getAction();
		if (action != null)
			return new ActionEvent(action, this);
		else
			return null;
	}

	@Override
	public String draw(PGraphics canvas) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);
		guiManager.drawComponentes(components, canvas);
		canvas.popMatrix();
		return null;
	}

}
