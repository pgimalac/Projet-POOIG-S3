package jeu.plateau.cases;

/**
 *	
 */

public class CaseHotel extends Case{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7025272972861574181L;
	
	public CaseHotel(){super();}

	public void arriveSurCase(int i){
		joueurs[i]=2;
	}

	public boolean peutJouer(int i){
		if(super.peutJouer()){
			if(joueurs[i]==0) return true;
			joueurs[i]--;
			return false;
		}
		return false;
	}




}