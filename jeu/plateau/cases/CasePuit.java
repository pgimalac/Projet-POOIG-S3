package jeu.plateau.cases;

import jeu.Joueur;

/**
*/

public class CasePuit extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4826365605668224665L;


	public CasePuit(){
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
		return "puit";
	}
	
}