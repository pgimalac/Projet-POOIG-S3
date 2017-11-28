package jeu;

/**
 *
 */

import jeu.plateau.Plateau;

public class JeuOie extends Jeu{

	private static final long serialVersionUID = -6311358070333990328L;

	public JeuOie(){
		super(new Plateau().setDefaultOie());
	}

	public void faireJouerLeJoueur(Joueur joueur){
		int nombreDes=getDes();
		joueur.avancer(nombreDes);
	}

	private int getDes(){
		return super.getDes()+super.getDes();
	}

}