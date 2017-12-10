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
		help();
		menu();
	}

	public void afficher(){
		while (!jeu.estFini()){

		}
		System.out.println(jeu.getClassement()+"\n");		
	}

	public Jeu getJeu(){
		System.out.print("Entrez 'o' pour un jeu de l'oie, 'n' pour un numéri et autre chose pour quitter.");
		String s=sc.nextLine();
		if (s==null || s.length()==0) return null;
		char c=s.toLowerCase().charAt(0);
		Jeu jeu=null;
		if (c=='o') jeu=new JeuOie();
		else if (c=='n') jeu=new JeuNumeri();
		super.setJeu(jeu);
		return jeu;
	}

	private void help(){
		System.out.println("Bienvenue dans ce jeu !");
		System.out.println("Vous pouvez jouer au jeu de l'oie ou au numéri, et parametrer de nombreuses options.");
		System.out.println("En mode console il n'est pas possible de créer un plateau personnalisé mais les options sont toujours modifiables.");
		System.out.println(" Le choix que vous devez faire tout d'abord est : jeu de l'oie ou numéri ?");
	}
}