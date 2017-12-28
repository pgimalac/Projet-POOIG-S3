package jeu.affichage;

/**
 *	L'affichage en console	
 */

import jeu.Jeu;
import jeu.JeuOie;
import jeu.JeuNumeri;
import jeu.Joueur;
import jeu.JoueurIA;

import jeu.listeners.GameListener;
import jeu.listeners.GameOverListener;
import jeu.listeners.CannotPlayListener;
import jeu.listeners.PlayListener;

import jeu.events.GameEvent;
import jeu.events.GameOverEvent;
import jeu.events.CannotPlayEvent;
import jeu.events.PlayEvent;

import jeu.options.Option;

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;

import java.util.Scanner;
import java.util.ArrayList;

public class AffichageCUI extends Affichage implements GameOverListener,CannotPlayListener,PlayListener{

	private static Scanner sc=new Scanner(System.in);

	class AffichagePlateauSpiraleCUI implements AffichagePlateau{
		private String plateauS;

		@Override
		public void afficher(Plateau plateau){
		}

		@Override
		public String toString(){
			return "spirale";
		}
	}

	class AffichagePlateauRectangleCUI implements AffichagePlateau{
		private String plateauS=null;

		@Override
		public void afficher(Plateau plateau){
			if (plateauS==null){
				StringBuilder sb=new StringBuilder(BLACKf);
				StringBuilder separation=new StringBuilder(WHITEb+BLACKf);
				int size=plateau.size();
				for (int i=0;i<size;i++){
					sb.append(COLORS[i%COLORS.length]+centrer(LARGEUR_CASE,(i+1)+"-"+plateau.getCase(i).toString()));
					for (int j=LARGEUR_CASE;j>0;j--)
						separation.append('-');
					if ((i+1)%NOMBRE_CASE_LARGEUR==0 && i<size-NOMBRE_CASE_LARGEUR){
						sb.append("\n"+separation.toString()+"\n"+BLACKf); 
						separation=new StringBuilder(WHITEb);
					}
				}
				sb.append(RESET);
				plateauS=sb.toString();
			}
			System.out.println(plateauS);
		}

		@Override
		public String toString(){
			return "rectangle";
		}
	}

	class AffichagePlateauZigzagCUI implements AffichagePlateau{
		private String plateauS;

		@Override
		public void afficher(Plateau plateau){

		}

		@Override
		public String toString(){
			return "zigzag";
		}
	}

	class AffichagePlateauColonneCUI implements AffichagePlateau{
		private String plateauS;

		@Override
		public void afficher(Plateau plateau){

		}

		@Override
		public String toString(){
			return "colonne";
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
	public static final char MENU='m',QUITTER='q',SAUVEGARDER='s',CONTINUER='c',RECOMMENCER='r',CHARGER='l',NOUVEAU='n',CREDITS='z',MODIFIER_PARAM='p';

	static{
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
			EFFACER="";RESET="";
			BLACKf=""; REDf=""; GREENf=""; YELLOWf=""; BLUEf=""; MAGENTAf=""; CYANf=""; WHITEf="";
			BLACKb=""; REDb=""; GREENb=""; YELLOWb=""; BLUEb=""; MAGENTAb=""; CYANb=""; WHITEb="";
			COLORS=new String[1];
			COLORS[0]="";
		}else{
			EFFACER="\033[H\033[2J";RESET="\u001b[0m";
			BLACKf="\u001b[30m"; REDf="\u001b[31m"; GREENf="\u001b[32m"; YELLOWf="\u001b[33m"; BLUEf="\u001b[34m"; MAGENTAf="\u001b[35m"; CYANf="\u001b[36m"; WHITEf="\u001b[37m";
			BLACKb="\u001b[40m"; REDb="\u001b[41m"; GREENb="\u001b[42m"; YELLOWb="\u001b[43m"; BLUEb="\u001b[44m"; MAGENTAb="\u001b[45m"; CYANb="\u001b[46m"; WHITEb="\u001b[47m";
			COLORS=new String[6];
			COLORS[0]=REDb;
			COLORS[1]=GREENb;
			COLORS[2]=YELLOWb;
			COLORS[3]=BLUEb;
			COLORS[4]=MAGENTAb;
			COLORS[5]=CYANb;
		}
	}

	public boolean estCommande(char c){
		return c==MENU || c==QUITTER || c==SAUVEGARDER || c==CONTINUER || c==RECOMMENCER || c==CHARGER || c==NOUVEAU || c==CREDITS;
	}

	public AffichageCUI(){
		NOMBRE_CASE_LARGEUR=9;
		LARGEUR_CASE=12;
		WAITING_TIME=800;
	}

	private int NOMBRE_CASE_LARGEUR;
	private int LARGEUR_CASE;
	private int WAITING_TIME;

	private void setNombreCaseLargeur(int l){
		NOMBRE_CASE_LARGEUR=l;
	}

	private String centrer(int largeur, String s){
		if (s.length()>largeur){
			return s.substring(0,largeur-1)+".";
		}else{
			StringBuilder sb=new StringBuilder(s);
			for (int i=largeur-s.length();i>0;i--){
				if (i%2==0) sb.append(' ');
				else sb.insert(0,' ');				
			}
			return sb.toString();
		}
	}

