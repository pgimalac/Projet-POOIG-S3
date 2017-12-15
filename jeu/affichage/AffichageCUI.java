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

		public void afficher(Plateau plateau){
		}
	}

	class AffichagePlateauRectangleCUI implements AffichagePlateau{
		private String plateauS=null;

		public void afficher(Plateau plateau){
			if (plateauS==null){
				StringBuilder sb=new StringBuilder(BLACKf);
				StringBuilder separation=new StringBuilder();
				int size=plateau.size();
				for (int i=0;i<size;i++){
					sb.append(COLORS[i%COLORS.length]+centrer(LARGEUR_CASE,(i+1)+"-"+plateau.getCase(i).toString())+"  ");
					if (i%maximum_largeur==0 && i!=size-1){ sb.append("\n"+separation.toString()+"\n"); separation=new StringBuilder(WHITEb); }
					for (int i=LARGEUR_CASE+ESPACE_CASE;i>=0;i--)
						separation.append('-');
				}
				sb.append(separation.toString()+RESET);

				plateauS=sb.toString();
			}
			System.out.println(plateauS);
		}
	}

	class AffichagePlateauZigzagCUI implements AffichagePlateau{
		private String plateauS;

		public void afficher(Plateau plateau){

		}
	}

	class AffichagePlateauColonneCUI implements AffichagePlateau{
		private String plateauS;

		public void afficher(Plateau plateau){

		}
	}

	protected AffichagePlateau getDefaultAffichagePlateau(){
		return new AffichagePlateauRectangleCUI();
	}

	public static final String EFFACER,RESET;
	public static final String BLACKf, REDf, GREENf, YELLOWf, BLUEf, MAGENTAf, CYANf, WHITEf;
	public static final String BLACKb, REDb, GREENb, YELLOWb, BLUEb, MAGENTAb, CYANb, WHITEb;
	private static final String[] COLORS;

	public static final String HELP="Les commandes attendues sont soit indiquées explicitement soit entre parenthèses.\nTaper autre chose que ce qui est attendu ne fera rien.\nTaper q pour quitter ou m pour accéder au menu (sauf cas particuliers, si une réponse est attendue par exemple).\n";
	public static final char MENU='m',QUITTER='q',SAUVEGARDER='s',CONTINUER='c',RECOMMENCER='r',CHARGER='l',NOUVEAU='n',CREDITS='z'/*,MODIFIER_PARAM='p'*/;

	static{
		if (System.getProperty("os.name").startsWith("Windows")){
			EFFACER="",RESET="";
			BLACKf="", REDf="", GREENf="", YELLOWf="", BLUEf="", MAGENTAf="", CYANf="", WHITEf="";
			BLACKb="", REDb="", GREENb="", YELLOWb="", BLUEb="", MAGENTAb="", CYANb="", WHITEb="";
			COLORS=new String[1];
			COLORS[0]="";
		}else{
			EFFACER="\033[H\033[2J",RESET="\u001b[0m";
			BLACKf="\u001b[30m", REDf="\u001b[31m", GREENf="\u001b[32m", YELLOWf="\u001b[33m", BLUEf="\u001b[34m", MAGENTAf="\u001b[35m", CYANf="\u001b[36m", WHITEf="\u001b[37m";
			BLACKb="\u001b[40m", REDb="\u001b[41m", GREENb="\u001b[42m", YELLOWb="\u001b[43m", BLUEb="\u001b[44m", MAGENTAb="\u001b[45m", CYANb="\u001b[46m", WHITEb="\u001b[47m";
			COLORS={REDb,GREENb,YELLOWb,BLUEb,MAGENTAb,CYANb};
		}
	}

	public boolean estCommande(char c){
		return c==MENU || c==QUITTER || c==SAUVEGARDER || c==CONTINUER || c==RECOMMENCER || c==CHARGER || c==NOUVEAU || c==CREDITS;
	}

	public AffichageCUI(){
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

	public void afficher(){
		help();
		menu();
		char c=getCommande(super.jeuEnCours());
		switch (c){
			case QUITTER : System.exit(0);
			case SAUVEGARDER : Affichage.sauvegarderLeJeu(super.getJeu()); break;
			case CHARGER : charger(); break;
			case NOUVEAU : setJeu(); break;
			case CREDITS : credits(); break;
			case RECOMMENCER : super.getJeu().recommencer();
			case CONTINUER : jouer(); break;
		}
	}

	private boolean charger(){
		StringBuilder sb=new StringBuilder("Voici la liste des sauvegardes existantes :\n");
		String[] t=Affichage.sauvegardes.list();
		int i=1;
		for (String s:t){
			if (s.matches(Affichage.REGEX_SAVE)){
				sb.append((i+1)+". "+t[i]+"\n");
				i++;
			}
		}

		if (i==1){ System.out.println("Il n'existe aucune sauvegarde !"); return false; }

		sb.append("\nEntrez le numéro ou le nom complet d'une sauvegarde pour charger la partie : ")
		System.out.print(sb.toString());
		String n=null;

		while (true){
			n=sc.nextLine();
			if (n==null) continue;
			else if (n.length()==1 && n.charAt(0)==QUITTER) System.exit(0);
			else if (n.length()==1 && n.charAt(0)==MENU) return false;
			else if (n.matches(Affichage.REGEX_SAVE)){
				for (String s:t){
					if (s.equals(n)){
						super.setJeu(Affichage.chargerLeJeu(n));
						return true;
					}
				}
			}else{
				try{
					int j=Integer.parseInt(n);
					if (j>0 && j<i){
						for (String s:t){
							if (s.matches(REGEX_SAVE && j==1){ super.setJeu(Affichage.chargerLeJeu(s)); return true; }
							else if (s.matches(REGEX_SAVE)) j--;
						}
					}
				}catch(NumberFormatException nfe){}
			}
			return false;
		}
	}

	private void jouer(){
		Jeu jeu=super.getJeu();
		super.afficherPlateau();
		int numeroDuTour=jeu.getNumeroDuTour()-1;
		while (!jeu.estFini()){
			int tmp=jeu.getNumeroDuTour();
			if (tmp!=numeroDuTour){
				super.afficherPlateau();
				numeroDuTour=tmp;
				System.out.println("Tour "+numeroDuTour);
			}

			String s=jeu.getJoueur getName();

			
		}
	}

	private char getCommande(boolean jeuEnCours){
		while (true){
			String s=sc.nextLine();
			if (s==null || s.length()!=1) continue;
			char c=s.charAt(0);
			if (estCommande(c)){
				if (jeuEnCours || (c!=CONTINUER && c!=SAUVEGARDER && c!=RECOMMENCER)) return c;
			}
		}
		return '';
	}

	private void menu(){
		StringBuilder sb=new StringBuilder(EFFACER+RESET+"MENU\n\n");
		if (super.getJeu()!=null) sb.append("Continuer (c)\nRecommencer avec les mêmes paramètres (r)\nSauvegarder (s)\n");
		sb.append("Nouveau jeu (n)\nCharger une sauvegarde ("")\nModifier les paramètres (p)\nCrédits (z)\nQuitter (q)\n");
		System.out.println(sb.toString());
	}

	private Jeu setJeu(){
		System.out.print("Entrer o pour jouer au jeu de l'oie ou n pour jouer au numéri : ");
		String s=null;
		boolean b=true;
		Jeu j=super.getJeu();
		do{
			s=sc.nextLine();
			if (s!=null && s.length()==1){
				if (s.charAt(0)=='n'){ b=true; j=new JeuNumeri(); }
				else if (s.charAt(0)=='o'){ b=true; j=new JeuOie(); }
				else if (s.charAt(0)=='q') System.exit(0);
				else if (s.charAt(0)=='m') b=true;
			}
		}while(b);
		super.setJeu(jeu);
		return jeu;
	}


	public void help(){
		System.out.println(RESET+HELP);
	}

	public void credits(){
		System.out.println("Programme créé entièrement par Marie Bétend et Pierre Gimalac. Les bugs doivent avoir été causés par Maxime Flin car aucun code écrit par Marie ou Pierre ne peut être bugé.");
	}

}