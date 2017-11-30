package jeu;

public abstract class JeuException extends RuntimeException{
	
}

class JeuFiniException extends JeuException{
	JeuFiniException(){ super("Le jeu est fini."); }
	JeuFiniException(String message){ super(message); }
}