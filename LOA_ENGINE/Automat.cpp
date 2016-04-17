#include "Automat.hpp"

void Automat::process_game_state(game_state GS){

	{

		switch (GS)
		{
		case INIT:
			break;

		case END:
			//TODO: send signal to GUI with
			break;
		default:
			;
		}
	}
}
