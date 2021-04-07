package components2;

import java.util.ArrayList;

import components1.Node;
import components1.Status;

public class Branch implements Node{
	private static int idCounter=-1;
	private int branchId;
	private String branchName;
	private ArrayList<Truck> listTrucks;
	private ArrayList<Package> listPackages;
	
	
	public Branch()
	{	
		
		this.branchId = idCounter;
		this.branchName="Branch " + Integer.toString(branchId);
		listTrucks = new ArrayList<Truck>();
		listPackages = new ArrayList<Package>();
		idCounter++;
		System.out.println("Creating Branch " + Integer.toString(branchId) + this);
		
	}
	
	
	public Branch(String branchName)
	{
		this.branchId = idCounter;
		this. branchName = branchName;
		listTrucks = new ArrayList<Truck>();
		listPackages = new ArrayList<Package>();
		idCounter++;
		System.out.println("Creating Branch " + Integer.toString(branchId) + this);
	}

	
	//getters setters
	
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
	
	
	//methods
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
	
	
	
	@Override
	public void work() {
		
		for(Truck tr: listTrucks)
		{
			((Node)tr).work();
		}
		ArrayList<Package>temp=this.listPackages;
		for(int i=0;i<temp.size();i++)
		{
			if(temp.get(i).getStatus() == Status.CREATION)
			{
				collectPackage(temp.get(i));
			}
			else if(temp.get(i).getStatus() == Status.DELIVERY)
			{
				deliverPackage(temp.get(i));
				this.getListPackages().remove(temp.get(i));
			}
		}
	}
	
	
	@Override
	public void collectPackage(Package p) {
		// TODO Auto-generated method stub
		for(Truck tr: this.listTrucks)
		{
			if(tr.isAvaliable())
			{
				tr.getPackages().add(p);
				//truck setup
				tr.setTimeLeft((p.getSenderAdress().getStreet() % 10) + 1);
				tr.setAvaliable(false);
				//package setup
				p.setStatus(Status.COLLECTION);
				if(tr instanceof Van)
				{
					p.addTracking((Van)tr, p.getStatus());
				}
				
				//work van
				System.out.println("Van " + Integer.toString(tr.getTruckID()) +  " is collecting package " + Integer.toString(p.getPackageId())
				+ ", time to arrive: " + Integer.toString(tr.getTimeLeft()));
				
				break;
			}
		}
	}

	@Override
	public void deliverPackage(Package p) {
		// TODO Auto-generated method stub
		for(Truck tr: this.listTrucks)
		{
			if(tr.isAvaliable())
			{
				tr.getPackages().add(p);
				//truck setup
				tr.setTimeLeft((p.getDestinationAdress().getStreet() % 10) + 1);
				tr.setAvaliable(false);
				//package setup
				p.setStatus(Status.DISTRIBUTION);
				p.addTracking((Van)tr, p.getStatus());
				//work van
				System.out.println("Van " + Integer.toString(tr.getTruckID()) +  " is delivering package " + Integer.toString(p.getPackageId())
				+ ", time to arrive: " + Integer.toString(tr.getTimeLeft()));
				
				break;
			}
		}
	}


	@Override
	public String Print() {
		// TODO Auto-generated method stub
		return "Branch " + Integer.toString(this.getBranchId());
	}
	

	
	
}