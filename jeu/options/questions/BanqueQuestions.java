package jeu.options.questions;

import jeu.exceptions.QuestionException;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.Serializable;
import java.io.File;
import java.io.FileNotFoundException;

public class BanqueQuestions implements Serializable{

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

	public Question get(){
		Question q=questions.remove(0);
		questions.add(q);
		return q;
	}

	public BanqueQuestions(String s){
		File liste=new File(s);
		boolean b=liste.exists() && liste.isFile() && liste.canRead();
		if (b){
			Scanner scan;
			try{
				scan=new Scanner(liste);
				while(scan.hasNextLine())
					questions.add(new Question(scan.nextLine(),scan.nextLine()));
			}catch(FileNotFoundException e){
				b=false;
			}
		}
		if (!b){
			throw new QuestionException("Problème avec le fichier de questions");
		}
	}

	public BanqueQuestions(){
		this("./jeu/options/questions/Questions.txt");
	}

}