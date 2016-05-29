package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.content.ContentManager;
import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.MTGuiP5PApplet;
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

public class MainScreenTransition extends MTGuiP5PApplet {

	private Window auxWindow;
	private Ani anitransition;

	private Home homeScreen;
	private SecondScreen secondScreen;

	private boolean transition = false;

	public void setup() {

		size(1024, 768, P2D);
		frameRate(60);
		Ani.init(this);
		initGUI();
	}

	private void initGUI() {

		mtContext = new BaseApp(this, g);

		homeScreen = new Home(mtContext);
		homeScreen.init(mtContext);

		secondScreen = new SecondScreen(mtContext);
		secondScreen.init(mtContext);

		window = homeScreen;

	}

	public void draw() {

		background(0);
		mtContext.drawDebugPointers(g);

		if (transition) {
			auxWindow.drawUndecorated(g);
			if (anitransition.isEnded()) {
				transition = false;
			}
		}

		String action = window.drawUndecorated(g);

	}

	protected void doAction(ActionEvent action) {

		if (action != null) {

			println("action " + action.getAction());

			if (window == homeScreen) {
				if (action.getAction().equals("button1Action")) {
					// vamos a ahcer algo como mover esta pantalla
					auxWindow = window;
					secondScreen.getPosition().x = -width;
					window = secondScreen;
					transition = true;

					window.animate(0, 0, 3);
					anitransition = auxWindow.animate(width, 0, 3);
				}
			} else if (window == secondScreen) {
				if (action.getAction().equals("button1Action")) {
					// vamos a ahcer algo como mover esta pantalla
					auxWindow = window;
					homeScreen.getPosition().x = width;
					window = homeScreen;
					transition = true;

					window.animate(0, 0, 3);
					anitransition = auxWindow.animate(-width, 0, 3);
				}
			}
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
