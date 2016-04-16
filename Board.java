package board;

import enums.Kolor;
import grafika.Pionek;
import grafika.Szachownica;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

	private static final int _A = 65;
	/**
	 * 
	 */
	private static final int AMOUNT_OF_PAWNS = 12;
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = Szachownica.getPlansza().getIconWidth();
	private static final int HEIGHT = Szachownica.getPlansza().getIconWidth();
	private ImageIcon plansza;
	private List<Pionek> biale;
	private List<Pionek> czerwone;
	private Timer timer;

	public Board() {
		super();
		plansza = Szachownica.getPlansza();
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
			addMouseListener(pion.getListener());
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
}
