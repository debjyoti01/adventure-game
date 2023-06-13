package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Life implements VisibleObjects {
	Point location;
	Image coin = new ImageIcon(getClass().getResource("/res/img/icons/life.png")).getImage();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public Life(Point location) {
		this.location = location;
	}

	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
		// display hero's life
		g.drawImage(coin, location.x, location.y, (int) (d.getHeight() / 25), (int) (d.getHeight() / 25), null);
	}
}
