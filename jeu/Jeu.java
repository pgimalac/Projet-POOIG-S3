package jeu;

/**
 *
 *
 */

import jeu.plateau.Plateau;
import jeu.affichage.*;
import jeu.options.*;

import java.util.LinkedList;

import java.io.Serializable;


public class Jeu implements Serializable {

	private static final long serialVersionUID = 3350919143027733149L;
	
	private LinkedList<Joueur> joueurs;
	private Plateau plateau;
	private ListeDOptions options;
	private Affichage affichage;

	public Jeu(Plateau p, ListeDOptions o){
		plateau=p;
		options=o;
	}
	
	public void jouer() {
		
	}
		
}