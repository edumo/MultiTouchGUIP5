package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.content.ContentManager;
import org.edumo.content.ContextApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.WindowManager;
import org.edumo.gui.Window;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.decorator.RectDecorator;
import org.edumo.screens.Home;
import org.edumo.screens.SecondScreen;
import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import de.looksgood.ani.Ani;

import processing.core.PApplet;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;
import TUIO.TuioTime;

public class MainScreenTransition extends PApplet {

	private TuioProcessing tuioClient;

	private TUIOConverter tuioConverter;

	public int MAX_TUIOS_PROCESSED = 5;

	private Window currentScreen;
	private Window auxScreen;
	private Ani anitransition;

	private Home homeScreen;
	private SecondScreen secondScreen;

	private boolean transition = false;

	private WindowManager currentWindowManager;

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
		
		ContextApp contextApp = new ContextApp();
		contextApp.parent = this;
		contextApp.canvas = g;
		contextApp.contentManager = new ContentManager(this);

		homeScreen = new Home();
		homeScreen.init(contextApp);

		secondScreen = new SecondScreen();
		secondScreen.init(contextApp);

		currentWindowManager = homeScreen.getGuiManager();
		currentScreen = homeScreen;

	}

	public void draw() {

		background(0);
		drawDebugPointers();

		if (transition) {
			auxScreen.draw(g);
			if (anitransition.isEnded()) {
				transition = false;
			}
		}

		String action = currentScreen.draw(g);

	}

	private void doAction(ActionEvent action) {

		println("action " + action.getAction());

		if (currentScreen == homeScreen) {
			if (action.getAction().equals("button1Action")) {
				// vamos a ahcer algo como mover esta pantalla
				auxScreen = currentScreen;
				secondScreen.getPos().x = -width;
				currentScreen = secondScreen;
				transition = true;

				currentScreen.animate(0, 0, 3);
				anitransition = auxScreen.animate(width, 0, 3);
			}
		} else if (currentScreen == secondScreen) {
			if (action.getAction().equals("button1Action")) {
				// vamos a ahcer algo como mover esta pantalla
				auxScreen = currentScreen;
				homeScreen.getPos().x = width;
				currentScreen = homeScreen;
				transition = true;

				currentScreen.animate(0, 0, 3);
				anitransition = auxScreen.animate(-width, 0, 3);
			}
		}

		currentWindowManager = currentScreen.getGuiManager();
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
			if (currentWindowManager != null) {
				ActionEvent action = currentWindowManager.pressed(touchPointer);
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
			if (currentWindowManager != null) {
				ActionEvent action = currentWindowManager.drag(touchPointer);
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
			if (currentWindowManager != null) {
				ActionEvent action = currentWindowManager.onRelease(touchPointer);
				if (action != null) {
					doAction(action);
				}
			}
		}
	}

	@Override
	public void mousePressed() {
		TouchPointer touchPointer = tuioConverter.mouseToPointer(g, this);
		if (currentWindowManager != null) {
			ActionEvent action = currentWindowManager.pressed(touchPointer);
			// if (action != null) {
			// doAction(action);
			// }
		}
	}

	@Override
	public void mouseReleased() {
		TouchPointer touchPointer = tuioConverter.mouseToPointer(g, this);
		if (currentWindowManager != null) {
			ActionEvent action = currentWindowManager.onRelease(touchPointer);
			if (action != null) {
				doAction(action);
			}
		}
	}

	@Override
	public void mouseDragged() {
		TouchPointer touchPointer = tuioConverter.mouseToPointer(g, this);
		if (currentWindowManager != null) {
			ActionEvent action = currentWindowManager.drag(touchPointer);
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

		String[] appletArgs = new String[] { "org.edumo.MainScreenTransition" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
