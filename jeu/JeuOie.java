package jeu;

/**
 *
 */

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;
import jeu.plateau.cases.CaseDepart;

public class JeuOie extends Jeu{

	private static final long serialVersionUID = -6311358070333990328L;

	public JeuOie(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		super(plateau,nombreDeJoueursHumains,nombreDeJoueursIA);
		super.initialiserPionsJoueurs(1,(CaseDepart)getCase(0));
	}

	public JeuOie(int nombreDeJoueursHumains, int nombreDeJoueursIA){
		this(Plateau.getDefaultOie(),nombreDeJoueursHumains,nombreDeJoueursIA);
	}

	@Override
	protected GameOverEvent getGameOver(){
		return new GameOverOieEvent(this);
	}

	@Override
	public int getDes(){
		return super.getDes()+super.getDes();
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

	public static int getMinimumJoueurs(){
		return 2;
	}

	public static int getMaximumJoueurs(){
		return Integer.MAX_VALUE-1;
	}

	@Override
	public void jouer(){
		Joueur joueur=super.joueurEnTrainDeJouer();
		joueur.setCase(super.getCase(joueur.getCase(),super.getValeurDes()));
		joueurSuivant();
	}


}