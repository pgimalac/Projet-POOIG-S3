/**
 * 
 * 
 * 
 * 
 */

package jeu;

/**
 *
 *
 */

import jeu.plateau.Plateau;
import jeu.Affichage.*;
import jeu.Options.*;

import java.util.LinkedList;

import java.io.Serializable;


public class Jeu implements Serializable {

	private LinkedList<Joueur> joueurs;
	private Plateau plateau;
	private ListeDOptions options;
	private Affichage affichage;

	public Jeu(){}
		
}