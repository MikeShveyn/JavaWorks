package components2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import components1.Drawable;
import components1.Node;
import components1.Status;
import components1.ThreadBand;


/**
 * ID 336249743
 * ID 336249628
 *  
 * Branch class represent local branch with Vans 
 * Branch implements Node methods 
 * idCOunter - static variable that counts amount of instances
 * BranchId - calculated by idCOunter
 * listTrucks - list of Vans
 * listPackages - list of packages in brunch
 */


public class Branch implements Node, Cloneable, ThreadBand, Drawable, Runnable{
	private static int idCounter=-1;
	private int branchId;
	private String branchName;
	private ArrayList<Truck> listTrucks;
	private ArrayList<Package> listPackages;
	boolean isRun = true;
	boolean getSleep = false;
	public int y_cor;
	public ArrayList<Package> localListPacks;
	//Constructor------------------------------------------------------------------------------------------------------------
	public Branch(int ycr)
	{	
		super();
		this.y_cor = ycr;
		
		this.branchId = idCounter;
		this.branchName="Branch " + Integer.toString(branchId);
		listTrucks = new ArrayList<Truck>();
		listPackages = new ArrayList<Package>();
		localListPacks = new ArrayList<>();
		idCounter++;
		System.out.println("Creating Branch " + Integer.toString(branchId) + this);
		
		
	}
	
	
	public Branch(String branchName)
	{	
		super();
		this.branchId = idCounter;
		this. branchName = branchName;
		listTrucks = new ArrayList<Truck>();
		listPackages = new ArrayList<Package>();
		idCounter++;
		System.out.println("Creating Branch " + Integer.toString(branchId) + this);
		
	}

	 
	//getters setters---------------------------------------------------------------------------------------------------------------
	
	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public ArrayList<Truck> getListTrucks() {
		return listTrucks;
	}

	public void setListTrucks(ArrayList<Truck> listTrucks) {
		this.listTrucks = listTrucks;
	}

	public ArrayList<Package> getListPackages() {
		return listPackages;
	}

	public void setListPackages(ArrayList<Package> listPackages) {
		this.listPackages = listPackages;
	}
	
	public  int GetId()
	{
		return idCounter;
	}
	
	public int SetId(int i)
	{
		return idCounter = i;
	}
	
	
	
	
	//methods----------------------------------------------------------------------------------------------------------
	//Runnable interface
	@Override
	public void run()
	{
		
		/**
		 * Thread main loop call work function and go to sleep
		 * Can be stopped and resumed 
		 */
	
		while(true)
		{
			try {
				Thread.sleep(500);
				synchronized(this) {
					while(!isRun)
					{
						wait();
					}
				}
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}

				work();
			
		}
	}
	


	@Override
	synchronized public void StopMe() {
		// TODO Auto-generated method stub
		isRun = false;
	}


	@Override
	synchronized public void ResumeMe() {
		// TODO Auto-generated method stub
		isRun = true;
		notify();
	}


	@Override
	synchronized public void DrawMe(Graphics g) {
		//DRAW SELF
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(133, 143, 237));
		for(int i = 0 ;i < this.listPackages.size(); i++)
		{
			if(this.getListPackages().get(i).getStatus() == Status.BRANCH_STORAGE)
				g2d.setColor(Color.BLUE);
		}
			
