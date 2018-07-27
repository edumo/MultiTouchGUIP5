package org.edumo.io;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

public class DataParserFromTxt {

	public void parse(PApplet parent,String filePath ) {
		
		String[] s = parent.loadStrings(filePath);
		String text = "";
		for (int i = 0; i < s.length; i++) {
			String line = s[i];
			if (line.contains("=")) {

				String[] fields = line.split("=");

				parent.println("+++++"+fields[0]);
				parent.println(fields[1]);
			} else {
				if (!line.contains("{") && !line.contains("}")) {
					text += line;
					//text += "\n";
				}
			}
		}
		
		parent.println("+++++"+"texto");
		parent.println(text);
	}

	public void draw() {
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "DataParserFromTxt" };
		if (passedArgs != null) {
			PApplet.main(PApplet.concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}