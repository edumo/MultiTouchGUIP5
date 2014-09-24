package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.gui.ActionEvent;
import org.edumo.gui.Component;
import org.edumo.gui.GUIManager;
import org.edumo.gui.button.ButonText;
import org.edumo.gui.decorator.RectDecorator;
import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;
import TUIO.TuioTime;

public class Main extends PApplet {

	private TuioProcessing tuioClient;

	private TUIOConverter tuioConverter;

	public int MAX_TUIOS_PROCESSED = 5;

	private GUIManager currentGuiManager;

	private List<Component> components;

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
		currentGuiManager = new GUIManager();
		components = new ArrayList<Component>();
		tuioConverter = new TUIOConverter();
		tuioClient = new TuioProcessing(this);
		tuioConverter.init(tuioClient);

		ButonText butonText = currentGuiManager.addTextButton(g, "button1Name",
				"button1Action", width / 2, height / 2, 36, CENTER);
		
		RectDecorator rectDecorator = new RectDecorator(butonText, 255);

		components.add(rectDecorator);
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
		println("tuvimos la acción  " + action.getAction());
	}

	public void removeTuioCursor(TuioCursor tcur) {
		if (tcur.getCursorID() <= MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = tuioConverter.tuioToTouchPointer(g,
					tcur);
			if (currentGuiManager != null) {
				ActionEvent action = currentGuiManager.release(touchPointer);
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
			ActionEvent action = currentGuiManager.release(touchPointer);
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

		String[] appletArgs = new String[] { "org.edumo.Main" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
