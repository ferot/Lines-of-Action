package gui;

import javax.swing.JFrame;

public class Launcher extends JFrame {

	private static final int WIDTH_FIX = 5;
	private static final int HEIGHT_FIX = 27;
	private final static String GAME_NAME = "Lines of Action";

	public Launcher() {
		super(GAME_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Launcher launch = new Launcher();
		launch.setSize((int) (Chessboard.getPlansza().getImage()
				.getWidth(null) + WIDTH_FIX), (int) (Chessboard.getPlansza()
				.getImage().getHeight(null) + HEIGHT_FIX));
		launch.add(new Board());
		launch.setResizable(false);
		launch.repaint();
	}

}
