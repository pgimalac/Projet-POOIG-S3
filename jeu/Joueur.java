package jeu;

import jeu.plateau.cases.Case;
import jeu.JeuException;

/**
 *	
 */

public class Joueur{
	private Pion[] pions;
	private String nom;
	private int score;

	public Case getCase(){ return getCase(0); }
	public Case getCase(int i){ return pions[i].getCase(); }

	public void setCase(Case c){ setCase(0,c); }
	public void setCase(int i, Case c){ pions[i].setCase(c); }

	public Joueur(int numero){
		nom="Joueur "+numero;
		score=0;
		pions=null;
	}

	public boolean estHumain(){ return true; }

	public void setNom(String nom){ this.nom=nom; }

	public void initialiserPionsJoueurs(int nbPionsParJoueur, Case c){
		if (pions!=null) throw new PionsDejaInitialisesException(nom);
		pions=new Pions[nbPionsParJoueur];
		for (int i=0;i<nbPionsParJoueur;i++){
			pions[i]=new Pion(c);
		}
	}

	class PionsDejaInitialisesException extends JeuException{
		PionsDejaInitialisesException(String nom){ super("Les pions de "+nom+" ont déjà été initialisés !"); }
	}

	class Pion{
		private Case case;

		void setCase(Case c){ case=c; }
		Case getCase(){ return case; }

		Pion(Case case){ this.case=case; }
		Pion(){ this(null); }
	}
	
}