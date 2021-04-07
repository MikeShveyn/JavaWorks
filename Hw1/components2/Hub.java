package components2;

import java.util.ArrayList;

import components1.Node;
import components1.Status;

public class Hub implements Node{
	
	private ArrayList<Branch> branches;
	private int branchIndex;
	
	public Hub()
	{
		branches = new ArrayList<Branch>();
		branches.add(new Branch("HUB"));
		branchIndex = 1;
	}

	//getters setters
	public ArrayList<Branch> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<Branch> branches) {
		this.branches = branches;
	}

	//methods
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
	
	
	
	
	@Override
	public void work() 
	{
		// TODO Auto-generated method stub
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
		
		
		
		for(Truck tr: branches.get(0).getListTrucks())
		{
			
			if(tr.isAvaliable() && tr instanceof StandardTruck)
			{
				StandardTruck truck=((StandardTruck)tr);
				sendTruck(truck);
				loadTruck(tr);
			}
			else if(tr.isAvaliable() && tr instanceof NonStandardTruck)
			{
				
				sendNonSTruck(tr);
			}
		}
		
		
		
	}
	
	
	private void  loadTruck(Truck tr)
	{
		double sumPackWeight=0;
		ArrayList<Package> temp=branches.get(0).getListPackages();
		for(int i=0;i<temp.size();i++)
		{	//Check that destination of the truck it's the same with each package
			
			int destName=temp.get(i).getDestinationAdress().getZip();
			StandardTruck truck=((StandardTruck)tr);
			if(truck.getDestination().getBranchId()==destName)
			{	
				if(temp.get(i) instanceof StandardPackage || temp.get(i) instanceof SmallPackage)
				{
					
					if(temp.get(i) instanceof StandardPackage)
					{
						sumPackWeight += ((StandardPackage)temp.get(i)).getWeight();
					}
					else if(temp.get(i) instanceof SmallPackage)
					{
						sumPackWeight+=1;
					}
					
					
					if(sumPackWeight <= truck.getMaxWeight())
					{
						collectPackage(temp.get(i));
						temp.get(i).addTracking(truck, temp.get(i).getStatus());
						truck.getPackages().add(temp.get(i));
						temp.remove(temp.get(i));
					}

				}
			
			}
		}
	}
	
	
	private void sendTruck(StandardTruck truck)
	{
		int temp=this.getRundomNumber(branchIndex, this.getBranches().size()-1);
		truck.setDestination(this.getBranches().get(temp));
		this.branchIndex = this.GetNextIndex(branchIndex);
		
		truck.setAvaliable(false);
		truck.setTimeLeft(truck.getRundomNumber(1, 10));
		System.out.println("StandatdTruck "+ truck.getTruckID() + " loaded packages at HUB");
		System.out.println("StandatdTruck "+ truck.getTruckID() +" is on it's way to "+
				truck.getDestination().getBranchName()+ " time to arrive: " + truck.getTimeLeft());
	}
	
	
	
	
	private void sendNonSTruck(Truck tr)
	{
		NonStandardTruck truck = ((NonStandardTruck)tr);
		ArrayList<Package> temp=branches.get(0).getListPackages();
		for(int i=0;i<temp.size();i++ )
		{
			if(temp.get(i) instanceof NonStandardPackage)
			{
				NonStandardPackage pack = ((NonStandardPackage)temp.get(i));
				if(truck.getHeight() <= pack.getHeight() && truck.getLength() <= pack.getLength() && truck.getWidth() <= pack.getWidth())
				{
					collectPackage(temp.get(i));
					temp.get(i).addTracking(truck, temp.get(i).getStatus());
					truck.getPackages().add(pack);
					temp.remove(temp.get(i));
					truck.setAvaliable(false);
					System.out.println("NonStandartTruck"  + tr.getTruckID() + " has collected package " + temp.get(i).getPackageId());
					break;
				}
				
			}
		}
	}
	
	@Override
	public void collectPackage(Package p) {
		if(p instanceof NonStandardPackage) 
		{
			p.setStatus(Status.COLLECTION);
		}
		else 
		{
			p.setStatus(Status.BRANCH_TRANSPORT);
		}
	}

	@Override
	public void deliverPackage(Package p) {
		
		
	}

	@Override
	public String Print() {
		// TODO Auto-generated method stub
		return "Hub ";
	}
	
	private int getRundomNumber(int min,int max) {
		return (int)((Math.random()*(max-min))+min);
	}
	
	private int GetNextIndex(int i)
	{
	   int index =  (i + 1) % (this.getBranches().size());
	   
	   if(index == 0)
		   return 1;
	   return index;
	}

}
