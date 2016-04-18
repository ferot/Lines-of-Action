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



public:
	short int x,y;
	Point map_coords();
	Point();
	Point(int,int);

	short int get_coord(char);
};


#endif /* POINT_HPP_ */
