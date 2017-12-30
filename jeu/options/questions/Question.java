package jeu.options.questions;

import java.io.Serializable;

public class Question implements Serializable{
	private final String question;
	private final String reponse;

	public Question(String q, String r){
		question=q;
		reponse=r;
	}

	public boolean isTrue(String s){
		return reponse.equals(s);
	}

	public String getQuestion(){
		return question;
	}

	public String getReponse(){
		return reponse;
	}
}