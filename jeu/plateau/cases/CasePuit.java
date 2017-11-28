/**
	
*/

package jeu.plateau.cases;

public class CasePuit extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4826365605668224665L;

	public CasePuit(int i, Case p, Case s){ super(i,p,s); }
	
	public CasePuit(int i){ this(i,null,null); }
	
}