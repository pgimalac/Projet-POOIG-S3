package jeu.options;

import jeu.exceptions.WrongOptionException;

public abstract class Option{
	protected String option;
	protected int valeur;
	protected String[] valeurs;

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
		if (i<0 || i>valeurs.length)
			throw new WrongOptionException(this.getClass(),i);
		valeur=i;
	}
}
