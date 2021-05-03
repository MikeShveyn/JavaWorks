package components2;

import java.awt.Graphics;
import java.util.ArrayList;

import components1.Node;
import components1.Status;
import components1.ThreadBand;


/**
 * ID 336249743
 * ID 336249628
 * 
 * Hub is main local brunch contains local brunches and trucks that deliver packages between them
 * 
 * branches - List of all local branches 
 * branchIndex - last branch (used to store last branch that truck was send to)
 */


public class Hub extends Thread implements Node, ThreadBand {
	
	private ArrayList<Branch> branches;
	private int branchIndex;
	boolean isRun = true;
	
	//constructor
	public Hub()
	{	
		super();
		branches = new ArrayList<Branch>();
		branches.add(new Branch("HUB"));
		//hub is brunch in index 0 so we start count local brunches from 1 index
		branchIndex = 1;
	}

	//getters setters
	public ArrayList<Branch> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<Branch> branches) {
		this.branches = branches;
	}

	
	
	
	//methods-----------------------------------------------------------------------------------------------------------------------
	
	//Runnable interface
	@Override
	public void run() {

		// TODO Auto-generated method stub	
		while(true)
		{
			synchronized(this)
			{
				while(!isRun)
				{
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			}
		
			work();
		}
		
	}
	
	
	@Override
	public void Sleep()
	{
		for(Branch br: this.branches)
		{
			br.Sleep();
		}
		
		
		try {
			Thread.sleep(500);
		}catch(InterruptedException e) {}
		
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
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	@Override
	public void work() 
	{
		/**
		 * Node interface function implementation
		 * Apply work function to all brunches and all Hub trucks
		 * Find all available hub's trucks , depends on type load and send them 
		 */
		
		//Apply work to all brunches and trucks
		ApplyWork();
		
		//Load and Send available trucks
		LoadAndSend();
		
	}
	
	
	private void ApplyWork()
	{
		/**
		 * apply work() for all local brunches and hub's trucks
		 */
		
		for(Node nd : branches)
		{
			if(((Branch)nd).getBranchName() != "HUB")
			{	
				nd.work();
				
			}
			else
			{
				for(Truck tr: this.getBranches().get(0).getListTrucks())
				{
					((Node)tr).work();
				}
				
			}
			
		}
	}
	
	private void LoadAndSend()
	{
		/**
		 * Load and Send Available trucks 
		 * Standard trucks load and send to random local brunch 
		 * NonStandardTruck send to Customer to take NonStandardPackage
		 */
		for(Truck tr: branches.get(0).getListTrucks())
		{
			synchronized(tr)
			{
				]if(tr.isAvaliable() && tr instanceof StandardTruck)
				{
					//Prepare truck for sending to local brunch
					StandardTruck truck=((StandardTruck)tr);
					sendTruck(truck);
					//Load truck with packages
					loadTruck(tr);
				}
				else if(tr.isAvaliable() && tr instanceof NonStandardTruck)
				{
					//Prepare truck for sending to customer 
					sendNonSTruck(tr);
				}
			}
			
		}
	}
	
	
	private void  loadTruck(Truck tr)
	{
		/**
		 * Check that package destinations is equal to truck destination
		 * Check weight of packages to fit weight of truck
		 */
		
		double sumPackWeight=0;
		ArrayList<Package> temp = branches.get(0).getListPackages();
		StandardTruck truck = ((StandardTruck)tr);
		
		for(int i=0;i<temp.size();i++)
		{	
			//Check that package destinations is equal to truck destination
			int destName=temp.get(i).getDestinationAdress().getZip();
			
			if(truck.getDestination().getBranchId()==destName)
			{	
				//check if type of package fit StandardTruck
				if(temp.get(i) instanceof StandardPackage || temp.get(i) instanceof SmallPackage)
				{
					//Depends of package's type calculate sumPackageWeight
					if(temp.get(i) instanceof StandardPackage)
					{
						sumPackWeight += ((StandardPackage)temp.get(i)).getWeight();
					}
					else if(temp.get(i) instanceof SmallPackage)
					{
						sumPackWeight+=1;
					}
					
					//Check weight, add package to truck and remove from hub
					if(sumPackWeight <= truck.getMaxWeight())
					{
						//change package tracking
						collectPackage(temp.get(i));
						temp.get(i).addTracking(truck, temp.get(i).getStatus());
						
						//add package to truck and remove from hub
						truck.getPackages().add(temp.get(i));
						temp.remove(temp.get(i));
					}

				}
			
			}
		}
	}
	
	
	
	private void sendTruck(StandardTruck truck)
	{
		/**
		 * There is no way to send more than one truck to the same brunch 
		 * Calculate random local brunch for each truck and increment branchIndex
		 * Print massages
		 */
		
		//Calculate random brunch
		int temp=this.getRundomNumber(branchIndex, this.getBranches().size()-1);
		truck.setDestination(this.getBranches().get(temp));
		this.branchIndex = this.GetNextIndex(branchIndex);
		
		//Setup truck
		truck.setAvaliable(false);
		truck.setTimeLeft(truck.getRundomNumber(1, 10) * 10);
		
		//Print Massages
		System.out.println("StandatdTruck "+ truck.getTruckID() + " loaded packages at HUB");
		System.out.println("StandatdTruck "+ truck.getTruckID() +" is on it's way to "+
				truck.getDestination().getBranchName()+ " time to arrive: " + truck.getTimeLeft());
		
	}
	
	
	
	
	 private void sendNonSTruck(Truck tr)
	{
		/**
		 * Check that NonStandardPackage fit NonStandardTruck and Send truck to Collect Package
		 */
		
		NonStandardTruck truck = ((NonStandardTruck)tr);
		ArrayList<Package> temp=branches.get(0).getListPackages();
		
		//Find NonStandardPackage
		for(int i=0;i<temp.size();i++ )
		{
			if(temp.get(i) instanceof NonStandardPackage)
			{
				NonStandardPackage pack = ((NonStandardPackage)temp.get(i));
				
				synchronized(pack)
				{
				//Check Package fit Truck
				//if(truck.getHeight() >= pack.getHeight() && truck.getLength() >= pack.getLength() && truck.getWidth() >= pack.getWidth())
				//{
					//Package tracking
					collectPackage(temp.get(i));
					temp.get(i).addTracking(truck, temp.get(i).getStatus());
					
					//Setup truck time and print massage
					truck.setAvaliable(false);
					truck.setTimeLeft(truck.getRundomNumber(1, 10) * 10);
					System.out.println("NonStandartTruck"  + tr.getTruckID() + " is collecting package " + temp.get(i).getPackageId() + " time to arrive: " + tr.getTimeLeft() );
					
					//Add package to truck and remove from hub
					truck.getPackages().add(pack);
					temp.remove(temp.get(i));
					
					break;
				//}
				}
				
			}
		}
	}
	
	
	
	@Override
	 public void collectPackage(Package p) {
		/**
		 * Node interface function implementation
		 * Manage package Status
		 */
		/*
		 * synchronized(p) {
		 */
			if(p instanceof NonStandardPackage) 
			{
				p.setStatus(Status.COLLECTION);
			}
			else 
			{
				p.setStatus(Status.BRANCH_TRANSPORT);
			}
		//}
	}

	
	@Override
	 public void deliverPackage(Package p) {
		/**
		 * Node interface function implementation
		 */	
	}
	
	
	@Override
	public String Print() {
		/**
		 * Node interface function implementation
		 */
		return "Hub ";
	}
	
	//help functions----------------------------------------------------------------------------------------------------------------------------
	
	private int getRundomNumber(int min,int max) {
		/**
		 * return random integer in range
		 */
		return (int)((Math.random()*(max-min))+min);
	}
	
	
	private int GetNextIndex(int i)
	{
		/**
		 * calculate and return next index always stand in the bounds
		 */
	   int index =  (i + 1) % (this.getBranches().size());
	   
	   if(index == 0)
		   return 1;
	   return index;
	}
	
	
	
	// default methods --------------------------------------------------------------------------------------------------------------------
		@Override
		public String toString() {
			return "Hub [" + branches + "]";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Hub other = (Hub) obj;
			if (branches == null) {
				if (other.branches != null)
					return false;
			} else if (!branches.equals(other.branches))
				return false;
			return true;
		}

	
	

}
