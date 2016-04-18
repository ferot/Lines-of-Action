package engine;

import enums.PlayerColor;
import gui.Marker;
import gui.Pawn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Engine {
	
	private Engine() {}

	private static final int _A = 65;
	private static final int AMOUNT_OF_PAWNS = 12;

	private static List<Pawn> pawns;
	private static PlayerColor turn;
	private static List<Marker> marker;

	// **********************************************************
	// Inicjalizacja silnika
	// **********************************************************

	static {
		List<Pawn> whites =new ArrayList<Pawn>();
		List<Pawn> reds = new ArrayList<Pawn>();
		add(whites, PlayerColor.WHITE);
		add(reds, PlayerColor.RED);
		placePawns(whites, PlayerColor.WHITE);
		placePawns(reds, PlayerColor.RED);
		pawns = new ArrayList<Pawn>();
		pawns.addAll(whites);
		pawns.addAll(reds);
		turn = PlayerColor.RED;
		marker = new ArrayList<Marker>();
	}

	// **********************************************************
	// Publiczne metody statyczne
	// **********************************************************

	public static boolean checkPawnClicked(Coordinates pole) {
		for (Pawn pawn : pawns) {
			if (pawn.getxPos() == pole.getX() && pawn.getyPos() == pole.getY()
					&& pawn.getColor() == turn) {
				pawn.setPressed(true);
				marker.add(new Marker(pawn, pole));
				marker.addAll(getPossibleMoves(pawn));
				return true;
			}
		}
		return false;
	}

	public static void checkGameFinished() {
		// TODO metoda od fera

	}

	public static void changeTurn() {
		turn = (turn == PlayerColor.WHITE ? PlayerColor.RED : PlayerColor.WHITE);
	}

	public static Collection<? extends Marker> getPossibleMoves(Pawn pion) {
		return new ArrayList<Marker>();
		// TODO metoda od fera
	}

	public static boolean checkMove(Coordinates pole, Pawn pawn) {
		// TODO metoda od Fera
		return true;
	}

	public static Pawn checkPressed() {
		for (Pawn pawn : Engine.getPawns()) {
			if (pawn.isPressed()) {
				return pawn;
			}
		}
		return null;
	}

	public static void removeMarkers(Pawn pawn) {
		List<Marker> toDelete = new ArrayList<Marker>();
		for (Marker mark : marker) {
			if (mark.getPawn().equals(pawn)) {
				toDelete.add(mark);
			}
		}
		marker.removeAll(toDelete);
	}

	public static void move(Pawn pawn, Coordinates pole) {
		pawn.setxPos(pole.getX());
		pawn.setyPos(pole.getY());
		pawn.setPressed(false);

	}

	// ********************************************************************************
	// Metody prywatne
	// ********************************************************************************

	private static void placePawns(List<Pawn> list, PlayerColor color) {
		int i = 0;
		for (Pawn pawn : list) {
			if (color == PlayerColor.WHITE) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					pawn.setxPos(_A + 0);
					pawn.setyPos(i + 2);
				} else {
					pawn.setxPos(7 + _A);
					pawn.setyPos(i - 4);
				}
			} else if (color == PlayerColor.RED) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					pawn.setxPos(i + 1 + _A);
					pawn.setyPos(1);
				} else {
					pawn.setxPos(i - 5 + _A);
					pawn.setyPos(8);
				}
			}
			i++;
		}
	}

	private static void add(List<Pawn> list, PlayerColor color) {
		for (int i = 0; i < AMOUNT_OF_PAWNS; i++) {
			list.add(new Pawn(color));
		}
	}

	// *************************************************************
	// Metody do udostêpnienia danych
	// *************************************************************

	public static List<Pawn> getPawns() {
		return pawns;
	}

	public static PlayerColor getTurn() {
		return turn;
	}

	public static List<Marker> getMarker() {
		return marker;
	}

}
