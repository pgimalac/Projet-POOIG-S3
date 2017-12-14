package jeu;

/**
 *
 *
 */

import jeu.plateau.Plateau;

import java.util.LinkedList;
import java.util.Random;

import java.io.Serializable;


public abstract class Jeu implements Serializable {

	private static final long serialVersionUID = 3350919143027733149L;
	public int MAXIMUM_JOUEURS=Integer.MAX_VALUE;
	public int MINIMUM_JOUEURS=2;

	private static final Random des=new Random();
	public static int getDes(){ return des.nextInt(6)+1; } // lancé d'un dé à 6 faces

	public static final JeuNumeri getNewNumeri(){ return new JeuNumeri(); }
	public static final JeuOie getNewOie(){ return new JeuOie(); }

	private boolean fini;
	private int numeroDuTour;
	private int enTrainDeJouer;
	private final Joueur[] joueurs;
	private final Plateau plateau;
	private final ArrayList<Joueur> classement;
	private final int nombreDeJoueurs;

	private final EventListenerList jeuFiniListeners;

	public void addJeuFiniListener(jeuFiniListener j){ jeuFiniListeners.add(j); }

	protected void fireJeuFini(JeuFiniEvent e) {
        for(JeuFiniListener jeuFiniListener : jeuFiniListeners)
            jeuFiniListener.jeuFini(e);
    }


	private Joueur joueurEnTrainDeJouer(){ return joueurs[enTrainDeJouer]; }

	protected Case getCase(int i){ return plateau.getCase(i); }

	protected void jeuFini(){ fini=true; fireJeuFini(new JeuFiniEvent()); }

	public boolean estFini(){ return fini; }

	public String getRaisonFin(){
		if (!estFini()) throw new jeuFiniException("Le jeu n'est pas encore fini");
		else{ return "Partie finie !\n"+getClassement(); }
	}

	private Joueur getJoueur(int i){ return joueurs[i]; }

	public int getNumeroDuTour(){ return numeroDuTour; }

	public Joueur getClassement(int i){ return classement.get(i); }

	public Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA){
		if (plateau==null) throw new IllegalArgumentException("Le plateau n'a pas été initialisé");
		else if (nombreDeJoueursHumains<0 || nombreDeJoueursIA<0) throw new IllegalArgumentException("Le nombre d"+((nombreDeJoueursHumains<0)?"e joueurs":"'IA")+"est négatif");
		else if (nombreDeJoueursHumains+nombreDeJoueursIA>MAXIMUM_JOUEURS) throw new IllegalArgumentException("Le nombre de joueurs est trop grand");

		nombreDeJoueurs=nombreDeJoueursHumains+nombreDeJoueursIA;
		numeroDuTour=1;
		fini=false;
		this.plateau=plateau;
		joueurs=new Joueur[nombreDeJoueurs];
		for (int i=0;i<nombreDeJoueurs;i++){ if (i<nombreDeJoueursHumains) joueurs[i]=new Joueur(i+1); else joueurs[i]=new JoueurIA(i+1); classement.add(getJoueur(i)); }
		enTrainDeJouer=0;
		jeuFiniListeners= new EventListenerList();
	}

	public Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA, int nbPionsParJoueur, Case depart){
		this(plateau,nombreDeJoueursHumains,nombreDeJoueursIA);
		initialiserPionsJoueurs(nbPionsParJoueur,depart);
	}

	public Jeu(Plateau plateau, int nombreDeJoueursHumains, int nombreDeJoueursIA, int nbPionsParJoueur){
		this(plateau,nombreDeJoueursHumains,nombreDeJoueursIA,nbPionsParJoueur,plateau.getCase(0));
	}

	protected void initialiserPionsJoueurs(int nbPionsParJoueur, CaseDepart depart){ // doit être appelée avant de commencer à jouer
		this.nbPionsParJoueur=nbPionsParJoueur;
		this.caseDepart=depart;
		for (Joueur joueur:joueurs)
			joueur.initialiserPionsJoueurs(nbPionsParJoueur, c);
	}


	private Joueur joueurSuivant(){
		if (jeuFini()) throw new jeuFiniException("Le jeu est fini : aucun joueur ne peut jouer.");
		enTrainDeJouer=(enTrainDeJouer+1)%nombreDeJoueurs;
		if (enTrainDeJouer==0) numeroDuTour++;

		if (joueurEnTrainDeJouer().peutJouer()) return joueurEnTrainDeJouer();
		else return joueurSuivant();
	}

	public Plateau getPlateau(){ return plateau; }

	public void recommencer(){
		initialiserPionsJoueurs()
	}

	public boolean peutJouer(int numeroDuJoueur){
		return true;
	}

	public String getClassement(){
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<nombreDeJoueurs;i++)
			sb.append(i+". "+getClassement(i)+"\n");
	}
}