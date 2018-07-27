package org.edumo.gui;

import processing.awt.PGraphicsJava2D;
import processing.core.*;

public class PGraphicsJava2DJustify extends PGraphicsJava2D {
  
  public static int JUSTIFY = 4;
  
  static protected void showTextFontException(String method) {
    throw new RuntimeException("Use textFont() before " + method + "()");
  }
  
  public void text(String str, float x1, float y1, float x2, float y2) {
    if (textFont == null) {
      showTextFontException("text");
    }

    if (textMode == SCREEN) loadPixels();

    float hradius, vradius;
    switch (rectMode) {
    case CORNER:
      x2 += x1; y2 += y1;
      break;
    case RADIUS:
      hradius = x2;
      vradius = y2;
      x2 = x1 + hradius;
      y2 = y1 + vradius;
      x1 -= hradius;
      y1 -= vradius;
      break;
    case CENTER:
      hradius = x2 / 2.0f;
      vradius = y2 / 2.0f;
      x2 = x1 + hradius;
      y2 = y1 + vradius;
      x1 -= hradius;
      y1 -= vradius;
    }
    if (x2 < x1) {
      float temp = x1; x1 = x2; x2 = temp;
    }
    if (y2 < y1) {
      float temp = y1; y1 = y2; y2 = temp;
    }

//    float currentY = y1;
    float boxWidth = x2 - x1;

//    // ala illustrator, the text itself must fit inside the box
//    currentY += textAscent(); //ascent() * textSize;
//    // if the box is already too small, tell em to f off
//    if (currentY > y2) return;

    float spaceWidth = textWidth(' ');

    if (textBreakStart == null) {
      textBreakStart = new int[20];
      textBreakStop = new int[20];
    }
    textBreakCount = 0;

    int length = str.length();
    if (length + 1 > textBuffer.length) {
      textBuffer = new char[length + 1];
    }
    str.getChars(0, length, textBuffer, 0);
    // add a fake newline to simplify calculations
    textBuffer[length++] = '\n';

    int sentenceStart = 0;
    for (int i = 0; i < length; i++) {
      if (textBuffer[i] == '\n') {
//        currentY = textSentence(textBuffer, sentenceStart, i,
//                                lineX, boxWidth, currentY, y2, spaceWidth);
        boolean legit =
          textSentence(textBuffer, sentenceStart, i, boxWidth, spaceWidth);
        if (!legit) break;
//      if (Float.isNaN(currentY)) break;  // word too big (or error)
//      if (currentY > y2) break;  // past the box
        sentenceStart = i + 1;
      }
    }

    // lineX is the position where the text starts, which is adjusted
    // to left/center/right based on the current textAlign
    float lineX = x1; //boxX1;
    if (textAlign == CENTER) {
      lineX = lineX + boxWidth/2f;
    } else if (textAlign == RIGHT) {
      lineX = x2; //boxX2;
    }

    float boxHeight = y2 - y1;
    //int lineFitCount = 1 + PApplet.floor((boxHeight - textAscent()) / textLeading);
    // incorporate textAscent() for the top (baseline will be y1 + ascent)
    // and textDescent() for the bottom, so that lower parts of letters aren't
    // outside the box. [0151]
    float topAndBottom = textAscent() + textDescent();
    int lineFitCount = 1 + PApplet.floor((boxHeight - topAndBottom) / textLeading);
    int lineCount = Math.min(textBreakCount, lineFitCount);

    if (textAlignY == CENTER) {
      float lineHigh = textAscent() + textLeading * (lineCount - 1);
      float y = y1 + textAscent() + (boxHeight - lineHigh) / 2;
      for (int i = 0; i < lineCount; i++) {
        textLineAlignImpl(textBuffer, textBreakStart[i], textBreakStop[i], lineX, y, boxWidth);
        y += textLeading;
      }

    } else if (textAlignY == BOTTOM) {
      float y = y2 - textDescent() - textLeading * (lineCount - 1);
      for (int i = 0; i < lineCount; i++) {
        textLineAlignImpl(textBuffer, textBreakStart[i], textBreakStop[i], lineX, y, boxWidth);
        y += textLeading;
      }

    } else {  // TOP or BASELINE just go to the default
      float y = y1 + textAscent();
      for (int i = 0; i < lineCount; i++) {
        textLineAlignImpl(textBuffer, textBreakStart[i], textBreakStop[i], lineX, y, boxWidth);
        y += textLeading;
      }
    }

    if (textMode == SCREEN) updatePixels();
  }

  //////////////////////////////////////////////////////////////

  // TEXT IMPL

  // These are most likely to be overridden by subclasses, since the other
  // (public) functions handle generic features like setting alignment.


  /**
   * Handles placement of a text line, then calls textLineImpl
   * to actually render at the specific point.
   */
  protected void textLineAlignImpl(char buffer[], int start, int stop,
                                   float x, float y, float boxWidth) {
    if (textAlign == CENTER) {
      x -= textWidthImpl(buffer, start, stop) / 2f;

    } else if (textAlign == RIGHT) {
      x -= textWidthImpl(buffer, start, stop);
    }

    textLineImpl(buffer, start, stop, x, y, boxWidth);
  }
  protected void textLineAlignImpl(char buffer[], int start, int stop,
                                   float x, float y) {
    textLineAlignImpl(buffer, start, stop, x, y, 0);
  }


  /**
   * Implementation of actual drawing for a line of text.
    */
  protected void textLineImpl(char buffer[], int start, int stop,
                              float x, float y) {
    textLineImpl(buffer, start, stop, x, y, 0);
  }
  protected void textLineImpl(char buffer[], int start, int stop,
                              float x, float y, float boxWidth) {
    
    int nbSpaces = 0, strWidth = 0, nbPixels = 0, j = 0;
    if (textAlign == JUSTIFY) {
      // remove last character if this is a space or similar
      if (stop > 0 && WHITESPACE.indexOf(buffer[stop-1]) > -1) stop--;
      // count number of spaces and total string width
      for (int index = start; index < stop; index++) {
        if (WHITESPACE.indexOf(buffer[index]) > -1) nbSpaces++;
        strWidth += textWidth(buffer[index]);
      }
      // count how many extra-pixels to add to reach boxWidth
      nbPixels = (int) boxWidth - strWidth;
      // distribute the extra pixels after each space
      int[] pixelsToAddAfterToken = new int[nbSpaces];
      for (int i = 0; i < nbSpaces; i++) {
        pixelsToAddAfterToken[i] = (int) (nbPixels/nbSpaces);
        if (i <= nbPixels%nbSpaces) pixelsToAddAfterToken[i]++;
      }
      
      for (int index = start; index < stop; index++) {
          textCharImpl(buffer[index], x, y);
          
          x += textWidth(buffer[index]);
          // if this is a space char, add the extra pixel for justification
          if (WHITESPACE.indexOf(buffer[index]) > -1) x += pixelsToAddAfterToken[j++];
          
      }
      
    }
    else {
        
        for (int index = start; index < stop; index++) {
          textCharImpl(buffer[index], x, y);

          // this doesn't account for kerning
          x += textWidth(buffer[index]);
          
        }
        
    }
  //  textX = x;
  //  textY = y;
  //  textZ = 0;  // this will get set by the caller if non-zero
  }

}

