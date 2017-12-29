package jeu.options;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.Serializable;
import java.io.File;

public class BanqueQuestions implements Serializable{
	class Question implements Serializable{
		public final String question;
		public final String reponse;

		public Question(String q, String r){
			question=q;
			reponse=r;
		}

		public boolean isTrue(String s){
			return reponse.equals(s);
		}
	}

	private ArrayList<Question> questions=new ArrayList<Question>();

	private Random rand=new Random();

	public void randomize(){
		ArrayList<Question> liste=new ArrayList<Question>();
		liste.addAll(questions);
		questions.removeAll(questions);

		int i=liste.size();
		while (i!=0){
			i--;
			questions.add(liste.remove(rand.nextInt(i)));
		}
	}



}