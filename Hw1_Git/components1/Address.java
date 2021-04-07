package components1;

/**
 * ID 336249743
 * ID 336249628
 * 
 * Class Address represents address of customer
 * zip = brunch number using to connect package to closest brunch
 * street = 6 digit number using to calculate time between different addresses
 */

public class Address {
	int zip;
	int street;
	
	
	//constructor
	public Address(int zip, int street)
	{
		this.zip = zip;
		setStreet(street);
	}

	
	//getters setters
	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getStreet() {
		return street;
	}

	public void setStreet(int street) {
		
		if(Integer.toString(street).length() == 6)
		{
			this.street = street;
		}
		else
		{
			System.out.println("Irrelevant street number!!!");
		}
		
	}

	//default methods
	@Override
	public String toString() {
		return " " + zip + "-" + street;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (street != other.street)
			return false;
		if (zip != other.zip)
			return false;
		return true;
	}
	
	
	
	
	
}
