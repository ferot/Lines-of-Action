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

	set_symbol();

	/*creates pawns and assigns initial pawn's coordinates
	 * coords are in format 1-8 A-H
	 */
	if(color==white){
		int n=7;
		for(unsigned int i=0;i<NUMBER_OF_PAWNS;i++)
		{

			Pawns.push_back(Pawn());

			if(i<6){
				Pawns[i].p.y=n--;
				Pawns[i].p.x='A';
			}
			else{
				if(i==6)
					n=7;
				Pawns[i].p.y=n--;
				Pawns[i].p.x='H';
			}
		}

	}
	else{
		int n=0;
			for(unsigned int i=0;i<NUMBER_OF_PAWNS;i++)
			{

				Pawns.push_back(Pawn());

				if(i<6){
					Pawns[i].p.y=8;
					Pawns[i].p.x='B'+n++;
				}
				else{
					if(i==6)
						n=0;
					Pawns[i].p.y=1;
					Pawns[i].p.x='B'+n++;
				}
			}

	}
		;//TODO: generacja pionków czarnych ('poziomych')
}
short int Player::get_nr_pawns(){
	return 0;
}
