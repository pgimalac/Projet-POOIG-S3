package jeu.events;

import jeu.Jeu;

import java.util.EventObject;

public class GameEvent extends EventObject{
	public GameEvent(Jeu jeu){
		super(jeu);
	}
}
