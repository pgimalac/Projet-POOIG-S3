package jeu;

/**
 *
 */

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;
import jeu.plateau.cases.CaseDepart;

public class JeuOie extends Jeu{

	private static final long serialVersionUID = -6311358070333990328L;

	class OptionPositionFinOie extends Option{
		public OptionPositionFinOie(){
			super.option="position finale pour gagner";
			String[] s=new String[2];
			s[0]="au moins la case d'arrivée";
			s[1]="exactement la case d'arrivée";
			super.valeurs=s;
			super.setValue(0);
		}
	}

	class OptionPionCaseOie extends Option{
		public OptionPionCaseOie(){
			super.option="arrivée d'un pion sur une case occupée";
			String[] s=new String[3];
			s[0]="rien ne se passe (plusieurs pions sont sur la même case)";
			s[1]="les deux pions échangent de place";
			s[2]="le pion qui arrive recule jusqu'à une case libre";
			super.valeurs=s;
			super.setValue(0);
		}
	}


	public JeuOie(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		super(plateau,nombreDeJoueursHumains,nombreDeJoueursIA);
		super.initialiserPionsJoueurs(1,(CaseDepart)getCase(0));

		super.addOption(new OptionPositionFinOie());
		super.addOption(new OptionPionCaseOie());
	}

	public JeuOie(int nombreDeJoueursHumains, int nombreDeJoueursIA){
		this(Plateau.getDefaultOie(),nombreDeJoueursHumains,nombreDeJoueursIA);
	}

	@Override
	protected GameOverEvent getGameOver(){
		return new GameOverOieEvent(this);
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