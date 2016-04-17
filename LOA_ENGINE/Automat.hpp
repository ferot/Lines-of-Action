/*
 * Automat.hpp
 *
 *  Created on: 17 kwi 2016
 *      Author: Fero
 */

#ifndef AUTOMAT_HPP_
#define AUTOMAT_HPP_
 enum game_state{
	INIT=0,
	END
};

class Automat{
	 game_state GAMESTATE;
public:
	void process_game_state(game_state GS);
};


#endif /* AUTOMAT_HPP_ */
