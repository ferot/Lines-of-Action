/*
 * main.cpp
 *
 *  Created on: 20 mar 2016
 *      Author: Fero
 */
#include <iostream>
#include <assert.h>

#include "Automat.hpp"
using namespace std;
int main(){
	try{
		Color Col;


		cout<<"POINT tests"<<endl<<endl;
		Point p('A',2);
		p=p.map_coords();
		assert(p.x==0&&p.y==6);
		/*	p.x=0;
		p.y=9;
		p.map_coords();
			assert(p.x==8&&p.y==-56);//throws exc
		 */
		assert(p.get_coord('y')==6&& p.get_coord('x')==0);
		Point x;
		x.x='H';
		x.y=1;
		x=x.map_coords();
		assert(x.x==7&&x.y==7);
		//PLAYER tests
		Player p1(Col=white);
		Player p2(Col= black);
		assert(p1.get_symbol()=='O'&& p2.get_symbol()=='X');
		assert(p1.Pawns.size()==12);
		assert(p1.Pawns[0].p.x=='A'&&p1.Pawns[0].p.y==7);
		/*
		//prints values of pawns for debug purposes
		for(int i=0;i<p2.Pawns.size();i++){
			cout<<endl<<p2.Pawns[i].p.x<<" "<<p2.Pawns[i].p.y<<endl;
		}
		 */

		//
		cout<<"BOARD tests"<<endl<<endl;
		Board b(&p1,&p2);
		p1.Pawns[2].p.x='C';
		p1.Pawns[2].p.y=4;
		p2.Pawns[11].p.x='D';
		p2.Pawns[11].p.y=7;
		b.draw_pawns();
		b.show_board();
		cout<<endl<<"AUTOMAT tests"<<endl<<endl;
Automat A(&p1,&p2);
A.move_pawn(Point('A',2),Point('B',5),white);
A.move_pawn(Point('F',8),Point('D',2),black);
b.draw_pawns();
		b.show_board();
	}
	catch(int a){
		cout<<"ERRROR NR : "<<a;
	}
	catch(...){
		cout<<"default exception.."<<endl;
	}


}
