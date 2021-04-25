package components3;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class createPanel extends JPanel implements ActionListener, ChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] options= {"Number of Branches","Number of trucks per brunch","Number of packages"};
	private JButton OkButton,CancelButton;
	JSlider branches,trucks,packs;
	int branhesNum,trucksNum,packsNum;
	createPanel()
	{
		setBackground(Color.white);
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(4,1));
		
		branches=new JSlider(JSlider.HORIZONTAL,1,10,1);
		branches.setName(options[0]);
		branches.setPaintTrack(true);
		branches.setPaintTicks(true);
		branches.setPaintLabels(true);
		branches.addChangeListener(this);
		p1.add(branches);
		
		
		trucks=new JSlider(JSlider.HORIZONTAL,1,10,1);
		trucks.setName(options[1]);
		trucks.setPaintTrack(true);
		trucks.setPaintTicks(true);
		trucks.setPaintLabels(true);
		trucks.addChangeListener(this);
		p1.add(trucks);
		
		
		packs=new JSlider(JSlider.HORIZONTAL,1,20,1);
		packs.setName(options[2]);
		packs.setPaintTrack(true);
		packs.setPaintTicks(true);
		packs.setPaintLabels(true);
		packs.addChangeListener(this);
		p1.add(packs);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,2));
		JButton OkButton=new JButton("Ok");
		OkButton.addActionListener(this);
		p2.add(OkButton);
		
		JButton CancelButton=new JButton("Cancel");
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
			
		}
		else if(action.equals("Cancel"))
		{
			
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
