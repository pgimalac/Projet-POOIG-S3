package jeu;

/**
 *
 */

import jeu.plateau.Plateau;
import jeu.options.ListeDOptions;

public class JeuOie extends Jeu{

	private static final long serialVersionUID = -6311358070333990328L;

	public JeuOie(){
		super(new Plateau().setDefaultOie());
	}

}