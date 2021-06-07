package components2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import components1.*;


/**
 * ID 336249743
 * ID 336249628
 * 
 * Package represent abstract package consist of parameters and methods
 * idCounter - static variable to count instances
 * packageId - package id based on idCounter
 * priority - priority of package(enum Priority)
 * status - status of package(enum Status)
 * senderAdress , desinationAdress - consist of zip and street address 
 * used by tracks to calculate time and by MainOffice to set package to closest  local branch
 * tracking - list of tracking 
 */


public abstract class Package implements Drawable{
	private static int idCounter=1000;
	private  int packageID; 
	private Priority priority;
	private Status status;
	private Address senderAdress;
	private Address destinationAdress;
	private ArrayList<Tracking> tracking;
	protected int x_cor;
	
	//constructor
	public Package(Priority priority, Address senderAddress, Address destinationAddress, int xcor)
	{
		this.priority = priority;
		this.senderAdress = senderAddress;
		this.destinationAdress = destinationAddress;
		this.status = Status.CREATION;
		this.tracking = new ArrayList<Tracking>();
		this.packageID=idCounter;
		idCounter++;
		this.x_cor = xcor;
	}

	
	//getters setters--------------------------------------------------------------------------------------------------------
	public int getPackageId() {
		return this.packageID;
	}
	
	
	
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

	
	//methods------------------------------------------------------------------------------------------------
	@Override
	 synchronized public void DrawMe(Graphics g) {
		
		/**
		 * Interface function that draw object position  and change colors depend on status
		 */
		//DRAW PACKAGES DEPEND ON STATUS
		Graphics2D g2d = (Graphics2D) g;
		if(this.getStatus() == Status.CREATION)
		{
			g2d.setColor(new Color(158, 17, 17));
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.setColor(new Color(255,0,0));
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		
		else if( this.getStatus() == Status.DELIVERED)
		{
			g2d.setColor(new Color(255,0,0));
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.setColor(new Color(158, 17, 17));
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		else
		{
			g2d.setColor(new Color(255,0,0));
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		
		
		if(this instanceof NonStandardPackage)
		{
			//DRAW LINE FROM SENDER TO RECIVER
			g2d.setColor(Color.RED);
			g2d.drawLine(215 + x_cor , 40 ,215 + x_cor , 540);
		}
		
	}
	
	
	public String[] makeRow() 
	{
		/**
		 * Function used to put all tracking information of the package to table row
		 */
		String [] ar=new String [5];
		ar[0]=Integer.toString(this.getPackageId());
		ar[1]=this.getSenderAdress().getAddress();
		ar[2]=this.getDestinationAdress().getAddress();
		ar[3]=this.getPriority().toString();
		ar[4]=this.getStatus().toString();
		return ar;
	}
	
	public void addTracking(Node node, Status status)
	{
		/**
		 * Add tracking point (time, location, status)
		 */
		tracking.add(new Tracking(MainOffice.clock ,node, status));
	}
	
	
	
	public String printTracking()
	{
		/**
		 * Print each tracking point of package
		 */
			String s = "Sender: " + this.senderAdress.toString() + " Package: " + this.packageID + 
					" Status: " + this.getTracking().get(this.getTracking().size() - 1).getStatus();
			return s;
	}
	
	
	
	//default methods----------------------------------------------------------------------------------------------------------------
	
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



	
	
}
