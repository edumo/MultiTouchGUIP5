package org.edumo;

import java.util.ArrayList;
import java.util.List;

import org.edumo.content.ContentManager;
import org.edumo.content.MTContext;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.Window;
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

	MTContext mtContext;

	private Window window;

	public void setup() {

		// size(displayWidth, displayHeight, OPENGL);

		size(1024, 768, OPENGL);
		mtContext = new MTContext(this, g);
		frameRate(60);
		initGUI();
	}

	private void initGUI() {

		window = new Window(mtContext);

		ButtonText butonText = window.getGuiManager().addTextButton(g,
				"button1Name", "button1Action", width / 2, height / 2, 36,
				CENTER);

		ButtonImage buttonImage = window.getGuiManager().addButton(
				"botonImagen", width / 2, height / 3, "keyblank.jpg",
				"keyblank.jpg");

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

		String[] appletArgs = new String[] { "org.edumo.MainSimpleButton" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
