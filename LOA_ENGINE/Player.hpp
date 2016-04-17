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
	char symbol=' ';
	const unsigned short int NUMBER_OF_PAWNS =12;
public:
	//short int get_coords
	Color color;
	Player(Color c);
	std::vector<Pawn> Pawns;
};



#endif /* PLAYER_HPP_ */
