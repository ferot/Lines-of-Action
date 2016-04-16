package board;

import enums.Kolor;
import grafika.Coordinates;
import grafika.Pionek;
import grafika.Szachownica;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

	private static final int _A = 65;
	private static final int AMOUNT_OF_PAWNS = 12;
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = Szachownica.getPlansza().getIconWidth();
	private static final int HEIGHT = Szachownica.getPlansza().getIconWidth();
	protected static final int POLE_1_X = 12;
	protected static final int POLE_1_Y = 397;
	private ImageIcon plansza;
	private List<Pionek> biale;
	private List<Pionek> czerwone;
	private Timer timer;
	private static int poleXSize;
	private static int poleYSize;

	public Board() {
		super();
		plansza = Szachownica.getPlansza();
		poleXSize = (HEIGHT - 22) / 8;
		poleYSize = (WIDTH - 22) / 8;
		setDoubleBuffered(true);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		biale = new ArrayList<Pionek>();
		czerwone = new ArrayList<Pionek>();
		add(biale, Kolor.Bialy);
		add(czerwone, Kolor.Czerwony);
		placePawns(biale, Kolor.Bialy);
		placePawns(czerwone, Kolor.Czerwony);
		timer = new Timer(20, this);
		timer.start();
		addMouseListener(newMouseListener());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(plansza.getImage(), 0, 0, null);
		for (Pionek pion : biale) {
			pion.paintComponent(g2d);
		}
		for (Pionek pion : czerwone) {
			pion.paintComponent(g2d);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void placePawns(List<Pionek> list, Kolor kolor) {
		int i = 0;
		for (Pionek pion : list) {
			if (kolor == Kolor.Bialy) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					pion.setyPos(i + 2);
					pion.setxPos(_A + 0);
				} else {
					pion.setyPos(i - 4);
					pion.setxPos(7 + _A);
				}
			} else if (kolor == Kolor.Czerwony) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					pion.setxPos(i + 1 + _A);
					pion.setyPos(1);
				} else {
					pion.setxPos(i - 5 + _A);
					pion.setyPos(8);
				}
			}
			i++;
		}
	}

	private void add(List<Pionek> list, Kolor kolor) {
		for (int i = 0; i < AMOUNT_OF_PAWNS; i++) {
			list.add(new Pionek(kolor));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread(new Runnable() {
			public void run() {
				repaint();
			}
		});

	}

	public MouseAdapter newMouseListener() {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Coordinates pole = translateClickToPolePlanszy(e.getX(), e.getY());
				if (pole.getX()<_A || pole.getX() >= _A +8 || 
						pole.getY()<=0 || pole.getY() >8){
					return;
				}
				Pionek pawn = checkPressed();
				if (pawn != null) {
					if (checkMove(pole, pawn)) { // TODO metoda od Fera
						pawn.setxPos(pole.getX());
						pawn.setyPos(pole.getY());
						System.out.println(pole.getX() + ", " + pole.getY());
						pawn.setPressed(false);
						Board.this.repaint();
						return;
					 }
				}
				checkPawnClicked(pole);
				
			}

			private boolean checkMove(Coordinates pole, Pionek pawn) {
				return true;
			}

			private Pionek checkPressed() {
				for (Pionek pawn : biale) {
					if (pawn.isPressed()) {
						return pawn;
					}
				}
				for (Pionek pawn : czerwone) {
					if (pawn.isPressed()) {
						return pawn;
					}
				}
				return null;
			}

			private Coordinates translateClickToPolePlanszy(int x, int y) {
				int xx = 0, yy = 0;
				for (int i = 0; i < 8; i++){
					if (x >= (POLE_1_X + i * poleXSize) && x <= (POLE_1_X + (i+1)*poleXSize)){
						xx = _A + i;
						break;
					}
				}
				for (int i = 0; i < 8; i++) {
					if (y >= (POLE_1_Y- i* poleYSize ) && y <= (POLE_1_Y - (i-1) * poleYSize)) {
						yy = i + 1;
						break;
					}
				}
				return new Coordinates(xx, yy);
			}
		};

	}

	protected boolean checkPawnClicked(Coordinates pole) {
		for (Pionek pawn : biale){
			if (pawn.getxPos() == pole.getX() && pawn.getyPos() == pole.getY()){
				pawn.setPressed(true);
				return true;
			}
		}
		for (Pionek pawn : czerwone){
			if (pawn.getxPos() == pole.getX() && pawn.getyPos() == pole.getY()){
				pawn.setPressed(true);
				return true;
			}
		}
		return false;
	}

}
