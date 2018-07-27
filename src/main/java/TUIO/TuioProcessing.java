package TUIO;

import java.awt.event.*;
import java.lang.reflect.*;
import processing.core.*;
import java.util.*;

public class TuioProcessing implements TuioListener {
		
	private PApplet parent;
	private Method addTuioCursor, removeTuioCursor, updateTuioCursor, refresh;
	private TuioClient client;
	public int port;
			
	public TuioProcessing(PApplet parent) {
		this(parent,3333);
	}
	
	public TuioProcessing(PApplet parent, int port) {
		this.parent = parent;
		parent.registerMethod("dispose",this);
		
		try { refresh = parent.getClass().getMethod("refresh",new Class[] { TuioTime.class, Integer.class } ); }
		catch (Exception e) { 
			System.out.println("TUIO: missing or wrong 'refresh(TuioTime bundleTime, Integer port)' method implementation");
			refresh = null;
		}
				
		try { addTuioCursor = parent.getClass().getMethod("addTuioCursor", new Class[] { TuioCursor.class, Integer.class  }); }
		catch (Exception e) { 
			System.out.println("TUIO: missing or wrong 'addTuioCursor(TuioCursor tcur, Integer port)' method implementation");
			addTuioCursor = null;
		}
		
		try { removeTuioCursor = parent.getClass().getMethod("removeTuioCursor", new Class[] { TuioCursor.class, Integer.class  }); }
		catch (Exception e) { 
			System.out.println("TUIO: missing or wrong 'removeTuioCursor(TuioCursor tcur, Integer port)' method implementation");
			removeTuioCursor = null;
		}
		
		try { updateTuioCursor = parent.getClass().getMethod("updateTuioCursor", new Class[] { TuioCursor.class, Integer.class  }); }
		catch (Exception e) { 
			System.out.println("TUIO: missing or wrong 'updateTuioCursor(TuioCursor tcur, Integer port)' method implementation");
			updateTuioCursor = null;
		}
		
		this.port = port;
		client = new TuioClient(port);
		client.addTuioListener(this);
		client.connect();
	}
	
	public void addTuioCursor(TuioCursor tcur) {
		
		if (addTuioCursor!=null) {
			try { 
				addTuioCursor.invoke(parent, new Object[] { tcur, port });
			}
			catch (IllegalAccessException e) {}
			catch (IllegalArgumentException e) {}
			catch (InvocationTargetException e) {}
		}
	}
	
	public void updateTuioCursor(TuioCursor tcur) {
		if (updateTuioCursor!=null) {
			try { 
				updateTuioCursor.invoke(parent, new Object[] { tcur, port });
			}
			catch (IllegalAccessException e) {}
			catch (IllegalArgumentException e) {}
			catch (InvocationTargetException e) {}
		}
	}
	
	public void removeTuioCursor(TuioCursor tcur) {
		if (removeTuioCursor!=null) {
			try { 
				removeTuioCursor.invoke(parent, new Object[] { tcur, port });
			}
			catch (IllegalAccessException e) {}
			catch (IllegalArgumentException e) {}
			catch (InvocationTargetException e) {}
		}
	}
	
	public void refresh(TuioTime bundleTime) {
		if (refresh!=null) {
			try { 
				refresh.invoke(parent,new Object[] { bundleTime, port });
			}
			catch (IllegalAccessException e) {}
			catch (IllegalArgumentException e) {}
			catch (InvocationTargetException e) {}
		}
	}
	
	public ArrayList<TuioCursor> getTuioCursorList() {
		return client.getTuioCursorList();
	}	
	
	public TuioCursor getTuioCursor(long s_id) {
		return client.getTuioCursor(s_id);
	}	

// Unused TUIO interface methods
	public void addTuioObject(TuioObject tobj) {}
	
	public void updateTuioObject(TuioObject tobj) {}
	
	public void removeTuioObject(TuioObject tobj) {}
	
	public void addTuioBlob(TuioBlob tblb) {}
	
	public void updateTuioBlob(TuioBlob tblb) {}
	
	public void removeTuioBlob(TuioBlob tblb) {	}
	
	public ArrayList<TuioBlob> getTuioBlobList() {
		return null;
	}	
	
	public TuioObject getTuioObject(long s_id) {
		return null;
	}
	
	public TuioBlob getTuioBlob(long s_id) {
		return null;
	}	
	
	public ArrayList<TuioObject> getTuioObjectList() {
		return null;
	}
	
	public void pre() {
		//method that's called just after beginFrame(), meaning that it 
		//can affect drawing.
	}

	public void draw() {
		//method that's called at the end of draw(), but before endFrame().
	}
	
	public void mouseEvent(MouseEvent e) {
		//called when a mouse event occurs in the parent applet
	}
	
	public void keyEvent(KeyEvent e) {
		//called when a key event occurs in the parent applet
	}
	
	public void post() {
		//method called after draw has completed and the frame is done.
		//no drawing allowed.
	}
	
	public void size(int width, int height) {
		//this will be called the first time an applet sets its size, but
		//also any time that it's called while the PApplet is running.
	}
	
	public void stop() {
		//can be called by users, for instance movie.stop() will shut down
		//a movie that's being played, or camera.stop() stops capturing 
		//video. server.stop() will shut down the server and shut it down
		//completely, which is identical to its "dispose" function.
	}
	
	public void dispose() {
	
		if (client.isConnected()) client.disconnect();
	
		//this should only be called by PApplet. dispose() is what gets 
		//called when the host applet is stopped, so this should shut down
		//any threads, disconnect from the net, unload memory, etc. 
	}
}
