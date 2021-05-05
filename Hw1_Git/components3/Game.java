package components3;

import javax.swing.JFrame;

/*
 * ID 336249743 ID 336249628 The Project represents delivery system and contains
 * MainOffice , Hub , brunches , delivery trucks and packages.
 * 
 */

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame myframe = new JFrame("Post Tracking System");
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myPanel mypanel = new myPanel();
		myframe.add(mypanel);
		myframe.setSize(1200, 700);
		myframe.setVisible(true);
	}

}


