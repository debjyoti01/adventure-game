package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class VillenDieAnimation extends Thread implements VisibleObjects {
	Point location;
	int lifeTime = 400;
	Image bullet = new ImageIcon(getClass().getResource("/res/img/icons/villen-die.gif")).getImage();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public VillenDieAnimation(Point location) {
		this.location = location;
	}

	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
		// draw villen die animation
		g.drawImage(bullet, location.x, location.y, (int) (d.getHeight() / 10), (int) (d.getHeight() / 10), null);
	}
}
