package jeu;

import jeu.Jeu;

import java.util.EventObject;

public class GameEvent extends EventObject{
	public GameEvent(Jeu jeu){
		super(jeu);
	}
}

class GameOverEvent extends GameEvent{
	private String raison;

	public GameOverEvent(Jeu j){
		super(j);
	}

	protected void setRaison(String s){
		raison=s;
	}

	public String toString(){
		return raison;
	}
}

class GameOverNumeriEvent extends GameOverEvent{
	public GameOverNumeriEvent(Jeu j){
		super(j);
		super.setRaison("Les cases finales sont toutes occupées. "+j.getGagnant()+" a gagné !");
	}
}

class GameOverOieEvent extends GameOverEvent{
	public GameOverOieEvent(Jeu j){
		super(j);
		super.setRaison(j.getRaisonFin());
	}
}

class CannotPlayEvent extends GameEvent{
	public Joueur getJoueur(){
		return joueur;
	}

	private Joueur joueur;

	public CannotPlayEvent(Jeu jeu, Joueur joueur){
		super(jeu);
		this.joueur=joueur;
	}
}