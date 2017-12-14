package jeu;

/**
 *
 */

import jeu.plateau.Plateau;

public class JeuOie extends Jeu{

	private static final long serialVersionUID = -6311358070333990328L;

	public JeuOie(Plateau plateau){
		super(plateau);
		super.initialiserPionsJoueurs(1,getCase(0));
	}

	public JeuOie(){
		this(new Plateau().setDefaultOie());
	}

	private int getDes(){
		return super.getDes()+super.getDes();
	}

}