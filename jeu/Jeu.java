package jeu;

/**
 *
 *
 */

import jeu.JeuFiniListener;
import jeu.JeuFiniEvent;
import jeu.JeuFiniException;
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
		valeurDe=des.nextInt(6)+1;
	} // lancé d'un dé à 6 faces

	private int valeurDe=1;

	private boolean fini;
	private int numeroDuTour;
	private int enTrainDeJouer;
	private final Joueur[] joueurs;
	private final Plateau plateau;
	private final ArrayList<Joueur> classement;
	private final int nombreDeJoueurs;

	private final EventListenerList jeuFiniListeners;

	public void addJeuFiniListener(JeuFiniListener j){
		jeuFiniListeners.add(j);
	}

	protected void fireJeuFini(JeuFiniEvent e) {
		jfl=jeuFiniListeners.getListenerList();
        for(JeuFiniListener jeuFiniListener : jfl)
            jeuFiniListener.jeuFini(e);
    }

    public int getValeurDe(){
    	return valeurDe;
    }

	public Joueur joueurEnTrainDeJouer(){
		return joueurs[enTrainDeJouer];
	}

	protected Case getCase(int i){
		return plateau.getCase(i);
	}

	protected void jeuFini(){
		fini=true;
		fireJeuFini(new JeuFiniEvent());
	}

	public boolean estFini(){
		return fini;
	}

	public String getName(){
		return joueurEnTrainDeJouer().toString();
	}

	public String getRaisonFin(){
		if (!estFini()) throw new JeuFiniException("Le jeu n'est pas encore fini");
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
		for (int i=0;i<nombreDeJoueurs;i++){
			if (i<nombreDeJoueursHumains) 
				joueurs[i]=new Joueur(i+1); 
			else 
				joueurs[i]=new JoueurIA(i+1); 
			classement.add(getJoueur(i));
		}
		enTrainDeJouer=0;
		jeuFiniListeners = new EventListenerList();
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
		if (estFini()) throw new JeuFiniException("Le jeu est fini : aucun joueur ne peut jouer.");
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

	public abstract String choix();
	public abstract boolean choix(int d);
	public abstract boolean choix(int d, String entree);
	public abstract void jouer(int d);


}