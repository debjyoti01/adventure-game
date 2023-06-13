package adventure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Tile implements VisibleObjects {
	Point location;
	Image tileBrick = new ImageIcon(getClass().getResource("/res/img/tile/brick.png")).getImage();
	Image tileWater = new ImageIcon(getClass().getResource("/res/img/tile/water.png")).getImage();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	int value;

	public Tile(Point location, int value) {
		this.location = location;
		this.value = value;
	}

	@Override
	public void display(Graphics g) {
		// TODO Auto-generated method stub
		
		// print the tile according to the matrix values
		if (value == 1)
			g.drawImage(tileBrick, location.x, location.y, (int) (d.getHeight() / 20), (int) (d.getHeight() / 20),
					null);
		if (value == 2)
			g.drawImage(tileWater, location.x, location.y, (int) (d.getHeight() / 20), (int) (d.getHeight() / 20),
					null);
	}
}
