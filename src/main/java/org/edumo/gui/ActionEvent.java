package org.edumo.gui;

public class ActionEvent {

	String action;
	
	GUIComponent component;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public GUIComponent getComponent() {
		return component;
	}

	public void setComponent(GUIComponent component) {
		this.component = component;
	}

	public ActionEvent(String action, GUIComponent component) {
		super();
		this.action = action;
		this.component = component;
	}

}
