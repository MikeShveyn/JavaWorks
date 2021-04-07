package components1;

public class Tracking {
	
	private int time;//time int
	private Node node;
	private Status status; //enum class represent status
	
	//constractor
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

	
	//methods
	@Override
	public String toString() {
		return time + ":  " + node.Print() + ", status " + status;
		//add fields
	}


	@Override
	public boolean equals(Object obj) {
		//add fields
		return true;
	}
	
	

	
	
	
	
	
}
