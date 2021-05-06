package components2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import components1.Address;
import components1.Priority;
import components1.Status;


/**
 * ID 336249743
 * ID 336249628
 * 
 * StandardPackage represent StandardPackage 
 * weight- double that store weight of the package , used by Hub to calculate StandardTrucks load 
 *
 */


public class StandardPackage extends Package {

	private double weight;
	
	//constructor
	public StandardPackage(Priority priority, Address senderAddress, Address destinationAddress, double weight, int xcor) {
		super(priority, senderAddress, destinationAddress, xcor);
		this.weight = weight;
	}
	
	
	//getters setters
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	
	
	synchronized public void DrawMe(Graphics g) {
		
		//DRAW PACKAGES DEPEND ON STATUS
		Graphics2D g2d = (Graphics2D) g;
		if(this.getStatus() == Status.CREATION)
		{
			g2d.setColor(new Color(158, 17, 17));
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.setColor(new Color(255,0,0));
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		
		else if( this.getStatus() == Status.DELIVERED)
		{
			g2d.setColor(new Color(255,0,0));
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.setColor(new Color(158, 17, 17));
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		else
		{
			g2d.setColor(new Color(255,0,0));
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
	}
	
	//default methods

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardPackage other = (StandardPackage) obj;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "StandartPackage [" + super.toString() + " weight=" + weight + "]";
	}
	
	
	
	
	
	
	

}
