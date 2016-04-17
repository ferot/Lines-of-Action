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

	//creates pawns and assigns initial pawn's coordinates
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
		int n=7;
			for(unsigned int i=0;i<NUMBER_OF_PAWNS;i++)
			{

				Pawns.push_back(Pawn());

				if(i<6){
					Pawns[i].p.y=8;
					Pawns[i].p.x='H'-n--;
				}
				else{
					if(i==6)
						n=7;
					Pawns[i].p.y=1;
					Pawns[i].p.x='H'-n--;
				}
			}

	}
		;//TODO: generacja pionków czarnych ('poziomych')
}

