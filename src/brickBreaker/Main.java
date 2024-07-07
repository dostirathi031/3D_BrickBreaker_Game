package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj=new JFrame();
		gameplay game=new gameplay();
		 
	       obj.setBounds(100,100,700,600);
		   obj.setTitle("brickBracker");
		   obj.setResizable(false);
		   obj.setVisible(true);
           obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
           //add arguments will always carry components
           obj.add(game);
           
           
	}

}
