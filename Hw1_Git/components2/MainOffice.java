package components2;

import java.util.ArrayList;
import java.util.Random;

import components1.Address;
import components1.Priority;


/**
 * ID 336249743
 * ID 336249628
 * 
 * Class MainOffice represent Brain of project 
 * MainOffice create all brunches trucks and packages and keep track of them
 * Main office work by clock ticking , each second apply work() function to each object implements Node interface 
 * also add new packages and store tracking report
 * 
 * clock - clock of all project
 * hub - represent central brunch (field control office) 
 * packages - list of all packages that have been created by the system
 */


public class MainOffice implements Runnable{
	static int clock;
	Hub hub;
	ArrayList<Package> packages;
	boolean isRun = false;
	int packagesNum;
	
	//constructor ------------------------------------------------------------------------------------
	public MainOffice(int branches, int trucksForBranch, int packsNum)
	{
		/**
		 * Set clock to 0
		 * ArrayList of packages to null
		 * 
		 * Create Hub and trucks for hub 
		 * Create Branches and trucks for each brunch
		 * 
		 */
		
		clock = 0;
		packages = new ArrayList<Package>();
		packagesNum = packsNum;
		//HUB
		hub = new Hub();
		Branch brancheHub=hub.getBranches().get(0);
		
		//create trucks for hub
		for(int i=0;i<trucksForBranch;i++)
		{
			//create StandartTruck
			StandardTruck str = new StandardTruck();
			str.setDefaultHub(brancheHub);
			
			//Add StandartTruck to hub
			brancheHub.getListTrucks().add(str);
			
		}
		
		//Create and add NonStandartTruck to hub
		brancheHub.getListTrucks().add(new NonStandardTruck());
		System.out.println();
		
		
		//Create branches and truck for each one
		for(int i=1;i<=branches;i++)
		{
			//add brunch
			hub.getBranches().add(new Branch());
			
			//add Vans for branch
			for(int j=0;j<trucksForBranch;j++)
			{
				hub.getBranches().get(i).getListTrucks().add(new Van());
			}
			System.out.println();
		}
		
		
	}

	
	
	//getters setters ------------------------------------------------------------------------------------
	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		MainOffice.clock = clock;
	}

	public Hub getHub() {
		return hub;
	}

	public void setHub(Hub hub) {
		this.hub = hub;
	}

	public ArrayList<Package> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}


	
	//Methods---------------------------------------------------------------------------------------------------------
	//Runnable interface
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		/**
		 * Play Start Clock with tick() 
		 * each 5 seconds add new package to the system
		 * by the end of play time loop print tracking report
		 */
		System.out.println("========================== START ==========================");
		while(isRun)
		{
			if(this.packagesNum > 0 && clock % 5 == 0)
			{
				//Add new package to the system
				addPackage();
				//Update GUI
			}
			
			//Move clock and apply work() in each Node implemented object
			tick();
			
		}
		
		//print report
		printReport();
		
	}

	
	
	
	private void tick()
	{
		/**
		 * Manage time of the system and apply work to all Node implemented objects
		 */
		//clock setup
		System.out.println(clockString());
		this.setClock(clock + 1);
		//sleep
		try {
			Thread.sleep(500);
		}catch(InterruptedException e) {}
		//apply work() in hub and than in all Node implemented objects
		//hub.work();
		
	}
	
	
	
	private void addPackage()
	{	
		/**
		 * Add Random package to the system
		 * Create random package 
		 * Depend on its type each package gets different properties 
		 */
		
		//setup
		Random rand = new Random();
		Package pack;
		
		//all common package properties setup random 
		Address sender = new Address(getRundomNumber(0, hub.getBranches().size() - 1), getRundomNumber(100000, 999999)); 
		Address reciver = new Address(getRundomNumber(0, hub.getBranches().size() - 1),getRundomNumber(100000, 999999));
		
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
				pack = new SmallPackage(pr, sender,reciver,acknol);
				pack.addTracking(null, pack.getStatus());
				
				//add packs to closest brunch system
				for(Branch br: hub.getBranches())
				{
					if(br.getBranchId() == sender.getZip())
					{
						br.getListPackages().add(pack);
					}
				}
				
				System.out.println("Creating " + pack.toString() );
				break;
				
			case "StandardPackage":
				//additional setup
				double weight = getRundomDouble(1,10);
				
				//pack setup
				pack = new StandardPackage(pr, sender, reciver, weight);
				pack.addTracking(null, pack.getStatus());
				
				//add packs to closest brunch system
				for(Branch br: hub.getBranches())
				{
					if(br.getBranchId() == sender.getZip())
					{
						br.getListPackages().add(pack);
					}
				}
				
				System.out.println("Creating " + pack.toString() );
				break;
				
			case "NonStandardPackage":
				//pack setup
				pack = new NonStandardPackage(pr, sender, reciver, getRundomNumber(1, 500), getRundomNumber(1, 1000),getRundomNumber(1, 400));
				pack.addTracking(null, pack.getStatus());
				
				//add package to Hub
				hub.getBranches().get(0).getListPackages().add(pack);
				System.out.println("Creating " + pack.toString() );
				break;
				
				
			default:
				pack = null;
				break;
				
		}
		
		
		//add to packages list
		if(pack != null)
		{
			packages.add(pack);
			this.packagesNum --;
		}
		else
			System.err.println("Main office Switch case Bug!!!");
		
	}
	

	
	private void printReport()
	{
		/**
		 * Print tracking of all packages was created by the system
		 */
		System.out.println("========================================!!!STOP!!!=========================================================");
		for(Package p : packages)
		{
			System.out.println("Tracking " +p);
			p.printTracking();
		}
		
	}
	
	
	
	private String clockString()
	{
		/**
		 * return real Clock(String) representation of integer clock
		 */
		int min = clock/60;
		int sec = clock%60;
		return Integer.toString(min) + ":" + Integer.toString(sec);
	}
	
	//HELP FUCNTION ------------------------------------------------------------------------------------
	
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
	
	
	//default methods ------------------------------------------------------------------------------------
		@Override
		public String toString() {
			return "MainOffice [clock=" + clock + ", packages=" + packages + "]";
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MainOffice other = (MainOffice) obj;
			if (packages == null) {
				if (other.packages != null)
					return false;
			} else if (!packages.equals(other.packages))
				return false;
			return true;
		}


	
	
	
}
