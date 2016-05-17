package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import engine.Coordinates;

public class Marker extends JComponent {

	private static final String MARKER_PATH = "images/marker.png";
	private static final long serialVersionUID = 1L;
	private Pawn markedPawn;
	private Coordinates field;
	private static Image image = new ImageIcon(MARKER_PATH).getImage();

	public Marker() {
		field= null;
		markedPawn = null;
	}

	public Marker(Pawn p, Coordinates c) {
		field = c;
		markedPawn = p;
	}

	public Marker(Coordinates c) {
		field = c;
		markedPawn = null;
	}

	public Pawn getPawn() {
		return markedPawn;
	}

	public void setPawn(Pawn pawn) {
		this.markedPawn = pawn;
	}

	public static Image get_Image() {
		return image;
	}

	public static void setMarker(Image marker) {
		Marker.image = marker;
	}

	public Coordinates getField() {
		return field;
	}

	public void setField(Coordinates field) {
		this.field = field;
	}

	public void paintComponent(Graphics g) {
		if (field.getX() >= 0 && field.getX() <= 7 && field.getY() >= 0
				&& field.getY() <= 7) {
			g.drawImage(image,
					Chessboard.getChessboardFields()[field.getX()][field.getY()]
							.getX(), Chessboard.getChessboardFields()[field
							.getX()][field.getY()].getY(),
					null);
		} else {
			System.out.println("Nieprawid³owe wspó³rzêdne: [" + field.getX()
					+ ", " + field.getY() + "]");
		}
	}

}
