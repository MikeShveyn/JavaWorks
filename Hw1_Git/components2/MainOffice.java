package components2;

import java.util.ArrayList;

import components1.Address;
import components1.Priority;

public class MainOffice {
	static int clock;
	Hub hub;
	ArrayList<Package> packages;
	
	public MainOffice(int branches, int trucksForBranch)
	{
		clock = 0;
		packages = new ArrayList<Package>();
		
		//HUB
		hub = new Hub();
		Branch brancheHub=hub.getBranches().get(0);
		for(int i=0;i<trucksForBranch;i++)
		{
			StandardTruck str = new StandardTruck();
			str.setDefaultHub(brancheHub);
			brancheHub.getListTrucks().add(str);
			
		}
		
		brancheHub.getListTrucks().add(new NonStandardTruck());
		System.out.println();
		
		//branches + tracks
		for(int i=1;i<=branches;i++)
		{
			hub.getBranches().add(new Branch());
			
			for(int j=0;j<trucksForBranch;j++)
			{
				hub.getBranches().get(i).getListTrucks().add(new Van());
			}
			System.out.println();
		}
		
		
	}

	//getters setters
	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
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


	
	//methods
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
		if (clock != other.clock)
			return false;
		if (packages == null) {
			if (other.packages != null)
				return false;
		} else if (!packages.equals(other.packages))
			return false;
		return true;
	}
	
	
	public void play(int playTime)
	{
		System.out.println("========================== START ==========================");
		for(int i = 0; i < playTime; i++)
		{
			if(i % 5 == 0)
			{
				addPackage();
			}
			tick();
			
		}
		
		printReport();
	}
	
	private void tick()
	{
		//clock setup
		System.out.println(clockString());
		this.setClock(clock + 1);
		
		//work in all node
		hub.work();
		
	}
	
	private void addPackage()
	{
		Package pack;
		//all setup random 
		Priority[] priority = Priority.values();
		String[] packageTypes = {"SmallPackage", "StandardPackage", "NonStandardPackage"};
		String randomPackage = packageTypes[getRundomNumber(0,2)];
		
		Address sender = new Address(getRundomNumber(0, hub.getBranches().size() - 1), getRundomNumber(100000, 999999)); 
		Address reciver = new Address(getRundomNumber(0, hub.getBranches().size() - 1),getRundomNumber(100000, 999999));
		Priority pr = priority[getRundomNumber(0, priority.length - 1 )];
		
		
		//additional setup
		switch(randomPackage)
		{
			case "SmallPackage":
				boolean acknol = getRundomBool();
				pack = new SmallPackage(pr, sender,reciver,acknol);
				
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
				double weight = getRundomDouble(1,10);
				pack = new StandardPackage(pr, sender, reciver, weight);
				
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
				pack = new NonStandardPackage(pr, sender, reciver, getRundomNumber(1, 500), getRundomNumber(1, 1000),getRundomNumber(1, 400));
				hub.getBranches().get(0).getListPackages().add(pack);
				System.out.println("Creating " + pack.toString() );
				break;
				
			default:
				pack = null;
				break;
				
		}
		
		
		//add to packages
		if(pack != null)
		{
			
			packages.add(pack);
		}
		else
		{
			System.err.println("Main office Switch case Bug!!!");
		}
		
		
	}
	
	
	private int getRundomNumber(int min,int max) {
		return (int)((Math.random()*(max-min))+min);
	}
	
	private double getRundomDouble(int min,int max) {
		return ((Math.random()*(max-min))+min);
	}
	
	private boolean getRundomBool() {
		if(getRundomNumber(0,1) == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void printReport()
	{
		System.out.println("========================================!!!STOP!!!=========================================================");
		for(Package p : packages)
		{
			System.out.println("Tracking " +p);
			p.printTracking();
		}
		
	}
	
	private String clockString()
	{
		int min = clock/60;
		int sec = clock%60;
		return Integer.toString(min) + ":" + Integer.toString(sec);
	}
	
	
	
	
	
}
