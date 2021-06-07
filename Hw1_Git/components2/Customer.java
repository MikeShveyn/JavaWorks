package components2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components1.Address;
import components1.Priority;

public class Customer implements Runnable{
	private static int idCounter=1;
	private int id;
	private Address sender;
	private MainOffice mO;
	private List<Package> packs;
	Random rand = new Random();

public Customer(MainOffice mo)
{
	this.packs = new ArrayList<Package>();
	mO = mo;
	this.sender = new Address(getRundomNumber(0, this.mO.hub.getBranches().size() - 1), getRundomNumber(100000, 999999));   
	this.id = idCounter;
	idCounter++;
}



private void addPackage()
{	
	/**
	 * Add Random package to the system
	 * Create random package 
	 * Depend on its type each package gets different properties 
	 */
	
	//setup
	Package pack;
	
	//all common package properties setup random 
	Address reciver = new Address(getRundomNumber(0, this.mO.hub.getBranches().size() - 1),getRundomNumber(100000, 999999));
	
	Priority[] priority = Priority.values();
	Priority pr = priority[getRundomNumber(0, priority.length - 1 )];
	
	String[] packageTypes = {"SmallPackage", "StandardPackage", "NonStandardPackage"};
	String randomPackage = packageTypes[rand.nextInt(packageTypes.length)];
	
	
	//additional setup by package type
	switch(randomPackage)
	{
		case "SmallPackage":
			//additional setup
			boolean acknol = getRundomBool();
			
			//pack setup
			pack = new SmallPackage(pr, sender,reciver,acknol, this.mO.getxPackCor());
			pack.addTracking(null, pack.getStatus());
			
			//add packs to closest brunch system
			for(Branch br: this.mO.hub.getBranches())
			{
				if(br.getBranchId() == sender.getZip())
				{
					br.getListPackages().add(pack);
					br.localListPacks.add(pack);
				}
			}
			
			System.out.println("Creating " + pack.toString() );
			break;
			
		case "StandardPackage":
			//additional setup
			double weight = getRundomDouble(1,10);
			
			//pack setup
			pack = new StandardPackage(pr, sender, reciver, weight, this.mO.getxPackCor());
			pack.addTracking(null, pack.getStatus());
			
			//add packs to closest brunch system
			for(Branch br: this.mO.hub.getBranches())
			{
				if(br.getBranchId() == sender.getZip())
				{
					br.getListPackages().add(pack);
					br.localListPacks.add(pack);
				
				}
			}
			
			System.out.println("Creating " + pack.toString() );
			break;
			
		case "NonStandardPackage":
			//pack setup
			pack = new NonStandardPackage(pr, sender, reciver, getRundomNumber(1, 500), getRundomNumber(1, 1000),getRundomNumber(1, 400), this.mO.getxPackCor());
			pack.addTracking(null, pack.getStatus());
			
			//add package to Hub
			this.mO.hub.getBranches().get(0).getListPackages().add(pack);
			this.mO.hub.localListPacks.add(pack);
			System.out.println("Creating " + pack.toString() );
			break;
			
			
		default:
			pack = null;
			break;
			
	}
	
	
	//add to packages list
	if(pack != null)
	{
		int temp=this.mO.getxPackCor();
		this.mO.setxPackCor(temp+this.mO.getDxPackCor());
		this.mO.packages.add(pack);
		this.mO.drawObjects.add(pack);
		this.packs.add(pack);
	}
	else
		System.err.println("Main office Switch case Bug!!!");
	
}





@Override
public void run() {
	// TODO Auto-generated method stub
	int numPacks = 1;
	while(numPacks < 6)
	{
		try {
			Thread.sleep(this.getRundomNumber(2000, 5000));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	addPackage();
	numPacks++;
		
	}
	
	
	while(numPacks > 0)
	{
		try {
			Thread.sleep(10000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/// read txt
		try {
			
			List<String> data = this.mO.packageLog.readEntry();
			if(data != null)
			{
				int count = 0;
				for(String log : data)
				{
					if(log.contains(this.sender.toString()))
					{
						count++;
					}
				}
				
				if(count == 5)
					numPacks = 0;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	for(Package p: this.packs)
	{
		this.mO.drawObjects.remove(p);
		this.mO.hub.localListPacks.remove(p);
		for(int i = 1; i < this.mO.hub.getBranches().size(); i++)
		{
			this.mO.hub.getBranches().get(i).localListPacks.remove(p);
		}
		
	}
	
	if(this.mO.getxPackCor() > 400)
		this.mO.setxPackCor(0);
	else
		this.mO.setxPackCor(400);
	
	System.out.println(this.id + " Customer Done!!!");
}


//Help func
private int getRundomNumber(int min,int max)
{
	 /**
	  * RETURN RANDOM INT IN RANGE
	  */
	  return (int)((Math.random()*(max-min))+min);
	
}

private double getRundomDouble(int min,int max)
{
	/**
	 * RETURN RANDOM DOUBLE IN RANGE
	 */
	return ((Math.random()*(max-min))+min);
}


private boolean getRundomBool()
{
	/**
	 * RETURN RANDOM BOOL
	 */
	if(getRundomNumber(0,1) == 1)
		return true;
	else
		return false;
}








}