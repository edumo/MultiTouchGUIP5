package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.content.ContentManager;
import org.edumo.content.MTContext;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.Window;
import org.edumo.gui.WindowManager;
import org.edumo.gui.button.ButtonText;
import org.edumo.gui.decorator.RectDecorator;
import org.edumo.gui.keyboard.KeyboardComponent;
import org.edumo.touch.TUIOConverter;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;
import TUIO.TuioTime;

public class MainKeyboard extends PApplet {

	MTContext mtContext;

	Window window;

	public void setup() {

		size(1024, 768, OPENGL);
		
		mtContext = new MTContext(this,g);
		frameRate(60);
		initGUI();
	}

	private void initGUI() {

		window = new Window(mtContext);
		KeyboardComponent keyboardComponent = new KeyboardComponent();
		String[][] chars = {
				{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "'", "@",
						KeyboardComponent.DELETE },
				{ "q", "w", "e", "r", "t", "y", "u", "i", "o", "p" },
				{ "a", "s", "d", "f", "g", "h", "j", "k", "l", "�", "�" },
				{ "z", "x", "c", "v", "b", "n", "m", /* ",", */"-", "_", "." },
				{ KeyboardComponent.SPACE } };

		String[] imgs = { "keyblank.jpg", "keyblank.jpg", "keyblank.jpg" };

		keyboardComponent
				.init(this, window.getGuiManager(), chars, 50, "kb33", imgs);
		keyboardComponent.setPosition(100, 200);
		
		window.add(keyboardComponent);
	}

	public void draw() {

		background(0);
		mtContext.drawDebugPointers(g);

		window.draw(g);

	}

	private void doAction(ActionEvent action) {
		if (action != null)
			println("tuvimos la acci�n  " + action.getAction());
	}

	public void addTuioCursor(TuioCursor tcur) {
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter
					.tuioToTouchPointer(g, tcur);
			ActionEvent action = window.hidPressed(touchPointer);
			// doAction(action);
		}

	}

	public void updateTuioCursor(TuioCursor tcur) {
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter
					.tuioToTouchPointer(g, tcur);
			ActionEvent action = window.hidDragged(touchPointer);
			doAction(action);
		}
	}

	public void removeTuioCursor(TuioCursor tcur) {
		if (tcur.getCursorID() <= mtContext.MAX_TUIOS_PROCESSED) {
			TouchPointer touchPointer = mtContext.tuioConverter
					.tuioToTouchPointer(g, tcur);
			ActionEvent action = window.hidReleased(touchPointer);
			doAction(action);
		}
	}

	@Override
	public void mousePressed() {
		TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g,
				this);
		ActionEvent action = window.hidPressed(touchPointer);
		doAction(action);
	}

	@Override
	public void mouseReleased() {
		TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g,
				this);
		ActionEvent action = window.hidReleased(touchPointer);
		doAction(action);
	}

	@Override
	public void mouseDragged() {
		TouchPointer touchPointer = mtContext.tuioConverter.mouseToPointer(g,
				this);
		ActionEvent action = window.hidDragged(touchPointer);
		doAction(action);
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainKeyboard" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
