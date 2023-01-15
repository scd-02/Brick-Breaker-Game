package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	/**
	 * @Sourav
	 */
	private static final long serialVersionUID = 1L;
	private boolean play = false;
	private int score = 0;

	private int totalBricks = 21;

	private Timer timer;
	private int delay = 8;

	private int playerX = 310;

	private int ballposX = 220;
	private int ballposY = 390;
	private int ballXDir = -1;
	private int ballYDir = -2;

	private MapGenerator map;

	public GamePlay() {
		map = new MapGenerator(3, 7);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		timer = new Timer(delay, this);
		timer.start();

	}

	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 685, 562);

		map.draw((Graphics2D) g);

		// border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(683, 0, 3, 592);

		// score
		g.setColor(Color.green);
		g.setFont(new Font("serif", Font.BOLD, 24));
		g.drawString("" + score, 590, 30);

		// paddle
		g.setColor(Color.cyan);
		g.fillRect(playerX, 550, 100, 8);

		// ball
		g.setColor(Color.green);
		g.fillOval(ballposX, ballposY, 20, 20);

		if (ballposY > 570) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;

			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("    Game Over Score: " + score, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("   Press Enter to Restart ", 190, 340);
		}
		if (totalBricks == 0) {
			play = false;
			ballXDir = -1;
			ballYDir = -2;

			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("     Awesome : " + score, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("   Press Enter to Restart ", 190, 340);
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYDir = -ballYDir;
			}

			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickposX = j * map.brickWidth + 80;
						int brickposY = i * map.brickHeight + 50;
						int bricksHeight = map.brickHeight;
						int bricksWidth = map.brickWidth;

						Rectangle brickrect = new Rectangle(brickposX, brickposY, bricksWidth, bricksHeight);
						Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);

						if (brickrect.intersects(ballrect)) {
							map.setBrickValue(0, i, j);
							score += 5;
							totalBricks--;

							if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
								ballXDir = -ballXDir;
							} else {
								ballYDir = -ballYDir;
							}
							break A;
						}
					}
				}

			}

			ballposX += ballXDir;
			ballposY += ballYDir;

			if (ballposX < 0) {
				ballXDir = -ballXDir;
			}
			if (ballposY < 0) {
				ballYDir = -ballYDir;
			}
			if (ballposX > 663) {
				ballXDir = -ballXDir;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 583) {
				playerX = 583;
			} else {
				moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX <= 3) {
				playerX = 3;
			} else {
				moveLeft();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				ballposX = 220;
				ballposY = 390;
				ballXDir = -1;
				ballYDir = -2;
				score = 0;
				playerX = 310;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

}
