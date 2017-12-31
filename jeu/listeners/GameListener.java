package jeu.listeners;

import jeu.events.QuestionEvent;

public interface GameListener extends java.util.EventListener{
	public void question(QuestionEvent e);
}