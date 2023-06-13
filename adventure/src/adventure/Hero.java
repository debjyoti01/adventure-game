package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Hero extends Thread implements VisibleObjects {

	Point location;
	Image hero;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	int curX;
	int curY;
	int tarX;
	int tarY;
	int stepSize;
	int respawnTime;
	boolean respawn = false;
	Timer timer;

	public Hero(Point location) {
		this.location = location;
		curX = location.x;
		curY = location.y;
		tarX = location.x;
		tarY = location.y;
		stepSize = 2;
		start();
	}

	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
		if (respawn) { // respawn flag is set then display
			hero = new ImageIcon(getClass().getResource("/res/img/charecter/hero-respawn1.gif")).getImage();
		} else {
			hero = new ImageIcon(getClass().getResource("/res/img/charecter/hero.png")).getImage();
		}

		g.drawImage(hero, location.x, location.y, (int) (d.getHeight() / 10), (int) (d.getHeight() / 10), null);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (respawn) {
				respawnTime--;
				if (respawnTime < 0) {
					respawn = false;
				}
			}
			
			// Calculate the distance between the current position and the target position
			int dx = tarX - location.x;
			int dy = tarY - location.y;
			double distance = Math.sqrt(dx * dx + dy * dy);

			// If the distance is less than the step size, move directly to the target
			// position
			if (distance < stepSize) {
				location.x = tarX;
				location.y = tarY;
			}
			// Otherwise, move towards the target position by the step size
			else {
				double ratio = stepSize / distance;
				location.x += (int) (dx * ratio);
				location.y += (int) (dy * ratio);
			}
		}

	}
}
