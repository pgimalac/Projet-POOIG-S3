package jeu.plateau.cases;

import jeu.Joueur;

/**
 *	
 */

public class CaseGagnante extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1652475914965460316L;

	public CaseGagnante(){
		super();
	}

	@Override
	public void arriveSurCase(Joueur j){
		System.out.println(j+" est arrivé sur la case gagnante, BRAVO !");
	}

	@Override
	public String toString(){
		return "victoire";
	}

}