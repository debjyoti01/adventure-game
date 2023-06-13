package adventure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class MyFrame extends JFrame implements ActionListener, KeyListener {

	MyCanvas canvas = new MyCanvas();
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	Hero hero;
	ArrayList<Tile> tileList = new ArrayList<>();
	ArrayList<Coin> coinList = new ArrayList<>();
	ArrayList<Life> lifeList = new ArrayList<>();
	ArrayList<Villen> villenList = new ArrayList<>();
	ArrayList<VillenBullets> villenBulletList = new ArrayList<>();
	ArrayList<HeroBullets> heroBulletList = new ArrayList<>();
	ArrayList<VillenDieAnimation> villenDieAnimationList = new ArrayList<>();

	int collectCoinCount = 0;
	int killVillenCount = 0;
	int count = 0;
	int[][] pos = CSVRead.read("/src/res/csv/world-map.csv");
	Timer timerTileColision;
	Timer timerHeroFall;
	boolean flag = true;
	boolean gameEndFlag = false;

	public MyFrame() {
		setTitle("My Game");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		hero = new Hero(new Point((int) (d.getHeight() / 20) * 3, 100));
		canvas.objects.add(hero);

		// adding tile coin and villen
		for (int i = 0; i < pos.length; i++) {
			for (int j = 0; j < pos[0].length; j++) {
				if (pos[i][j] == 1 || pos[i][j] == 2) {
					Tile temp = new Tile((new Point(j * (int) (d.getHeight() / 20), i * (int) (d.getHeight() / 20))),
							pos[i][j]);
					canvas.objects.add(temp);
					tileList.add(temp);
				}

				if (pos[i][j] == 100) {
					Coin coin = new Coin((new Point(j * (int) (d.getHeight() / 20), i * (int) (d.getHeight() / 20))),
							1);
					canvas.objects.add(coin);
					coinList.add(coin);
				}

				if (pos[i][j] == 200) {
					Villen villen = new Villen(
							(new Point(j * (int) (d.getHeight() / 20), i * (int) (d.getHeight() / 20))), 1);
					canvas.objects.add(villen);
					villenList.add(villen);

					VillenBullets villenBullets = new VillenBullets(
							(new Point(j * (int) (d.getHeight() / 20), (i + 1) * (int) (d.getHeight() / 20))),
							villen.location);
					villen.villenBullet = villenBullets;
					canvas.objects.add(villenBullets);
					villenBulletList.add(villenBullets);
				}

			}
		}

		// adding hero's life
		for (int i = 0; i < 3; i++) {
			Life life = new Life((new Point((i + 1) * (int) (d.getHeight() / 20), (int) (d.getHeight() / 20))));
			canvas.objects.add(life);
			lifeList.add(life);
		}

		add(canvas);

		colisionTile();
		coinCollect();
		heroBulletHitVillen();
		villenBulletHitHero();
		addKeyListener(this);
		Timer timer = new Timer(100, this);
		timer.start();
	}

	// main method
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame f = new MyFrame();
		f.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();

		if (!gameEndFlag)
			if (keyCode == KeyEvent.VK_LEFT) {
				boolean lflag = true;
				if (count > 0) {
					// check that if hero goes left it hit with tile or not and set the lflag accordingly 
					for (int i = 0; i < tileList.size(); i++) {
						if ((tileList.get(i).location.x + (int) (d.getHeight() / 20)) == (hero.location.x)
								&& ((tileList.get(i).location.y > hero.location.y
										&& tileList.get(i).location.y < (hero.location.y + (int) (d.getHeight() / 10)))
										|| ((tileList.get(i).location.y + (d.getHeight() / 20)) > hero.location.y
												&& (tileList.get(i).location.y
														+ (d.getHeight() / 20)) < (hero.location.y
																+ (int) (d.getHeight() / 10))))) {
							lflag = false;
							break;
						}
					}
					// if left move is possible
					if (lflag) {
						count--;
						// move Coin
						for (int i = 0; i < coinList.size(); i++) {
							coinList.get(i).location.x += (int) (d.getHeight() / 20);
						}
						// move Villen
						for (int i = 0; i < villenList.size(); i++) {
							villenList.get(i).location.x += (int) (d.getHeight() / 20);
						}
						// move Villen Die animation
						for (int i = 0; i < villenDieAnimationList.size(); i++) {
							villenDieAnimationList.get(i).location.x += (int) (d.getHeight() / 20);
						}
						// move Villen Bullets
						for (int i = 0; i < villenBulletList.size(); i++) {
							villenBulletList.get(i).location.x += (int) (d.getHeight() / 20);
						}
						// move Hero Bullets
						for (int i = 0; i < heroBulletList.size(); i++) {
							heroBulletList.get(i).location.x += (int) (d.getHeight() / 20);
							heroBulletList.get(i).initialLocation.x += (int) (d.getHeight() / 20);
						}

						moveTile();

					}
					lflag = true;
				}
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				boolean rflag = true;
				for (int i = 0; i < tileList.size(); i++) {
					// check that if hero goes right it hit with tile or not and set the rflag accordingly 
					if ((tileList.get(i).location.x) == (hero.location.x + (int) (d.getHeight() / 10))
							&& ((tileList.get(i).location.y > hero.location.y
									&& tileList.get(i).location.y < (hero.location.y + (int) (d.getHeight() / 10)))
									|| ((tileList.get(i).location.y + (d.getHeight() / 20)) > hero.location.y
											&& (tileList.get(i).location.y + (d.getHeight() / 20)) < (hero.location.y
													+ (int) (d.getHeight() / 10))))) {
						rflag = false;
						break;
					}
				}
				// if right move is possible
				if (rflag) {
					count++;
					// move Coin
					for (int i = 0; i < coinList.size(); i++) {
						coinList.get(i).location.x -= (int) (d.getHeight() / 20);
					}
					// move Villen
					for (int i = 0; i < villenList.size(); i++) {
						villenList.get(i).location.x -= (int) (d.getHeight() / 20);
					}
					// move Villen Bullets
					for (int i = 0; i < villenBulletList.size(); i++) {
						villenBulletList.get(i).location.x -= (int) (d.getHeight() / 20);
					}
					// move Villen Die animation
					for (int i = 0; i < villenDieAnimationList.size(); i++) {
						villenDieAnimationList.get(i).location.x -= (int) (d.getHeight() / 20);
					}
					// move Hero Bullets
					for (int i = 0; i < heroBulletList.size(); i++) {
						heroBulletList.get(i).location.x -= (int) (d.getHeight() / 20);
						heroBulletList.get(i).initialLocation.x -= (int) (d.getHeight() / 20);
					}

					moveTile();

				}
			} else if (keyCode == KeyEvent.VK_UP) {
				if (flag) {
					boolean uFlag = true;
					MyAudioPlayer heroDie = new MyAudioPlayer();
					heroDie.play("hero-jump", false);
					for (int i = 0; i < tileList.size(); i++) {
						if ((tileList.get(i).location.x <= (hero.location.x + (int) (d.getHeight() / 20)))
								&& ((tileList.get(i).location.x + (int) (d.getHeight() / 20)) > hero.location.x)) {
							int tempY = (hero.location.y) - 3 * (int) (d.getHeight() / 10);
							// if after up move hero hit the upper wall set jump height according to the wall(short jump)
							if ((tileList.get(i).location.y + (int) (d.getHeight() / 20)) >= tempY
									&& (tileList.get(i).location.y + (int) (d.getHeight() / 20)) <= (hero.location.y)) {
								flag = false;
								tempY = hero.location.y;
								hero.location.y = (tileList.get(i).location.y + (int) (d.getHeight() / 20));
								hero.tarY = tempY;
								uFlag = false;
								if (hero.location.y >= tileList.get(i).location.y)
									flag = true;
								break;
							}
						}
					}
					if (uFlag)
						hero.location.y -= 3 * (int) (d.getHeight() / 10);
				}
			}
		canvas.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		// hero fire bullet
		if (keyCode == KeyEvent.VK_SPACE) {
			HeroBullets heroBullet = new HeroBullets(new Point(hero.location.x + (int) (d.getHeight() / 10),
					hero.location.y + (int) (d.getHeight() / 20)));
			MyAudioPlayer audioPlayer = new MyAudioPlayer();
			audioPlayer.play("bullet", false);
			heroBulletList.add(heroBullet);
			canvas.objects.add(heroBullet);
		}
	}

	// check the tile collied with hero and hero's bullet
	public void colisionTile() {
		timerTileColision = new Timer(30, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tileList.size(); i++) {
					// bullet hit tile
					for (int j = 0; j < heroBulletList.size(); j++) {
						if ((heroBulletList.get(j).location.x
								+ 2 * (int) (d.getHeight() / 70)) >= tileList.get(i).location.x
								&& (heroBulletList.get(j).location.x
										+ 2 * (int) (d.getHeight() / 70)) <= tileList.get(i).location.x
												+ (int) (d.getHeight() / 20)) {
							if ((heroBulletList.get(j).location.y
									+ (int) (d.getHeight() / 70)) > tileList.get(i).location.y
									&& (heroBulletList.get(j).location.y) < (tileList.get(i).location.y)
											+ (int) (d.getHeight() / 20)) {
								canvas.objects.remove(heroBulletList.get(j));
								heroBulletList.remove(j);
								break;
							}

						}
					}

					// hero hit the tile
					if ((tileList.get(i).location.x <= (hero.location.x + (int) (d.getHeight() / 20)))
							&& ((tileList.get(i).location.x + (int) (d.getHeight() / 20)) > hero.location.x)) {
						if (tileList.get(i).location.y >= (hero.location.y + (int) (d.getHeight() / 10))) {
							flag = false;
							hero.tarY = tileList.get(i).location.y - (int) (d.getHeight() / 10);
							if (hero.location.y >= tileList.get(i).location.y - (int) (d.getHeight() / 10)) {
								flag = true;
								if (tileList.get(i).value == 2) {
									lifeList.clear();
									checkGameOver();
								}
							}

							break;
						}
					}
				}
			}

		});

		timerTileColision.start();
	}

	// coin collection by hero
	public void coinCollect() {
		timerTileColision = new Timer(30, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < coinList.size(); i++) {
					if ((coinList.get(i).location.x) == (hero.location.x)
							&& ((coinList.get(i).location.y > hero.location.y
									&& coinList.get(i).location.y < (hero.location.y + (int) (d.getHeight() / 10)))
									|| ((coinList.get(i).location.y + (d.getHeight() / 20)) > hero.location.y
											&& (coinList.get(i).location.y + (d.getHeight() / 20)) < (hero.location.y
													+ (int) (d.getHeight() / 10))))) {
						MyAudioPlayer collectCoin = new MyAudioPlayer();
						collectCoin.play("collect-coin", false);
						collectCoinCount++;
						canvas.objects.remove(coinList.get(i));
						coinList.remove(i);
						canvas.repaint();
						break;
					}

					if ((coinList.get(i).location.x) == (hero.location.x + (int) (d.getHeight() / 20))
							&& ((coinList.get(i).location.y > hero.location.y
									&& coinList.get(i).location.y < (hero.location.y + (int) (d.getHeight() / 10)))
									|| ((coinList.get(i).location.y + (d.getHeight() / 20)) > hero.location.y
											&& (coinList.get(i).location.y + (d.getHeight() / 20)) < (hero.location.y
													+ (int) (d.getHeight() / 10))))) {
						MyAudioPlayer collectCoin = new MyAudioPlayer();
						collectCoin.play("collect-coin", false);
						canvas.objects.remove(coinList.get(i));
						coinList.remove(i);
						canvas.repaint();
						break;
					}
				}
			}

		});

		timerTileColision.start();
	}

	// villens bullet hit heto then respawn hero add sound and decrease hero's life
	public void villenBulletHitHero() {
		Timer timerBulletColision = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < villenBulletList.size(); i++) {
					//checking bullet hit the hero or not
					if (hero.location.y <= villenBulletList.get(i).location.y
							&& (hero.location.y + (int) (d.getHeight() / 10)) > villenBulletList.get(i).location.y) {
						if (hero.location.x <= villenBulletList.get(i).location.x && (hero.location.x
								+ (int) (d.getHeight() / 10)) > villenBulletList.get(i).location.x) {

							villenBulletList.get(i).location.x = villenList.get(i).location.x;
							// if hero is not already in respawn stage
							if (!hero.respawn) {
								canvas.objects.remove(lifeList.get(lifeList.size() - 1));
								lifeList.remove(lifeList.size() - 1);
								
								// this method add sound
								checkGameOver();
								
								hero.respawn = true;
								hero.respawnTime = 200;
							}
							break;
						}
					}
				}

			}

		});

		timerBulletColision.start();
	}

	// hero's bullet hit villen, villen delete from villen list, canvas and sound created and animation added, increment the kill count
	// hero's bullets distance validation from initial position
	public void heroBulletHitVillen() {
		Timer timerBulletColision = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < heroBulletList.size(); i++) {
					for (int j = 0; j < villenList.size(); j++) {
						if (heroBulletList.get(i).location.y > villenList.get(j).location.y
								&& (heroBulletList.get(i).location.y < villenList.get(j).location.y
										+ (int) (d.getHeight() / 10))) {
							if ((heroBulletList.get(i).location.x
									+ (int) (d.getHeight() / 70)) > villenList.get(j).location.x
									&& (heroBulletList.get(i).location.x
											+ (int) (d.getHeight() / 70)) < (villenList.get(j).location.x
													+ (int) (d.getHeight() / 10))) {
								canvas.objects.remove(villenBulletList.get(j));
								villenBulletList.remove(j);
								
								// adding villen killing sound
								MyAudioPlayer villenDie = new MyAudioPlayer();
								villenDie.play("villen-die", false);
								
								// villen die animation added
								VillenDieAnimation tempVillenDieAnimation = new VillenDieAnimation(
										villenList.get(j).location);
								
								// delete villen form canvas and villen list
								villenDieAnimationList.add(tempVillenDieAnimation);
								canvas.objects.add(tempVillenDieAnimation);
								canvas.objects.remove(villenList.get(j));
								villenList.remove(j);
								canvas.objects.remove(heroBulletList.get(i));
								
								// increment the kill count 
								killVillenCount++;
								
							}
						}

					}
					
					// checking the hero's bullets distance from initial position
					// if distance is more the the particular range then delete the bullet
					if ((heroBulletList.get(i).initialLocation.x
							+ 15 * (int) (d.getHeight() / 20)) <= heroBulletList.get(i).location.x) {
						canvas.objects.remove(heroBulletList.get(i));
						heroBulletList.remove(i);
						break;
					}

				}

				// delete villen kill animation
				for (int i = 0; i < villenDieAnimationList.size(); i++) {
					villenDieAnimationList.get(i).lifeTime--;
					if (villenDieAnimationList.get(i).lifeTime < 0) {
						canvas.objects.remove(villenDieAnimationList.get(i));
						villenDieAnimationList.remove(i);
						break;
					}
				}
			}

		});

		timerBulletColision.start();
	}

	// move the tile when hero is moving
	public void moveTile() {
		for (int i = 0; i < tileList.size(); i++) {
			canvas.objects.remove(tileList.get(i));
		}

		tileList.clear();

		for (int i = 0; i < pos.length; i++) {
			for (int j = count, k = 0; j < pos[i].length
					&& k < ((int) (d.getWidth() / (int) (d.getHeight() / 20)) + 2); j++, k++) {
				if (pos[i][j] == 1 || pos[i][j] == 2) {
					Tile temp = new Tile(
							(new Point((j - count) * (int) (d.getHeight() / 20), i * (int) (d.getHeight() / 20))),
							pos[i][j]);
					canvas.objects.add(temp);
					tileList.add(temp);
				}
				// if reaches at the end of the world map
				if (pos[i][j] == 3) {
					gameWin();
				}
			}
		}
		canvas.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		canvas.repaint();
	}

	// check game over or not
	public void checkGameOver() {

		if (gameEndFlag == false) {

			// play the hero die sound
			MyAudioPlayer heroDie = new MyAudioPlayer();
			heroDie.play("hero-die", false);
			if (lifeList.size() <= 0) {
				// delete hero from game
				gameEndFlag = true;
				canvas.objects.remove(hero);

				// display the game lose and score board
				JFrame f = new JFrame("Game Over");
				f.setSize(600, 400);

				JTextArea textArea = new JTextArea(5, 40);
				textArea.setLineWrap(true);
				textArea.setText("\n\t    GAME OVER" + "\n\n\tCoin Collected: " + collectCoinCount
						+ "\n\tVillen Killed:  " + killVillenCount + "\n\tTotal Score:    "
						+ (2 * collectCoinCount + 5 * killVillenCount));

				Font font = new Font("Monospaced", Font.BOLD, 30);
				textArea.setFont(font);
				textArea.setForeground(Color.BLUE);
				textArea.setEditable(false);
				JScrollPane scrollPane = new JScrollPane(textArea);

				f.add(scrollPane, BorderLayout.CENTER);

				f.setVisible(true);
			}

		}

	}

	public void gameWin() {

		if (gameEndFlag == false) {

			// play the hero die sound
			MyAudioPlayer winGame = new MyAudioPlayer();
			winGame.play("win-game", false);
			
			// delete hero from game
			gameEndFlag = true;
			canvas.objects.remove(hero);

			// display the game win and score board
			JFrame f = new JFrame("Game Win");
			f.setSize(600, 400);

			JTextArea textArea = new JTextArea(5, 40);
			textArea.setLineWrap(true);
			textArea.setText("\n\t     VICTORY" + "\n\n\tCoin Collected: " + collectCoinCount + "\n\tVillen Killed:  "
					+ killVillenCount + "\n\tTotal Score:    " + (2 * collectCoinCount + 5 * killVillenCount));

			Font font = new Font("Monospaced", Font.BOLD, 30);
			textArea.setFont(font);
			textArea.setForeground(Color.BLUE);
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);

			f.add(scrollPane, BorderLayout.CENTER);

			f.setVisible(true);

		}

	}

}
