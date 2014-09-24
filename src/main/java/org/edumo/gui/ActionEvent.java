package org.edumo.gui;

public class ActionEvent {

	String action;
	
	Component component;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public ActionEvent(String action, Component component) {
		super();
		this.action = action;
		this.component = component;
	}

}
