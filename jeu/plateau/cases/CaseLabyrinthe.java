package jeu.plateau.cases;

/**
 *	
 */

public class CaseLabyrinthe extends Case{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7985409714027022714L;
	
	private Case destination;

	public CaseLabyrinthe(Case c){
		super();
		destination=c;
	}

	public Case getDestination(){ return destination; }

	public void arriveSurCase(int i){}

	public Case getCase(){
		return destination;
	}

}