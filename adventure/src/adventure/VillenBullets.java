package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class VillenBullets extends Thread implements VisibleObjects {
	Point location;
	Point villenLocation;
	Image bullet=new ImageIcon(getClass().getResource("/res/img/icons/villen-bullet.png")).getImage();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	public VillenBullets(Point location,Point villenLocation) {
		this.location = location;
		this.villenLocation = villenLocation;
		start();
	}
	
	public void run() {
		while(true) {
			// the villen location and bullet location to check to determine bullet's life
			if((villenLocation.x-10*(int) (d.getHeight()/20))>location.x) {
				
				location.x = villenLocation.x;
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// villen bullets move
			location.x-=5;
		}
	}
	
	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(bullet,location.x,location.y,3*(int) (d.getHeight()/70),(int) (d.getHeight()/70),null);
	}

}
