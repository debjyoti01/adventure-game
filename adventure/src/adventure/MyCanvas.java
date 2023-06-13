package adventure;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyCanvas extends JPanel {
	List<VisibleObjects> objects = new ArrayList<VisibleObjects>();
	Image background = null;
	MyAudioPlayer audioPlayer = new MyAudioPlayer();

	public MyCanvas() {
		// load the background image
		background = new ImageIcon(getClass().getResource("/res/img/backgrounds/game_background.jpg")).getImage();
		
		//load the background sound
		audioPlayer.play("opening", true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		for (VisibleObjects o : objects) {
			o.display(g);
		}
	}
}
