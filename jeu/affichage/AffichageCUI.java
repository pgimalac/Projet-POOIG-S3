package jeu.affichage;

/**
 *	L'affichage en console	
 */

import jeu.Jeu;
import jeu.JeuOie;
import jeu.JeuNumeri;

import java.util.Scanner;

public class AffichageCUI extends Affichage{

	private static Scanner sc=new Scanner(System.in);

	class AffichagePlateauSpiraleCUI implements AffichagePlateau{
		private String plateauS;

		{
			plateauS=null;
		}

		public void afficher(Plateau plateau){
			if (plateauS!=null) System.out.println(plateauS);
			StringBuilder sb=new StringBuilder();

		}
	}

	class AffichagePlateauRectangleCUI implements AffichagePlateau{
		private String plateauS;

		{
			plateauS=null;
		}

		public void afficher(Plateau plateau){
			if (plateauS!=null) System.out.println(plateauS);
			StringBuilder sb=new StringBuilder();

		}
	}

	class AffichagePlateauZigzagCUI implements AffichagePlateau{
		private String plateauS;

		{
			plateauS=null;
		}

		public void afficher(Plateau plateau){
			if (plateauS!=null) System.out.println(plateauS);
			StringBuilder sb=new StringBuilder();

		}
	}

	class AffichagePlateauColonneCUI implements AffichagePlateau{
		private String plateauS;

		{
			plateauS=null;
		}

		public void afficher(Plateau plateau){
			if (plateauS!=null) System.out.println(plateauS);
			StringBuilder sb=new StringBuilder();

		}
	}

	public AffichageCUI(){
		super.setJeu(setJeu());
	}

	public static int MAXIMUM_LARGEUR;
	public static int MAXIMUM_HAUTEUR;

	public int getMaximumLargeur(){
		return MAXIMUM_LARGEUR; 
	}
	public int getMaximumHauteur(){
		return MAXIMUM_HAUTEUR;
	}


	public void afficher(){
		int aff=Jeu.MAXIMUM_JOUEURS/jeu.getNbJoueurs();
		while (!jeu.estFini()){
			if (jeu.getNumeroDuTour()%aff==0) afficher(jeu);
			else System.out.println(jeu.getClassement());

			System.out.println(jeu);
		}
		System.out.println(jeu.getClassement()+"\n");		
	}

	private Jeu setJeu(){
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
		System.out.println("Le choix que vous devez faire tout d'abord est : jeu de l'oie ou numéri ?");
		System.out.print("Entrez 'o' pour un jeu de l'oie, 'n' pour un numéri et autre chose pour quitter.");
	}
}