package jeu.plateau.cases;

import jeu.Joueur;

/**
 *	
 */

public class CaseHotel extends Case{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7025272972861574181L;
	
	public CaseHotel(){
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
		return "hôtel";
	}





}