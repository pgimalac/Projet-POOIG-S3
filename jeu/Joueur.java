package jeu;

import jeu.plateau.cases.Case;

/**
 *	
 */

public class Joueur{
	private Pion[] pions;
	private String nom;
	private int score;

	class Pion{
		private Case case;

		void setCase(Case c){ case=c; }
		Case getCase(){ return case; }
		Pion(){ case=null; }
	}


	
	
}