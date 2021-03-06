package components2;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import components1.Node;
import components1.Status;
import components1.ThreadBand;



/**
 * ID 336249743
 * ID 336249628
 * 
 * StandardTruck represent truck that connected to Hub
 * maxWeight - max Truck Load
 * destination - current destination of StandardTruck , HUB OR LOCAL BRUNCH
 * defaultHub - default destination - Hub
 */


public class StandardTruck extends Truck implements Node{
	
	private int maxWeight;
	private Branch destination;
	private Branch defaultHub;
	public int x_start = 0;
	public int y_start = 0;
	
	//constructor--------------------------------------------------------------------------
	public StandardTruck ()
	{
		super();
		this.maxWeight=this.getRundomNumber(100, 300);
		this.destination=null;
		this.defaultHub = null;
		System.out.println("Creating " + this);
	}
	
	public StandardTruck(String licensePlate,String truckModel,int maxWeight)
	{
		this.setLicensePlate(licensePlate);
		this.setTruckModel(truckModel);
		this.maxWeight=maxWeight;
		this.destination=null;
		this.defaultHub = null;
		System.out.println("Creating " + this);
	}
	
	//getters setters-----------------------------------------------------------------------------
	public Branch getDefaultHub() {
		return defaultHub;
	}

	public void setDefaultHub(Branch defaultHub) {
		this.defaultHub = defaultHub;
	}


	
	public int getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}
	public Branch getDestination() {
		return destination;
	}
	public void setDestination(Branch destination) {
		this.destination = destination;
	}
	
	//Runnable interface
	@Override
	public void run() {
		/**
		 * Thread main loop call work function and go to sleep
		 * Can be stopped and resumed 
		 */
		// TODO Auto-generated method stub
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
		/**
		 * Draw objec
		 */
		// TODO Auto-generated method stub
		//DRAW SELF
		Graphics2D g2d = (Graphics2D) g;
		if(!this.isAvaliable())
		{
			if(this.getPackages().size() == 0)
				g2d.setColor(new Color(219, 177, 59));
			else if(this.getPackages().size() > 0)
				g2d.setColor(new Color(120, 99, 40));
			
			g2d.fillRect(x_origin + x_cor,y_origin +  y_cor, 16,16);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(x_origin + x_cor + 7, y_origin +  y_cor + 5, 10, 10);
			g2d.fillOval(x_origin + x_cor + 7, y_origin +  y_cor - 5, 10, 10);
			g2d.fillOval(x_origin + x_cor - 7, y_origin +  y_cor + 5, 10, 10);
			g2d.fillOval(x_origin + x_cor - 7, y_origin +  y_cor - 5, 10, 10);

		}
		
	}
	
	
	public void Move()
	{
		/**
		 * Move coordinates of the object
		 */
		x_origin += x_cor;
		y_origin += y_cor;		
	}
	
	//Node methods---------------------------------------------------------------------------------------
	@Override
	 public void work()
	{
		/**
		 * Time and availability check 
		 * UNload Packages at current branch
		 *  Depends on location load new packages from Hub or collect packages from branch 
		 */
		if(this.isAvaliable()==false) 
		{
			//time left setup
			double timeL=this.getTimeLeft();
			this.setTimeLeft(timeL - 1);
			Move();
			//if trip is ended
			if(this.getTimeLeft() <= 0)
			{	
				//UNload packages at current branch
				UnLoadPackages();
				 
				//check where truck currently is
				if(this.destination.getBranchName()=="HUB")
				{
					System.out.println("StandardTruck " + this.getTruckID() + "  unloaded packages at HUB");
					this.setAvaliable(true);
				}
				else 
				{
					//we currently at branch
					System.out.println("StandardTruck " + this.getTruckID() + "  unloaded packages at " + this.destination.getBranchName());
					if(WeightCheck())
					{
						//collect all packages 
						ArrayList<Package> temp=this.getDestination().getListPackages();
						for(int i=0;i<temp.size();i++)
						{
							collectPackage(temp.get(i));
						}
						
						//print relevant message
						System.out.println("StandardTruck " + this.getTruckID() + "  loaded packages at " + this.destination.getBranchName());
						// change destination	
						this.setDestination(this.defaultHub);
						
						//Generate timeLeft 
						this.setTimeLeft(this.getRundomNumber(1, 6) * 10);
						//print relevant message
						System.out.println("StandardTruck" + Integer.toString(this.getTruckID()) + "  is on it's way to the HUB "+"time to arrive: "
									+ this.getTimeLeft());
						
						//this.x_origin = 1100;
						//this.y_origin = 300;
						this.x_Dest = this.x_start;
						this.y_Dest = this.y_start;
						
						this.x_cor = (this.x_Dest - this.x_origin)/(int)(this.getTimeLeft());
						this.y_cor = (this.y_Dest - this.y_origin)/(int)(this.getTimeLeft());
						
					}
					
				}
			}
		}
		
		
	}
	
	private void UnLoadPackages()
	{
		/**
		 * Unload packages at current branch
		 */
	
		System.out.println("StandardTruck" + Integer.toString(this.getTruckID()) + " arrived to " + this.destination.getBranchName());
		//move all trucks packages to the destination
		for(Package p: this.getPackages())
		{
			deliverPackage(p);
		}
		
		//remove packages from truck
		this.getPackages().clear();
		
	}
	
	@Override
	 public void collectPackage(Package p) 
	{
		/**
		 * check if package can be taken and change its status
		 */
		/*
		 * synchronized(p) {
		 */
			if(p.getStatus() == Status.BRANCH_STORAGE)
			{
				//change package status
				p.setStatus(Status.HUB_TRANSPORT);
				p.addTracking(this, p.getStatus());
				//add package to truck list
				this.getPackages().add(p);
	
				//remove package from branch
				ArrayList<Package> temp = this.getDestination().getListPackages();
				if(temp.contains(p))
				{
					temp.remove(p);
				}
			}
		//}
		
	}
	
	@Override
	public void deliverPackage(Package p)
	{
		/**
		 * Depends on destination change package status
		 */
		/*
		 * synchronized(p) {
		 */
			if(this.destination.getBranchName()=="HUB")
			{
				p.setStatus(Status.HUB_STORAGE);
				p.addTracking(destination, p.getStatus());
				//add to package to hub
				this.getDestination().getListPackages().add(p);
				
				
			}
			else
			{
				p.setStatus(Status.DELIVERY);
				p.addTracking(destination, p.getStatus());
				
	
				//add package to local branch
				this.getDestination().getListPackages().add(p);
				this.getDestination().localListPacks.add(p);
				
			}	
		//}
	}
	
	
	//help function -----------------------------------------------------------------------------------------------------
	private boolean WeightCheck()
	{
		/**
		 * Calculate weight of all packages and return if could be loaded to Truck or nor
		 */
		double sum = 0;
		//calculate total weight
		for(Package p: this.destination.getListPackages())
		{
			if(p.getStatus() != Status.DELIVERY)
			{
				//different packages has different weight values
				if(p instanceof StandardPackage)
					sum += ((StandardPackage)p).getWeight();
				else
					sum+=1;
			}
		}
		
		//weight check
		if(sum >= this.maxWeight)
			return false;
		else
			return true;
	}
	
	
	@Override
	public String Print() {
		/**
		 * print massage
		 */
		return "StandardTruck " + Integer.toString(this.getTruckID());
		
	}
	
	
	//default methods-----------------------------------------------------------------------------------
	
		@Override
		public String toString() {
			return "StandardTruck [" +super.toString() + " maxWeight=" + maxWeight +  "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			StandardTruck other = (StandardTruck) obj;
			if (destination == null) {
				if (other.destination != null)
					return false;
			} else if (!destination.equals(other.destination))
				return false;
			if (maxWeight != other.maxWeight)
				return false;
			return true;
		}

		
		
	
}
