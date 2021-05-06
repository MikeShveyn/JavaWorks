package components2;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import components1.Address;
import components1.Drawable;
import components1.Priority;
import components1.ThreadBand;
import components3.myPanel;

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


public class MainOffice extends Thread implements ThreadBand{
	static int clock;
	Hub hub;
	ArrayList<Package> packages;
	boolean isRun = true;
	int packagesNum;
	
	public ArrayList<Drawable> drawObjects;
	ArrayList<ThreadBand> threadBands;
	ArrayList<Thread> gameThreads;
	myPanel localPanel;
	
	private int xPackCor = 0;
	//constructor ------------------------------------------------------------------------------------
	public MainOffice(int branches, int trucksForBranch, int packsNum, myPanel lp)
	{
		super();
		/**
		 * Set clock to 0
		 * ArrayList of packages to null
		 * 
		 * Create Hub and trucks for hub 
		 * Create Branches and trucks for each brunch
		 * 
		 */
		///calculate y between branches
		int y_cor = 0;

		this.localPanel = lp;
		clock = 0;
		packages = new ArrayList<Package>();
		packagesNum = packsNum;
		threadBands = new ArrayList<>();
		gameThreads = new ArrayList<>();
		drawObjects = new ArrayList<>();
		//HUB
		hub = new Hub();
		Branch brancheHub=hub.getBranches().get(0);
		
		threadBands.add(hub);
		gameThreads.add(hub);
		drawObjects.add(hub);
		//threadBands.add(brancheHub);
		//gameThreads.add(brancheHub);
		//drawObjects.add(brancheHub);
		//create trucks for hub
		for(int i=0;i<trucksForBranch;i++)
		{
			//create StandartTruck
			StandardTruck str = new StandardTruck();
			str.setDefaultHub(brancheHub);
			
			//Add StandartTruck to hub
			brancheHub.getListTrucks().add(str);
			threadBands.add(str);
			gameThreads.add(str);
			drawObjects.add(str);
		}
		
		//Create and add NonStandartTruck to hub
		NonStandardTruck nstr = new NonStandardTruck();
		brancheHub.getListTrucks().add(nstr);
		threadBands.add(nstr);
		gameThreads.add(nstr);
		drawObjects.add(nstr);
		
		//Create branches and truck for each one
		for(int i=1;i<=branches;i++)
		{
			//add brunch
			Branch br = new Branch(y_cor);
			hub.getBranches().add(br);
			threadBands.add(br);
			gameThreads.add(br);
			drawObjects.add(br);
			y_cor += 50;
			//add Vans for branch
			for(int j=0;j<trucksForBranch;j++)
			{
				Van v = new Van();
				hub.getBranches().get(i).getListTrucks().add(v);
				threadBands.add(v);
				gameThreads.add(v);
				drawObjects.add(v);
				
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
		//List Add start
		Start();
		System.out.println("========================== START ==========================");

		
		while(true)
		{
			try {
				Thread.sleep(500);
				
				localPanel.repaint();
				
				synchronized(this) {
					while(!isRun)
					{
						wait();
					}
				}
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}

			if(this.packagesNum > 0 && clock % 5 == 0)
			{
				//Add new package to the system
				addPackage();
			}
			
			//Move clock and apply work() in each Node implemented object
			tick();
		}
		
			
	}
		
	
	private void tick()
	{
		/**
		 * Manage time of the system and apply work to all Node implemented objects
		 */
		//clock setup
		System.out.println(clockString());
		this.setClock(clock + 1);

	}
	

	synchronized public void Start()
	{
		for(Thread thd: gameThreads)
		{
			thd.start();
		
		}
	}
	

	
	@Override
	synchronized public void StopMe() {
		// TODO Auto-generated method stub
		//Array list stop
		for(ThreadBand trb: this.threadBands)
			trb.StopMe();
		isRun = false;
	}



	@Override
	synchronized public void ResumeMe() {
		// TODO Auto-generated method stub
		
		isRun = true;
		notify();
		
		//Array list resume
		for(ThreadBand trb: this.threadBands)
			trb.ResumeMe();
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
				pack = new SmallPackage(pr, sender,reciver,acknol, this.xPackCor);
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
				pack = new StandardPackage(pr, sender, reciver, weight, this.xPackCor);
				pack.addTracking(null, pack.getStatus());
				
				//add packs to closest brunch system
				for(Branch br: hub.getBranches())
				{
					if(br.getBranchId() == sender.getZip())
					{
						br.getListPackages().add(pack);
						//br.localListPacks.add(pack);
					}
				}
				
				System.out.println("Creating " + pack.toString() );
				break;
				
			case "NonStandardPackage":
				//pack setup
				pack = new NonStandardPackage(pr, sender, reciver, getRundomNumber(1, 500), getRundomNumber(1, 1000),getRundomNumber(1, 400), this.xPackCor);
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
			this.xPackCor += 50;
			packages.add(pack);
			drawObjects.add(pack);
			this.packagesNum --;
		}
		else
			System.err.println("Main office Switch case Bug!!!");
		
	}
	

	
	public void printReport()
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
