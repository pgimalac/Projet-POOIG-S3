package jeu.affichage;

/**
 *	L'affichage en console	
 */

import jeu.Jeu;
import jeu.JeuOie;
import jeu.JeuNumeri;

import java.util.Scanner;

public class AffichageCUI implements Affichage{

	private static Scanner sc=new Scanner(System.in);

	public AffichageCUI(){
		System.out.println("Bienvenue dans ce jeu !");
		System.out.println("Il s'agit d'un jeu de l'oie très personnalisable : basiquement c'est un jeu de plateau au tracé relativement linéaire (un départ, un ensemble de cases ordonnées, un parcours par défaut du plateau menant à au moins une case finale.");
		System.out.println("En mode console il n'est pas possible de beaucoup personnaliser sa partie, le jeu est relativement limité mais certaines options sont tout de même paramétrables. Le choix que vous devez faire tout d'abord est : jeu de l'oie ou numéri ?");
	}

	public Jeu getJeu(){
		System.out.print("Entrez 'o' pour un jeu de l'oie, 'n' pour un numéri et autre chose pour quitter.");
		String s=sc.nextLine();
		if (s==null || s.length()==0) return null;
		char c=s.toLowerCase().charAt(0);
		if (c=='o') return new JeuOie();
		else if (c=='n') return new JeuNumeri();
		else return null;
	}
}