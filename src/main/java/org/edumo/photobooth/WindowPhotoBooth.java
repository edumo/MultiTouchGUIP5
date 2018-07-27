package org.edumo.photobooth;

import org.edumo.content.BaseApp;
import org.edumo.gui.Window;

public class WindowPhotoBooth extends Window {

	protected BaseAppPhotoBooth mtContext;
	
	public WindowPhotoBooth(BaseAppPhotoBooth contextApp) {
		super(contextApp);
		this.mtContext = contextApp;
	}

	public WindowPhotoBooth(BaseApp contextApp, boolean param) {
		super(contextApp, param);
	}

}
