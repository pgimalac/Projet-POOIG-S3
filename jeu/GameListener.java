package jeu;

public interface GameListener extends java.util.EventListener{
}

interface GameOverListener extends GameListener{
	void GameOver(GameOverEvent e);
}

interface CannotPlayListener extends GameListener{
	void cannotPlay(CannotPlayEvent e);
}