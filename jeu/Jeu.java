package jeu;

/**
 *
 *
 */

import jeu.GameOverListener;
import jeu.GameOverEvent;
import jeu.GameOverException;
import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;
import jeu.plateau.cases.CaseDepart;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.EventListenerList;

import java.io.Serializable;


public abstract class Jeu implements Serializable {

	private static final long serialVersionUID = 3350919143027733149L;
	private static final int MAXIMUM_JOUEURS=Integer.MAX_VALUE-1;
	private static final int MINIMUM_JOUEURS=2;

	public static int getMinimumJoueurs(){
		return 2;
	}

	public static int getMaximumJoueurs(){
		return Integer.MAX_VALUE-1;
	}

	private static final Random des=new Random();

	public int getDes(){
		valeurDes=des.nextInt(6)+1;
		return valeurDes;
	} // lancé d'un dé à 6 faces

	private int valeurDes=1;

	private boolean fini;
	private int numeroDuTour;
	private int enTrainDeJouer;
	private final Joueur[] joueurs;
	private final Plateau plateau;
	private final ArrayList<Joueur> classement;
	private final int nombreDeJoueurs;

	private final EventListenerList GameOverListeners;

	public void addGameOverListener(GameOverListener j){
		GameOverListeners.add(GameOverListener.class,j);
	}

	protected void fireGameOver(GameOverEvent e) {
		GameOverListener[] jfl=GameOverListeners.getListeners(GameOverListener.class);
        for(GameOverListener GameOverListener : jfl)
            GameOverListener.GameOver(e);
    }

    public int getValeurDes(){
    	return valeurDes;
    }

	public Joueur joueurEnTrainDeJouer(){
		return joueurs[enTrainDeJouer];
	}

	protected Case getCase(int i){
		return plateau.getCase(i);
	}

	protected abstract GameOverEvent getGameOver();

	protected void GameOver(){
		fini=true;
		fireGameOver(getGameOver());
	}

	public Joueur getGagnant(){
		if (!estFini()){
			throw new GameOverException("Le jeu n'est pas fini.");
		}else{
			return classement.get(0);
		}
	}

	public boolean estFini(){
		return fini;
	}

	public String getName(){
		return joueurEnTrainDeJouer().toString();
	}

	public String getRaisonFin(){
		if (!estFini()) throw new GameOverException("Le jeu n'est pas encore fini");
		else{ return "Partie finie !\n"+getClassement(); }
	}

	private Joueur getJoueur(int i){
		return joueurs[i];
	}

	public int getNumeroDuTour(){
		return numeroDuTour;
	}

	public String getClassement(int i){
		return classement.get(i).toString();
	}

	protected Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		if (plateau==null)
			throw new IllegalArgumentException("Le plateau n'a pas été initialisé");
		else if (nombreDeJoueursHumains<0 || nombreDeJoueursIA<0)
			throw new IllegalArgumentException("Le nombre d"+((nombreDeJoueursHumains<0)?"e joueurs":"'IA")+"est négatif");
		else if (nombreDeJoueursHumains+nombreDeJoueursIA>MAXIMUM_JOUEURS)
			throw new IllegalArgumentException("Le nombre de joueurs est trop grand");

		nombreDeJoueurs=nombreDeJoueursHumains+nombreDeJoueursIA;
		numeroDuTour=1;
		fini=false;
		this.plateau=plateau;
		joueurs=new Joueur[nombreDeJoueurs];
		classement=new ArrayList<Joueur>();
		for (int i=0;i<nombreDeJoueurs;i++){
			if (i<nombreDeJoueursHumains) 
				joueurs[i]=new Joueur(i+1); 
			else 
				joueurs[i]=new JoueurIA(i+1); 
			classement.add(getJoueur(i));
		}
		enTrainDeJouer=0;
		GameOverListeners = new EventListenerList();
	}

	public Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA, int nbPionsParJoueur, CaseDepart depart){
		this(plateau,nombreDeJoueursHumains,nombreDeJoueursIA);
		initialiserPionsJoueurs(nbPionsParJoueur,depart);
	}

	public Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA, int nbPionsParJoueur){
		this(plateau,nombreDeJoueursHumains,nombreDeJoueursIA,nbPionsParJoueur,(CaseDepart)plateau.getCase(0));
	}

	protected void initialiserPionsJoueurs(int nbPionsParJoueur, CaseDepart depart){ // doit être appelée avant de commencer à jouer
		for (Joueur joueur:joueurs)
			joueur.initialiserPionsJoueurs(nbPionsParJoueur,depart);
	}

	public void recommencer(){
		for (Joueur joueur : joueurs)
			joueur.recommencer();
	}

	protected Joueur joueurSuivant(){
		if (estFini()) throw new GameOverException("Le jeu est fini : aucun joueur ne peut jouer.");
		enTrainDeJouer=(enTrainDeJouer+1)%nombreDeJoueurs;
		if (enTrainDeJouer==0) numeroDuTour++;

		if (joueurEnTrainDeJouer().peutJouer())
			return joueurEnTrainDeJouer();
		else 
			return joueurSuivant();
	}

	public Plateau getPlateau(){
		return plateau;
	}

	public boolean peutJouer(int numeroDuJoueur){
		return true;
	}

	public String getClassement(){
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<nombreDeJoueurs;i++)
			sb.append(i+". "+getClassement(i)+"\n");
		return sb.toString();
	}

	protected Case getCase(Case c, int des){
		return getCase(plateau.getCase(c),des);
	}

	protected Case getCase(int depart, int des){
		while(des!=0){
			if (depart==plateau.size()-1 && des>0) des=-des; // si l'on voulait avancer mais que l'on est à la fin on recule.
			if (depart==0 && des<0) des=0; // si l'on voulait reculer mais que l'on est au début on ne bouge pas.
			depart+=Math.abs(des)/des;
			des-=Math.abs(des)/des;
		}
		return plateau.getCase(depart);
	}

	public abstract String getChoix();
	public abstract boolean choix();
	public abstract boolean choix(String entree);
	public abstract void jouer();


}