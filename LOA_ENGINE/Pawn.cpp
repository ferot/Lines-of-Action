/*
 * Pawn.cpp
 *
 *  Created on: 17 kwi 2016
 *      Author: Fero
 */
#include "Pawn.hpp"
const int BAD_COORD = -666;
Pawn::Pawn(): p(0,0){
}

/**
 * Method returns desired (by additional parameter "which") coordinate
 */
short int Pawn::get_coord(char which){
	if(which=='x')
		return this->p.x;
	else if(which=='y')
		return this->p.y;
	else throw BAD_COORD;
}
