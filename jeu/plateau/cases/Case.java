package jeu.plateau.cases;

import java.io.Serializable;

import jeu.Joueur;

/**
 * Cette classe représente une case du plateau sans aucune particularité.
 * @see Plateau
 * 
 * @author Pierre
 */

public class Case implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -905604265593242877L;
	
	/**
	 * 
	 */

	public Case(){

	}

	public int getScore(){
		return 0;
	}

	public boolean peutJouer(Joueur j){
		return true;
	}

	public String toString(){
		return "normale";
	}

	public Case getCase(){ // renvoie la case vers laquelle cette case renvoie (@overide pour CasePont,...)
		return this;
	}

	public void arriveSurCase(Joueur j){

	}	
}