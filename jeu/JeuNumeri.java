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

	@Override
	public boolean choix(){
		return false;
	}

	@Override
	public String getChoix(){
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
	}

	@Override
	public boolean peutJouer(Joueur joueur){
		return true;
	}

	@Override
	public boolean estFini(){
		return false;
	}


}