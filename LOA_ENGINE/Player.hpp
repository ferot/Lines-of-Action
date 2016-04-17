/*
 * Player.h
 *
 *  Created on: 20 mar 2016
 *      Author: Fero
 */

#ifndef PLAYER_HPP_
#define PLAYER_HPP_h
#include <vector>
#include "Pawn.hpp"

enum Color{
	white=0,
	black
};

class Player{

	const unsigned short int NUMBER_OF_PAWNS =12;
public:
	char symbol=' ';
	//short int get_coords
	Color color;
	Player(Color c);
	inline char get_symbol(){
		return symbol;
	}

	//assign symbol to player's (for debug purposes)
	inline	void set_symbol(){
		color == white ? symbol='O': symbol ='X';
	}
	std::vector<Pawn> Pawns;
};



#endif /* PLAYER_HPP_ */
