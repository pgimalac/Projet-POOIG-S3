package jeu.options;

import jeu.Jeu;

import jeu.exceptions.OptionException;
import jeu.exceptions.WrongOptionException;

public abstract class Option{
	protected String option;
	protected int valeur;
	protected String[] valeurs;
	private Jeu jeu;

	protected void setJeu(Jeu j){
		if (jeu==null)
			jeu=j;
		else
			throw new OptionException("Le jeu a déjà été initialisé.");
	}

	public boolean checkJeu(Jeu j){
		return jeu==j;
	}

	public String toString(){
		return option;
	}

	public String[] getValues(){
		return valeurs;
	}

	public String getValue(){
		return valeurs[valeur];
	}

	public int getIntValue(){
		return valeur;
	}

	public void setValue(int i){
		if (jeu==null)
			throw new OptionException("Le jeu n'a pas été initialisé.");
		else if (jeu.partieCommencee())
			throw new OptionException("On ne peut modifier une option après le début de la partie.");
		if (i<0 || i>valeurs.length)
			throw new WrongOptionException(this.getClass(),i);
		else
			valeur=i;
	}
}
