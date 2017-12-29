package jeu;

/**
 *
 */

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;
import jeu.plateau.cases.CaseDepart;

import jeu.events.GameOverEvent;
import jeu.events.PlayEvent;

import jeu.exceptions.ChoiceException;
import jeu.exceptions.WrongOptionException;

import java.util.Scanner;
import java.util.ArrayList;

public class JeuNumeri extends Jeu{

	private static final long serialVersionUID = -7585923130073982710L;
	private final ArrayList<Case> CASES_FINALES=new ArrayList<Case>();

	public JeuNumeri(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		super(plateau,nombreDeJoueursHumains,nombreDeJoueursIA,6,(CaseDepart)plateau.getCase(0));
		CASES_FINALES.add(super.getCase(40));
		CASES_FINALES.add(super.getCase(39));
		CASES_FINALES.add(super.getCase(38));
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

	private Case getProchaineCaseVide(Case c){
		int n=plateau.getCase(c);
		for(;n<plateau.size(); n++){
			if(estVide(plateau.getCase(n))) return plateau.getCase(n);
		}
		return null;
	}

	//private int getDerniereCaseOccupee(){
	//	int ppc=0;
	//	int caseCourante=0;
	//	for(Joueur joueur: this){
	//		for(int)
	//	}
	//	return ppc;
	//}

	private final static int SUM_DICES=6;
	private ArrayList<Integer> choixPions=new ArrayList<Integer>();

	@Override
	public boolean choix(){
		return choixPions.isEmpty();
	}

	@Override
	public String getChoix(){
		if (!choix())
			throw new ChoiceException();
		else 
			return "Entrer les numéros des pions à bouger séparés par un espace : ";
	}

	@Override
	public boolean choix(String entree){
		if (!choix())
			throw new ChoiceException();

		try{
			Scanner sc=new Scanner(entree);
			int e=0;
			while (sc.hasNext()){
				Integer integer=new Integer(Integer.parseInt(sc.next()));
				choixPions.add(integer);
				e+=integer.intValue();
			}
			if (e!=SUM_DICES)
				throw new NumberFormatException();
		}catch(NumberFormatException e){
			choixPions.removeAll(choixPions);
			return false;
		}
		return true;
	}

	@Override
	public void jouer(){
		if (choix())
			throw new ChoiceException("Il y a un choix à faire.");

		Joueur joueur = joueurEnTrainDeJouer();
		int de=super.lancerDes();
		for(Integer integer : choixPions){
			int i=integer.intValue()-1;
			Case tmp=getProchaineCaseVide(joueur.getCase(i));
			if (tmp!=null)
				joueur.setCase(i,tmp);
		}
		choixPions.removeAll(choixPions);

		actualiserScore(joueur);

		super.firePlay(new PlayEvent(this,joueur,super.getDes()));//je sais pas si c'est ca Pierre tu corrigeras merci <3
	}

	private void actualiserScore(Joueur joueur){
		int score=0;
		for(int i=0;i<6;i++){
			score+=(i+1)*joueur.getCase(i).getScore();
		}
		joueur.setScore(score);
	}

	@Override
	public boolean peutJouer(Joueur joueur){
		return true;
	}


	@Override
	public boolean estFini(){
		for (Case c:CASES_FINALES){
			if (super.estVide(c))
				return false;
		}
		return true;
	}



}