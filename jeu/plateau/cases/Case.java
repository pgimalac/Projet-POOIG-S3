package jeu.plateau.cases;

import java.io.Serializable;

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

	protected int[] joueurs;

	public Case(){ joueurs=null;}

	public void setJ(int n){joueurs= new int[n];}

	public boolean peutJouer(){
		return true;
	}

	public Case getCase(){ // renvoie la case vers laquelle cette case renvoie (@overide pour CasePont)
		return this;
	}

	public void arriveSurCase(int i){}	
}