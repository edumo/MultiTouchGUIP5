package org.edumo;

import org.edumo.content.BaseApp;
import org.edumo.gui.ActionEvent;
import org.edumo.gui.GUIComponent;
import org.edumo.gui.MTGuiP5PApplet;
import org.edumo.gui.Window;
import org.edumo.gui.behaviour.DraggableDecorator;
import org.edumo.gui.button.ButtonImage;
import org.edumo.gui.button.ButtonVideo;

import processing.core.PApplet;
import processing.core.PVector;
import processing.video.Movie;

public class MainDraggableResizableVideo extends MTGuiP5PApplet {

	ButtonVideo dragableVideo;
	
	public void setup() {
		smooth(4);
		mtContext = new BaseApp(this, g);
		frameRate(60);
		initGUI();
		mtContext.ignoreMouseIfTUIO = true;
	}

	public void movieEvent(Movie m) {
		m.read();
	}
	
	private void initGUI() {

		window = new Window(mtContext);

		dragableVideo = window.getWindowManager().addButtonVideo("play", width / 2, height / 2, "/home/edumo/big_buck_bunny_1080p_H264_AAC_25fps_7200K.MP4");
		dragableVideo.setResizeOnDraw(new PVector(200, 200));
		
//		Movie movie2 = new Movie(this, "/home/edumo/big_buck_bunny_1080p_H264_AAC_25fps_7200K.MP4");
//		dragableImage.img = movie2;
//		movie2.play();
//		movie2.pause();

		DraggableDecorator decorator = window.getWindowManager().addDraggable( dragableVideo);
		window.getWindowManager().addResizable( dragableVideo);
//		window.getWindowManager().addPlayable(decorator);
		
		
	}

	public void draw() {

		background(0);
		mtContext.drawDebugPointers(g);
		window.drawUndecorated(g);

	}

	protected void doAction(ActionEvent action) {
		if (action != null){
			println("action " + action.getAction());
			dragableVideo.play();
		}
	}
	
	@Override
	public void settings() {
		size(1024, 768, P3D);
	}

	static public void main(String[] passedArgs) {

		String[] appletArgs = new String[] { "org.edumo.MainDraggableResizableVideo" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
