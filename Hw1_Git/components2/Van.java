package components2;

import components1.Node;
import components1.Status;


/**
 * ID 336249743
 * ID 336249628
 * 
 * Van represent trucks that connected to local branches
 * 
 * 
 */


public class Van extends Truck implements Node{
	
	
	//Constructors------------------------------------------------------------------
	public Van() {
		super();
		System.out.println("Creating " + this);
	}
	
	public Van(String licensePlate, String truckModel) {
		super(licensePlate,truckModel);
		System.out.println("Creating " + this);
	}
	
	
	//Node methods----------------------------------------------------------------------------
	@Override
	public void work() 
	{
		/**
		 * Time and availability check 
		 * Main Logic 
		 */
		if(this.isAvaliable()==false) 
		{
			//time left setup
			int timeL=this.getTimeLeft();
			this.setTimeLeft(timeL - 1);
			
			if(this.getTimeLeft() == 0)
			{
				MainLogic();
			}
		}
	}

	
	private void MainLogic()
	{
		/**
		 * depends on package status collect or delvier package
		 */
		Package tempPackage  = this.getPackages().get(0);
		if(tempPackage.getStatus() == Status.COLLECTION)
		{
			collectPackage(tempPackage);
		}
		else if(tempPackage.getStatus() == Status.DISTRIBUTION)
		{
			deliverPackage(tempPackage);
		}
	}
	
	
	@Override
	public void collectPackage(Package p) {
		/**
		 * change package status and clear truck 
		 */
		
		//change package status
		p.setStatus(Status.BRANCH_STORAGE);
		p.addTracking(this, p.getStatus());
	
		//print massage
		System.out.println("Van " + Integer.toString(this.getTruckID()) + " has collected package " + Integer.toString(p.getPackageId())
								+ "and arrived back to branch" + Integer.toString(p.getSenderAdress().getZip()) );
		//clear package 
		this.getPackages().clear();
			
	}
	

	
	@Override
	public void deliverPackage(Package p) {
		/**
		 * chage package status and return truck to be available
		 */
		//package status
		p.setStatus(Status.DELIVERED);
		p.addTracking(null, p.getStatus());
		
		//print massage
		System.out.println("Van " + Integer.toString(this.getTruckID()) + " has delivered package "+ Integer.toString(p.getPackageId())
					+ " to the destination");
		
		//clear package 
		this.getPackages().clear();
		//return truck to be available
		this.setAvaliable(true);
	}


	@Override
	public String Print() {
		/**
		 * print massage
		 */
		return "Van " + Integer.toString(this.getTruckID());
	}
	
	//default functions-------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "Van [" +super.toString() + "]";
	}

}
