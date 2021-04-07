package components2;

import components1.Node;
import components1.Status;

public class Van extends Truck implements Node{
	
	//Constructors
	public Van() {
		super();
		System.out.println("Creating " + this);
	}
	
	public Van(String licensePlate, String truckModel) {
		super(licensePlate,truckModel);
		System.out.println("Creating " + this);
	}
	
	//methods
	@Override
	public void work() 
	{
		if(this.isAvaliable()==false) 
		{
			//time left setup
			int timeL=this.getTimeLeft();
			this.setTimeLeft(timeL - 1);
			
			if(this.getTimeLeft() == 0)
			{
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
		}
	}

	@Override
	public void collectPackage(Package p) {
		p.setStatus(Status.BRANCH_STORAGE);
		p.addTracking(this, p.getStatus());
		//change status to branch number
		
		System.out.println("Van " + Integer.toString(this.getTruckID()) + " has collected package " + Integer.toString(p.getPackageId())
								+ "and arrived back to branch" + Integer.toString(p.getSenderAdress().getZip()) );
		//avaliable
		//clear package 
		this.getPackages().clear();
	}
	

	
	@Override
	public void deliverPackage(Package p) {
		// TODO Auto-generated method stub
		p.setStatus(Status.DELIVERED);
		p.addTracking(null, p.getStatus());
		//change status to customer
		
		System.out.println("Van " + Integer.toString(this.getTruckID()) + " has delivered package "+ Integer.toString(p.getPackageId())
					+ " to the destination");
		//avaliable
		//clear package 
		this.getPackages().clear();
	}

	@Override
	public String toString() {
		return "Van [" +super.toString() + "]";
	}

	@Override
	public String Print() {
		// TODO Auto-generated method stub
		return "Van " + Integer.toString(this.getTruckID());
	}

}
