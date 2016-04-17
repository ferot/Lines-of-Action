package grafika;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Marker extends JComponent {
	private static final long serialVersionUID = 1L;
	private Pionek markedPawn;
	private Coordinates field;
	private static Image image = new ImageIcon("marker.png").getImage();

	public Marker() {
		field= null;
		markedPawn = null;
	}

	public Marker(Pionek p, Coordinates c) {
		field = c;
		markedPawn = p;
	}

	public Marker(Coordinates c) {
		field = c;
		markedPawn = null;
	}

	public Pionek getPawn() {
		return markedPawn;
	}

	public void setPawn(Pionek pawn) {
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
		if (field.getX() >= 65 && field.getX() <= 72 && field.getY() >= 1
				&& field.getY() <= 8) {
			g.drawImage(image,
					Szachownica.getPlanszaPola()[field.getX() - 65][8 - field.getY()].getX(),
					Szachownica.getPlanszaPola()[field.getX() - 65][8 - field.getY()].getY(),
					null);
		} else {
			System.out.println("Nieprawid³owe wspó³rzêdne: [" + field.getX()
					+ ", " + field.getY() + "]");
		}
	}

}
