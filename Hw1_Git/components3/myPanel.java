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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import components1.Drawable;
import components1.Tracking;
import components2.Branch;
import components2.MainOffice;

public class myPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel p1, p2;
	private JButton[] b_num;
	private String[] names = { "Create System", "Start", "Stop", "Resume", "All packages info", "Branch info"};
	private MainOffice game;
	private createPanel setupPanel;
	private boolean firstPaint = false;
	private boolean startPaint = false;
	Object item;
	
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
		

	}

	public void destroy() {
		System.exit(0);
	}

	// Systems
	synchronized public void createSystem() {
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
		  super.paintComponent(g);
		  
		  if(this.firstPaint) {
			  
			  for(int i = 0; i < game.drawObjects.size(); i++)
			  {
				  game.drawObjects.get(i).DrawMe(g);
			  }		  
			  
		  }
		  

	  }
	  
	  public void createMainOffice(int numB,int numTr,int numPack)
	  {
		  game = new MainOffice(numB, numTr, numPack, this);
		  this.firstPaint = true;
		  repaint();
	  }
	  

	synchronized public void start() {
		if (game != null) {
			this.game.start();
		}
	}

	synchronized public void stop() {
		MainOffice mainO =  ((MainOffice)game);
		mainO.StopMe();
		}
		


	synchronized public void resume() {
		MainOffice mainO =  ((MainOffice)game);
		mainO.ResumeMe();
		notify();
	}

	synchronized public void packInfo() {
		MainOffice mainO =  ((MainOffice)game);
		JFrame f = new JFrame();
		f.setTitle("Packages Info");
		String[] columnNames = {"Package ID", "Sender", "Destination", "Priority", "Status"};
		String[][] rowNames=mainO.rowNames();
		if(rowNames!=null)
		{	mainO.printReport();
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
}

class ButtonListener implements ActionListener {
	private myPanel panel;
	private int btn_ind;

	public ButtonListener(myPanel p, int b) {
		panel = p;
		btn_ind = b;
	}

	public void actionPerformed(ActionEvent e) {
		// {"Create System", "Start","Stop","Resume","All packages info", "Branch
		// info"};
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
		}

	}
};
