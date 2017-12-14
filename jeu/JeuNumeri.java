package jeu;

/**
 *
 */

import jeu.plateau.Plateau;

public class JeuNumeri extends Jeu{

	private static final long serialVersionUID = -7585923130073982710L;

	public JeuNumeri(Plateau plateau){
		super(plateau);
		super.initialiserPionsJoueurs(6,null);
	}

	public JeuNumeri(){
		this(new Plateau().setDefaultNumeri());
	}
}