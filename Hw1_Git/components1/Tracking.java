package components1;


/**
 * ID 336249743
 * ID 336249628
 * 
 * Class Tracking represents tracking points of package during its delivery
 * time - (all system work by clock tick) time showing current time value 
 * node - (Node is interface) node represent current location of package,  all classes that implements Node can be used like destination
 * status - Enum represent current status of package
 */


public class Tracking {
	private int time;
	private Node node;
	private Status status; 
	
	
	//constructor
	public Tracking(int time, Node node, Status status)
	{
		this.time = time;
		this.node = node;
		this.status = status;
	}
	
	
	//getters setters
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
	//default methods
	
	@Override
	public String toString() {
		if(node == null)
		{
			return time + ":  CUSTOMER, status " + status;
		}
		return time + ":  " + node.Print() + ", status " + status;
		//add fields
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tracking other = (Tracking) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (status != other.status)
			return false;
		if (time != other.time)
			return false;
		return true;
	}



	
	

	
	
	
	
	
}
