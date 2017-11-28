package jeu.plateau.cases;

/**
 *	Case dotée d'un coefficient multiplicatif n : si l'on fait x avec le(s) dé(s) et qu'on arrive sur cette case alors on avance à nouveau de x(n-1)
 */

public class CaseOie extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5595502758871248475L;
	private int coefficient;

	public CaseOie(int i, Case p, Case s){ super(i,p,s); }
	public CaseOie(int i){ this(i,null,null); }

	public int getCoefficient(){ return coefficient; }
	public void setCoefficient(int i){ coefficient=i; }



}