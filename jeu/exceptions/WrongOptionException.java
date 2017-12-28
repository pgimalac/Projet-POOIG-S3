package jeu.exceptions;

import jeu.Jeu;
import jeu.options.Option;

public class WrongOptionException extends GameException{
	private Class option;
	private int value;
	public WrongOptionException(Class<? extends Option> c, int valeur){
		option=c;
		value=valeur;
	}

	public String toString(){
		return "L'option "+option.getName()+" n'a pas pour valeur possible "+value+".";
	}
}