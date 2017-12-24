package jeu;

/**
 *
 */

import jeu.plateau.Plateau;
import jeu.PasDeChoixException;

public class JeuOie extends Jeu{

	private static final long serialVersionUID = -6311358070333990328L;

	public JeuOie(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		super(plateau,nombreDeJoueursHumains,nombreDeJoueursIA);
		super.initialiserPionsJoueurs(1,(CaseDepart)getCase(0));
	}

	public JeuOie(int nombreDeJoueursHumains, int nombreDeJoueursIA){
		this(Plateau.getDefaultOie(),nombreDeJoueursHumains,nombreDeJoueursIA);
	}

	@override
	public int getDes(){
		return super.getDes()+super.getDes();
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

	public static int getMinimumJoueurs(){
		return 2;
	}

	public static int getMaximumJoueurs(){
		return Integer.MAX_VALUE-1;
	}


	public void jouer(int d){
		Joueur joueur=super.joueurEnTrainDeJouer();
		joueur.setCase(super.getCase(joueur.getCase(),d));
		joueurSuivant();
	}


}