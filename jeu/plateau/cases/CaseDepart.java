package jeu.plateau.cases;

/**
 *	
 */

public class CaseDepart extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8398217857639434969L;

	public CaseDepart(int i, Case p, Case n){ super(i,p,n);	}
	
	public CaseDepart(int i){ this(i,null,null); }
}