package org.edumo.gui.button;

import org.edumo.gui.ActionEvent;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Capture;
import processing.video.Movie;

public class ButtonCapture extends ButtonImage {

	public Capture capture;

	boolean play = false;

	public boolean drawMovie = false;

	public String drawUndecorated(PGraphics canvas) {

		if (capture.available() == true) {
			capture.read();
		}
		
		if (!drawMovie)
			return drawUndecorated(canvas, capture);
		else
			return drawUndecorated(canvas, img);
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		ActionEvent actionEvent = super.hidReleased(touche);

		return actionEvent;
	}

	public void setCapture(Capture movie) {
		img = movie;
		this.capture = movie;
	}

	public void setThumbnail(PImage img) {
		this.img = img;
	}

//	public void playPause() {
//		if (play) {
//			capture.pause();
//			play = false;
//		} else {
//			if (capture.time() >= capture.duration() - 1) {
//				capture.jump(0);
//			}
//			capture.play();
//			play = true;
//		}
//
//	}

	public void play() {
		// if (play) {
		// movie.pause();
		// play = false;
		// } else {
		// if (movie.time() >= movie.duration() - 1) {
//		capture.volume(0);
//		capture.jump(0);
//		// }
//		capture.play();
//		capture.volume(0);
		play = true;
		drawMovie = true;

//		capture.volume(0);
		capture.start();
		// F}

	}

	@Override
	protected void updateRealPos(PGraphics canvas) {

		super.updateRealPos(canvas);
//		capture.volume(0);
//		if (capture.time() >= capture.duration()) {
//			play = false;
//		}
	}

	public void stop() {
		capture.stop();
		drawMovie = false;
	}
}
