package components1;

import components2.Package;

public interface Node {
	
	public void collectPackage(Package p);
	public void deliverPackage(Package p);
	public void work();
	public String Print();
}
