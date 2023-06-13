package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Coin implements VisibleObjects {
	Point location;
	Image coin=new ImageIcon(getClass().getResource("/res/img/icons/Coin.png")).getImage();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	int value;
	
	public Coin(Point location, int value) {
		this.location = location;
		this.value = value;
	}
	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
			g.drawImage(coin,location.x,location.y,(int) (d.getHeight()/20),(int) (d.getHeight()/20),null);
//		System.out.println(((int) (d.getHeight()/5)));
	}
}
