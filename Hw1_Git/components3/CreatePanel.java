package components3;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import components2.MainOffice;

class createPanel extends JPanel implements ActionListener, ChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] options= {"Number of Branches","Number of trucks per brunch","Number of packages"};
	private JButton OkButton,CancelButton;
	JSlider branches,trucks,packs;
	private int branhesNum,trucksNum,packsNum;
	
	createPanel()
	{
		setBackground(Color.white);
		JPanel p1 = new JPanel();
		p1.setPreferredSize(new Dimension(560,360));
		p1.setLayout(new GridLayout(7,1));
		
		JLabel label1=new JLabel();
		label1.setText(options[0]);
		p1.add(label1);
		
		branches=new JSlider(1,10);
		branches.setMajorTickSpacing(1);
		branches.setPaintTrack(true);
		branches.setPaintTicks(true);
		branches.setPaintLabels(true);
		branches.setName(options[0]);
		branches.addChangeListener(this);
		p1.add(branches);
		
		JLabel label2=new JLabel();
		label2.setText(options[1]);
		p1.add(label2);
		
		trucks=new JSlider(1,10);
		trucks.setMajorTickSpacing(1);
		trucks.setName(options[1]);
		trucks.setPaintTrack(true);
		trucks.setPaintTicks(true);
		trucks.setPaintLabels(true);
		trucks.addChangeListener(this);
		p1.add(trucks);
		
		JLabel label3=new JLabel();
		label3.setText(options[2]);
		p1.add(label3);
		
		packs=new JSlider(2,20);
		packs.setMajorTickSpacing(2);
		packs.setName(options[2]);
		packs.setPaintTrack(true);
		packs.setPaintTicks(true);
		packs.setPaintLabels(true);
		packs.addChangeListener(this);
		p1.add(packs);
		
		this.setBrunchNum(branches.getValue());
		this.setTrucksNum(trucks.getValue());
		this.setPacksNum(packs.getValue());
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,2));
		OkButton=new JButton("Ok");
		OkButton.addActionListener(this);
		p2.add(OkButton);
		
		CancelButton=new JButton("Cancel");
		CancelButton.addActionListener(this);
		p2.add(CancelButton);
		p1.add(p2);
		add(p1);
	}
	
	public int getBranchNum() {
		return this.branhesNum;
	}
	
	public int getTrucksNum() {
		return this.trucksNum;
	}
	
	public int getPacksNum() {
		return this.packsNum;
	}
	
	public void setBrunchNum(int b) {
		this.branhesNum=b;
	}
	
	public void setTrucksNum(int t) {
		this.trucksNum=t;
	}
	
	public void setPacksNum(int p) {
		this.packsNum=p;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals("Ok")) 
		{
				/////?????????????????????????????????
			JFrame t=(JFrame)SwingUtilities.getWindowAncestor(this);
			t.dispose();
		}
		else if(action.equals("Cancel"))
		{
			JFrame t=(JFrame)SwingUtilities.getWindowAncestor(this);
			t.dispose();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent event) {
		JSlider source = (JSlider)event.getSource();
        if(source.getName().equals(options[0])) {
        	this.setBrunchNum(source.getValue());
        }
        else if(source.getName().equals(options[1])) {
        	this.setTrucksNum(source.getValue());
        }
        else if(source.getName().equals(options[2])) {
        	this.setPacksNum(source.getValue());
        }
        
	}
}