		g2d.fillRect(50, 70 + y_cor, 40,30);
		
	
		//DRAW LINES TO PACKAGES SENDR AND RECIVER
		for(int i = 0; i < this.localListPacks.size(); i ++)
		{
			if(this.localListPacks.get(i).getSenderAdress().getZip() == this.getBranchId())
				g2d.drawLine(90, 85 + y_cor ,215 + this.localListPacks.get(i).x_cor , 40);
			if(this.localListPacks.get(i).getDestinationAdress().getZip() == this.getBranchId())
				g2d.drawLine(90, 85 + y_cor ,215 + this.localListPacks.get(i).x_cor , 540);
		}
		
	
	}

	
	
	
	@Override
	public void work() {
		/**
		 *  Node method implementation
		 *  Apply work to each Van
		 *  Go throw local packages decide collect or deliver
		 */
		
		//Apply work to each van
		//ApplyWork();
		
		//Go throw local packages decide collect or deliver
		PackagesLogic();
		
	}
	

	
	synchronized private void PackagesLogic()
	{
		/**
		 * Go throw local packages decide collect or deliver
		 */
		
		ArrayList<Package>temp=this.listPackages;
		for(int i=0;i<temp.size();i++)
		{
			
			//depends of package status send truck to  collect or deliver it
			if(temp.get(i).getStatus() == Status.CREATION)
			{
				collectPackage(temp.get(i));
			}
			else if(temp.get(i).getStatus() == Status.DELIVERY)
			{
				deliverPackage(temp.get(i));
				//remove package from local storage
				this.getListPackages().remove(temp.get(i));
			}
		}
	}
	
	
	@Override
	public void collectPackage(Package p) {
		/**
		 *  Node method implementation
		 *  Go throw Vans , find available van and send it to collect package
		 */
		
		/*
		 * synchronized(this) {
		 */
			for(Truck tr: this.listTrucks)
			{
				//Find available Van
				if(tr.isAvaliable())
				{
					//truck setup
					tr.getPackages().add(p);
					tr.setTimeLeft(((p.getSenderAdress().getStreet() % 10) + 1) * 10);
					
					//package setup
					p.setStatus(Status.COLLECTION);
					p.addTracking((Van)tr, p.getStatus());
					
					
					//Print massage
					System.out.println("Van " + Integer.toString(tr.getTruckID()) +  " is collecting package " + Integer.toString(p.getPackageId())
					+ ", time to arrive: " + Double.toString(tr.getTimeLeft()));
					
					tr.x_origin = 82;
					tr.y_origin = 80 + this.y_cor;
					tr.x_Dest = 207 + p.x_cor;
					tr.y_Dest = 35;
					
					tr.x_cor = (int)((tr.x_Dest - tr.x_origin)/(tr.getTimeLeft()));
					tr.y_cor = (int)((tr.y_Dest - tr.y_origin)/(tr.getTimeLeft()));
					
					tr.setAvaliable(false);
					break;
				}
			}
		//}
		
	}

	@Override
	public void deliverPackage(Package p) {
		/**
		 *  Node method implementation
		 *  Go throw Vans , find available van and send it to deliver package
		 */
		/*
		 * synchronized(this) {
		 */
			for(Truck tr: this.listTrucks)
			{
				if(tr.isAvaliable())
				{
					//truck setup
					tr.getPackages().add(p);
					tr.setTimeLeft(((p.getDestinationAdress().getStreet() % 10) + 1) * 10);
					
					
					//package setup
					p.setStatus(Status.DISTRIBUTION);
					p.addTracking((Van)tr, p.getStatus());
					
					
					//print massage
					System.out.println("Van " + Integer.toString(tr.getTruckID()) +  " is delivering package " + Integer.toString(p.getPackageId())
					+ ", time to arrive: " + Double.toString(tr.getTimeLeft()));
					
					tr.x_origin = 82;
					tr.y_origin = 80 + this.y_cor;
					tr.x_Dest = 223 + p.x_cor;
					tr.y_Dest = 545;
					
					tr.x_cor = (int)((tr.x_Dest - tr.x_origin)/(tr.getTimeLeft()));
					tr.y_cor = (int)((tr.y_Dest - tr.y_origin)/(tr.getTimeLeft()));
					
					
					tr.setAvaliable(false);
					break;
				}
			}
		//}
	}


	@Override
	public String Print() {
		/**
		 * Node method implementation
		 */
		return "Branch " + Integer.toString(this.getBranchId());
	}
	
	
	//default methods-----------------------------------------------------------------------------------------------------------------------
	
		@Override
		public String toString() {
			return " branchName: " + branchName + ", trucks=" + listTrucks.size()
					+ ", packages=" + listPackages.size();
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Branch other = (Branch) obj;
			if (branchId != other.branchId)
				return false;
			if (branchName == null) {
				if (other.branchName != null)
					return false;
			} else if (!branchName.equals(other.branchName))
				return false;
			if (listPackages == null) {
				if (other.listPackages != null)
					return false;
			} else if (!listPackages.equals(other.listPackages))
				return false;
			if (listTrucks == null) {
				if (other.listTrucks != null)
					return false;
			} else if (!listTrucks.equals(other.listTrucks))
				return false;
			return true;
		}


		public String[][] makePInfo()
		{
			/**
			 * make table row from package tracking
			 */
			String[][] arr=new String[this.listPackages.size()][5];
			if(this.listPackages.size()==0){return null;}
			int i=0;
			for(Package p : this.listPackages)
			{
				arr[i]=p.makeRow();
				i++;
				if(i== this.listPackages.size()) {break;}
			}
			return arr;
		}
		
		@Override
		 public Object clone() {
		      Object clone = null;
		      try {
		         clone = super.clone();
		      } catch (CloneNotSupportedException e) {
		         e.printStackTrace();
		      }
		      return clone;
		   }
	
	
	
}
