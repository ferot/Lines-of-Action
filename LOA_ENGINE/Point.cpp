/*
 * Point.cpp
 *
 *  Created on: 16 kwi 2016
 *      Author: Fero
 */

#include "Point.hpp"
const int BAD_COORD = -666;
const int BAD_COORD_MAPPING = -667;
Point::Point(int a,int b):x(8-a),y(b-65){

}
Point Point::map_coords(int x,int y){
	//TODO sprawdü czy dane sπ z zakresu 1-8 i A-H
	if(x<1 ||x>8 || y >73 || y < 65)
		return Point(8-x,y-65);
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
