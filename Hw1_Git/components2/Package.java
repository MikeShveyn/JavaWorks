package components2;

import java.util.ArrayList;

import components1.*;

public abstract class Package {
	private static int idCounter=1000;
	private  int packageID; // check where define
	private Priority priority;
	private Status status;
	private Address senderAdress;
	private Address destinationAdress;
	private ArrayList<Tracking> tracking;
	
	
	//constructor
	public Package(Priority priority, Address senderAddress, Address destinationAddress)
	{
		this.priority = priority;
		this.senderAdress = senderAddress;
		this.destinationAdress = destinationAddress;
		this.status = Status.CREATION;
		this.tracking = new ArrayList<Tracking>();
		this.packageID=idCounter;
		idCounter++;
	}

	
	//getters setters

	public Priority getPriority() {
		return priority;
	}


	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public Address getSenderAdress() {
		return senderAdress;
	}


	public void setSenderAdress(Address senderAdress) {
		this.senderAdress = senderAdress;
	}


	public Address getDestinationAdress() {
		return destinationAdress;
	}


	public void setDestinationAdress(Address destinationAdress) {
		this.destinationAdress = destinationAdress;
	}


	public ArrayList<Tracking> getTracking() {
		return tracking;
	}


	public void setTracking(ArrayList<Tracking> tracking) {
		this.tracking = tracking;
	}

	
	//methods
	
	public void addTracking(Node node, Status status)
	{
		tracking.add(new Tracking(MainOffice.clock ,node, status));
	}
	
	public void printTracking()
	{
		for(Tracking track : tracking)
		{
			System.out.println(track);
		}
	}
	
	
	@Override
	public String toString()
	{
		return "packageID=" + packageID + ", priority=" + priority + ", status=" + status + ", senderAdress="
				+ senderAdress + ", destinationAdress=" + destinationAdress + ",";
	}
		
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Package other = (Package) obj;
		if (destinationAdress == null) {
			if (other.destinationAdress != null)
				return false;
		} else if (!destinationAdress.equals(other.destinationAdress))
			return false;
		if (packageID != other.packageID)
			return false;
		if (priority != other.priority)
			return false;
		if (senderAdress == null) {
			if (other.senderAdress != null)
				return false;
		} else if (!senderAdress.equals(other.senderAdress))
			return false;
		if (status != other.status)
			return false;
		if (tracking == null) {
			if (other.tracking != null)
				return false;
		} else if (!tracking.equals(other.tracking))
			return false;
		return true;
	}


	public int getPackageId() {
		return this.packageID;
	}
	
	
}
