package components2;

import java.util.ArrayList;

import components1.Drawable;
import components1.ThreadBand;

public class Momento{
	
	private ArrayList<ThreadBand> threadBandsSaved;
	private ArrayList<Thread> gameThreadSaved;
	private ArrayList<Drawable> drawObjectsSaved;
	private Hub hubSaved;
	private int y;

    public Momento(Hub h, ArrayList<ThreadBand> threadBands, ArrayList<Thread> gameThread,ArrayList<Drawable> drawObjects, int y)
    {
    	this.threadBandsSaved = threadBands;
    	this.gameThreadSaved = gameThread;
    	this.drawObjectsSaved = drawObjects;
    	this.hubSaved=h;
    	this.y=y;
    	
    }

    public ArrayList<ThreadBand> threadBandsSBack() {return this.threadBandsSaved;}
    public ArrayList<Thread> gameThreadBack(){return this.gameThreadSaved;}
    public ArrayList<Drawable> drawObjectsBack(){return this.drawObjectsSaved;}
    public Hub getHubBack() {return this.hubSaved;}
    public int getYBack() {return this.y;}
    
    
    
    public String toString()
    {
    	String s= "ThreadbAND "  + this.threadBandsSaved.toString() +  " ThreadbsAVED "  + this.gameThreadSaved.toString() + 
    		 " DrawObjects"   + this.drawObjectsSaved.toString() + " Y " +  this.y + " hUB " + this.hubSaved.toString();
		return s;
    	
    }
}
