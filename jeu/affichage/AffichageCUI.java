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
			StringBuilder sb=new StringBuilder();
			StringBuilder separation=new StringBuilder();
			int size=plateau.size();
			for (int i=0;i<size;i++){
				sb.append(centrer(LARGEUR_CASE,(i+1)+"-"+plateau.get(i).toString())+"  ");
				if (i%maximum_largeur==0 && i!=size-1){ sb.append("\n"+separation.toString()+"\n"); separation=new StringBuilder(); }
				for (int i=LARGEUR_CASE+ESPACE_CASE;i>=0;i--)
					separation.append('-');
			}
			sb.append(separation.toString());

			afficher(sb.toString());
		}
	}

	private void afficher(String s){
		
	}

	private String centrer(int largeur, String s){
		if (s.length()>largeur){
			return s.subString(0,largeur-2)+".";
		}else{
			StringBuilder sb=new StringBuilder(s);
			for (int i=largeur-s.length();i>=0;i--){
				if (i%2==0) sb.append(' ');
				else sb.insert(0,' ');				
			}
			return sb.toString();
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

	protected AffichagePlateau getDefaultAffichagePlateau(){
		return new AffichagePlateauRectangleCUI();
	}

	private static final String EFFACER="\033[H\033[2J",RESET="\u001b[0m";
	private static final String BLACKf="\u001b[30m", REDf="\u001b[31m", GREENf="\u001b[32m", YELLOWf="\u001b[33m", BLUEf="\u001b[34m", MAGENTAf="\u001b[35m", CYANf="\u001b[36m", WHITEf="\u001b[37m";
	private static final String BLACKb="\u001b[40m", REDb="\u001b[41m", GREENb="\u001b[42m", YELLOWb="\u001b[43m", BLUEb="\u001b[44m", MAGENTAb="\u001b[45m", CYANb="\u001b[46m", WHITEb="\u001b[47m";

	public AffichageCUI(){
		super.setJeu(setJeu());
		maximum_largeur=10;
		maximum_hauteur=15;
	}

	private int maximum_hauteur;
	private int maximum_largeur;

	public int getMaximumLargeur(){
		return maximum_largeur; 
	}
	public int getMaximumHauteur(){
		return maximum_hauteur;
	}

	public int setMaximumLargeur(int l){
		maximum_largeur=l;
	}
	public int setMaximumHauteur(int l){
		maximum_hauteur=l;
	}


	public void afficher(){
		int aff=super.getJeu().MAXIMUM_JOUEURS/jeu.getNbJoueurs();
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