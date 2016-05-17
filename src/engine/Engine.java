package engine;

import enums.MoveDirection;
import enums.PlayerColor;
import gui.Marker;
import gui.Pawn;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static boolean checkPawnClicked(Coordinates pole) {
		for (Pawn pawn : pawns) {
			if (pawn.getxPos() == pole.getX() && pawn.getyPos() == pole.getY() && pawn.getColor() == turn) {
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
		} else {
			resetPawnsNumber(iterate);
		}

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

		Coordinates c = new Coordinates(pion.getxPos(), pion.getyPos());
		c.map_coords();
		for (MoveDirection direction : MoveDirection.values()) {
			int dist = 0;
			switch (direction) {
			case VERTICAL:
				dist = 0;
				for (int j = 0; j <= 7; j++) {
					if (brd[j][c.getX()] != ' ')
						dist++;
				}
				System.out.println("vertical"+dist);
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

				int x2 = c.getX();
				int y2 = c.getY();
				x2++;
				y2++;

				// check upper-right corner
				while (!out_of_boundary(x2, y2)) {
					if ((brd[y2][x2] != ' '))
						dist++;
					x2++;
					y2++;
				}
				x2 = c.getY();
				y2 = c.getX();
				x2--;
				y2--;
				// check down-left corner
				while (!out_of_boundary(x2, y2)) {
					if ((brd[y2][x2] != ' '))
						dist++;
					x2--;
					y2--;
				}
				dist++;// remember to count the pawn (itself)!!
				System.out.println("down left"+dist);
				//	checkAndMarkDownRight(pion, dist);
				break;
			case DOWN_RIGHT:
				int x = c.getX();
				int y = c.getY();
				x--;
				y--;

				// check upper-left corner
				while (!out_of_boundary(x, y)) {
					if ((brd[x][y] != ' '))
						dist++;
					x--;
					y--;
				}
				x = c.getX();
				y = c.getY();
				x++;
				y++;
				// check down-right corner
				while (!out_of_boundary(x, y)) {
					if ((brd[x][y] != ' '))
						dist++;
					x++;
					y++;
				}
				dist++;// remember to count the pawn (itself)!!
				System.out.println("down right"+dist);
				checkAndMarkDownRight(pion, dist);
				break;
			case HORIZONTAL:
				dist = 0;
				for (int j = 0; j <= 7; j++) {
					if (brd[c.getY()][j] != ' ')
						dist++;
				}
				System.out.println("horizontal"+dist);
				checkAndMarkHorizontal(pion, dist);
				break;
			default:
				break;
			}
		}
		return new ArrayList<Marker>();
	}

	public static boolean checkMove(Coordinates pole, Pawn pawn) {
		System.out.println(pole.getX());
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
		brd[8 - pawn.getyPos()][-_A + pawn.getxPos()] = ' ';
		brd[8 - pole.getY()][-_A + pole.getX()] = pawn.getColor() == PlayerColor.RED ? 'r' : 'w';
		pawn.setxPos(pole.getX());
		pawn.setyPos(pole.getY());
		pawn.setPressed(false);
		drawBoard();
	}

	// ********************************************************************************
	// Metody prywatne
	// ********************************************************************************

	private static void checkAndMarkVertical(Pawn pion, int dist) {
		int x = pion.getxPos() - _A;
		int y = 8 - pion.getyPos();
		char col = pion.getColor() == PlayerColor.RED ? 'r' : 'w';
		if (y - dist >= 0 && y - dist < 8) {
			if (brd[y-dist][x] != col) {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos(), // Tutaj
						// ten
						// plus
						// jest
						// ok
						// bo
						// pozniej
						// jest
						// odejmowane
						// przy
						// rysowaniu
						pion.getyPos() + dist)));
			}
		}
		if (y + dist >= 0 && y + dist < 8) {
			if (brd[y+dist][x] != col) {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos(), // Tutaj
						// ten
						// minus
						// jest
						// ok
						// bo
						// pozniej
						// jest
						// odejmowane
						// przy
						// rysowaniu
						pion.getyPos() - dist)));
			}
		}
	}

	private static void checkAndMarkHorizontal(Pawn pion, int dist) {
		int x = pion.getxPos() - _A;
		int y = 8 - pion.getyPos();
		char col = pion.getColor() == PlayerColor.RED ? 'r' : 'w';
		if (x - dist >= 0 && x - dist < 8) {
			if (brd[y][x-dist] != col) {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos() - dist, pion.getyPos())));
			}
		}
		if (x + dist >= 0 && x + dist < 8) {
			if (brd[y][x+dist] != col) {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos() + dist, pion.getyPos())));
			}
		}
	}

	private static void checkAndMarkDownRight(Pawn pion, int dist) {
		// TODO: handle marker
		Coordinates c = new Coordinates(pion.getxPos(), pion.getyPos());
		c.map_coords();
		int x = c.getX();
		int y = c.getY();
		if ((x + dist >= 0 && x + dist < 8) && (y + dist >= 0 && y + dist < 8)) {
			if (brd[y + dist][x + dist] == ' ') {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos() + dist, pion.getyPos() + dist)));
			}
		}
		if ((x - dist >= 0 && x - dist < 8) && (y - dist >= 0 && y - dist < 8)) {
			if (brd[y - dist][x - dist] == ' ') {
				marker.add(new Marker(pion, new Coordinates(pion.getxPos() - dist, pion.getyPos() - dist)));
				System.out.println();
			}
		}
	}

	private static void placePawns(List<Pawn> list, PlayerColor color) {
		int i = 0;
		for (Pawn pawn : list) {
			if (color == PlayerColor.WHITE) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					brd[i + 1][0] = 'w';
					pawn.setxPos(_A + 0);
					pawn.setyPos(i + 2);
				} else {
					brd[i - 5][7] = 'w';
					pawn.setxPos(7 + _A);
					pawn.setyPos(i - 4);
				}
			} else if (color == PlayerColor.RED) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					brd[0][i + 1] = 'r';
					pawn.setxPos(i + 1 + _A);
					pawn.setyPos(1);
				} else {
					brd[7][i - 5] = 'r';
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

	private static void initBoard() {
		brd = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				brd[i][j] = ' ';
			}
		}
	}
	public static void drawBoard(){
		System.out.println(" -------------------------");
		for(int i =0; i<8; i++){
			System.out.print(8-i);
			System.out.print("|");
			for(int j=0;j<8;j++){
				System.out.print(brd[i][j]+" |");
				if(j==7)System.out.print("\n");
			}
			System.out.println(" -------------------------");
			if(i==7)	System.out.println("   A  B  C  D  E  F  G  H");
		}
	}


	private static boolean out_of_boundary(int x, int y) {
		if (x >= 0 && y >= 0 && x < 8 && y < 8)
			return false;
		else
			return true;

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
