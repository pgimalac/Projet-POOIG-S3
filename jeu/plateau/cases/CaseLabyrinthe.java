package jeu.plateau.cases;

/**
 *	
 */

public class CaseLabyrinthe extends Case{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7985409714027022714L;
	private int destination;

	public CaseLabyrinthe(int i, Case p, Case s, int d){ super(i,p,s); destination=d; }
	public CaseLabyrinthe(int i, int d){ this(i,null,null,d); }

	public void setDestination(int i){ destination=i; }
	public int getDestination(){ return destination; }

}