package components2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import components1.Drawable;
import components1.ThreadBand;
import components3.myPanel;

/**
 * ID 336249743 ID 336249628
 * 
 * Class MainOffice represent Brain of project MainOffice create all brunches
 * trucks and packages and keep track of them Main office work by clock ticking
 * , each second apply work() function to each object implements Node interface
 * also add new packages and store tracking report
 * 
 * clock - clock of all project hub - represent central brunch (field control
 * office) packages - list of all packages that have been created by the system
 */

public class MainOffice extends Thread implements ThreadBand {
	

	static MainOffice mainOfInstance;
	static int clock;
	Hub hub;
	ArrayList<Package> packages;
	boolean isRun = true;

	public ArrayList<Drawable> drawObjects;
	ArrayList<ThreadBand> threadBands;
	ArrayList<Thread> gameThreads;
	myPanel localPanel;

	private int xPackCor = 0;
	private int dxPackCor = 0;
	
	private File file;
	Log packageLog;
		

	public static MainOffice getInstance(int branches, int trucksForBranch, myPanel lp) throws IOException {
		if (mainOfInstance == null) {
			mainOfInstance = new MainOffice(branches, trucksForBranch,lp);
		}
		return mainOfInstance;
	}

	// constructor
	// ------------------------------------------------------------------------------------
	private MainOffice(int branches, int trucksForBranch,myPanel lp) throws IOException {
		super();
		/**
		 * Set clock to 0 ArrayList of packages to null
		 * 
		 * Create Hub and trucks for hub Create Branches and trucks for each brunch
		 * 
		 */
		
		file = new File("PackageLog.txt");
		packageLog = new Log(file);
		
		this.localPanel = lp;
		clock = 0;
		packages = new ArrayList<Package>();
		threadBands = new ArrayList<>();
		gameThreads = new ArrayList<>();
		drawObjects = new ArrayList<>();
		// HUB
		hub = new Hub();
		Branch brancheHub = hub.getBranches().get(0);

		threadBands.add(hub);
		gameThreads.add(hub);
		drawObjects.add(hub);
		// threadBands.add(brancheHub);
		// gameThreads.add(brancheHub);
		// drawObjects.add(brancheHub);
		// create trucks for hub
		for (int i = 0; i < trucksForBranch; i++) {
			// create StandartTruck
			StandardTruck str = new StandardTruck();
			str.setDefaultHub(brancheHub);

			// Add StandartTruck to hub
			brancheHub.getListTrucks().add(str);
			threadBands.add(str);
			gameThreads.add(str);
			drawObjects.add(str);
		}

		// Create and add NonStandartTruck to hub
		NonStandardTruck nstr = new NonStandardTruck();
		brancheHub.getListTrucks().add(nstr);
		threadBands.add(nstr);
		gameThreads.add(nstr);
		drawObjects.add(nstr);

		/// calculate y between branches
		int dy_cor = 470 / branches;
		int y_cor = 0;
		// calculate packages dx
		this.setDxPackCor(800 / 10);

		// Create branches and truck for each one
		for (int i = 1; i <= branches; i++) {

			// add brunch
			Branch br = new Branch(y_cor);
			hub.getBranches().add(br);
			threadBands.add(br);
			gameThreads.add(br);
			drawObjects.add(br);
			y_cor += dy_cor;
			// add Vans for branch
			for (int j = 0; j < trucksForBranch; j++) {
				Van v = new Van();
				hub.getBranches().get(i).getListTrucks().add(v);
				threadBands.add(v);
				gameThreads.add(v);
				drawObjects.add(v);

			}
			System.out.println();
		}

	}

	// getters setters
	// ------------------------------------------------------------------------------------
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
	
	public int getxPackCor() {
		return xPackCor;
	}

	public void setxPackCor(int xPackCor) {
		this.xPackCor = xPackCor;
	}

	public int getDxPackCor() {
		return dxPackCor;
	}

	public void setDxPackCor(int dxPackCor) {
		this.dxPackCor = dxPackCor;
	}


	// Methods---------------------------------------------------------------------------------------------------------
	// Runnable interface
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/**
		 * Play Start Clock with tick() each 5 seconds add new package to the system by
		 * the end of play time loop print tracking report
		 */
		// List Add start
		
		
		Start();
		StartCustomers();
		
		System.out.println("========================== START ==========================");
		localPanel.repaint();

		while (true) {
			try {
				Thread.sleep(500);

				localPanel.repaint();

				synchronized (this) {
					while (!isRun) {
						wait();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * if(this.packagesNum > 0 && clock % 5 == 0) { //Add new package to the system
			 * addPackage(); }
			 */

			// Move clock and apply work() in each Node implemented object
			tick();
		}

	}
	
	
	private void StartCustomers()
	{
		// 2.Execute two threads simultaneously
		Executor executor = Executors.newFixedThreadPool (2);
		for(int i = 0; i <10; i++)
		{
			executor.execute (new Customer(this));
		}
		
		((ExecutorService) executor).shutdown(); 
	}
	
	private void tick() {
		/**
		 * Manage time of the system and apply work to all Node implemented objects
		 */
		// clock setup
		System.out.println(clockString());
		this.setClock(clock + 1);
		

	}

	synchronized public void Start() {
		for (Thread thd : gameThreads) {
			thd.start();

		}
	}

	@Override
	synchronized public void StopMe() {
		// TODO Auto-generated method stub
		// Array list stop
		for (ThreadBand trb : this.threadBands)
			trb.StopMe();
		isRun = false;
	}

	@Override
	synchronized public void ResumeMe() {
		// TODO Auto-generated method stub

		isRun = true;
		notify();

		// Array list resume
		for (ThreadBand trb : this.threadBands)
			trb.ResumeMe();
	}

	public String[][] rowNames() {
		/**
		 * make row from package tracking data
		 */
		if (this.packages.size() != 0) {
			String[][] arr = new String[this.packages.size()][5];
			int i = 0;
			for (Package p : packages) {
				arr[i] = p.makeRow();
				i++;
				if (i == this.packages.size()) {
					break;
				}
			}
			return arr;
		}
		return null;
	}

	public void printReport(Package p) {
		/**
		 * Print tracking of all packages was created by the system
		 */
			try {
				packageLog.writeEntry(p.printTracking());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

	}

	private String clockString() {
		/**
		 * return real Clock(String) representation of integer clock
		 */
		int min = clock / 60;
		int sec = clock % 60;
		return Integer.toString(min) + ":" + Integer.toString(sec);
	}

	// HELP FUCNTION
	// ------------------------------------------------------------------------------------

	
	// default methods
	// ------------------------------------------------------------------------------------
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
