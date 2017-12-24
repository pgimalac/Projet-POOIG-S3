package jeu;

import jeu.Jeu;

public abstract class JeuException extends RuntimeException{	
}

class JeuFiniException extends JeuException{
	String message;
	JeuFiniException(){
		this("Le jeu est fini.");
	}

	JeuFiniException(String m){
		super();
		message=m;
	}
}

class PasDeChoixException extends JeuException{
	PasDeChoixException(){
		super("Il n'y a pas de choix possible.");
	}
}