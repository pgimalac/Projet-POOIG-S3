package jeu.plateau.cases;

import java.io.Serializable;

/**
 * Cette classe représente une case du plateau sans aucune particularité.
 * @see Plateau
 * 
 * @author Pierre
 */

public class Case implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -905604265593242877L;
	
	// pas final car un éventuel outil de création interactif de plateau pourrait obliger à changer ce numéro au fur et à mesure de la création...

	/**
	 * 
	 */
	private int numero; 
	private Case suivante;
	
	
	private Case precedente;

	public Case(int i, Case p, Case s){
		numero=i;
		precedente=p;
		suivante=s;
	}

	public Case(int i){ this(i,null,null); }

	public Case getNext(){ return suivante; }
	public Case getPrevious(){ return precedente; }
	public void setNext(Case c){ suivante=c; }
	public void setPrevious(Case c){ precedente=c; }

	public boolean peutBouger(){ return false; }
	public int getDestination(){ return numero; }
	
}