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

	private boolean fini;
	private int numeroDuTour;
	
	private Joueur[] joueurs;
	private Plateau plateau;

	public Jeu(Plateau p){
		numeroDuTour=0;
		fini=false;
		plateau=p;
	}


	public boolean estFini(){ return fini; }
	public int getNumeroDuTour(){ return numeroDuTour; }
	
	public void jouer() {
		while (!this.estFini()){
			numeroDuTour++;
			int numeroDuJoueur=-1;
			for (Joueur joueur : joueurs){
				numeroDuJoueur++;
				if (this.estFini()) break;
				else if (peutJouer(numeroDuJoueur)) faireJouerLeJoueur(joueur);
			}
		}
	}

	private Joueur getJoueur(int i){ return joueurs[i]; }

	public abstract void faireJouerLeJoueur(Joueur joueur);
	public boolean peutJouer(int numeroDuJoueur){
		return true;
	}
}