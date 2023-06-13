package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Villen implements VisibleObjects {
	Point location;
	Image coin = new ImageIcon(getClass().getResource("/res/img/charecter/villen.png")).getImage();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	VillenBullets villenBullet;
	int value;

	public Villen(Point location, int value) {
		this.location = location;
		this.value = value;
	}

	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
		// draw villen
		g.drawImage(coin, location.x, location.y, (int) (d.getHeight() / 10), (int) (d.getHeight() / 10), null);
	}
}