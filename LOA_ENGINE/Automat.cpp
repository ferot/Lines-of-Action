#include "Automat.hpp"
#include <algorithm>
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

void Automat::move_pawn(Point source,Point dest,Color col){
	//TODO : Walidator ruchu (przeskakiwanie nieswoich pionk�w, ruch poza plansz�, ruch o w�a�ciw� ilo�c p�l)

	if(col==white){
		//TODO znale�c id piona o koordynatach source
		const Pawn Item(source);
		std::vector<Pawn>::iterator it;
		if((it=std::find(PW->Pawns.begin(),PW->Pawns.end(),Item))!= PW->Pawns.end()){
			*it=dest;
				}
		}
	else if(col==black){
		const Pawn Item(source);
				std::vector<Pawn>::iterator it;
				if((it=std::find(PB->Pawns.begin(),PB->Pawns.end(),Item))!= PB->Pawns.end()){
					*it=dest;
						}
				}
	}



Automat::Automat(Player *pw,Player *pb){
	this->GAMESTATE=INIT;
	PW=pw;
	PB=pb;
}
