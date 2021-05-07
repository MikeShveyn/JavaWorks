package components2;



import components1.Address;
import components1.Priority;



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