	public void afficher(){
		help();
		while(true){
			menu();
			char c=getCommande(super.jeuEnCours(),super.jeuFini());
			switch (c){
				case QUITTER : System.exit(0);
				case SAUVEGARDER : Affichage.sauvegarderLeJeu(super.getJeu()); break;
				case CHARGER : charger(); break;
				case NOUVEAU : setJeu(); jouer(); break;
				case CREDITS : credits(); break;
				case RECOMMENCER : super.getJeu().recommencer();
				case CONTINUER : jouer(); break;
				case MODIFIER_PARAM : modifierParam(); break;
			}
		}
	}

	private void modifierParam(){
		while(true){
			String[] t=new String[4];
			t[0]="type d'affichage du plateau";
			t[1]="nombre de case sur la largeur de l'écran";
			t[2]="largeur d'une case";
			t[3]="temps d'attente après le tour d'une IA ou à la fin d'un tour";
			StringBuilder sb=new StringBuilder("Voici les paramètres qui peuvent être modifiés :\n");
			for (int i=0;i<t.length;i++)
				sb.append((i+1)+". "+t[i]+"\n");
			sb.append("\nEntrez le numero de l'option à modifier. Entrez rien ou autre chose pour retourner au menu. ");
			System.out.print(sb.toString());
			String tmp=sc.nextLine();
			switch (tmp){
				case "1" :
					System.out.println(t[0]+", les affichages possibles sont ceux-ci :\n1. spirale\n2. rectangle\n3. zigzag\n4. colonne\nEntrez le numero de l'option choisie. ");
					String choix=sc.nextLine();
					switch(choix){
						case "1" :
							super.setAffichage(new AffichagePlateauSpiraleCUI());
							break;
						case "2" :
							super.setAffichage(new AffichagePlateauRectangleCUI());
							break;
						case "3" :
							super.setAffichage(new AffichagePlateauZigzagCUI());
							break;
						case "4" :
							super.setAffichage(new AffichagePlateauColonneCUI());
							break;
					}
					break;
				case "2" :
					System.out.println("Le nombre actuel de case sur la largeur de l'écran est "+NOMBRE_CASE_LARGEUR+". Entrez la nouvelle valeur : ");
					try{
						NOMBRE_CASE_LARGEUR=Integer.parseInt(sc.nextLine());
					}catch(NumberFormatException nfe){
						System.out.println("Entrée invalide.");
					}
					break;
				case "3" :
					System.out.println("La largeur actuelle d'une case est "+NOMBRE_CASE_LARGEUR+". Entrez la nouvelle valeur : ");
					try{
						LARGEUR_CASE=Integer.parseInt(sc.nextLine());
					}catch(NumberFormatException nfe){
						System.out.println("Entrée invalide.");
					}
					break;
				case "4" :
					System.out.println("Le temps d'attente actuel après le tour d'une IA est "+WAITING_TIME+". Entrez la nouvelle valeur : ");
					try{
						NOMBRE_CASE_LARGEUR=Integer.parseInt(sc.nextLine());
					}catch(NumberFormatException nfe){
						System.out.println("Entrée invalide.");
					}
					break;
				default : return;
			}
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

		sb.append("\nEntrez le numéro ou le nom complet d'une sauvegarde pour charger la partie : ");
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
							if (s.matches(REGEX_SAVE) && j==1){ 
								super.setJeu(Affichage.chargerLeJeu(s));
								return true;
							}
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
		int numeroDuTour=jeu.getNumeroDuTour()-1;
		while (!jeu.estFini()){
			int tour=jeu.getNumeroDuTour();
			if (tour!=numeroDuTour){

				System.out.print(RESET+EFFACER);
				super.afficherPlateau();
				System.out.println("");

				numeroDuTour=tour;
				System.out.println("Tour "+numeroDuTour);

				System.out.println(getPositions());
			}

			Joueur joueur=jeu.joueurEnTrainDeJouer();
			System.out.print("C'est au tour de "+joueur+" de jouer : appuyer sur entrée pour lancer les dés ! ");
			String tmp=sc.nextLine();
			if (tmp.length()==1){
				if (tmp.charAt(0)==QUITTER)
					System.exit(0);
				else if (tmp.charAt(0)==MENU)
					return;
			}

			int d=jeu.lancerDes();
			while(jeu.choix()){ // tant qu'il y a un choix à faire
				System.out.println(jeu.getChoix()); // on affiche le choix à faire
				while (!jeu.choix(sc.nextLine()))	// tant que la réponse au choix n'est pas valide, on demande une réponse
					System.out.println("Entrée invalide !");
			}
			jeu.jouer();
		}
	}

	private String getPositions(){
		StringBuilder sb=new StringBuilder();
		Jeu jeu=super.getJeu();
		for (Joueur joueur : jeu)
			sb.append(joueur+" : "+getPositions(joueur)+"\n");
		return sb.toString();
	}

	private String getPositions(Joueur joueur){
		StringBuilder sb=new StringBuilder();
		Jeu jeu=super.getJeu();
		if (joueur.getNombrePions()==1)
			sb.append("case "+(jeu.getCase(joueur.getCase())+1)+" (case "+joueur.getCase()+")");
		else{
			int i=1;
			for (Case c : joueur){
				sb.append("pion "+i+" : "+(jeu.getCase(c)+1)+" (case "+c+"). ");
			}
		}
		return sb.toString();
	}

	private char getCommande(boolean jeuEnCours, boolean jeuFini){
		while (true){
			String s=sc.nextLine();
			if (s!=null && s.length()==1){
				char c=s.charAt(0);
				if (estCommande(c)){
					if (!jeuEnCours && c!=CONTINUER && c!=SAUVEGARDER && c!=RECOMMENCER) return c;
					else if (jeuEnCours && !jeuFini) return c;
					else if (jeuEnCours && jeuFini && c!=CONTINUER && c!=SAUVEGARDER) return c;
				}
			}
			commandeInvalide();
		}
	}

	private void commandeInvalide(){
		System.out.println("Entrée invalide.");
	}

	private void menu(){
		StringBuilder sb=new StringBuilder("MENU\n\n");
		if (super.getJeu()!=null){
			if (!super.getJeu().estFini())
				sb.append("Continuer ("+CONTINUER+")\nSauvegarder ("+SAUVEGARDER+")\n");
			sb.append("Recommencer avec les mêmes paramètres ("+RECOMMENCER+")\n");
		}
		sb.append("Nouveau jeu ("+NOUVEAU+")\nCharger une sauvegarde ("+CHARGER+")\nModifier les paramètres ("+MODIFIER_PARAM+")\nCrédits ("+CREDITS+")\nQuitter ("+QUITTER+")\n");
		System.out.println(sb.toString());
	}

	private Jeu setJeu(){
		System.out.print("Entrer o pour jouer au jeu de l'oie ou n pour jouer au numéri : ");
		String s=null;
		boolean b=true;
		Jeu jeu=super.getJeu();

		int min=2,max=2;

		do{
			s=sc.nextLine();
			if (s!=null && s.length()==1){
				if (s.charAt(0)=='n'){
					b=false;
					min=JeuNumeri.getMinimumJoueurs();
					max=JeuNumeri.getMaximumJoueurs();
				}
				else if (s.charAt(0)=='o'){
					b=false;
					min=JeuOie.getMinimumJoueurs();
					max=JeuOie.getMaximumJoueurs();
				}
				else if (s.charAt(0)==QUITTER)
					System.exit(0);
				else if (s.charAt(0)==MENU) 
					return jeu;
				else
					commandeInvalide();
			}
		}while(b);

		System.out.print("Il doit y avoir entre "+min+" et "+max+" joueurs. Combien de joueurs humains y-a-t-il ? ");

		int humains=-1;
		do{
			try{
				humains=Integer.parseInt(sc.next());
			}catch(NumberFormatException nfe){
				commandeInvalide();
			}
		}while(humains<0 || humains>max);

		min=Math.max(0,min-humains);
		max-=humains;
		System.out.print("Combien y-a-t-il de joueurs IA (entre "+min+" et "+max+") ? ");

		int ia=-1;
		do{
			try{
				ia=Integer.parseInt(sc.next());
			}catch(NumberFormatException nfe){
				commandeInvalide();
			}
		}while(ia<0 || ia>max);

		if (s.charAt(0)=='o'){
			jeu=new JeuOie(humains,ia);
		}else{
			jeu=new JeuNumeri(humains,ia);
		}
		super.setJeu(jeu);


		// paramétrage de la partie
		System.out.print("Modifier les options par défaut du jeu ? (o/N) ");
		sc.nextLine();
		String tmp=sc.nextLine();
		if (tmp.length()!=0 && tmp.toLowerCase().charAt(0)=='o'){
			ArrayList<Option> options=jeu.getOptions();
			if (options.isEmpty()){
				System.out.println("Aucune option disponible pour ce jeu !");
			}else{
				int nb=1;
				for (Option option : options){
					System.out.println(nb+". "+option);
				}
			}
		}

		return jeu;
	}


	public void help(){
		System.out.println(RESET+HELP);
	}

	public void credits(){
		System.out.println("Programme créé entièrement par Marie Bétend et Pierre Gimalac. Les bugs doivent avoir été causés par Maxime Flin car aucun code écrit par Marie ou Pierre ne peut être bugé.");
	}

	public void gameOver(GameOverEvent e){
		System.out.println("Partie finie !");
	}

	public void cannotPlay(CannotPlayEvent e){
		System.out.println(e.toString());
	}

	public void play(PlayEvent e){
		Joueur joueur=e.getJoueur();
		System.out.println(((joueur.estHumain())?"":"C'est au tour de "+joueur+" de jouer.\n")+joueur+" fait "+e.getDes()+" : "+getPositions(joueur));
		sleep();
	}

	private void sleep(){
		try{
			Thread.sleep(WAITING_TIME);
		}catch(InterruptedException e){}
	}

}