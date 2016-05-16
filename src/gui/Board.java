package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Coordinates;
import engine.Engine;

public class Board extends JPanel {

	private static final int FRAME_SIZE = 22;
	private static final long serialVersionUID = 1L;
	private static final int _A = 65;
	private static final int WIDTH = Chessboard.getChessboard().getIconWidth();
	private static final int HEIGHT = Chessboard.getChessboard().getIconWidth();
	private static final int FIELD_1_X = 12;
	private static final int FIELD_1_Y = 397;

	private ImageIcon chessboard;
	private static int fieldXSize;
	private static int fieldYSize;

	public Board() {
		super();
		chessboard = Chessboard.getChessboard();
		fieldXSize = (HEIGHT - FRAME_SIZE) / 8;
		fieldYSize = (WIDTH - FRAME_SIZE) / 8;
		setDoubleBuffered(true);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		addMouseListener(newMouseListener());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(chessboard.getImage(), 0, 0, null);
		for (Pawn pawn : Engine.getPawns()) {
			pawn.paintComponent(g2d);
		}
		for (Marker mark : Engine.getMarker()) {
			mark.paintComponent(g2d);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public MouseAdapter newMouseListener() {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Coordinates field = translateClickToBoardField(e.getX(), e.getY());
				if (field.getX()<_A || field.getX() >= _A +8 || 
						field.getY()<=0 || field.getY() >8){
					return;
				}
				Pawn pawn = Engine.checkPressed();
				if (pawn != null) {
					if (Engine.checkMove(field, pawn)) {
						Engine.move(pawn, field);
						Engine.removeMarkers(pawn);
						Engine.checkGameFinished();
						Engine.changeTurn();
						Board.this.repaint();
						return;
					 }
				}
				Engine.checkPawnClicked(field);
				Board.this.repaint();
			}

			private Coordinates translateClickToBoardField(int x, int y) {
				int xx = 0, yy = 0;
				for (int i = 0; i < 8; i++){
					if (x >= (FIELD_1_X + i * fieldXSize) && x <= (FIELD_1_X + (i+1)*fieldXSize)){
						xx = _A + i;
						break;
					}
				}
				for (int i = 0; i < 8; i++) {
					if (y >= (FIELD_1_Y- i* fieldYSize ) && y <= (FIELD_1_Y - (i-1) * fieldYSize)) {
						yy = i + 1;
						break;
					}
				}
				return new Coordinates(xx, yy);
			}
		};

	}

}
