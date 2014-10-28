package org.edumo.gui.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.WindowManager;
import org.edumo.gui.HIDEventListener;
import org.edumo.gui.button.AbstractButton;
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
	private AbstractButton comaB;
	private AbstractButton puntoB;
	
	private ButtonImage espacio;
	private ButtonImage borrar;
	private ButtonImage letra;
	
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
					string = "DELETE";
					keySizeW = keySize * 2;
				}
				ButtonText butonText = new ButtonText();
				butonText.init(string, action + "-" + chars[i][j], new PVector(
						posX, posY), keySizeW, keySize, 22);
				components.add(butonText);
				listeners.add(butonText);

				if (string.equals(".")) {
					puntoB = butonText;
				}
				if (string.equals(",")) {
					comaB = butonText;
				}

				posX += 65;
			}
			posXCero += keySize / 5;
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

		ButtonImage firstButtonImage = null;
		
		for (int i = 0; i < chars.length; i++) {
			String[] line = chars[i];
			ButtonImage buttonImage = null;
			
			for (int j = 0; j < line.length; j++) {
				
				String imgPath = imgs[0];
				String imgPathPressed = imgs[1];

				String string = line[j].toUpperCase();

				if (string.equals(SPACE)) {
					string = " ";
					imgPath = imgs[imgs.length - 2];
					imgPathPressed = imgs[imgs.length - 1];
				} else if (string.equals(DELETE)) {
					string = "DELETE";
					imgPath = imgs[imgs.length - 4];
					imgPathPressed = imgs[imgs.length - 3];
				}

				buttonImage = guiManager.addButton(action + "-" + chars[i][j],
						posX, posY, imgPath, imgPathPressed, false,
						PApplet.CENTER);
				
				buttonImage.setLabel(string);
				buttonImage.setTextSize(28);
				buttonImage.setTextColor(255);
				buttonImage.setTextAlign(PApplet.CENTER);
				
				// Hack: para el proyecto Confesionario
				buttonImage.setTextOffset(-2, -8);
				
				if(firstButtonImage == null){
					firstButtonImage = buttonImage; 
				}
				
				// Hack: para el proyecto Confesionario
				if (string.equals(" ")) {
					espacio = buttonImage;
					espacio.setPosition(354, 280);
				} else if (string.equals("DELETE")) {
					borrar = buttonImage;
					borrar.setLabel(" ");
					borrar.setPosition(840, 0);
				} else if (string.equals("Q")) {
					letra = buttonImage;
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

				components.add(buttonImage);

				posX += buttonImage.img.width + 1;
			}
			posXCero += keySize / 5;
			posX = posXCero;
			posY += firstButtonImage.img.height;
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
	public ActionEvent hidMoved(TouchPointer touche) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String drawUndecorated(PGraphics canvas) {

		canvas.pushMatrix();
		canvas.translate(pos.x, pos.y);
		guiManager.drawComponentes(components, canvas);
		canvas.popMatrix();
		
		return null;
	}

}
