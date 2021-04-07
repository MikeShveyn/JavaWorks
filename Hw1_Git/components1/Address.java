package components1;

public class Address {
	int zip;//branch number
	int street;//6 digit number
	
	
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
