package jeu;

/**
 *
 *
 */

import jeu.plateau.Plateau;
import jeu.affichage.*;

import java.util.LinkedList;

import java.io.Serializable;


public abstract class Jeu implements Serializable {

	private static final long serialVersionUID = 3350919143027733149L;

	private boolean fini;
	private int numeroDuTour;
	
	private LinkedList<Joueur> joueurs;
	private Plateau plateau;
	private Affichage affichage;

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

	public abstract void faireJouerLeJoueur(Joueur joueur);
	public boolean peutJouer(int numeroDuJoueur){
		return true;
	}

		
}