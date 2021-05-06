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
	
	
	
	public void run() {
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
		// TODO Auto-generated method stub
		//DRAW SELF
			Graphics2D g2d = (Graphics2D) g;
			
			if(!this.isAvaliable() && this.getPackages().size() > 0)
			{

				g2d.setColor(Color.BLUE);
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
		x_origin += x_cor;
		y_origin += y_cor;
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
			double timeL=this.getTimeLeft();
			this.setTimeLeft(timeL - 1);
			Move();
			if(this.getTimeLeft() <= 0)
			{
				MainLogic();
			}
		}
	}

	
	private void MainLogic()
	{
		/**
		 * depends on package status collect or deliver package
		 */
		if(this.getPackages().size() != 0)
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
	
	
	@Override
	public void collectPackage(Package p) {
		/**
		 * change package status and clear truck 
		 */
		/*
		 * synchronized(p) {
		 */
			//change package status
			p.setStatus(Status.BRANCH_STORAGE);
			p.addTracking(this, p.getStatus());
		
			//print massage
			System.out.println("Van " + Integer.toString(this.getTruckID()) + " has collected package " + Integer.toString(p.getPackageId())
									+ "and arrived back to branch" + Integer.toString(p.getSenderAdress().getZip()) );
			//clear package 
			this.getPackages().clear();
		//}
		
			
	}
	

	
	@Override
	  public void deliverPackage(Package p) {
		/**
		 * change package status and return truck to be available
		 */
		/*
		 * synchronized(p) {
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
		//}
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
