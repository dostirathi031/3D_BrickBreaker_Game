package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

//JPanel is a package that contains group of components 
//keylistner and action listener is interface which contain abstract method and necssarily need to override

public class gameplay extends JPanel implements KeyListener,ActionListener {
	
	private boolean play=false;
	private int score = 0;

	private int totalBricks = 21;
	//how fast the ball move this can be done by delay...by reducing delay we can increase the speed  
	private Timer timer;
	private int delay=2;
	//position of panel from x axis
	private int playerx=310;
	//position of ball from x and y axis
	private int ballposx=120;
	private int ballposy=350;
	//direction of ball to x and y
	private int ballxdir=-2;
	private int ballydir=-4;
	private MapGenerator map;
	public gameplay() {
		map=new MapGenerator(3,7);
  
		
		//to implement interface the argument should pass as this
		addKeyListener(this);
		
		//setFocusable(boolean n), it´s mainly used to activate or deactivate the focus event
		//of the view, both in the tactile / mouse mode, and in the keyboard (cursor) mode.
		setFocusable(true);
		
		//setFocusTraversalKeysEnabled() decides whether or not focus traversal keys
		//(TAB key, SHIFT+TAB, etc.) 
		setFocusTraversalKeysEnabled(false);
		timer= new Timer(delay, this);
		timer.start();
		}
	public void paint(Graphics g) {
		//converts a Graphics object (g) to a Graphics2D object (g2d).
		//graphics2d is used to enhance objects or components
		 Graphics2D g2d = (Graphics2D) g;
		//we use color work before then only it will applied to its next method
		//background
		g.setColor(Color.black);
		// argument in rect is postion at x axis,pos at y axis,width,height
		//x axis of top left and y axis of top left
		g.fillRect(1,1,692,592);
		
		map.draw((Graphics2D) g);
		//borders
		g2d.setColor(Color.blue);
		g2d.fillRect(692,0,3,592);
		g2d.fillRect(0,0,3,592);
		g2d.fillRect(0,0,692,3);
		
		//scores
		g2d.setColor(Color.yellow);
		g2d.setFont(new Font("Serif",Font.BOLD,25));
		g2d.drawString(""+score,590,30);
		
		//it shows that the ball is falling down the frame
		if(ballposy>570) {
			play=false;
			ballxdir=0;
			ballydir=0;
			g2d.setColor(Color.yellow);
			g2d.setFont(new Font("Serif",Font.BOLD,30));
			g2d.drawString("GAME OVER,Scores:"+score,190,300);
			
			g2d.setFont(new Font("Serif",Font.BOLD,20));
			g2d.drawString("press ENTER to restart",230,350);
		}
		
		//paddle
		//by using gradientpaint class we can provide shadow...or light effects in our components
		 GradientPaint gradient = new GradientPaint(playerx, 550, Color.green.brighter(), playerx, 550 + 8, Color.green.darker());
		    g2d.setPaint(gradient);
		    g2d.fillRect(playerx, 550, 100, 8);
		//ball
		g2d.setColor(Color.yellow);
	    g2d.fillOval(ballposx, ballposy, 20, 20);
	    
	    if(totalBricks<=0) {
			play=false;
			ballxdir=0;
			ballydir=0;
			g2d.setColor(Color.green);
			g2d.setFont(new Font("Serif",Font.BOLD,30));
			g2d.drawString("WON!!!!!!!,Scores:"+score,260,300);
			
			g2d.setFont(new Font("Serif",Font.BOLD,20));
			g2d.drawString("press ENTER to restart",270,350);
		}
		
	    g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		/*The - (negation) operator is applied to ballYdir, effectively reversing its sign.
		If ballYdir was positive (ball moving down), -ballYdir will be negative (ball will move up).
		If ballYdir was negative (ball moving up), -ballYdir will be positive (ball will move down).*/
		if(play) {
			if(new Rectangle(ballposx,ballposy,20,20).intersects(new Rectangle(playerx,550,100,8))) {
				ballydir=-ballydir;
			}
			A: for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickx=j*map.brickwidth+80;
						int bricky=i*map.brickheight+50;
						int brickwidth=map.brickwidth;
						int brickheight=map.brickheight;
						//creation of rectangle to each bricks and a ball..for intersection purpose
						Rectangle rect=new Rectangle(brickx,bricky,brickwidth,brickheight);
						Rectangle ballrect=new Rectangle(ballposx,ballposy,20,20);
						Rectangle brickrect=rect;
						
						if(ballrect.intersects(brickrect)) {
							map.setvalue(0,i,j);
							totalBricks--;
							score+=5;
							/*if ball will intersect to the right and left of the brick then 
							ball will change its x direction*/
							if(ballposx+19<=brickrect.x||ballposx+1>=brickrect.x+brickrect.width) {
								ballxdir=-ballxdir;
								}else {
									ballydir=-ballydir;
								}
							break A;
						}
					}
				}
			}
			ballposx+=ballxdir;
			ballposy+=ballydir;
			// Check for collision with the left wall
			if(ballposx<0) {
				ballxdir=-ballxdir;
			}
			// Check for collision with the top wall
			if(ballposy<0) {
				ballydir=-ballydir;
			}
			// Check for collision with the right wall
			if(ballposx>670) {
				ballxdir=-ballxdir;
			}
					
		}
		//repaint function is used so that we can call paint method again and again so that example like
		//moving panel can draw the panel again with that dimension over frame
		repaint();
		}
    @Override
	public void keyPressed(KeyEvent e) {
    	//(virtual key) VK_RIGHT is used in a key event handler to detect when the right arrow key is pressed:
    	//e.getKeyCode() is the button code that is pressed
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    		if (playerx >= 600) {
                playerx = 600;
            } else {
                moveRight();
            }
    	}
    	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    		if (playerx <= 10) {
                playerx = 10;
            } else {
                moveleft();
            }
    	}
    	
    	if(e.getKeyCode()==KeyEvent.VK_ENTER) {
    		if(!play) {
    			play=true;
    			ballposx=120;
    			ballposy=350;
    			ballxdir=-2;
    			ballydir=-4;
    			playerx=310;
    			score=0;;
    			totalBricks=21;
    			map=new MapGenerator(3,7);
    			repaint();
    		}
    	}
	}
    @Override
	public void keyReleased(KeyEvent e) {
		}
    @Override
     public void keyTyped(KeyEvent e) {
		}
        public void moveRight() {
        	play=true;
        	playerx+=20;
        }
        public void moveleft() {
        	play=true;
        	playerx-=20;
        }
    
    
}
