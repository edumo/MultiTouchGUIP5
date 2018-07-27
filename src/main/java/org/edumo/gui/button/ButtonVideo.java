package org.edumo.gui.button;

import org.edumo.gui.ActionEvent;
import org.edumo.touch.TouchPointer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Movie;

public class ButtonVideo extends ButtonImage {

	public Movie movie;

	boolean play = false;

	public boolean drawMovie = false;

	public String drawUndecorated(PGraphics canvas) {

		if (!drawMovie)
			return drawUndecorated(canvas, movie);
		else
			return drawUndecorated(canvas, img);
	}

	@Override
	public ActionEvent hidReleased(TouchPointer touche) {
		ActionEvent actionEvent = super.hidReleased(touche);

		return actionEvent;
	}

	public void setMovie(Movie movie) {
		img = movie;
		this.movie = movie;
	}

	public void setThumbnail(PImage img) {
		this.img = img;
	}

	public void playPause() {
		if (play) {
			movie.pause();
			play = false;
		} else {
			if (movie.time() >= movie.duration() - 1) {
				movie.jump(0);
			}
			movie.play();
			play = true;
		}

	}

	public void play() {
		// if (play) {
		// movie.pause();
		// play = false;
		// } else {
		// if (movie.time() >= movie.duration() - 1) {
		movie.volume(0);
		movie.jump(0);
		// }
		movie.play();
		movie.volume(0);
		play = true;
		drawMovie = true;

		movie.volume(0);
		// F}

	}

	@Override
	protected void updateRealPos(PGraphics canvas) {

		super.updateRealPos(canvas);
		movie.volume(0);
		if (movie.time() >= movie.duration()) {
			play = false;
		}
	}

	public void stop() {
		movie.stop();
		drawMovie = false;
	}
}
