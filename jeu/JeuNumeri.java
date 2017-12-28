package jeu;

/**
 *
 */

import jeu.plateau.Plateau;

import jeu.events.GameOverEvent;

import jeu.exceptions.ChoiceException;
import jeu.exceptions.WrongOptionException;

public class JeuNumeri extends Jeu{

	private static final long serialVersionUID = -7585923130073982710L;
	private String choixPions=""; //trouver un moyen de stocker le choix des pions et pouvoir le co;parer au tir du de

	public JeuNumeri(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		super(plateau,nombreDeJoueursHumains,nombreDeJoueursIA,false);
		super.initialiserPionsJoueurs(6,null,false);// meme remarque que pour le jeu de l'oie
	}

	public JeuNumeri(int nombreDeJoueursHumains, int nombreDeJoueursIA){
		this(Plateau.getDefaultNumeri(),nombreDeJoueursHumains,nombreDeJoueursIA,false);
	}

	public static int getMinimumJoueurs(){
		return 2;
	}

	public static int getMaximumJoueurs(){
		return 6;
	}

	public Case getProchaineCaseVide(){
		int n=getPosition();//trouver un moyen de choper le numero de la case du pion qu'on souhaite bouger 
		for(int i=n; i<plateau.size(); i++){
			if(estVide(plateau.getCase(i))) return plateau.getCase(i);
		}
		return null;
	}

	private int getDerniereCaseOccupee(){
		int ppc=0;
		int caseCourante=0;
		for(Joueur joueur: this){
			for
		}
		return ppc;
	}

	@Override
	public boolean choix(){
		if(choixPions.length()==0) return true;
		return false;
	}

	@Override
	public String getChoix(){
		System.out.println
		throw new ChoiceException();
		//return "";
	}

	@Override
	public boolean choix(String entree){
		throw new ChoiceException();
		//return false;
	}

	@Override
	public void jouer(){
		Joueur joueur = joueurEnTrainDeJouer();
		int de=landerDes();


	}

	@Override
	public boolean peutJouer(Joueur joueur){
		return true;
	}

	@Override
	public boolean estFini(){
		if(getDerniereCaseOccupee()=plateau.size-((joueurs.length)*6+1)) return true;
		return false;
	}


}