package jeu.events;

import jeu.Jeu;

public class QuestionEvent extends GameEvent{
	private String question;

	public QuestionEvent(Jeu j, String question){
		super(j);
		this.question=question;
	}

	public String getQuestion(){
		return question;
	}
}