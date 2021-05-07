package components2;



import components1.Address;
import components1.Priority;

/**
 * ID 336249743
 * ID 336249628
 * 
 * NonStandardPackage represent NonStandardPackage 
 * 
 * width , length, height 
 * 
 * 
 * 
 */


public class NonStandardPackage extends Package{

	private int width;
	private int length;
	private int height;
	
	public NonStandardPackage(Priority priority, Address senderAddress, Address destinationAddress, int width, int length, int height, int xcor) {
		super(priority, senderAddress, destinationAddress, xcor);
		this.height = height;
		this.width = width;
		this.length = length; 
		
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NonStandardPackage other = (NonStandardPackage) obj;
		if (height != other.height)
			return false;
		if (length != other.length)
			return false;
		if (width != other.width)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "NonStandardPackage ["  + super.toString() +   " width=" + width + ", length=" + length + ", height=" + height + "]";
	}
	
	
	
	
	

}
