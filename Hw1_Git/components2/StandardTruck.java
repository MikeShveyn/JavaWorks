package components2;



import java.util.ArrayList;

import components1.Node;
import components1.Status;



/**
 * ID 336249743
 * ID 336249628
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


public class StandardTruck extends Truck implements Node{
	
	private int maxWeight;
	private Branch destination;
	private Branch defaultHub;
	
	
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
	
	//getters setters
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
	
	//methods
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
	@Override
	public void work()
	{
		if(this.isAvaliable()==false) 
		{
			//time left setup
			int timeL=this.getTimeLeft();
			this.setTimeLeft(timeL - 1);
			
			//if trip is ended
			if(this.getTimeLeft() == 0)
			{	
				//message that truck  made it to the destination
				System.out.println("StandardTruck" + Integer.toString(this.getTruckID()) + " arrived to " + this.destination.getBranchName());
				//move all trucks packages to the destination
				for(Package p: this.getPackages())
				{
					deliverPackage(p);
				}
				
				this.getPackages().clear();
				
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
						ArrayList<Package> temp=this.getDestination().getListPackages();
						for(int i=0;i<temp.size();i++)
						{
							collectPackage(temp.get(i));
						}
						
						
						System.out.println("StandardTruck " + this.getTruckID() + "  loaded packages at " + this.destination.getBranchName());
						// change destination
					
						this.setDestination(this.defaultHub);
						
						//Generate timeLeft 
						this.setTimeLeft(this.getRundomNumber(1, 6));
						//print relevant message
						System.out.println("StandardTruck" + Integer.toString(this.getTruckID()) + "  is on it's way to the HUB "+"time to arrive: "
									+ this.getTimeLeft());
					}
					
				}
			}
		}
		
		
	}
	
	@Override
	public void collectPackage(Package p) 
	{
		if(p.getStatus() != Status.DELIVERY)
		{
			// TODO Auto-generated method stub
			p.setStatus(Status.HUB_TRANSPORT);
			p.addTracking(this, p.getStatus());
			this.getPackages().add(p);
			ArrayList<Package> temp = this.getDestination().getListPackages();
			if(temp.contains(p))
			{
				temp.remove(p);
			}
		}
		
	}
	
	@Override
	public void deliverPackage(Package p)
	{
		// TODO Auto-generated method stub
		if(this.destination.getBranchName()=="HUB")
		{
			p.setStatus(Status.HUB_STORAGE);
			p.addTracking(destination, p.getStatus());
			this.getDestination().getListPackages().add(p);
			
			
		}
		else
		{
			p.setStatus(Status.DELIVERY);
			p.addTracking(destination, p.getStatus());
			this.getDestination().getListPackages().add(p);
			
		}	
	}
	
	private boolean WeightCheck()
	{
		double sum = 0;
		for(Package p: this.destination.getListPackages())
		{
			if(p.getStatus() != Status.DELIVERY)
			{
				if(p instanceof StandardPackage)
				{
					sum += ((StandardPackage)p).getWeight();
				}
				else
				{
					sum+=1;
				}
			}
			
			
		}
		
		if(sum >= this.maxWeight)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	@Override
	public String Print() {
		// TODO Auto-generated method stub
		return "StandardTruck " + Integer.toString(this.getTruckID());
		
	}
}
