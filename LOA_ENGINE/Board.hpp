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

enum Dist_type{
	v,
	h,
	dl,// "\"
	dr// "/"
};
class Board{
	char brd[8][8];
public:
	Board(Player*,Player*);
	void draw_pawns();
	void show_board();
	void clear_board();
	void map_coords(Point);
	short int count_move(Pawn,Dist_type type);
	bool out_of_boundary(int x,int y);
	Player * P1;
	Player * P2;
};



#endif /* BOARD_HPP_ */
