package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.content.ContentManager;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.WindowManager;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.decorator.RectDecorator;
import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;
import TUIO.TuioTime;

public class MainSimpleButton extends PApplet {

	private TuioProcessing tuioClient;

	private TUIOConverter tuioConverter;

	public int MAX_TUIOS_PROCESSED = 5;

	private WindowManager currentGuiManager;

	private List<GUIComponent> components;

	public void init() {
		// / to make a frame not displayable, you can
		// use frame.removeNotify()

		frame.removeNotify();

		frame.setUndecorated(true);

		// addNotify, here i am not sure if you have
		// to add notify again.
		frame.addNotify();

		super.init();
	}

	public void setup() {

		// size(displayWidth, displayHeight, OPENGL);

		size(1024, 768, OPENGL);
		frameRate(60);
		initGUI();
	}

	private void initGUI() {

		currentGuiManager = new WindowManager(new ContentManager(this));
		components = new ArrayList<GUIComponent>();
		tuioConverter = new TUIOConverter();
		tuioClient = new TuioProcessing(this);
		tuioConverter.init(tuioClient);

		ButtonText butonText = currentGuiManager.addTextButton(g,
				"button1Name", "button1Action", width / 2, height / 2, 36,
				CENTER);

		ButtonImage buttonImage = currentGuiManager.addButton("botonImagen",
				width / 2, height / 3, "keyBlank.jpg", "keyBlank.jpg");

		components.add(butonText);
		components.add(buttonImage);
	}

	public void draw() {

		background(0);
		drawDebugPointers();

		currentGuiManager.drawComponentes(components, g);

	}

	private void drawDebugPointers() {
		List<TouchPointer> touches = tuioConverter.getPointers(g, this);

		for (int i = 0; i < touches.size(); i++) {
			TouchPointer pointer = touches.get(i);

			stroke(192, 192, 192);
			fill(192, 192, 192);
			ellipse(pointer.getScreenX(width), pointer.getScreenY(height), 5, 5);
		}
	}

	public void addTuioCursor(TuioCursor tcur) {
		if (tcur.getCursorID() <= MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = tuioConverter.tuioToTouchPointer(g,
					tcur);
			if (currentGuiManager != null) {
				ActionEvent action = currentGuiManager.pressed(touchPointer);
				// if (action != null) {
				// doAction(action);
				// }
			}
		}

	}

	public void updateTuioCursor(TuioCursor tcur) {
		if (tcur.getCursorID() <= MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = tuioConverter.tuioToTouchPointer(g,
					tcur);
			if (currentGuiManager != null) {
				ActionEvent action = currentGuiManager.drag(touchPointer);
				if (action != null) {
					doAction(action);
				}
			}
		}
	}

	private void doAction(ActionEvent action) {
		println("tuvimos la acci�n  " + action.getAction());
	}

	public void removeTuioCursor(TuioCursor tcur) {
		if (tcur.getCursorID() <= MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = tuioConverter.tuioToTouchPointer(g,
					tcur);
			if (currentGuiManager != null) {
				ActionEvent action = currentGuiManager.onRelease(touchPointer);
				if (action != null) {
					doAction(action);
				}
			}
		}
	}

	@Override
	public void mousePressed() {
		TouchPointer touchPointer = tuioConverter.mouseToPointer(g, this);
		if (currentGuiManager != null) {
			ActionEvent action = currentGuiManager.pressed(touchPointer);
			// if (action != null) {
			// doAction(action);
			// }
		}
	}

	@Override
	public void mouseReleased() {
		TouchPointer touchPointer = tuioConverter.mouseToPointer(g, this);
		if (currentGuiManager != null) {
			ActionEvent action = currentGuiManager.onRelease(touchPointer);
			if (action != null) {
				doAction(action);
			}
		}
	}

	@Override
	public void mouseDragged() {
		TouchPointer touchPointer = tuioConverter.mouseToPointer(g, this);
		if (currentGuiManager != null) {
			ActionEvent action = currentGuiManager.drag(touchPointer);
			if (action != null) {
				doAction(action);
			}

		}
	}

	void refresh(TuioTime bundleTime) {
		// redraw();
		if (tuioClient.getTuioCursors().size() > 30) {
			tuioClient.stop();
			tuioClient.dispose();
			tuioClient = new TuioProcessing(this);
			tuioConverter.init(tuioClient);
			System.out.println("Refresh: Reiniciamos los TUIO");
		}
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainSimpleButton" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
