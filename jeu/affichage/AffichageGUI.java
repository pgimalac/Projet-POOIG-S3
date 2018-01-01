package jeu.affichage;

import jeu.Jeu;
import jeu.JeuOie;
import jeu.JeuNumeri;
import jeu.Joueur;

//import jeu.listeners.GameListener;
//import jeu.listeners.GameOverListener;
//import jeu.listeners.CannotPlayListener;
//import jeu.listeners.PlayListener;

//import jeu.events.GameEvent;
//import jeu.events.GameOverEvent;
//import jeu.events.CannotPlayEvent;
//import jeu.events.PlayEvent;

import jeu.options.Option;
import jeu.options.questions.Question;

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;

//import java.util.ArrayList;

public class AffichageGUI extends Affichage{
	
	public int getMaximumLargeur(){
		return -1;
	}

	public int getMaximumHauteur(){
		return -1;
	}

	public void afficher(){

	}

	protected AffichagePlateau getDefaultAffichagePlateau(){
		return null;
	}

	public void question(Question q){

	}

	protected void display(String s){

	}


}