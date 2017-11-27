package jeu.plateau.cases;

/**
 *	La classe représentant une case 'par défaut' (une case qui n'a rien de particulier) dont les autres cases vont hériter
 */

public class Case{

	// pas final car un éventuel outil de création intéractif de plateau pourrait obliger à changer ce numéro au fur et à mesure de la création...
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