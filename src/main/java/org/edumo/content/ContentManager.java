package org.edumo.content;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

public class ContentManager {

	private PApplet parent;

	private Map<String, PImage> imgContainer = new HashMap<>();

	public ContentManager(PApplet parent) {
		super();
		this.parent = parent;
	}

	public PImage loadImage(String path) {
		PImage img = imgContainer.get(path);

		if (img != null) {
			return img;
		} else {
			img = parent.loadImage(path);
			imgContainer.put(path, img);
		}

		return img;
	}

}
