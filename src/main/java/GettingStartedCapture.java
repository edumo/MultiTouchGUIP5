import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import processing.video.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class GettingStartedCapture extends PApplet {

	/**
	 * Getting Started with Capture.
	 * 
	 * Reading and displaying an image from an attached Capture device.
	 */

	Capture cam;

	public void setup() {

		String[] cameras = Capture.list();

		if (cameras == null) {
			println("Failed to retrieve the list of available cameras, will try the default...");
			cam = new Capture(this, 1280, 720);
		}
		if (cameras.length == 0) {
			println("There are no cameras available for capture.");
			exit();
		} else {
			println("Available cameras:");
			printArray(cameras);

			// The camera can be initialized directly using an element
			// from the array returned by list():
			cam = new Capture(this, cameras[0]);
			// Or, the settings can be defined based on the text in the list
			// cam = new Capture(this, 640, 480, "Built-in iSight", 30);

			// Start capturing the images from the camera
			cam.start();
		}
	}

	public void draw() {
		if (cam.available() == true) {
			cam.read();
		}
		image(cam, 0, 0, width, height);
		// The following does the same as the above image() line, but
		// is faster when just drawing the image without any additional
		// resizing, transformations, or tint.
		// set(0, 0, cam);
	}

	public void settings() {
		size(640, 480);
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "GettingStartedCapture" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
