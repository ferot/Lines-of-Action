package gui;

import javax.swing.ImageIcon;

import engine.Coordinates;

public class Chessboard {

	private static final String CHESSBOARD_PATH = "images/szachownica.png";
	private final static ImageIcon CHESSBOARD = new ImageIcon(CHESSBOARD_PATH);
	private final static int CHESSBOARD_FIELD_SIZE = 8;
	private static final int MARGIN_WIDTH = 11;
	private static final int MARGIN_HEIGHT = 11;
	private static final int WIDTH = CHESSBOARD.getIconWidth();
	private static final int HEIGHT = CHESSBOARD.getIconWidth();

	private static Coordinates chessboardFields[][];

	public static ImageIcon getChessboard() {
		return CHESSBOARD;
	}

	public static Coordinates[][] getChessboardFields() {
		return chessboardFields;
	}

	public static void setChessboardFields(Coordinates chessboardFds[][]) {
		Chessboard.chessboardFields = chessboardFds;
	}

	static {
		setChessboardFields(new Coordinates[CHESSBOARD_FIELD_SIZE][CHESSBOARD_FIELD_SIZE]);
		int height = HEIGHT - 2 * MARGIN_HEIGHT;
		int width = WIDTH - 2 * MARGIN_WIDTH;
		for (int j = 0; j < CHESSBOARD_FIELD_SIZE; j++) {
			for (int i = 0; i < CHESSBOARD_FIELD_SIZE; i++) {
				chessboardFields[j][i] = new Coordinates(MARGIN_HEIGHT + 2 + j
						* height / CHESSBOARD_FIELD_SIZE, i * width
						/ CHESSBOARD_FIELD_SIZE + MARGIN_WIDTH + 2);
			}
		}
	}
}
