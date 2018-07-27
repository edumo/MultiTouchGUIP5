package org.edumo.content;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Movie;

public class ContentManager {

	public PApplet parent;

	public Map<String, PImage> imgContainer = new HashMap<>();
	
	public ContentManager(PApplet parent) {
		super();
		this.parent = parent;
				
	}

	public PImage loadImage(String path) {
		PImage img = imgContainer.get(path);

		
		
		if (img != null) {
			return img;
		} else {
			if(path.equals("/home/edumo/git/ECI/src/main/resources/data/0.Normalizadas/Eventos culturales/Escaparates ARCO/2007/Princesa/3.jpg")){
//				System.out.println("cargamos imagen "+path);
			}
//			System.out.println("cargamos imagen "+path);
			img = parent.loadImage(path);
			imgContainer.put(path, img);
		}

		return img;
	}
	
	public Movie  loadVideo(String path) {
		
		Movie img = new Movie(parent, path);
		
		return img;
	}
	

}
