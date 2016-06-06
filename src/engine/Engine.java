package engine;

import enums.MoveDirection;
import enums.PlayerColor;
import gui.Board;
import gui.Marker;
import gui.Pawn;

import java.util.ArrayList;
import java.util.List;

import bot.Bot;
import bot.Tree;
import bot.Tree.Node;
import bot.TreeNodeContent;

public final class Engine {

	private Engine() {
	}

	private static final int AMOUNT_OF_PAWNS = 12;
	private static char[][] brd;
	private static List<Pawn> whites;
	private static List<Pawn> reds;
	private static List<Pawn> pawns;
	private static PlayerColor turn;
	private static List<Marker> marker;
	private static boolean pressed;
	private static Bot bot;
	private static boolean[] jump_flags = new boolean[8];

	// **********************************************************
	// Inicjalizacja silnika
	// **********************************************************

	static {
		init();
	}

	public static void init() {
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
		pressed = false;
		bot = new Bot(PlayerColor.WHITE);
	}

	// **********************************************************
	// Publiczne metody statyczne
	// **********************************************************

	public static boolean checkPawnClicked(Coordinates field) {
		Pawn pawn = getPawn(field);
		if (pawn != null && pawn.getColor() == turn) {
			if (pressed && pawn.getColor() == turn) {
				Pawn pressedPawn = getPressedPawn();
				pressedPawn.setPressed(false);
				removeMarkers(pressedPawn);
				pawn.setPressed(true);
				pressed = true;
				marker.add(new Marker(pawn, field));
				getPossibleMoves(pawn, true);
				return true;
			} else if (!pressed) {
				pawn.setPressed(true);
				pressed = true;
				marker.add(new Marker(pawn, field));
				getPossibleMoves(pawn, true);
				return true;
			}
		}
		return false;
	}

	private static Pawn getPressedPawn() {
		for (Pawn pawn : pawns) {
			if (pawn.isPressed()) {
				return pawn;
			}
		}
		return null;
	}

	public static Pawn getPawn(Coordinates field) {
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
		int x = pawn.getxPos();
		int y = pawn.getyPos();
		char c = pawn.getColor() == PlayerColor.RED ? 'r' : 'w';
		for (int i = -1; i <= 1; i++) {
			if (x + i < 0 || x + i > 7) {
				continue;
			}
			for (int j = -1; j <= 1; j++) {
				if (y + j < 0 || y + j > 7) {
					continue;
				}
				if (brd[y + j][x + i] == c && (i != 0 || j != 0)) {
					Pawn a = getPawn(new Coordinates(x + i, y + j));
					if (a != null) {
						temp.add(a);
					}
				}
			}
		}
		return temp;
	}

	public static boolean checkGameFinished() {
		if (whites.size() <= 1) {
			Board.setWinner(PlayerColor.WHITE);
			Board.setGameFinished(true);
			return true;
		}
		if (reds.size() <= 1) {
			Board.setWinner(PlayerColor.WHITE);
			Board.setGameFinished(true);
			return true;
		}
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
		int temp = -1;
		int i = 0;
		for (Pawn pawn : iterate) {
			if (i == 0) {
				temp = pawn.getCurrentNumber();
				i++;
			} else {
				if (pawn.getCurrentNumber() != temp) {
					finished = false;
					break;
				}
			}
		}
		if (finished) {
			Board.setWinner(turn);
			Board.setGameFinished(true);
			return true;
		} else {
			resetPawnsNumber(iterate);
		}
		return false;

	}

	private static void updateListNumber(int currentNumber, int current,
			List<Pawn> color) {
		for (Pawn pawn : color) {
			if(pawn.getCurrentNumber() == currentNumber){
				pawn.setCurrentNumber(current);
			}
		}
	}

	private static void resetPawnsNumber(List<Pawn> color) {
		for (Pawn pawn : color) {
			pawn.setCurrentNumber(pawn.getId());
		}
	}

	public static void changeTurn() {
		turn = (turn == PlayerColor.WHITE ? PlayerColor.RED : PlayerColor.WHITE);
		if (turn == bot.getColor()){
			drawBoard();
			bot.move(brd);
		}
	}

