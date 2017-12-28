package jeu.events;

import jeu.Jeu;
import jeu.Joueur;

public class CannotPlayEvent extends GameEvent{
	public Joueur getJoueur(){
		return joueur;
	}

	private Joueur joueur;

	public CannotPlayEvent(Jeu jeu, Joueur joueur){
		super(jeu);
		this.joueur=joueur;
	}
}

