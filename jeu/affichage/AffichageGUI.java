package jeu.affichage;

/**
 *	L'affichage graphique	
 */

public class AffichageGUI extends Affichage{
	
	public int getMaximumLargeur(){
		return -1;
	}

	public int getMaximumHauteur(){
		return -1;
	}

	public void afficher(){

	}

	protected AffichagePlateau getDefaultAffichagePlateau(){
		return null;
	}


}