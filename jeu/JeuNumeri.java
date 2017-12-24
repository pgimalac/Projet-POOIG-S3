package jeu;

/**
 *
 */

import jeu.plateau.Plateau;

public class JeuNumeri extends Jeu{

	private static final long serialVersionUID = -7585923130073982710L;

	public JeuNumeri(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		super(plateau,nombreDeJoueursHumains,nombreDeJoueursIA);
		super.initialiserPionsJoueurs(6,null);
	}

	public JeuNumeri(int nombreDeJoueursHumains, int nombreDeJoueursIA){
		this(Plateau.getDefaultNumeri(),nombreDeJoueursHumains,nombreDeJoueursIA);
	}

	public static int getMinimumJoueurs(){
		return 2;
	}

	public static int getMaximumJoueurs(){
		return 6;
	}

	public boolean choix(int d){
		return false;
	}

	public String choix(){
		throw new PasDeChoixException();
		return "";
	}

	public boolean choix(int d, String entree){
		throw new PasDeChoixException();
		return false;
	}

	public void jouer(int d){
		Joueur joueur=super.joueurEnTrainDeJouer();
		joueur.setCase(super.getCase(joueur.getCase(),d));
		joueurSuivant();
	}


}