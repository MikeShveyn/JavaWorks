package components3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import components2.MainOffice;


/**
 * ID 336249743
 * ID 336249628
 * The Project represents delivery system and  contains MainOffice , Hub , brunches ,
 *  delivery trucks and packages. 
 * 
 */


public class Game {

	public static void main(String[] args) {
		 // TODO Auto-generated method stub
		 JFrame myframe = new JFrame("Post Tracking System");
		 myframe.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		 myPanel mypanel = new myPanel();
		 myframe.add(mypanel);
		 myframe.setSize(1200,700);
		 myframe.setVisible(true);
	}

}

//Thread game = new Thread(new MainOffice(5, 4));
//game.start();



class myPanel extends JPanel
{
   private static final long serialVersionUID = 1L;
   private JPanel p1, p2;
   private JButton[] b_num;
   private String[] names = {"Create System", "Start","Stop","Resume","All packages info", "Branch info"};
   private Thread game; 
   boolean suspended;
   
   public myPanel()
   {
	    setBackground(Color.white);
	    p1=new JPanel();
		p1.setLayout(new GridLayout(1,4,5,5));

		b_num=new JButton[names.length];
		for(int i=0;i<names.length;i++)
		{
		    b_num[i]=new JButton(names[i]);
		    b_num[i].addActionListener(new ButtonListener(this,i));
		    b_num[i].setBackground(Color.lightGray);
		    p1.add(b_num[i]);		
		}

	    p2=new JPanel();
		p2.setLayout(new GridLayout(2,1,5,5));
		p2.add(p1);
		
		setLayout(new BorderLayout());
		add("South", p2);;
		
		suspended = false;
		//game = new Thread(this);
		//timer.start();
   }		

   public void paintComponent(Graphics g)
   {
	   	super.paintComponent(g);	   	
	    
   }


   public void destroy()
   {  	        
      System.exit(0);
   }
   
   //Systems
   synchronized public void  createSystem() {
	    //Create Panel
   		//game = new Thread(new MainOffice(brNum, truckNum, puckNum)); 
	   JFrame options=new JFrame("Create post System");
	   createPanel mypanel = new createPanel();
	   options.add(mypanel);
	   options.setSize(900,500);
	   options.setVisible(true);
   }
   
   synchronized public void start() {
    	suspended = false;
        notify();
    }

    synchronized public void  stop() {
    	suspended = true;
    }
    
    synchronized public void  resume() {
    	suspended = true;
    }
    
    synchronized public void packInfo() {
    	suspended = true;
    }
    
    synchronized public void  branchInfo() {
    	suspended = true;
    }

    
}

class ButtonListener implements ActionListener
{
   private myPanel panel;
   private int btn_ind;

   public ButtonListener(myPanel p, int b)
   {
	   panel = p;
	   btn_ind = b;
   }

   public void actionPerformed(ActionEvent e)
   {
	   //{"Create System", "Start","Stop","Resume","All packages info", "Branch info"};
	switch(btn_ind)
	{
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