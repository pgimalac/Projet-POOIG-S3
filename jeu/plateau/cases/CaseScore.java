package jeu.plateau.cases;

public class CaseScore extends Case {

	/**
	 * 
	 */

	private static final long serialVersionUID = -1992087893710853030L;
	private int score;
	
	public CaseScore(int sc){super();score=sc;}

	public CaseScore(){super();score=0;}

	public int getScore(){return score;}


}
