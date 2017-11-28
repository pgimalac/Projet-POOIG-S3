package jeu.plateau.cases;

/**
 *	
 */

public class CaseLabyrinthe extends Case{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7985409714027022714L;
	
	private  int destination;

	public CaseLabyrinthe(){super();destination=30;}

	public CaseLabyrinthe(int d){super();destination=d;}

	public int getDestination(){ return destination; }

}