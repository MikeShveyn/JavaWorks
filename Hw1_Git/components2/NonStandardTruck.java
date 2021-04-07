package components2;

import components1.Node;
import components1.Status;

public class NonStandardTruck extends Truck implements Node {
	private int width;
	private int length;
	private int height;
	
	public NonStandardTruck() 
	{
		super();
		this.height=this.getRundomNumber(200, 600);
		this.width=this.getRundomNumber(300, 600);
		this.length=this.getRundomNumber(800, 1500);
		System.out.println("Creating " + this);
	}
	
	public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height)
	{
		this.setLicensePlate(licensePlate);
		this.setTruckModel(truckModel);
		this.height=height;
		this.width=width;
		this.length=length;
		System.out.println("Creating " + this);
	}
	
	//getters setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	//methods
	
	@Override
	public String toString() {
		return "NonStandardTruck [" +super.toString() + " width=" + width + ", length=" + length + ", height=" + height + "]" ;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NonStandardTruck other = (NonStandardTruck) obj;
		if (height != other.height)
			return false;
		if (length != other.length)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	
	@Override
	public void work() {
		// TODO Auto-generated method stub
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
					this.setAvaliable(true);
				}
			}
		}
	}

	
	
	@Override
	public void collectPackage(Package p) {
		// TODO Auto-generated method stub
		p.setStatus(Status.DISTRIBUTION);
		p.addTracking(this, p.getStatus());
		
		this.setTimeLeft(CalcTimeLeft(p));
		
		System.out.println("NonStandardTruck " + Integer.toString(this.getTruckID()) + "delivering package " + Integer.toString(p.getPackageId())
								+ "time to arrive: " + this.getTimeLeft());
	}


	@Override
	public void deliverPackage(Package p) {
		// TODO Auto-generated method stub
		p.setStatus(Status.DELIVERED);
		p.addTracking(this, p.getStatus());
		
		System.out.println("NonStandardTruck " + Integer.toString(this.getTruckID()) + "has delivered package " + Integer.toString(p.getPackageId()));
	}
	
	private int CalcTimeLeft(Package p)
	{
		int abs =  Math.abs(p.getSenderAdress().getStreet() - p.getDestinationAdress().getStreet());
		return (abs % 10) + 1;
	}

	@Override
	public String Print() {
		// TODO Auto-generated method stub
		return "NonStandardTruck " + Integer.toString(this.getTruckID());
	}

	
}
