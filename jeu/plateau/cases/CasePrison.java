package jeu.plateau.cases;

import jeu.Joueur;

/**
 *	
 */

public class CasePrison extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1341834908599164419L;

	public CasePrison(){
		super();
	}

	@Override
	public void arriveSurCase(Joueur j){

	}

	@Override
	public boolean peutJouer(Joueur j){
		return true;
	}

	@Override
	public String toString(){
		return "prison";
	}

	


}