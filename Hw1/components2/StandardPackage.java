package components2;

import components1.Address;
import components1.Priority;

public class StandardPackage extends Package {

	private double weight;
	
	public StandardPackage(Priority priority, Address senderAddress, Address destinationAddress, double weight) {
		super(priority, senderAddress, destinationAddress);
		this.weight = weight;
	}
	
	
	//getters setters
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}


	//methods

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
