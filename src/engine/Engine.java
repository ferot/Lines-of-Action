package engine;

import enums.MoveDirection;
import enums.PlayerColor;
import gui.Marker;
import gui.Pawn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Engine {

	private Engine() {
	}

	private static final int _A = 65;
	private static final int AMOUNT_OF_PAWNS = 12;
	private static char[][] brd;
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
		initBoard();
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

	public static boolean checkPawnClicked(Coordinates field) {
		Pawn pawn = getPawn(field);
		if (pawn != null && pawn.getColor() == turn) {
			pawn.setPressed(true);
			marker.add(new Marker(pawn, field));
			marker.addAll(getPossibleMoves(pawn));
			return true;
			}
		return false;
	}

	private static Pawn getPawn(Coordinates field) {
		for (Pawn pawn : pawns) {
			if (pawn.getxPos() == field.getX()
					&& pawn.getyPos() == field.getY()) {
				return pawn;
			}
		}
		return null;
	}

	private static List<Pawn> getNeighbours(Pawn pawn) {
		List<Pawn> temp = new ArrayList<>();
		int x = pawn.getxPos() - _A;
		int y = 8 - pawn.getyPos();
		char c = pawn.getColor() == PlayerColor.RED ? 'r' : 'w';
		for (int i = -1; i <= 1; i++) {
			if (x + i < 0 || x + i > 7) {
				continue;
			}
			for (int j = -1; j <= 1; j++) {
				if (y + j < 0 || y + j > 7) {
					continue;
				}
				if (brd[x+i][y+j] == c && (i != 0 || j != 0)) {
					Pawn a = getPawn(new Coordinates(_A + x + i, 8 - y - j));
					if (a != null) {
						temp.add(a);
					}
				}
			}
		}
		return temp;
	}

	public static boolean checkGameFinished() {
		// TODO Obsluga wyst¹pienia zdarzenia tam gdzie return
		if (whites.size() <= 1)
			return true;
		if (reds.size() <= 1)
			return true;
		List<Pawn> tempList = null;
		List<Pawn> iterate = turn == PlayerColor.RED ? reds : whites;
		for (Pawn pawn : iterate) {
			tempList = getNeighbours(pawn);
			if (tempList.size() > 0) {
				for (Pawn pawns : tempList) {
					updateListNumber(pawns.getCurrentNumber(),
							pawn.getCurrentNumber(), iterate);
				}
			}
		}
		boolean finished = true;
		int temp=-1;
		int i =0;
		for (Pawn pawn : iterate) {
			if (i==0){
				temp = pawn.getCurrentNumber();
				i++;
			}else{
				if(pawn.getCurrentNumber() != temp){
					finished = false;
					break;
				}
			}
		}
		if (finished) {
			System.out.println("finished= " + finished + ": " + turn);
			return true;
		} else {
			resetPawnsNumber(iterate);
		}
		return false;

	}

	private static void updateListNumber(int currentNumber, int currentNumber2,
			List<Pawn> color) {
		for (Pawn pawn : color) {
			pawn.setCurrentNumber(pawn.getCurrentNumber() == currentNumber ? currentNumber2
					: pawn.getCurrentNumber());
		}

	}

	private static void resetPawnsNumber(List<Pawn> color) {
		for (Pawn pawn : color) {
			pawn.setCurrentNumber(pawn.getId());
		}
	}

	public static void changeTurn() {
		turn = (turn == PlayerColor.WHITE ? PlayerColor.RED : PlayerColor.WHITE);
	}

	public static Collection<? extends Marker> getPossibleMoves(Pawn pion) {
		// short int Board::count_move(Pawn A,Dist_type type){
		// short int dist=0;
		//
		// Point temp=A.p.map_coords();
		int x = pion.getxPos() - _A;
		int y = 8 - pion.getyPos();
		for (MoveDirection direction : MoveDirection.values()) {
			int dist = 0;
			switch (direction) {
			case VERTICAL:
				dist = 0;
				for (int j = 0; j <= 7; j++) {
					if (brd[x][j] != ' ')
						dist++;
				}
				checkAndMarkVertical(pion, dist);
				break;
			// return dist;
			//
			// case h:
			// for (int j = 0; j < 8; j++) {
			// if (brd[j][temp.x] != ' ')
			// dist++;
			// }
			// return dist;
			// case dl:// "\"
			// int x = temp.y;
			// int y = temp.x;
			// x--;
			// y--;
			//
			// // check upper-left corner
			// while (!out_of_boundary(x, y)) {
			// if ((brd[y][x] != ' '))
			// dist++;
			// x--;
			// y--;
			// }
			// x = temp.y;
			// y = temp.x;
			// x++;
			// y++;
			// // check down-right corner
			// while (!out_of_boundary(x, y)) {
			// if ((brd[y][x] != ' '))
			// dist++;
			// x++;
			// y++;
			// }
			// return (++dist);// remember to count the pawn (itself)!!
			// case dr:// "/"
			// int x2 = temp.y;
			// int y2 = temp.x;
			// x2++;
			// y2++;
			//
			// // check upper-right corner
			// while (!out_of_boundary(x2, y2)) {
			// if ((brd[y2][x2] != ' '))
			// dist++;
			// x2++;
			// y2++;
			// }
			// x2 = temp.y;
			// y2 = temp.x;
			// x2--;
			// y2--;
			// // check down-left corner
			// while (!out_of_boundary(x2, y2)) {
			// if ((brd[y2][x2] != ' '))
			// dist++;
			// x2--;
			// y2--;
			// }
			// return (++dist);// remember to count the pawn!!
			// return 0;
			// default:
			// return 0;
			// }
			// }
			case DOWN_LEFT:
				break;
			case DOWN_RIGHT:
				break;
			case HORIZONTAL:
				dist = 0;
				for (int j = 0; j <= 7; j++) {
					if (brd[j][y] != ' ')
						dist++;
				}
				checkAndMarkHorizontal(pion, dist);
				break;
			default:
				break;
			}
		}
		return new ArrayList<Marker>();
	}

	public static boolean checkMove(Coordinates pole, Pawn pawn) {
		for (Marker mark : marker) {
			if (mark.getField().equals(pole)) {
				return true;
			}
		}
		return false;
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
		brd[-_A + pawn.getxPos()][8 - pawn.getyPos()] = ' ';
		brd[-_A + pole.getX()][8 - pole.getY()] = pawn.getColor() == PlayerColor.RED ? 'r'
				: 'w';
		pawn.setxPos(pole.getX());
		pawn.setyPos(pole.getY());
		pawn.setPressed(false);

	}

	// ********************************************************************************
	// Metody prywatne
	// ********************************************************************************

	private static void checkAndMarkVertical(Pawn pion, int dist) {
		int x = pion.getxPos() - _A;
		int y = 8 - pion.getyPos();
		if (y - dist >= 0 && y - dist < 8) {
			if (brd[x][y - dist] == ' ') {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos(), 	// Tutaj ten plus jest ok bo pozniej jest odejmowane przy rysowaniu
						pion.getyPos() + dist)));
			}
		}
		if (y + dist >= 0 && y + dist < 8) {
			if (brd[x][y + dist] == ' ') {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos(),		// Tutaj ten minus jest ok bo pozniej jest odejmowane przy rysowaniu
						pion.getyPos() - dist)));
			}
		}
	}

	private static void checkAndMarkHorizontal(Pawn pion, int dist) {
		int x = pion.getxPos() - _A;
		int y = 8 - pion.getyPos();
		if (x - dist >= 0 && x - dist < 8) {
			if (brd[x - dist][y] == ' ') {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos()
						- dist, pion.getyPos())));
			}
		}
		if (x + dist >= 0 && x + dist < 8) {
			if (brd[x + dist][y] == ' ') {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos()
						+ dist, pion.getyPos())));
			}
		}
	}

	private static void placePawns(List<Pawn> list, PlayerColor color) {
		int i = 0;
		for (Pawn pawn : list) {
			if (color == PlayerColor.WHITE) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					brd[0][i + 1] = 'w';
					pawn.setxPos(_A + 0);
					pawn.setyPos(i + 2);
				} else {
					brd[7][i - 5] = 'w';
					pawn.setxPos(7 + _A);
					pawn.setyPos(i - 4);
				}
			} else if (color == PlayerColor.RED) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					brd[i + 1][0] = 'r';
					pawn.setxPos(i + 1 + _A);
					pawn.setyPos(1);
				} else {
					brd[i - 5][7] = 'r';
					pawn.setxPos(i - 5 + _A);
					pawn.setyPos(8);
				}
			}
			i++;
		}
	}

	private static void add(List<Pawn> list, PlayerColor color) {
		for (int i = 0; i < AMOUNT_OF_PAWNS; i++) {
			list.add(new Pawn(color, i + 1));
		}
	}

	private static void initBoard() {
		brd = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				brd[i][j] = ' ';
			}
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
