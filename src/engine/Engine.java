package engine;

import enums.MoveDirection;
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

	private static List<Pawn> whites;
	private static List<Pawn> reds;
	private static List<Pawn> pawns;
	private static PlayerColor turn;
	private static List<Marker> marker;

	// **********************************************************
	// Inicjalizacja silnika
	// **********************************************************

	static {
		whites = new ArrayList<Pawn>();
		reds = new ArrayList<Pawn>();
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
		// TODO Obsluga wyst¹pienia zdarzenia tam gdzie return
		if (whites.size() <= 1)
			return;
		if (reds.size() <= 1)
			return;
		// for (Pawn pawn : whites) {
		//
		// }
	}

	public static void changeTurn() {
		turn = (turn == PlayerColor.WHITE ? PlayerColor.RED : PlayerColor.WHITE);
	}

	public static Collection<? extends Marker> getPossibleMoves(Pawn pion) {
//		short int Board::count_move(Pawn A,Dist_type type){
//			short int dist=0;
//
//			Point temp=A.p.map_coords();
//			
		for (MoveDirection direction: MoveDirection.values()){
			switch (direction) {
			case VERTICAL:
				for (int j = 1; j <= 8; j++) {
					if(	brd[temp.y][j]!=' ')
						dist++;
				}

				return dist;

			case h:
				for(int j=0;j<8;j++){
					if(	brd[j][temp.x]!=' ')
						dist++;
				}
				return dist;
			case dl:// "\"
				int x=temp.y;
				int y=temp.x;
				x--;
				y--;

				//check upper-left corner
				while(!out_of_boundary(x,y)){
					if((brd[y][x]!=' '))
						dist++;
					x--;
					y--;
				}
				x=temp.y;
				y=temp.x;
				x++;
				y++;
				//check down-right corner
				while(!out_of_boundary(x,y)){
					if((brd[y][x]!=' '))
						dist++;
					x++;
					y++;
				}
				return (++dist);//remember to count the pawn (itself)!!
			case dr:// "/"
				int x2=temp.y;
				int y2=temp.x;
				x2++;
				y2++;

				//check upper-right corner
				while(!out_of_boundary(x2,y2)){
					if((brd[y2][x2]!=' '))
						dist++;
					x2++;
					y2++;
				}
				x2=temp.y;
				y2=temp.x;
				x2--;
				y2--;
				//check down-left corner
				while(!out_of_boundary(x2,y2)){
					if((brd[y2][x2]!=' '))
						dist++;
					x2--;
					y2--;
				}
				return (++dist);//remember to count the pawn!!
				return 0;
			default :
				return 0;
			}
		}
		return new ArrayList<Marker>();
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
