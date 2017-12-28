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
	private String choixPions="";

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

	public Case getProchaineCaseVide(Case c){
		int n=plateau.getCase(c);
		for(int i=n; i<plateau.size(); i++){
			if(estVide(plateau.getCase(i))) return plateau.getCase(i);
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

	@Override
	public boolean choix(){
		if(choixPions.length()==0) return true;
		return false;
	}

	@Override
	public String getChoix(){
		try{
			System.out.println("Quels pions voulez vous selectionner? Ne pas mettre d'espace entre les numeros");
			Scanner sc=new Scanner(System.in);
			return sc.next();
		}
		throw new ChoiceException();
		return "";
	}

	@Override
	public boolean choix(String entree,int n){
		try{
			int e=0;
			for(int i=0;i<entree.length();i++){
				e+=Character.getNumericValue(entree.charAt(i));
			}
			if(e==n) {
				choixPions=entree;
				return true;
			}
			return false;
		}
		throw new ChoiceException();
		return false;
	}

	@Override
	public void jouer(){
		Joueur joueur = joueurEnTrainDeJouer();
		int de=landerDes();
		while(choix()){
			while(!choix(getChoix,de)){}
		}
		for(int i=0;i<choixPions.length();i++){
			joueur.setCase(i,plateau.getProchaineCaseVide(joueur.getCase(i)));
		}
		choixPions="";
		int score=0;
		for(int i=0;i<7;i++){
			score+=i*joueur.getCase(i).getScore();
		}
		joueur.setScore(score);
		super.firePlay(new PlayEvent(this,joueur,super.getDes()));//je sais pas si c'est ca Pierre tu corrigeras merci <3
		while (!estFini() && !super.joueurSuivant().estHumain()){
			jouer();
		}
	}

	@Override
	public boolean peutJouer(Joueur joueur){
		return true;
	}

	@Override
	public boolean estFini(){
		boolean b1=false;
		boolean b2=false;
		boolean b3=false;
		for(Joueur joueur : joueur){
			for(Case cases : joueur){
				if (cases==plateau.getCase(39)) b1=true;
				if(cases==plateau.getCase(38)) b2=true;
				if(cases==plateau.getCase(37)) b3=true;
			}
		}
		return (b1&&b2&&b3);
	}



}