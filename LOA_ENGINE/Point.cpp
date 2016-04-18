/*
 * Point.cpp
 *
 *  Created on: 16 kwi 2016
 *      Author: Fero
 */

#include "Point.hpp"
const int BAD_COORD = -666;
const int BAD_COORD_MAPPING = -667;
Point::Point(int x1,int y1):x(x1),y(y1){

}
Point::Point():y(0),x(0){

}
Point Point::map_coords(){
	// sprawdŸ czy dane s¹ z zakresu 1-8 i A-H
	if((y>0 && y<9) && (x <73 && x > 64))
	{
		short int x2=x-65;
		short int y2=8-y;
		return Point(x2,y2);
	}
	else throw BAD_COORD_MAPPING;
}
/**
 * Method returns desired (by additional parameter "which") coordinate
 */
short int Point::get_coord(char which){
	if(which=='x')
		return this->x;
	else if(which=='y')
		return this->y;
	else throw BAD_COORD;
}
