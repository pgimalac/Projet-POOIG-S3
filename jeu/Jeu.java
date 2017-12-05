package jeu;

/**
 *
 *
 */

import jeu.plateau.Plateau;

import java.util.LinkedList;
import java.util.Random;

import java.io.Serializable;


public abstract class Jeu implements Serializable {

	private static final long serialVersionUID = 3350919143027733149L;

	private static final Random des=new Random();
	public static int getDes(){ return des.nextInt(6)+1; } // lancé d'un dé à 6 faces

	public static final JeuNumeri getNewNumeri(){ return new JeuNumeri(); }
	public static final JeuOie getNewOie(){ return new JeuOie(); }

	private boolean fini;
	private int numeroDuTour;
	private int enTrainDeJouer;
	private final Joueur[] joueurs;
	private final Plateau plateau;
	private final int[] classement;
	private final int nombreDeJoueurs;

	private Joueur joueurEnTrainDeJouer(){ return joueurs[enTrainDeJouer]; }
	protected Case getCase(int i){ return plateau.getCase(i); }
	protected void jeuFini(){ fini=true; }
	public boolean estFini(){ return fini; }
	public int getNumeroDuTour(){ return numeroDuTour; }

	public Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		if (plateau==null) throw new IllegalArgumentException("Le plateau n'a pas été initialisé");
		else if (nombreDeJoueursHumains<0 || nombreDeJoueursIA<0) throw new IllegalArgumentException("Le nombre d"+((nombreDeJoueursHumains<0)?"e joueurs":"'IA")+"est négatif");
		else if (nombreDeJoueursHumains+nombreDeJoueursIA>20) throw new IllegalArgumentException("Le nombre de joueurs est trop grand");

		nombreDeJoueurs=nombreDeJoueursHumains+nombreDeJoueursIA;
		numeroDuTour=0;
		fini=false;
		this.plateau=plateau;
		joueurs=new Joueur[nombreDeJoueurs];
		for (int i=0;i<nombreDeJoueursHumains;i++) if (i<nombreDeJoueursHumains) joueurs[i]=new Joueur(i+1); else joueurs[i]=new JoueurIA(i+1);
		enTrainDeJouer=0;
	}

	public Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA, int nbPionsParJoueur, Case c){
		this(plateau,nombreDeJoueursHumains,nombreDeJoueursIA);
		initialiserPionsJoueurs(nbPionsParJoueur,c);
	}

	protected void initialiserPionsJoueurs(int nbPionsParJoueur, Case c){ // doit être appelée avant de commencer à jouer
		for (Joueur joueur:joueurs)
			initialiserPionsJoueurs(nbPionsParJoueur, c);
	}


	private Joueur joueurSuivant(){
		if (jeuFini()) throw new jeuFiniException("Le jeu est fini : aucun joueur ne peut jouer.");
		enTrainDeJouer=(enTrainDeJouer+1)%nombreDeJoueurs;
		if (enTrainDeJouer==0) numeroDuTour++;

		if (joueurEnTrainDeJouer().peutJouer()) return joueurEnTrainDeJouer();
		else return joueurSuivant();
	}

	private Joueur getJoueur(int i){ return joueurs[i]; }

	public boolean peutJouer(int numeroDuJoueur){
		return true;
	}
}