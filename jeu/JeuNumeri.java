package jeu;

/**
 *
 */

import jeu.plateau.Plateau;

public class JeuNumeri extends Jeu{

	private static final long serialVersionUID = -7585923130073982710L;

	public JeuNumeri(){
		super(new Plateau().setDefaultNumeri());
		super.initialiserPionsJoueurs(6,null);
	}

	public void faireJouerLeJoueur(Joueur joueur){
		
	}
}