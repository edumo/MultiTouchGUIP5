package org.edumo.gui;

import org.edumo.touch.TouchPointer;


/**
 * interfaz para el remetimiento de los eventos de ratn/tuio/.../?
 * @author edumo
 *
 */

public interface HIDEventListener {

	String hidPressed(TouchPointer touche);
	
	String hidReleased(TouchPointer touche);
	
	String hidDragged(TouchPointer touche);
	
	boolean isActive();
	
}
