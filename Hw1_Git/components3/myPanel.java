package components3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import components1.Drawable;
import components1.Tracking;
import components2.Caretaker;
import components2.Branch;
import components2.MainOffice;
import components2.Momento;

public class myPanel extends JPanel implements ChangeListener,ActionListener {
	
	/**
	 * 
	 * ID 336249743
	 * ID 336249628
 	 * 
 	 * Main GUI of the system shows moving objects and controls states like stop resume or
 	 *  opening new windows with additional information
 	 *  
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p1, p2;
	private JButton[] b_num;
	private String[] names = { "Create System", "Start", "Stop", "Resume", "All packages info", "Branch info", "Clone Branch", "Restore", "Report"};
	private MainOffice game;
	private createPanel setupPanel;
	private boolean firstPaint = false;
	private boolean startPaint = false;
	Object item; // Branch Info
	Object item2; // Branch Clone
	int brunchNum;
	JFrame f;
	
	private Caretaker caretaker;
	
	public myPanel() {
		setBackground(Color.white);
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 4, 5, 5));

		b_num = new JButton[names.length];
		for (int i = 0; i < names.length; i++) {
			b_num[i] = new JButton(names[i]);
			b_num[i].addActionListener(new ButtonListener(this, i));
			b_num[i].setBackground(Color.lightGray);
			p1.add(b_num[i]);
		}

		p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 1, 5, 5));
		p2.add(p1);

		setLayout(new BorderLayout());
		add("South", p2);
		this.caretaker = new Caretaker();

	}

	public void destroy() {
		game.ShutDown();
		System.exit(0);
	}

	// Systems
	synchronized public void createSystem() {
		/**
		 * create and open Create Panel to get user input
		 */
		// Create Panel
		JFrame options = new JFrame("Create post System");
		setupPanel = new createPanel(this);
		options.add(setupPanel);
		options.setSize(600, 400);
		options.setVisible(true);
	}
	
	
	/////?????????????????????????????????
	  @Override
	  protected void paintComponent(Graphics g)
	  {
		  /**
		   * Paint components on main screen by calling each drawable object special method
		   */
		  super.paintComponent(g);
		  
		  if(this.firstPaint) {
			  
			  for(int i = 0; i < game.drawObjects.size(); i++)
			  {
				  game.drawObjects.get(i).DrawMe(g);
			  }		  
			  
		  }
		  

	  }
	  
	  public void createMainOffice(int numB,int numTr) throws IOException
	  {
		  /**
		   * Create Main Office
		   */
		  game = MainOffice.getInstance(numB, numTr,this);
		  this.firstPaint = true;
		  repaint();
	  }
	  

	synchronized public void start() {
		/**
		 * Start Game -  Threads
		 */
		if (game != null) {
			this.game.start();
		}
	}

	synchronized public void stop() {
		/** 
		 * Stop Game threads
		 */
		MainOffice mainO = ((MainOffice)game);
		mainO.StopMe();
		}
		


	synchronized public void resume() {
		/**
		 * Resume game threads
		 */
		MainOffice mainO =  ((MainOffice)game);
		mainO.ResumeMe();
		notify();
	}

	synchronized public void packInfo() {
		/**
		 * Open new window and create table that holds all package's tracking information
		 */
		MainOffice mainO =  ((MainOffice)game);
		JFrame f = new JFrame();
		f.setTitle("Packages Info");
		String[] columnNames = {"Package ID", "Sender", "Destination", "Priority", "Status"};
		String[][] rowNames=mainO.rowNames();
		if(rowNames!=null)
		{	
			JTable j = new JTable(rowNames, columnNames);
			j.setBounds(50, 70, 200, 300);
			JScrollPane sp = new JScrollPane(j);
			f.add(sp);
	        f.setSize(1200,700);
	        f.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "It's 0 packages.","Message", JOptionPane.ERROR_MESSAGE);	
		}
	}

	synchronized public void branchInfo() {
		/**
		 * Open new window that show branch information (packages that branch hold at that moment) 
		 */
		MainOffice mainO =  ((MainOffice)game);
		int size=mainO.getHub().getBranches().size();
		String s1[]=new String[size];
		JFrame fr = new JFrame();
		fr.setSize(400,300);
		fr.setTitle("Branch Info");
		s1[0]="HUB";
		for(int i=1;i<size;i++)
		{
			s1[i]=mainO.getHub().getBranches().get(i).getBranchName();
		}
		
		JComboBox<String> box=new JComboBox<String>(s1);
		JPanel p = new JPanel();
		p.add(box);
		fr.add(p);
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();				
				item = comboBox.getSelectedItem();
				
				makeTable();
			}});
		fr.setSize(300,300);
		fr.setVisible(true);
	}

	private void makeTable() {
		String[][] rowName=null;
		MainOffice mainO =  ((MainOffice)game);
		int size=mainO.getHub().getBranches().size();
		String[] columnName = {"Package ID", "Sender", "Destination", "Priority", "Status"};
		
		for(int i=0;i<size;i++)
		{
			String var=mainO.getHub().getBranches().get(i).getBranchName();
			String var1=this.item.toString();
			if(var.equals(var1))
			{
				rowName=mainO.getHub().getBranches().get(i).makePInfo();
				break;
			}
		}
		
		if(rowName!=null)
		{
			JFrame f1=new JFrame();
			JTable j = new JTable(rowName, columnName);
			j.setBounds(50, 70, 200, 300);
			JScrollPane sp = new JScrollPane(j);
	        f1.add(sp);
	        f1.setSize(800,700);
	        f1.setVisible(true);
		}
		else
		{	JOptionPane.showMessageDialog(null, "this branch has 0 packages.","Message", JOptionPane.ERROR_MESSAGE);	}
			
	}

	public void cloneBranch() {
		MainOffice mainO =  ((MainOffice)game);
		f = new JFrame();
		f.setTitle("Clone Branche");
		
		int size=mainO.getHub().getBranches().size() -1;
		System.out.println(size);
		String branchesNames[]=new String[size];
		for(int i=1;i<=size;i++)
		{
			branchesNames[i-1]=mainO.getHub().getBranches().get(i).getBranchName();
		}
		
		JComboBox<String> box=new JComboBox<String>(branchesNames);
		JPanel p = new JPanel();
		
		JLabel label1=new JLabel();
		label1.setText("Number of trucks:");
		p.add(label1);
		
		JSlider branches=new JSlider(1,10);
		branches.setMajorTickSpacing(1);
		branches.setPaintTrack(true);
		branches.setPaintTicks(true);
		branches.setPaintLabels(true);
		branches.setName("branchNum");
		branches.addChangeListener(this);
		
		
		JButton OkButton=new JButton("Ok");
		OkButton.addActionListener(this);
		
		p.add(box);
		p.add(branches);
		p.add(OkButton);
		f.add(p);
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();				
				item2 = comboBox.getSelectedItem();
			}

			});
		
		
		f.setSize(400,300);
		f.setVisible(true);
		
	}
	
	@Override
	public void stateChanged(ChangeEvent event) {
		/**
		 * Change variables by moving sliders
		 */
		JSlider source = (JSlider)event.getSource();
        if(source.getName().equals("branchNum")) {
        	brunchNum=source.getValue();
        }
      
        
	}

	public void restore() {
		this.game.getSystemBack();
		System.out.println("!!!Restore!!!!");
	}

	public void report() {
		// TODO Auto-generated method stub
		game.OpneFile();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.CopyBranch(item2, brunchNum);
		this.f.dispose();
	}
}

class ButtonListener implements ActionListener {
	/**
	 * Buttons handle
	 */
	private myPanel panel;
	private int btn_ind;

	public ButtonListener(myPanel p, int b) {
		panel = p;
		btn_ind = b;
	}

	public void actionPerformed(ActionEvent e) {
		/**
		 * Respond on each system button click
		 */
		// { "Create System", "Start", "Stop", "Resume", "All packages info", "Branch info", "Clone Branch", "Restore", "Report"}
		switch (btn_ind) {
		case 0:
			panel.createSystem();

			break;
		case 1:
			panel.start();
			break;
		case 2:
			panel.stop();
			break;
		case 3:
			panel.resume();
			break;
		case 4:
			panel.packInfo();
			break;
		case 5:
			panel.branchInfo();
			break;
		case 6: 
			panel.cloneBranch();
			break;
		case 7:
			panel.restore();
			break;
		case 8:
			panel.report();
			break;
		
		}

	}
};
