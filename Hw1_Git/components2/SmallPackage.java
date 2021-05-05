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
 * SmallPackage represent smallPackage 
 * acknowledge - bool that toggle massage if send or not massage  to customer that package was delivered
 */


public class SmallPackage extends Package {
	private boolean acknowledge;
	
	//constructor
	public SmallPackage(Priority priority, Address senderAddress, Address destinationAddress, boolean acknowledge, int xcor) {
		super(priority, senderAddress, destinationAddress, xcor);
		this.acknowledge = acknowledge;
	}

	
	//getters setters
	public boolean getAcknowledge() {
		return acknowledge;
	}

	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}

	
	
	@Override
	synchronized public void DrawMe(Graphics g) {
		
		
		//DRAW PACKAGES DEPEND ON STATUS
		Graphics2D g2d = (Graphics2D) g;
		if(this.getStatus() == Status.CREATION)
		{
			g2d.setColor(Color.RED);
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.setColor(Color.ORANGE);
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		
		else if( this.getStatus() == Status.DELIVERED)
		{
			g2d.setColor(Color.ORANGE);
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.setColor(Color.RED);
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		else
		{
			g2d.setColor(Color.ORANGE);
			g2d.fillOval(200 + x_cor, 10, 30,30);
			g2d.fillOval(200 + x_cor, 540, 30,30);
		}
		
		g2d.drawLine(x_cor, x_cor, x_cor, x_cor);
	}
	
	//default methods ----------------------------------------------------------------------------------------------

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmallPackage other = (SmallPackage) obj;
		if (acknowledge != other.acknowledge)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SmallPackage [" + super.toString() + " acknowledge=" + acknowledge + "]";
	}
	
	
	
	

}
