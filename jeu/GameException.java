package jeu;

import jeu.Jeu;

public abstract class GameException extends RuntimeException{
	String message;

	protected void setMessage(String s){
		message=s;
	}
}

class GameOverException extends GameException{
	GameOverException(){
		this("Le jeu est fini.");
	}

	GameOverException(String m){
		super();
		super.setMessage(m);
	}
}

class ChoiceException extends GameException{
	ChoiceException(){
		super();
		super.setMessage("Il n'y a pas de choix possible.");
	}
}