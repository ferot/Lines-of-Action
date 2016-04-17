/*
 * Player.cpp
 *
 *  Created on: 20 mar 2016
 *      Author: Fero
 */
#include "Player.hpp"
/**
 * Defines Player object and initializes positions of his pawns on board
 */
Player::Player(Color c): color(c){
	//assign symbol to player's (for debug purposes)
	color == white ? symbol='O': symbol ='X';

	//creates pawns and assigns initial pawn's coordinates
	int n=0;
	for(unsigned int i=0;i<NUMBER_OF_PAWNS;i++)
	{

		Pawns.push_back(Pawn());

		if(i<6){
			Pawns[i].y=8-i;
			Pawns[i].x='A';
			n++;
		}
		else{
			Pawns[i].y=14-i;
			Pawns[i].x='H';
		}

	}
}


