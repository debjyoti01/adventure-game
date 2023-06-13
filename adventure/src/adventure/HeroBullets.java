package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class HeroBullets extends Thread implements VisibleObjects {
	Point initialLocation;
	Point location;
	Image bullet = new ImageIcon(getClass().getResource("/res/img/icons/hero-bullet.png")).getImage();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public HeroBullets(Point location) {
		this.location = location;
		this.initialLocation = new Point(location.x, location.y);
		start();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// hero's bullet movement
			location.x += 5;
		}
	}

	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(bullet, location.x, location.y, 2 * (int) (d.getHeight() / 70), (int) (d.getHeight() / 70), null);

	}

}
