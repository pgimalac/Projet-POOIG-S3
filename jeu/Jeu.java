package jeu;

/**
 *
 *
 */

import jeu.listeners.*;
import jeu.events.*;
import jeu.exceptions.*;
import jeu.options.Option;

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;
import jeu.plateau.cases.CaseDepart;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collections;

// import javax.swing.event.EventListenerList; je voulais utiliser ça au début mais ça ne m'a pas eu l'air très pratique...

import java.io.Serializable;


public abstract class Jeu implements Serializable,Iterable<Joueur> {

	private static final long serialVersionUID = 3350919143027733149L;
	private static final int MAXIMUM_JOUEURS=Integer.MAX_VALUE-1;
	private static final int MINIMUM_JOUEURS=2;
	protected static final Random des=new Random();

	public static int getMinimumJoueurs(){
		return 2;
	}

	public static int getMaximumJoueurs(){
		return Integer.MAX_VALUE-1;
	}

	public Iterator<Joueur> iterator(){
		return (new ArrayList<Joueur>(Arrays.asList(joueurs))).iterator();
	}

	public int lancerDes(){
		setDes(des.nextInt(6)+1);
		return valeurDes;
	} // lancé d'un dé à 6 faces

	protected void setDes(int i){
		valeurDes=i;
	}

	public int getDes(){
		return valeurDes;
	}

	protected int valeurDes=1;
	protected int numeroDuTour;
	private int enTrainDeJouer;
	protected final Joueur[] joueurs;
	protected final Plateau plateau;
	protected final ArrayList<Joueur> classement;
	protected final int nombreDeJoueurs;

	private final ArrayList<GameListener> gameListeners;

	public void addGameListener(GameListener j){
		gameListeners.add(j);
	}

	protected void fireGameOver(GameOverEvent e) {
		for (GameListener listener : gameListeners){
			if (listener instanceof GameOverListener)
				((GameOverListener)listener).gameOver(e);
		}
    }

    protected void fireCannotPlay(CannotPlayEvent e){
		for (GameListener listener : gameListeners){
			if (listener instanceof CannotPlayListener)
				((CannotPlayListener)listener).cannotPlay(e);
		}
    }

    protected void firePlay(PlayEvent e){
		for (GameListener listener : gameListeners){
			if (listener instanceof PlayListener)
				((PlayListener)listener).play(e);
		}
    }

	private ArrayList<Option> options=new ArrayList<Option>();

	protected Option getOption(Class c){
		for(Option o : options){
			if (o.getClass().equals(c))
				return o;
		}
		return null;
	}

	public ArrayList<Option> getOptions(){
		return options;
	}

	protected void addOption(Option o){
		options.add(o);
	}

    public int getValeurDes(){
    	return valeurDes;
    }

	public Joueur joueurEnTrainDeJouer(){
		return joueurs[enTrainDeJouer];
	}

    public int getCase(Case c){
    	return plateau.getCase(c);
    }

	protected Case getCase(int i){
		return plateau.getCase(i);
	}

	public String getName(){
		return joueurEnTrainDeJouer().toString();
	}

	private Joueur getJoueur(int i){
		return joueurs[i];
	}

	public int getNumeroDuTour(){
		return numeroDuTour;
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
		this.plateau=plateau;
		joueurs=new Joueur[nombreDeJoueurs];
		classement=new ArrayList<Joueur>(){
			public Iterator<Joueur> iterator(){
				Collections.sort(this, Collections.reverseOrder());
				return (Iterator<Joueur>)this;
			}
		};
		for (int i=0;i<nombreDeJoueurs;i++){
			if (i<nombreDeJoueursHumains) 
				joueurs[i]=new Joueur(i+1); 
			else 
				joueurs[i]=new JoueurIA(i+1); 
			classement.add(getJoueur(i));
		}
		enTrainDeJouer=0;
		gameListeners = new ArrayList<GameListener>();
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
		plateau.recommencer();
	}

	protected Joueur joueurSuivant(){
		if (estFini()) throw new GameOverException("Le jeu est fini : aucun joueur ne peut jouer.");
		enTrainDeJouer=(enTrainDeJouer+1)%nombreDeJoueurs;
		if (enTrainDeJouer==0) numeroDuTour++;

		if (peutJouer())
			return joueurEnTrainDeJouer();
		else{
			fireCannotPlay(new CannotPlayEvent(this,joueurEnTrainDeJouer()));
			return joueurSuivant();
		}
	}

	public Plateau getPlateau(){
		return plateau;
	}

	protected void classement(){

	}

	public String getClassement(){ //TODO
		int j=1;
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<nombreDeJoueurs;i++)
			sb.append((i+1)+". "+classement.get(i)+"\n");
		return sb.toString();
	}

	public boolean peutJouer(){
		return peutJouer(joueurEnTrainDeJouer());
	}

	public boolean estVide(Case c){
		for (Joueur joueur : this){
			for (Case ca: joueur){
				if (ca==c)
					return false;
			}
		}
		return true;
	}

	public abstract boolean estFini();
	public abstract boolean peutJouer(Joueur joueur);
	public abstract String getChoix();
	public abstract boolean choix();
	public abstract boolean choix(String entree);
	public abstract void jouer(); // joue le tour d'un joueur


}