	public static List<TreeNodeContent> getPossibleMoves(Pawn pion,
			boolean markingOn) {
		List<TreeNodeContent> moveList = new ArrayList<TreeNodeContent>();
		Coordinates c = new Coordinates(pion.getxPos(), pion.getyPos());
		for (MoveDirection direction : MoveDirection.values()) {
			int dist = 0;
			switch (direction) {
			case VERTICAL:
				dist = checkVertical(c, dist);
				checkAndMarkVertical(pion, dist);
				break;
			case DOWN_LEFT:
				dist = checkDownLeft(c, dist);
				checkAndMarkDownLeft(pion, dist);
				break;
			case DOWN_RIGHT:
				dist = checkDownRight(c, dist);
				checkAndMarkDownRight(pion, dist);
				break;
			case HORIZONTAL:
				dist = checkHorizontal(c, dist);
				checkAndMarkHorizontal(pion, dist);
				break;
			default:
				break;
			}
		}
		return moveList;
	}

	public static boolean checkMove(Coordinates pole, Pawn pawn) {
		if (pawn.getxPos() == pole.getX() && pawn.getyPos() == pole.getY()) {
			pawn.setPressed(false);
			pressed = false;
			removeMarkers(pawn);
			return false;
		}
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
		brd[pawn.getyPos()][pawn.getxPos()] = ' ';
		brd[pole.getY()][pole.getX()] = pawn.getColor() == PlayerColor.RED ? 'r'
				: 'w';
		pawn.setPressed(false);
		Pawn temp = getPawn(pole);
		if (temp != null) {
			pawns.remove(temp);
			if (pawn.getColor() == PlayerColor.RED) {
				whites.remove(temp);
				temp = null;
			}
			if (pawn.getColor() == PlayerColor.WHITE) {
				reds.remove(temp);
				temp = null;
			}
		}
		pawn.setxPos(pole.getX());
		pawn.setyPos(pole.getY());
		pressed = false;
		// drawBoard();
	}

	// ********************************************************************************
	// Metody prywatne
	// ********************************************************************************
	private static double countValue(Pawn pawn, Coordinates coordinates,
			char[][] board) {
		double sum = 0;
		if (pawn.getColor() == PlayerColor.RED) {
			for (Pawn red : reds) {
				if (red == pawn) {
					sum += getDistance(reds, coordinates);
				} else {
					sum += getDistance(reds, red);
				}
			}
		}
		if (pawn.getColor() == PlayerColor.WHITE) {
			for (Pawn white : whites) {
				if (white == pawn) {
					sum += getDistance(whites, coordinates);
				} else {
					sum += getDistance(whites, white);
				}
			}
		}
		return sum;
	}
	
	private static double getDistance(List<Pawn> list,
			Coordinates coordinates) {
		int distance = 0;
		for (Pawn element : list) {
			distance += Math.sqrt(Math.abs(element.getxPos() -coordinates.getX())
					+ Math.abs(element.getyPos() - coordinates.getY()));
		}
		return distance;
	}

	private static int getDistance(List<Pawn> list, Pawn pawn) {
		int distance = 0;
		for (Pawn element : list) {
			distance += Math.sqrt(Math.abs(element.getxPos() - pawn.getxPos())
					+ Math.abs(element.getyPos() - pawn.getyPos()));
		}
		return distance;
	}

