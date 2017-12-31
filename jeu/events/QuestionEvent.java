package jeu.events;

import jeu.Jeu;

public class QuestionEvent extends GameEvent{
	private String question;
	private String reponse;

	public QuestionEvent(Jeu j, String question, String reponse){
		super(j);
		this.question=question;
		this.reponse=reponse;
	}

	public String getQuestion(){
		return question;
	}

	public String getReponse(){
		return reponse;
	}
}