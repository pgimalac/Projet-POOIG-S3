package jeu.plateau.cases;

import jeu.Joueur;

/**
 *	
 */

public class CaseMort extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6125234066920613090L;

	private CaseDepart depart;

	public CaseMort(CaseDepart c){
		super();
		depart=c;
	}

	@Override
	public Case getCase(){
		return (Case)depart;
	}

	@Override
	public String toString(){
		return "mort";
	}




}