package gui;

import javax.swing.ImageIcon;

import engine.Coordinates;

public class Chessboard {

	private final static ImageIcon PLANSZA = new ImageIcon("szachownica.png");
	private final static int PLANSZA_POLA_SIZE = 8;
	private static final int MARGIN_WIDTH = 11;
	private static final int MARGIN_HEIGHT = 11;
	private static final int WIDTH = PLANSZA.getIconWidth();
	private static final int HEIGHT = PLANSZA.getIconWidth();

	private static Coordinates planszaPola[][];

	public static ImageIcon getPlansza() {
		return PLANSZA;
	}

	public static Coordinates[][] getPlanszaPola() {
		return planszaPola;
	}

	public static void setPlanszaPola(Coordinates planszaPola[][]) {
		Chessboard.planszaPola = planszaPola;
	}

	static {
		setPlanszaPola(new Coordinates[PLANSZA_POLA_SIZE][PLANSZA_POLA_SIZE]);
		int height = HEIGHT - 2 * MARGIN_HEIGHT;
		int width = WIDTH - 2 * MARGIN_WIDTH;
		for (int j = 0; j < PLANSZA_POLA_SIZE; j++) {
			for (int i = 0; i < PLANSZA_POLA_SIZE; i++) {
				planszaPola[j][i] = new Coordinates(MARGIN_HEIGHT + 2 + j
						* height / PLANSZA_POLA_SIZE,
						i * width / PLANSZA_POLA_SIZE + MARGIN_WIDTH + 2);
			}
		}
	}
}
