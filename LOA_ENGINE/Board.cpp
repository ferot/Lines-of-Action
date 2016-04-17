/*
 * Boar.cpp
 *
 *  Created on: 20 mar 2016
 *      Author: Fero
 */
#include <iostream>
#include"Board.hpp"
#include"Point.hpp"
using namespace std;
Board::Board(Player* p1,Player* p2){
	P1=p1;
	P2=p2;
	clear_board();
}
void Board::clear_board(){
	for(int i=0;i<8;i++){
		for(int j=0;j<8;j++){
			brd[i][j]=' ';
			//TODO : draw Player's  pawns initial positions
		}
	}
}
void Board::show_board(){
	cout<<" -------------------------"<<endl;
	for(int i=0;i<8;i++){
		cout<<8-i<<"|";
		for(int j=0;j<8;j++){

			cout<<brd[i][j]<<" |";
			if(j==7)cout<<endl;

		}

		cout<<" -------------------------"<<endl;
		if(i==7)cout<<"   A  B  C  D  E  F  G  H";
	}
}

void Board::draw_pawns(){
	clear_board();
	unsigned short int i = 0;
	unsigned short int j= 0;
	do{
		//TODO: UWAGA - tutaj mog¹ byc potencjalne bugi : np. b³êdne koordynaty itd.
		Point p1,p2;
		p1=(P1->Pawns[i]).p.map_coords();
		//p2=P2->Pawns[j].p.map_coords();
		//cout<<p1.x<<p1.y<<endl;
		brd[p1.get_coord('y')][p1.get_coord('x')]=P1->symbol;
		brd[p2.get_coord('x')][p2.get_coord('y')]=P2->symbol;
		i++;
		j++;
	}while(i<P1->Pawns.size()&&i<P2->Pawns.size());


}

/*
 * Method maps
 */
void Board::map_coords(Point P){


}
