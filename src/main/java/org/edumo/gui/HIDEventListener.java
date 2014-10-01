package org.edumo.gui;

import org.edumo.touch.TouchPointer;


/**
 * interfaz para el remetimiento de los eventos de ratn/tuio/.../?
 * @author edumo
 *
 */

public interface HIDEventListener {

	ActionEvent hidPressed(TouchPointer touche);
	
	ActionEvent hidReleased(TouchPointer touche);
	
	ActionEvent hidDragged(TouchPointer touche);
	
	boolean isActive();
	
}
