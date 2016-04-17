/*
 * Point.hpp
 *
 *  Created on: 16 kwi 2016
 *      Author: Fero
 */

#ifndef POINT_HPP_
#define POINT_HPP_


class Point{
private:
	short int x,y;
	Point(int,int);
	Point map_coords(int x,int y);
public:
	short int get_coord(char);
};


#endif /* POINT_HPP_ */
