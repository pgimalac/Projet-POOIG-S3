package jeu.plateau.cases;

/**
 *	
 */

public class CaseGagnante extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1652475914965460316L;
	public CaseGagnante(int i, Case p, Case s){ super(i,p,s); }
	public CaseGagnante(int i){ this(i,null,null); }
	
}