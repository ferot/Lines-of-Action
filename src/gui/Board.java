package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Coordinates;
import engine.Engine;

public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int _A = 65;
	private static final int WIDTH = Chessboard.getPlansza().getIconWidth();
	private static final int HEIGHT = Chessboard.getPlansza().getIconWidth();
	protected static final int POLE_1_X = 12;
	protected static final int POLE_1_Y = 397;
	private ImageIcon plansza;

	private static int poleXSize;
	private static int poleYSize;

	public Board() {
		super();
		plansza = Chessboard.getPlansza();
		poleXSize = (HEIGHT - 22) / 8;
		poleYSize = (WIDTH - 22) / 8;
		setDoubleBuffered(true);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		addMouseListener(newMouseListener());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(plansza.getImage(), 0, 0, null);
		for (Pawn pion : Engine.getPawns()) {
			pion.paintComponent(g2d);
		}
		for (Marker mark : Engine.getMarker()) {
			mark.paintComponent(g2d);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// FIXME nie wiem czy to jest potrzebne
	}

	public MouseAdapter newMouseListener() {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Coordinates pole = translateClickToPolePlanszy(e.getX(), e.getY());
				if (pole.getX()<_A || pole.getX() >= _A +8 || 
						pole.getY()<=0 || pole.getY() >8){
					return;
				}
				Pawn pawn = Engine.checkPressed();
				if (pawn != null) {
					if (Engine.checkMove(pole, pawn)) {
						pawn.setxPos(pole.getX());
						pawn.setyPos(pole.getY());
						Engine.removeMarkers(pawn);
						System.out.println(pole.getX() + ", " + pole.getY());
						pawn.setPressed(false);
						Engine.changeTurn();
						Engine.checkGameFinished();
						Board.this.repaint();
						return;
					 }
				}
				Engine.checkPawnClicked(pole);
				Board.this.repaint();
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

}
