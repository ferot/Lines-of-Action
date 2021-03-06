/*
 * Automat.hpp
 *
 *  Created on: 17 kwi 2016
 *      Author: Fero
 */
#include "Point.hpp"
#include "Board.hpp"

#ifndef AUTOMAT_HPP_
#define AUTOMAT_HPP_

enum game_state{
	INIT=0,
	END
};

class Automat{
	game_state GAMESTATE;
public:
	void process_game_state(game_state GS);
	void move_pawn(Point,Point,Color);
	short int count_move(Pawn);
	Player *PW;
	Player *PB;
	Board *BRD;

	Automat(Player*,Player*,Board*);

};


#endif /* AUTOMAT_HPP_ */
