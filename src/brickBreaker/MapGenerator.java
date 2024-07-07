package brickBreaker;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;


public class MapGenerator {
	public int map[][];//declaration
 public int brickheight;
 public int brickwidth;
 
  public MapGenerator(int row,int col) {
	  map=new int[row][col];//creation
	  for(int i=0;i<map.length;i++) {
		  for(int j=0;j<map[0].length;j++) {
			  map[i][j]=1;
		  }
	  }
	    
	     brickwidth=540/col;
	     brickheight=150/row;
  }
   public void draw(Graphics2D g) {
	   for(int i=0;i<map.length;i++) {
		   for(int j=0;j<map[0].length;j++) {
			   if(map[i][j]>0) {
				   GradientPaint gradient = new GradientPaint( j * brickwidth + 80,  i * brickheight + 50, Color.red.brighter(),  j * brickwidth + 80 + brickwidth,  i * brickheight + 50 + brickheight, Color.red.darker());
	                g.setPaint(gradient);
	                g.fillRect( j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);

	                // Brick border for definition
	                g.setColor(Color.black);
	                g.drawRect( j * brickwidth + 80,  i * brickheight + 50, brickwidth, brickheight);
			   }
		   }
	   }  
		
	   }
    
     public void setvalue(int value,int row,int col) {
    	 map[row][col]=value;
     }
     
}
