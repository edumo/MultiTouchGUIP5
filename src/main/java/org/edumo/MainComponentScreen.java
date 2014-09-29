package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.gui.ActionEvent;
import org.edumo.gui.Component;
import org.edumo.gui.GUIManager;
import org.edumo.gui.ScreenComponent;
import org.edumo.gui.button.ButonText;
import org.edumo.gui.decorator.RectDecorator;
import org.edumo.screens.Home;
import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import de.looksgood.ani.Ani;

import processing.core.PApplet;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;
import TUIO.TuioTime;

public class MainComponentScreen extends PApplet {

	private TuioProcessing tuioClient;

	private TUIOConverter tuioConverter;

	public int MAX_TUIOS_PROCESSED = 5;

	private ScreenComponent currentScreen;
	private GUIManager currentGuiManager;

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
		Ani.init(this);
		initGUI();
	}

	private void initGUI() {

		tuioConverter = new TUIOConverter();
		tuioClient = new TuioProcessing(this);
		tuioConverter.init(tuioClient);

		currentScreen = new Home();
		currentScreen.init(g);
		currentGuiManager = currentScreen.getGuiManager();
	}

	public void draw() {

		background(0);
		drawDebugPointers();

		currentScreen.draw(g);

	}
	
	private void doAction(ActionEvent action) {
		
		println("action "+action.getAction());
		
		if(action.getAction().equals("button1Action")){
			//vamos a ahcer algo como mover esta pantalla
			currentScreen.animate(width/2, 0, 3);
		}
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

		String[] appletArgs = new String[] { "org.edumo.MainComponentScreen" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
