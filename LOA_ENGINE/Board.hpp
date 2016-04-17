/*
 * Board.h
 *
 *  Created on: 20 mar 2016
 *      Author: Fero
 */
#include "Player.hpp"
#include "Point.hpp"
#ifndef BOARD_HPP_
#define BOARD_HPP_

class Board{
	char brd[8][8];
public:
	Board();
	void draw_pawns();
	void show_board();
	void clear_board();
	void map_coords(Point);
	Player * P1;
	Player * P2;
};



#endif /* BOARD_HPP_ */
