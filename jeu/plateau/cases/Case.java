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

	public boolean peutJouer(int i){
		if(i>=joueurs.length || i<0) return false 
		else return true; 
	}

	public void arriveSurCase(int i){}
	
}