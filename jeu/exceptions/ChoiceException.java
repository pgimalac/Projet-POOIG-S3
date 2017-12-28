package jeu.exceptions;

import jeu.Jeu;

public class ChoiceException extends GameException{
	public ChoiceException(){
		super();
		super.setMessage("Il n'y a pas de choix possible.");
	}
}
