package jeu.listeners;

import jeu.events.QuestionEvent;

public interface QuestionListener extends GameListener{
	public void question(QuestionEvent e);
}