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
import org.edumo.screens.PhotoScene;
import org.edumo.screens.SecondScreen;
import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;
import TUIO.TuioTime;

public class MainPhotoBooth extends MTGuiP5PApplet {

	private Window auxWindow;
	private Ani anitransition;

	private Home homeScreen;
	private PhotoScene photoScene;

	private boolean transition = false;

	public void setup() {

		frameRate(60);
		Ani.init(this);
		initGUI();
	}

	private void initGUI() {

		mtContext = new BaseApp(this, g);

		homeScreen = new Home(mtContext);
		homeScreen.init(mtContext);

		photoScene = new PhotoScene(mtContext);
		photoScene.init(mtContext);

		window = homeScreen;

	}

	public void draw() {

		background(0);
		mtContext.drawDebugPointers(g);

		String action = window.drawUndecorated(g);

		if (transition) {

			if (anitransition != null && anitransition.isEnded()) {
				transition = false;
			} else {
				auxWindow.drawUndecorated(g);
			}
		}

	}

	protected void doAction(ActionEvent action) {

		if (transition) {
			return;
		}

		if (action != null) {

			println("action " + action.getAction());

			if (window == homeScreen) {
				if (action.getAction().equals("button1Action")) {
					transition = true;
					auxWindow = window;
					window = photoScene;
					window.setPosition(0, 0, 0);
					anitransition = auxWindow.animate(width, 0, 1.5f);
				}
			} else if (window == photoScene) {
				if (action.getAction().equals("button1Action")) {
					transition = true;
					auxWindow = window;
					window = homeScreen;
					window.setPosition(0, 0, 0);

					anitransition = auxWindow.animate(width, 0, 1.5f);
				}
				
				if (action.getAction().equals("takePhoto")) {
					photoScene.startCountDown();
				}
			}
		}

	}

	@Override
	public void settings() {
		size(1024, 768, P3D);
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainPhotoBooth" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
