package components1;

import components2.Package;


/**
 * ID 336249743
 * ID 336249628
 * 
 * 
 * Interface Node represent all working classes in project 
 * contains work() that we be accesses by main office down to each truck
 * delivery() and collect() are both part of work() function 
 * Print() used for print special tracking massages from different node classes
 * 
 */


public interface Node {
	
	public void collectPackage(Package p);
	public void deliverPackage(Package p);
	public void work();
	public String Print();
}
