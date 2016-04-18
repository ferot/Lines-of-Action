package engine;

import enums.Kolor;
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
	private static Kolor turn;
	private static List<Marker> marker;

	// **********************************************************
	// Inicjalizacja silnika
	// **********************************************************

	static {
		List<Pawn> biale =new ArrayList<Pawn>();
		List<Pawn> czerwone = new ArrayList<Pawn>();
		add(biale, Kolor.Bialy);
		add(czerwone, Kolor.Czerwony);
		placePawns(biale, Kolor.Bialy);
		placePawns(czerwone, Kolor.Czerwony);
		pawns = new ArrayList<Pawn>();
		pawns.addAll(biale);
		pawns.addAll(czerwone);
		turn = Kolor.Czerwony;
		marker = new ArrayList<Marker>();
	}

	// **********************************************************
	// Publiczne metody statyczne
	// **********************************************************

	public static boolean checkPawnClicked(Coordinates pole) {
		for (Pawn pawn : pawns) {
			if (pawn.getxPos() == pole.getX() && pawn.getyPos() == pole.getY()
					&& pawn.getKolor() == turn) {
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
		turn = (turn == Kolor.Bialy ? Kolor.Czerwony : Kolor.Bialy);
	}

	public static Collection<? extends Marker> getPossibleMoves(Pawn pion) {
		pion.getxPos();
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

	// ********************************************************************************
	// Metody prywatne
	// ********************************************************************************

	private static void placePawns(List<Pawn> list, Kolor kolor) {
		int i = 0;
		for (Pawn pion : list) {
			if (kolor == Kolor.Bialy) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					pion.setxPos(_A + 0);
					pion.setyPos(i + 2);
				} else {
					pion.setxPos(7 + _A);
					pion.setyPos(i - 4);
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

	private static void add(List<Pawn> list, Kolor kolor) {
		for (int i = 0; i < AMOUNT_OF_PAWNS; i++) {
			list.add(new Pawn(kolor));
		}
	}

	// *************************************************************
	// Metody do udostêpnienia danych
	// *************************************************************

	public static List<Pawn> getPawns() {
		return pawns;
	}

	public static Kolor getTurn() {
		return turn;
	}

	public static List<Marker> getMarker() {
		return marker;
	}

}
