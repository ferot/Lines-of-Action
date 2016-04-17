/*
 * Pawn.h
 *
 *  Created on: 20 mar 2016
 *      Author: Fero
 */

#ifndef PAWN_HPP_
#define PAWN_HPP_
#include "Point.hpp"
class Pawn{


	inline void set_coords(Point p){
		x=p.get_coord('x');
		y=p.get_coord('y');
	}
public:
	short int x,y;
	Pawn();


};



#endif /* PAWN_HPP_ */
