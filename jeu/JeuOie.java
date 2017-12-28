package jeu;

/**
 *
 */

import jeu.options.OptionPionCaseOie;
import jeu.options.OptionPositionFinOie;

import jeu.events.GameOverEvent;
import jeu.events.PlayEvent;

import jeu.exceptions.ChoiceException;
import jeu.exceptions.WrongOptionException;

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;
import jeu.plateau.cases.CaseDepart;

public class JeuOie extends Jeu{

	private static final long serialVersionUID = -6311358070333990328L;

	public JeuOie(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		super(plateau,nombreDeJoueursHumains,nombreDeJoueursIA,true);
		super.initialiserPionsJoueurs(1,(CaseDepart)getCase(0),true); //est ce que cette ligne est pas redondante ?

		super.addOption(new OptionPositionFinOie());
		super.addOption(new OptionPionCaseOie());
	}

	public JeuOie(int nombreDeJoueursHumains, int nombreDeJoueursIA){
		this(Plateau.getDefaultOie(),nombreDeJoueursHumains,nombreDeJoueursIA,true);
	}

	@Override
	public int lancerDes(){
		super.setDes(Jeu.des.nextInt(6)+des.nextInt(6)+2);
		return super.getDes();
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
	public boolean estFini(){
		boolean tousFini=true;
		 for(Joueur joueur : this){
		 	if (joueur.getCase().willPlay())
				return false;
			if (tousFini && !joueur.getCase().estFinale())
				tousFini=false;
		 }

		 if (!gameOverFired){
		 	if (tousFini)
		 		super.fireGameOver(new GameOverEvent(this,"tous les joueurs ont fini",super.getClassement()));
		 	else
		 		super.fireGameOver(new GameOverEvent(this,"tous les joueurs ont fini ou ne peuvent plus jouer",super.getClassement()));
		 	gameOverFired=true;
		 }
		 return true;

	}

	private boolean gameOverFired=false;

	public static int getMinimumJoueurs(){
		return 2;
	}

	public static int getMaximumJoueurs(){
		return Integer.MAX_VALUE-1;
	}

	@Override
	public void jouer(){
		Joueur joueur=super.joueurEnTrainDeJouer();
		Case tmp=getCase(joueur.getCase(),super.getDes());
		int option=super.getOption(OptionPionCaseOie.class).getIntValue();
		switch (option){
			case 0:
				break;
			case 1:
				if (!tmp.estInitiale() && !tmp.estFinale()){ // il peut y avoir plusieurs joueurs sur les cases finales et initiales
					for (Joueur j : this){
						if (j.getCase()==tmp){
							j.setCase(joueur.getCase());
							break;
						}
					}
				}
				break;
			case 2:
				while(!tmp.estFinale() && !tmp.estInitiale() && !super.estVide(tmp) && super.getCase(tmp)!=0)
					tmp=super.getCase(super.getCase(tmp)-1);
				break;
			default :
				throw new WrongOptionException(OptionPionCaseOie.class,option);
		}
		joueur.setCase(tmp);
		joueur.setScore(super.getCase(tmp));
		super.firePlay(new PlayEvent(this,joueur,super.getDes())); // TOOD
		super.classement();
		while (!estFini() && !super.joueurSuivant().estHumain()){
			jouer();
		}
	}

	@Override
	public boolean peutJouer(Joueur joueur){
		return joueur.getCase().peutJouer(joueur);
	}

	private Case getCase(Case depart, int des){
		return getCase(getCase(depart),des);
	}

	private Case getCase(int depart, int des){
		while (des!=0){
			if (depart==0 && des<0) des=0;
			else if (depart==super.getPlateau().size()-1 && des>0){
				int option=super.getOption(OptionPositionFinOie.class).getIntValue();
				switch (option){
					case 0:
						des=-des;
						break;
					case 1:
						des=0;
						break;
					default :
						throw new WrongOptionException(OptionPositionFinOie.class,option);
				}
			}else{
				depart+=Math.abs(des)/des;
				des-=Math.abs(des)/des;
			}
		}
		return getCase(depart);
	}

}