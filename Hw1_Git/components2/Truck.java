package components2;

import java.util.ArrayList;

import components1.Drawable;
import components1.Dynamic;
import components1.ThreadBand;


/**
 * ID 336249743
 * ID 336249628
 * 
 * 
 * Truck Class represent abstract truck
 * idCounter - static variable to count instances of different trucks
 * truckId -  truck id based on IdCounter
 * licensePlate - 6 digit integer
 * truckModel - random model
 * available - boolean that show if track can be used by branch or hub
 * timLeft - count to time between locations
 * packages - list of packages
 */


public abstract class Truck extends Thread implements ThreadBand, Drawable, Dynamic {
	
	private static int idCounter=2000;
	private int truckID;
	private String licensePlate;
	private String truckModel;
	private boolean avaliable;
	private double timeLeft;
	private ArrayList<Package> packages;
	protected boolean isRun = true;
	protected boolean getSleep = false;
	public int x_cor = 0;
	public int y_cor = 0;
	public int x_origin = 0;
	public int y_origin = 0;
	public int x_Dest = 0;
	public int y_Dest = 0;
	//constructors------------------------------------------------------------------------------------
	public Truck()
	{	
		super();
		this.truckModel='M'+Integer.toString(this.getRundomNumber(0,4));
		this.licensePlate=this.makeLicense();
		this.avaliable=true;
		this.packages=new ArrayList<Package>();
		this.truckID=idCounter;
		idCounter++;
	}
	
	
	public Truck(String licensePlate, String truckModel)
	{	
		super();
		this.licensePlate=licensePlate;
		this.truckModel=truckModel;
		this.avaliable=true;
		this.packages=new ArrayList<Package>();

	}
	
	//Help function------------------------------------------------------------------------------------------
	public int getRundomNumber(int min,int max) {
		return (int)((Math.random()*(max-min))+min);
	}
	
	
	private String makeLicense() {
		int num1=getRundomNumber(100,999);
		int num2=getRundomNumber(10,99);
		int num3=getRundomNumber(100,999);
		return Integer.toString(num1)+"-"+Integer.toString(num2)+"-"+Integer.toString(num3);
	}

	//Getters Setters-------------------------------------------------------------------------------------

	public int getTruckID() {
		return truckID;
	}



	public void setTruckID(int truckID) {
		this.truckID = truckID;
	}



	public String getLicensePlate() {
		return licensePlate;
	}



	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}



	public String getTruckModel() {
		return truckModel;
	}



	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}



	public boolean isAvaliable() {
		return avaliable;
	}



	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}



	public double getTimeLeft() {
		return timeLeft;
	}



	public void setTimeLeft(double timeLeft) {
		this.timeLeft = timeLeft;
	}



	public ArrayList<Package> getPackages() {
		return packages;
	}



	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}
	
	
	
	
	
	
	
	//default functions----------------------------------------------------------------------------------------------------
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Truck other = (Truck) obj;
		if (avaliable != other.avaliable)
			return false;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		if (packages == null) {
			if (other.packages != null)
				return false;
		} else if (!packages.equals(other.packages))
			return false;
		if (timeLeft != other.timeLeft)
			return false;
		if (truckID != other.truckID)
			return false;
		if (truckModel == null) {
			if (other.truckModel != null)
				return false;
		} else if (!truckModel.equals(other.truckModel))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "truckID=" + truckID + ", licensePlate=" + licensePlate + ", truckModel=" + truckModel
				+ ", avaliable=" + avaliable;
	}
	
	
}
