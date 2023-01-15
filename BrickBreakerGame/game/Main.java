package game;

import javax.swing.JFrame;

public class Main {

	/**
	 * @Sourav
	 */
	public static void main(String[] args) {

		JFrame f = new JFrame();
		GamePlay gamePlay = new GamePlay();

		f.setBounds(10, 10, 700, 600);
		f.setTitle("Brick Breaker Game");
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(gamePlay);

	}

}
