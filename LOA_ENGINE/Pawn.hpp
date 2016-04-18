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


	/*inline void set_coords(Point P){
		p.x=P.get_coord('x');
		p.y=P.get_coord('y');
	}
	 */
public:
	short int get_coord(char which);
	Point p;
	Pawn();
	Pawn(Point);
	inline	bool operator == (const Pawn &PWN){
		return(PWN.p.x==p.x && PWN.p.y==p.y);
	}
};



#endif /* PAWN_HPP_ */
