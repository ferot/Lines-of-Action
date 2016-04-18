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
	unsigned short int j = 0;
	do{
		// UWAGA - tutaj mog¹ byc potencjalne bugi : np. b³êdne koordynaty itd.
		Point p1,p2;
		p1=(P1->Pawns[i]).p.map_coords();
		p2=P2->Pawns[j].p.map_coords();
		//cout<<p1.x<<p1.y<<endl;
		brd[p1.get_coord('y')][p1.get_coord('x')]=P1->symbol;
		brd[p2.get_coord('y')][p2.get_coord('x')]=P2->symbol;
		i++;
		j++;
	}while(i<P1->Pawns.size()&&i<P2->Pawns.size());


}

short int Board::count_move(Pawn A,Dist_type type){
	short int dist=0;

	Point temp=A.p.map_coords();

	switch(type){
	case v:

		for(int j=0;j<8;j++){
			if(	brd[temp.y][j]!=' ')
				dist++;
		}

		return dist;

	case h:
		for(int j=0;j<8;j++){
			if(	brd[j][temp.x]!=' ')
				dist++;
		}
		return dist;
	case dl:// "\"
		int x=temp.y;
		int y=temp.x;
		x--;
		y--;

		//check upper-left corner
		while(!out_of_boundary(x,y)){
			if((brd[y][x]!=' '))
				dist++;
			x--;
			y--;
		}
		x=temp.y;
		y=temp.x;
		x++;
		y++;
		//check down-right corner
		while(!out_of_boundary(x,y)){
			if((brd[y][x]!=' '))
				dist++;
			x++;
			y++;
		}
		return (++dist);//remember to count the pawn (itself)!!
	case dr:// "/"
		int x2=temp.y;
		int y2=temp.x;
		x2++;
		y2++;

		//check upper-right corner
		while(!out_of_boundary(x2,y2)){
			if((brd[y2][x2]!=' '))
				dist++;
			x2++;
			y2++;
		}
		x2=temp.y;
		y2=temp.x;
		x2--;
		y2--;
		//check down-left corner
		while(!out_of_boundary(x2,y2)){
			if((brd[y2][x2]!=' '))
				dist++;
			x2--;
			y2--;
		}
		return (++dist);//remember to count the pawn!!
		return 0;
	default :
		return 0;
	}
}

/*
 * Method checks if desired point is out of matrix boundary (in 'standard' format
 */
bool Board::out_of_boundary(int x,int y){
	if(x>=0&&y>=0 && x<8 && y<8)
		return false;
	else return true;

}