	private static void checkAndMarkVertical(Pawn pion, int dist) {
		int x = pion.getxPos();
		int y = pion.getyPos();
		char col = pion.getColor() == PlayerColor.RED ? 'r' : 'w';
		char col_negated = col == 'r' ? 'w' : 'r';
		if (y - dist >= 0 && y - dist < 8) {
			for (int b = y - 1; (b >= y - dist + 1) && b < 8 && b >= 0; b--) {
				if (brd[b][x] == col_negated) {
					jump_flags[0] = true;
					break;
				}
			}
			if (brd[y - dist][x] != col) {
				if (jump_flags[0] == false){
					Coordinates coordinatesTo = new Coordinates(pion.getxPos(), pion.getyPos() - dist);
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[0] = false;
			}
		}
		if (y + dist >= 0 && y + dist < 8) {
			for (int b = y + 1; (b <= y + dist - 1) && b < 8 && b >= 0; b++) {
				if (brd[b][x] == col_negated) {
					jump_flags[1] = true;
					break;
				}
			}
			if (brd[y + dist][x] != col) {
				if (jump_flags[1] == false){
					Coordinates coordinatesTo = new Coordinates(pion.getxPos(), pion.getyPos() + dist);
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[1] = false;
			}
		}
	}


	private static void checkAndMarkHorizontal(Pawn pion, int dist) {
		int x = pion.getxPos();
		int y = pion.getyPos();
		char col = pion.getColor() == PlayerColor.RED ? 'r' : 'w';
		char col_negated = col == 'r' ? 'w' : 'r';
		if (x - dist >= 0 && x - dist < 8) {
			for (int b = x - 1; (b >= x - dist + 1) && b < 8 && b >= 0; b--) {
				if (brd[y][b] == col_negated) {
					jump_flags[2] = true;
					break;
				}
			}
			if (brd[y][x - dist] != col) {
				if (jump_flags[2] == false){
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()-dist, pion.getyPos());
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[2] = false;
			}
		}
		if (x + dist >= 0 && x + dist < 8) {
			for (int b = x + 1; (b <= x + dist - 1) && b < 8 && b >= 0; b++) {
				if (brd[y][b] == col_negated) {
					jump_flags[3] = true;
					break;
				}
			}
			if (brd[y][x + dist] != col) {
				if (jump_flags[3] == false){
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()+dist, pion.getyPos());
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[3] = false;
			}
		}
	}

	private static void checkAndMarkDownRight(Pawn pion, int dist) {
		Coordinates c = new Coordinates(pion.getxPos(), pion.getyPos());
		int x = c.getX();
		int y = c.getY();
		char col = pion.getColor() == PlayerColor.RED ? 'r' : 'w';
		char col_negated = col == 'r' ? 'w' : 'r';
		if ((x + dist >= 0 && x + dist < 8) && (y + dist >= 0 && y + dist < 8)) {
			for (int a = y + 1, b = x + 1; (b <= x + dist - 1) && b < 8 && b >= 0 && (a <= y + dist - 1) && a >= 0 && a < 8; b++, a++) {
				if (brd[a][b] == col_negated) {
					jump_flags[4] = true;
					break;
				}
			}
			if (brd[y + dist][x + dist] != col) {
				if (jump_flags[4] == false){
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()+dist, pion.getyPos()+dist);
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[4] = false;
			}
		}
		if ((x - dist >= 0 && x - dist < 8) && (y - dist >= 0 && y - dist < 8)) {
			for (int a = y + 1, b = x + 1; (b >= x - dist + 1) && b < 8 && b >= 0 && (a >= y - dist + 1) && a >= 0 && a < 8; b--, a--) {
				if (brd[a][b] == col_negated) {
					jump_flags[5] = true;
					break;
				}
			}
			if (brd[y - dist][x - dist] != col) {
				if (jump_flags[5] == false){
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()-dist, pion.getyPos()-dist);
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[5] =false;
			}
		}
	}

	private static void checkAndMarkDownLeft(Pawn pion, int dist) {
		Coordinates c = new Coordinates(pion.getxPos(), pion.getyPos());
		int x = c.getX();
		int y = c.getY();
		char col = pion.getColor() == PlayerColor.RED ? 'r' : 'w';
		char col_negated = col == 'r' ? 'w' : 'r';
		if ((x + dist >= 0 && x + dist < 8) && (y - dist >= 0 && y - dist < 8)) {
			for (int a = y - 1,b = x + 1; (b <= x + dist - 1) && b < 8 && b >= 0 && (a >= y - dist + 1) && a >= 0 && a < 8; b++, a--) {
				if (brd[a][b] == col_negated) {
					jump_flags[6] = true;
					break;
				}
			}
			if (brd[y - dist][x + dist] != col) {
				if (jump_flags[6] == false){
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()+dist, pion.getyPos()-dist);
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[6] = false;
			}
		}
		if ((x - dist >= 0 && x - dist < 8) && (y + dist >= 0 && y + dist < 8)) {
			for (int a = y + 1, b = x - 1; (b >= x - dist + 1) && b < 8 && b >= 0 && (a <= y + dist - 1) && a >= 0 && a < 8; b--, a++) {
				if (brd[a][b] == col_negated) {
					jump_flags[7] = true;
					break;
				}
			}
			if (brd[y + dist][x - dist] != col) {
				if (jump_flags[7] == false) {
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()
							- dist, pion.getyPos() + dist);
					marker.add(new Marker(pion, coordinatesTo));
				}
				jump_flags[7] = false;
			}
		}
	}

	private static void placePawns(List<Pawn> list, PlayerColor color) {
		int i = 0;
		for (Pawn pawn : list) {
			if (color == PlayerColor.WHITE) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					brd[i + 1][0] = 'w';
					pawn.setxPos(0);
					pawn.setyPos(i + 1);
				} else {
					brd[i - 5][7] = 'w';
					pawn.setxPos(7);
					pawn.setyPos(i - 5);
				}
			} else if (color == PlayerColor.RED) {
				if (i < AMOUNT_OF_PAWNS / 2) {
					brd[0][i + 1] = 'r';
					pawn.setxPos(i + 1);
					pawn.setyPos(0);
				} else {
					brd[7][i - 5] = 'r';
					pawn.setxPos(i - 5);
					pawn.setyPos(7);
				}
			}
			i++;
		}
	}

	private static void add(List<Pawn> list, PlayerColor color) {
		for (int i = 0; i < AMOUNT_OF_PAWNS; i++) {
			list.add(new Pawn(color, i));
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

	public static void drawBoard() {
		drawBoard(brd);
	}

	private static boolean out_of_boundary(int x, int y) {
		if (x >= 0 && y >= 0 && x < 8 && y < 8)
			return false;
		else
			return true;

	}


	public static List<Coordinates> getPointsFromBoard(char[][] board, char c) {
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[y][x] == c) {
					coordinates.add(new Coordinates(x, y));
				}
			}
		}
		return coordinates;
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

	public static Bot getBot() {
		return bot;
	}

	private static int checkVertical(Coordinates c, int dist) {
		return checkVertical(c, dist, brd);
	}

	private static int checkVertical(Coordinates c, int dist, char board[][]) {
		for (int j = 0; j <= 7; j++) {
			if (board[j][c.getX()] != ' ')
				dist++;
		}
		return dist;
	}

	private static int checkDownLeft(Coordinates c, int dist) {
		return checkDownLeft(c, dist, brd);
	}

	private static int checkDownLeft(Coordinates c, int dist, char board[][]) {
		int x2 = c.getX();
		int y2 = c.getY();
		x2++;
		y2--;

		// check upper-right corner
		while (!out_of_boundary(x2, y2)) {
			if ((board[y2][x2] != ' '))
				dist++;
			x2++;
			y2--;
		}
		x2 = c.getX();
		y2 = c.getY();
		x2--;
		y2++;
		// check down-left corner
		while (!out_of_boundary(x2, y2)) {
			if ((board[y2][x2] != ' '))
				dist++;
			x2--;
			y2++;
		}
		dist++;// remember to count the pawn (itself)!!
		return dist;
	}

	private static int checkDownRight(Coordinates c, int dist) {
		return checkDownRight(c, dist, brd);
	}

	private static int checkDownRight(Coordinates c, int dist, char board[][]) {
		int x = c.getX();
		int y = c.getY();
		x--;
		y--;

		// check upper-left corner
		while (!out_of_boundary(x, y)) {
			if ((board[y][x] != ' ')) {
				dist++;
			}
			x--;
			y--;
		}
		x = c.getX();
		y = c.getY();
		x++;
		y++;
		// check down-right corner
		while (!out_of_boundary(x, y)) {
			if ((board[y][x] != ' '))
				dist++;
			x++;
			y++;
		}
		dist++;// remember to count the pawn (itself)!!
		return dist;
	}

	private static int checkHorizontal(Coordinates c, int dist) {
		return checkHorizontal(c, dist, brd);
	}

	private static int checkHorizontal(Coordinates c, int dist, char[][] board) {

		for (int j = 0; j <= 7; j++) {
			if (board[c.getY()][j] != ' ')
				dist++;
		}
		return dist;
	}


	public static List<TreeNodeContent> getPossibleMoves(char[][] board,
			Coordinates coord) {
		List<TreeNodeContent> contents = new ArrayList<TreeNodeContent>();

		char col = board[coord.getY()][coord.getX()] == 'r' ? 'r' : 'w';
		char col_negated = col == 'r' ? 'w' : 'r';
		char temp_brd[][] = new char[8][8];
		temp_brd = cloneBoard(board);

		int dist = 0;
		int x = coord.getX();
		int y = coord.getY();
		Pawn pion = getPawn(coord);
		dist = checkHorizontal(coord, dist, board);

		if (x - dist >= 0 && x - dist < 8) {
			for (int b = x - 1; (b >= x - dist + 1) && b < 8 && b >= 0; b--) {
				if (board[y][b] == col_negated) {
					jump_flags[2] = true;
					break;
				}
			}
			if (board[y][x - dist] != col) {
				if (jump_flags[2] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y][x - dist] = col;
					Coordinates coordinatesFrom = new Coordinates(
							pion.getxPos(), pion.getyPos());
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()
							- dist, pion.getyPos());
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo, temp_brd),
							temp_brd));

				}
				jump_flags[2] = false;
			}
		}
		if (x + dist >= 0 && x + dist < 8) {
			for (int b = x + 1; (b <= x + dist - 1) && b < 8 && b >= 0; b++) {
				if (board[y][b] == col_negated) {
					jump_flags[3] = true;
					break;
				}
			}
			if (board[y][x + dist] != col) {
				if (jump_flags[3] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y][x + dist] = col;
					Coordinates coordinatesFrom = new Coordinates(
							pion.getxPos(), pion.getyPos());
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()
							+ dist, pion.getyPos());
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo,
									temp_brd),
							temp_brd));

				}
				jump_flags[3] = false;
			}
		}
		// pass dist=0 - new case
		dist = checkVertical(coord, 0, board);
		if (y - dist >= 0 && y - dist < 8) {
			for (int b = y - 1; (b >= y - dist + 1) && b < 8 && b >= 0; b--) {
				if (board[b][x] == col_negated) {
					jump_flags[0] = true;
					break;
				}
			}
			if (board[y - dist][x] != col) {
				if (jump_flags[0] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y - dist][x] = col;
					Coordinates coordinatesFrom = new Coordinates(coord.getX(),
							coord.getY());
					Coordinates coordinatesTo = new Coordinates(coord.getX(),
							coord.getY() - dist);
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo,
									temp_brd),
							temp_brd));

				}
				jump_flags[0] = false;
			}
		}
		if (y + dist >= 0 && y + dist < 8) {
			for (int b = y + 1; (b <= y + dist - 1) && b < 8 && b >= 0; b++) {
				if (board[b][x] == col_negated) {
					jump_flags[1] = true;
					break;
				}
			}
			if (board[y + dist][x] != col) {
				if (jump_flags[1] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y + dist][x] = col;
					Coordinates coordinatesFrom = new Coordinates(coord.getX(),
							coord.getY());
					Coordinates coordinatesTo = new Coordinates(coord.getX(),
							coord.getY() + dist);
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo,
									temp_brd),
							temp_brd));
				}
				jump_flags[1] = false;
			}
		}
		// pass dist=0 - new case
		dist = checkDownLeft(coord, 0, board);
		if ((x + dist >= 0 && x + dist < 8) && (y - dist >= 0 && y - dist < 8)) {
			for (int a = y - 1, b = x + 1; (b <= x + dist - 1) && b < 8
					&& b >= 0 && (a >= y - dist + 1) && a >= 0 && a < 8; b++, a--) {
				if (board[a][b] == col_negated) {
					jump_flags[6] = true;
					break;
				}
			}
			if (board[y - dist][x + dist] != col) {
				if (jump_flags[6] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y - dist][x + dist] = col;
					Coordinates coordinatesFrom = new Coordinates(
							pion.getxPos(), pion.getyPos());
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()
							+ dist, pion.getyPos() - dist);
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo,
									temp_brd),
							temp_brd));

				}
				jump_flags[6] = false;
			}
		}
		if ((x - dist >= 0 && x - dist < 8) && (y + dist >= 0 && y + dist < 8)) {
			for (int a = y + 1, b = x - 1; (b >= x - dist + 1) && b < 8
					&& b >= 0 && (a <= y + dist - 1) && a >= 0 && a < 8; b--, a++) {
				if (board[a][b] == col_negated) {
					jump_flags[7] = true;
					break;
				}
			}
			if (board[y + dist][x - dist] != col) {
				if (jump_flags[7] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y + dist][x - dist] = col;
					Coordinates coordinatesFrom = new Coordinates(
							pion.getxPos(), pion.getyPos());
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()
							- dist, pion.getyPos() + dist);
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo,
									temp_brd),
							temp_brd));

				}
				jump_flags[7] = false;
			}
		}
		// pass dist=0 - new case
		dist = checkDownRight(coord, 0, board);
		if ((x + dist >= 0 && x + dist < 8) && (y + dist >= 0 && y + dist < 8)) {
			for (int a = y + 1, b = x + 1; (b <= x + dist - 1) && b < 8
					&& b >= 0 && (a <= y + dist - 1) && a >= 0 && a < 8; b++, a++) {
				if (board[a][b] == col_negated) {
					jump_flags[4] = true;
					break;
				}
			}
			if (board[y + dist][x + dist] != col) {
				if (jump_flags[4] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y + dist][x + dist] = col;
					Coordinates coordinatesFrom = new Coordinates(
							pion.getxPos(), pion.getyPos());
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()
							+ dist, pion.getyPos() + dist);
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo,
									temp_brd),
							temp_brd));
				}
				jump_flags[4] = false;
			}
		}
		if ((x - dist >= 0 && x - dist < 8) && (y - dist >= 0 && y - dist < 8)) {
			for (int a = y + 1, b = x + 1; (b >= x - dist + 1) && b < 8
					&& b >= 0 && (a >= y - dist + 1) && a >= 0 && a < 8; b--, a--) {
				if (board[a][b] == col_negated) {
					jump_flags[5] = true;
					break;
				}
			}
			if (board[y - dist][x - dist] != col) {
				if (jump_flags[5] == false) {
					// clear current position of pawn and generate board for
					// move
					temp_brd[y][x] = ' ';
					temp_brd[y - dist][x - dist] = col;
					Coordinates coordinatesFrom = new Coordinates(
							pion.getxPos(), pion.getyPos());
					Coordinates coordinatesTo = new Coordinates(pion.getxPos()
							- dist, pion.getyPos() - dist);
					contents.add(new TreeNodeContent(coordinatesFrom,
							coordinatesTo, countValue(pion, coordinatesTo,
									temp_brd),
							temp_brd));

				}
				jump_flags[5] = false;
			}
		}

		return contents;
	}

	private static void drawBoard(char[][] board) {
		System.out.println(" -------------------------");
		for (int i = 0; i < 8; i++) {
			System.out.print(i);
			System.out.print("|");
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + " |");
				if (j == 7)
					System.out.print("\n");
			}
			System.out.println(" -------------------------");
			if (i == 7)
				System.out.println("   A  B  C  D  E  F  G  H");
		}

	}

	public static Node<TreeNodeContent> findBestOption(
			Tree<TreeNodeContent> tree, PlayerColor color) {
		setDistanceHeuristic(tree, color);
		setPawnBeatingHeuristic(tree, color);
		setToCenterHeuristic(tree, color);
		setSeparationFromGroupHeuristic(tree, color);
		return getBestMove(tree, color);
	}

	private static Node<TreeNodeContent> getBestMove(
			Tree<TreeNodeContent> tree,
			PlayerColor color2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void setSeparationFromGroupHeuristic(
			Tree<TreeNodeContent> tree,
			PlayerColor color2) {
		// TODO Auto-generated method stub

	}

	private static void setToCenterHeuristic(Tree<TreeNodeContent> tree,
			PlayerColor color2) {
		// TODO Auto-generated method stub

	}

	private static void setPawnBeatingHeuristic(Tree<TreeNodeContent> tree,
			PlayerColor color2) {
		// TODO Auto-generated method stub

	}

	private static void setDistanceHeuristic(Tree<TreeNodeContent> tree,
			PlayerColor color2) {

	}

	public static char[][] cloneBoard(char[][] board) {
		char[][] gameState = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				gameState[i][j] = board[i][j];
			}
		}
		return gameState;
	}
}
