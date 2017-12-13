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

	public static final String EFFACER="\033[H\033[2J",RESET="\u001b[0m";
	public static final String BLACKf="\u001b[30m", REDf="\u001b[31m", GREENf="\u001b[32m", YELLOWf="\u001b[33m", BLUEf="\u001b[34m", MAGENTAf="\u001b[35m", CYANf="\u001b[36m", WHITEf="\u001b[37m";
	public static final String BLACKb="\u001b[40m", REDb="\u001b[41m", GREENb="\u001b[42m", YELLOWb="\u001b[43m", BLUEb="\u001b[44m", MAGENTAb="\u001b[45m", CYANb="\u001b[46m", WHITEb="\u001b[47m";
	public static final String[] COLORS={REDb,GREENb,YELLOWb,BLUEb,MAGENTAb,CYANb};

	public static final char MENU='m',QUITTER='q',SAUVEGARDER='s',CONTINUER='c',RECOMMENCER='r',CHARGER='l',NOUVEAU='n',MODIFIER_PARAM='p',CREDITS='z';
	public static final String HELP="Les commandes attendues sont soit indiquées explicitement soit entre parenthèses.\nTaper autre chose que ce qui est attendu ne fera rien.\nTaper q pour quitter ou m pour accéder au menu (sauf cas particuliers, si une réponse est attendue par exemple).\n";

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


		while (!jeu.estFini()){
			afficher(super.AffichagePlateau.afficher());
			else System.out.println(jeu.getClassement());

			System.out.println(jeu);
		}
		System.out.println(jeu.getClassement()+"\n");		
	}

	private void menu(){
		StringBuilder sb=new StringBuilder(EFFACER+RESET+"MENU\n\n");
		if (jeu!=null) sb.append("Continuer (c)\nRecommencer avec les mêmes paramètres (r)\nSauvegarder (s)\n");
		sb.append("Nouveau jeu (n)\nCharger une sauvegarde ("")\nModifier les paramètres (p)\nCrédits (z)\nQuitter (q)\n");
		System.out.println(sb.toString());

		Char c=commande();

	}

	private char commande (){
		String s=null;
		boolean b=false;
		while (b){
			s=sc.nextLine();
			if (s=!null && s.length()==1){
				char c=s.charAt(0);
				if (c==)
			}

		}

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


	public void help(){
		System.out.println(RESET+HELP);
	}







	}



















}