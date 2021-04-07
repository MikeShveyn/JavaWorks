package components2;

import java.util.ArrayList;

public abstract class Truck {
	
	private static int idCounter=2000;
	private int truckID;
	private String licensePlate;
	private String truckModel;
	private boolean avaliable;
	private int timeLeft;
	private ArrayList<Package> packages;
	
	//constructors
	
	public Truck()
	{
		this.truckModel='M'+Integer.toString(this.getRundomNumber(0,4));
		this.licensePlate=this.makeLicense();
		this.avaliable=true;
		this.packages=new ArrayList<Package>();
		this.truckID=idCounter;
		idCounter++;
		//timeLeft
	}
	
	
	
	public Truck(String licensePlate, String truckModel)
	{	
		this.licensePlate=licensePlate;
		this.truckModel=truckModel;
		this.avaliable=true;
		this.packages=new ArrayList<Package>();
		//truckID,timeLeft
	}
	
	//Help function
	public int getRundomNumber(int min,int max) {
		return (int)((Math.random()*(max-min))+min);
	}
	
	private String makeLicense() {
		int num1=getRundomNumber(100,999);
		int num2=getRundomNumber(10,99);
		int num3=getRundomNumber(100,999);
		return Integer.toString(num1)+"-"+Integer.toString(num2)+"-"+Integer.toString(num3);
	}

	//Getters Setters

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



	public int getTimeLeft() {
		return timeLeft;
	}



	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}



	public ArrayList<Package> getPackages() {
		return packages;
	}



	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}


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
