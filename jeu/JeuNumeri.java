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

import jeu.options.OptionFaceSuppNumeri;
import jeu.options.OptionAlignementNumeri;

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

	private int SUM_DICES=6;
	private boolean pionEnnemi=false;
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
			if(pionEnnemi) return "Entrer le numero de la case ou se trouve le pion adverse que vous souhaitez deplacer :";
			return "Entrer les numéros des pions à bouger séparés par un espace : ";
	}

	@Override
	public boolean choix(String entree){
		if (!choix())
			throw new ChoiceException();

		try{
			Scanner scan=new Scanner(entree);
			int e=0;
			while (scan.hasNext()){
				Integer integer=new Integer(Integer.parseInt(scan.next()));
				choixPions.add(integer);// ici il faudra faire un truc pour check qu'on a pas mis plusieurs fois un meme pion 
				e+=integer.intValue();
			}
			if(pionEnnemi){
				return pionEnnemi()//trouver un moyen de passer le joueur actuel
			}
			else if (e!=SUM_DICES)
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
		int optionDe=super.getOption(OptionFaceSuppNumeri.class).getIntValue();
		switch(optionDe){
			case 0:
				SUM_DICES=super.lancerDes();
				break;
			case 1: 
				Random r =new Random();
				SUM_DICES=r.nextInt(7);
				if(SUM_DICES==0){
					//choix d'un pion ennemi
				}
				break;
			}
			for(Integer integer : choixPions){
				int i=integer.intValue()-1;
				Case tmp=getProchaineCaseVide(joueur.getCase(i));
				if (tmp!=null)
					joueur.setCase(i,tmp);
			}
		}
		choixPions.removeAll(choixPions);

		actualiserScore(joueur);

		int optionAli=super.getOption(OptionAlignementNumeri.class).getIntValue();
		switch(optionAli){
			case 0 : break;
			case 1 : if (pionsAlignes()){
						jouer();
					}
					break;
		}

		super.firePlay(new PlayEvent(this,joueur,super.getDes()));//je sais pas si c'est ca Pierre tu corrigeras merci <3
	}

	private boolean pionsAlignes(Joueur joueur){
		for(int i=0;i<7;i++){
			if(casesAutour(joueur.getCase(i),joueur)) return true;
		}
		return false;
	}

	private boolean casesAutour(Case c, Joueur j){
		boolean avant=false;
		boolean apres=false;
		for (int i=0;i<7 ;i++ ) {
			if(getCase(j.getCase(i))==getCase(c)-1)avant=true;
			if(getCase(j.getCase(i))==getCase(c)+1)apres=true;			
		}
		return (avant&&apres);
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