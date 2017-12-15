package jeu;

import jeu.plateau.cases.Case;
import jeu.JeuException;

/**
 *	
 */

public class Joueur implements Comparable,Serializable{
	private Pion[] pions;
	private String nom;
	private int score;

	private final CaseDepart depart;

	public Case getCase(){ return getCase(0); }
	public Case getCase(int i){ return pions[i].getCase(); }

	public void setCase(Case c){ setCase(0,c); }
	public void setCase(int i, Case c){ pions[i].setCase(c); }

	public int getScore(){ return score; }
	public void setScore(int sc){ score=sc; }

	public boolean estHumain(){ return true; }
	public void setNom(String nom){ this.nom=nom; }
	public String toString(){ return nom; }

	public Joueur(String s){
		nom=s;
		score=0;
		pions=null;
	}

	public int compareTo(Joueur j){
		if (j==null) throw new NullPointerException();
		return ((j.getScore()==this.getScore())?0:((j.getScore()>this.getScore())?-1:1));
	}

	public void initialiserPionsJoueurs(int nbPionsParJoueur, CaseDepart c){
		depart=c;
		pions=new Pions[nbPionsParJoueur];
		for (int i=0;i<nbPionsParJoueur;i++){
			pions[i]=new Pion(c);
		}
	}

	public void recommencer(){
		score=0;
		for (Pion p : pions) p.setCase(depart);
	}

	class Pion{
		private Case case;

		void setCase(Case c){ case=c; }
		Case getCase(){ return case; }

		Pion(CaseDepart case){ this.case=case; }
		Pion(){ this(null); }
	}
	
}