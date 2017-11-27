package jeu.plateau.cases;

public class CaseScore extends Case {

	private int score;
	
	public CaseScore(int i, Case p, Case s, int sc) {
		super(i, p, s);
		score=sc;
	}

	public CaseScore(int i, int s) {
		this(i,null,null,s);
	}

	public int getScore(){return score;}

}
