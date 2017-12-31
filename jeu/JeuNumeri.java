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

	public JeuNumeri(Plateau plateau, int nombreDeJoueursHumains){
		super(plateau,nombreDeJoueursHumains,6,(CaseDepart)plateau.getCase(0));
		CASES_FINALES.add(super.getCase(40));
		CASES_FINALES.add(super.getCase(39));
		CASES_FINALES.add(super.getCase(38));

		super.addOption(new OptionAlignementNumeri(this));
		super.addOption(new OptionFaceSuppNumeri(this));

	}

	public JeuNumeri(int nombreDeJoueursHumains){
		this(Plateau.getDefaultNumeri(),nombreDeJoueursHumains);
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

	private int pionEnnemi=-1;
	private ArrayList<Integer> choixPions=new ArrayList<Integer>();

	@Override
	public boolean choix(){
		if (super.getDes()==0 && !unPionPeutReculer(joueurEnTrainDeJouer()))
			return false;
		return (super.getDes()==0 && pionEnnemi==-1) || (super.getDes()!=0 && choixPions.isEmpty());
	}

	@Override
	public String getChoix(){
		if (!choix())
			throw new ChoiceException();
		else{
			if(super.getDes()==0 && pionEnnemi==-1) 
				return "Entrer le numero de la case ou se trouve le pion adverse que vous souhaitez deplacer : ";
			else if (super.getDes()!=0 && choixPions.isEmpty())
				return "Entrer les numéros des pions à bouger séparés par un espace : ";
		}
		throw new ChoiceException("Problème avec le choix.");
	}

	@Override
	public boolean choix(String entree){
		if (!choix())
			throw new ChoiceException();
		else if (super.getDes()==0 && pionEnnemi==-1){
			return (pionEnnemi>0 && pionEnnemi<super.plateau.size() && !super.estVide(super.getCase(pionEnnemi)) && !joueurEnTrainDeJouer().estSurCase(super.plateau.getCase(pionEnnemi)));
		}else if (super.getDes()!=0 && choixPions.isEmpty()){
			try{
				Scanner scan=new Scanner(entree);
				int e=0;
				while (scan.hasNext()){
					Integer integer=Integer.valueOf(scan.next());

					if (choixPions.contains(integer))
						throw new NumberFormatException();

					choixPions.add(integer);
					e+=integer.intValue();
				}
				if (e!=super.getDes())
					throw new NumberFormatException();
				else
					return true;
			}catch(NumberFormatException e){
				choixPions.removeAll(choixPions);
				return false;
			}
		}else{
			throw new ChoiceException("Problème avec le choix.");
		}
	}

	@Override
	public int lancerDes(){
		int option=super.getOption(OptionFaceSuppNumeri.class).getIntValue();
		int d=-1;
		switch (option){
			case 0:
				super.setDes(super.des.nextInt(6)+1);
				break;
			case 1:
				do{
					super.setDes(super.des.nextInt(7));
				}while(d==0 && !unPionPeutReculer(joueurEnTrainDeJouer()));
				break;
			default:
				throw new WrongOptionException(OptionFaceSuppNumeri.class,option);
		}
		return super.getDes();
	}

	@Override
	public void jouer(){
		if (choix())
			throw new ChoiceException("Il y a un choix à faire.");

		int d=super.getDes();
		Joueur joueur=joueurEnTrainDeJouer();

		if (d==0){
			Case c=super.getCase(pionEnnemi);
			for (Joueur j : this){
				if (j==joueur)
					continue;
				int i=0;
				for (Case cc : j){
					if (cc!=c)
						i++;
					else{
						do{
							pionEnnemi--;
						}while(pionEnnemi!=0 && !super.estVide(super.getCase(pionEnnemi)));
						j.setCase(i,super.getCase(pionEnnemi));
						actualiserScore(j);
						pionEnnemi=-1;
						return;
					}
				}
			}
		}else{
			for(Integer integer : choixPions){
				int i=integer.intValue()-1;
				Case tmp=getProchaineCaseVide(joueur.getCase(i));
				if (tmp!=null)
					joueur.setCase(i,tmp);
			}
			choixPions.removeAll(choixPions);

			actualiserScore(joueur);

			int optionAli=super.getOption(OptionAlignementNumeri.class).getIntValue();
			switch(optionAli){
				case 1 : 
					if (pionsAlignes(joueur))
						break;
				case 0 :
					super.joueurSuivant();
					break;
				default :
					throw new WrongOptionException(OptionAlignementNumeri.class,optionAli);
			}
			super.firePlay(new PlayEvent(this,joueur,super.getDes()));//je sais pas si c'est ca Pierre tu corrigeras merci <3
		}
	}

	private boolean unPionPeutReculer(Joueur joueur){
		// renvoie si un pion n'appartenant pas à ce joueur peut reculer
		for (Joueur j : this){
			if (j==joueur)
				continue;
			for (Case c : j){
				if (c!=super.getCase(0))
					return true;
			}
		}
		return false;
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