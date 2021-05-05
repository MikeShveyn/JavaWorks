package components2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import components1.Node;
import components1.Status;
import components1.ThreadBand;


/**
 * ID 336249743
 * ID 336249628
 * 
 * NonStandardTruck represent truck that connected to Hub and take NonStandardPackages
 * width ,length, height - size of truck helps measure if package can be taken or not 
 */


public class NonStandardTruck extends Truck implements Node {
	
	private int width;
	private int length;
	private int height;

	//constructor----------------------------------------------------------------------------------------------------------
	public NonStandardTruck() 
	{
		super();
		this.height=this.getRundomNumber(400, 800);
		this.width=this.getRundomNumber(400, 800);
		this.length=this.getRundomNumber(900, 1500);
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

	
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			synchronized(this) {
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
			
			
			if(getSleep)
			{
				try {
					
					Thread.sleep(500);
					
				}catch(InterruptedException e) {}
				
				getSleep = false;
			}
			
			
			work();
		}
		
	}
	

	
	@Override
	 public void Sleep()
	{
		getSleep = true;
	}
	
	
	@Override
	public void StopMe() {
		// TODO Auto-generated method stub
		isRun = false;
	}

	@Override
	public void ResumeMe() {
		// TODO Auto-generated method stub
		isRun = true;
		notify();
	}

	@Override
	public void DrawMe(Graphics g) {
		// TODO Auto-generated method stub
		//DRAW SELF
		Graphics2D g2d = (Graphics2D) g;
		if(this.getPackages().size() > 0)
		{

			if(this.getPackages().get(0).getStatus() == Status.COLLECTION)
				g2d.setColor(Color.RED);
			else if(this.getPackages().get(0).getStatus() == Status.DISTRIBUTION)
				g2d.setColor(Color.ORANGE);
			x_cor = 300;
			y_cor = 300;
			g2d.fillRect(x_cor, y_cor, 16,16);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(x_cor + 8, y_cor + 4, 10, 10);
			g2d.fillOval(x_cor + 8, y_cor - 4, 10, 10);
			g2d.fillOval(x_cor - 8, y_cor + 4, 10, 10);
			g2d.fillOval(x_cor - 8, y_cor - 4, 10, 10);
		}
		
		
	}
	
	
	//Node methods ---------------------------------------------------------------------------------------
	@Override
	public void work() {
		/**
		 * Time and availability check 
		 * Main Logic 
		 */
		if(this.isAvaliable()==false) 
		{
			//time left setup
			double timeL=this.getTimeLeft();
			this.setTimeLeft(timeL - 1);
			
			//time check
			if(this.getTimeLeft() <= 0)
			{
				//collect or deliver package
				MainLogic();
			}
		}
	}
	
	
	private void MainLogic()
	{
		/**
		 *Depends on package status deliver or collect 
		 */
		if(this.getPackages().size() > 0)
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
	
	@Override
	 public void collectPackage(Package p) {
		/**
		 * change package status and calculate time for van to collect it
		 */
		//package setup
		p.setStatus(Status.DISTRIBUTION);
		p.addTracking(this, p.getStatus());
		
		//set time
		this.setTimeLeft(CalcTimeLeft(p) * 10);
		
		//print massage
		System.out.println("NonStandardTruck " + Integer.toString(this.getTruckID()) + " delivering package " + Integer.toString(p.getPackageId())
								+ " time to arrive: " + this.getTimeLeft());
	}


	@Override
	 public void deliverPackage(Package p) {
		/**
		 * change package status remove from van
		 */
		// change pack status
		p.setStatus(Status.DELIVERED);
		p.addTracking(null, p.getStatus());
		// clear van package list
		this.getPackages().clear();

		// print masasge
		System.out.println("NonStandardTruck " + Integer.toString(this.getTruckID()) + " has delivered package "
				+ Integer.toString(p.getPackageId()));
	}
	
	
	@Override
	public String Print() {
		/**
		 * print massage
		 */
		return "NonStandardTruck " + Integer.toString(this.getTruckID());
	}

	//help func ----------------------------------------------------------------------------------------------------
	
	private int CalcTimeLeft(Package p)
	{
		/**
		 * return time based on sender and receiver addresses
		 */
		int abs =  Math.abs(p.getSenderAdress().getStreet() - p.getDestinationAdress().getStreet());
		return (abs % 10) + 1;
	}

	
	//default methods
	
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

	
		
	

		
}
