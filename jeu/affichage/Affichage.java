package jeu.affichage;

/**
 *	Une interface définissant les fonctions que chaque affichage doit implémenter
 */

import jeu.Jeu;
import jeu.plateau.Plateau;
import jeu.listeners.GameListener;

import java.io.Serializable;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Date;

import java.text.SimpleDateFormat;

public abstract class Affichage implements Serializable,GameListener{

	interface AffichagePlateau{
		public void afficher(Plateau plateau);
		public String toString();
	}

	private Jeu jeu;
	private AffichagePlateau affichagePlateau;

	public static final String CHEMIN_SAUVEGARDE="./sauvegardes/";
	public static final String REGEX_SAVE=".+\\.save$";
	protected static File sauvegardes;

	protected static boolean sauvegarde;

	{
		sauvegardes=new File(CHEMIN_SAUVEGARDE);
		if (!sauvegardes.exists() || !sauvegardes.isDirectory()) display("Sauvegarde ou chargement de sauvegarde impossible.");
		else if (!sauvegardes.canWrite() || !sauvegardes.canRead()) display("Droits manquants sur le dossier de sauvegarde pour charger et sauvegarder des parties.");
		sauvegarde=sauvegardes.exists() && sauvegardes.isDirectory() && sauvegardes.canWrite() && sauvegardes.canRead();
		jeu=null;
		setAffichage(this.getDefaultAffichagePlateau());
	}

	public abstract void afficher();
	protected abstract void display(String s);

	protected abstract AffichagePlateau getDefaultAffichagePlateau(); 

	protected void setAffichage(AffichagePlateau affichage){
		affichagePlateau=affichage;
	}

	protected void afficherPlateau(){
		affichagePlateau.afficher(jeu.getPlateau());
	}

	public boolean jeuEnCours(){
		return jeu!=null;
	}

	public boolean jeuFini(){
		return jeuEnCours() && jeu.estFini();
	}

	protected void setJeu(Jeu j){
		jeu=j;
		jeu.setGameListener(this);
	}

	protected Jeu getJeu(){
		return jeu;
	}

	protected Jeu chargerLeJeu(String nom){
		ObjectInputStream ois = null;
		Jeu jeu=null;
		try {
			final FileInputStream fichier = new FileInputStream("./sauvegardes/"+nom);
			ois = new ObjectInputStream(fichier);
			jeu = (Jeu) ois.readObject();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		return jeu;
	}

	protected void sauvegarderLeJeu(Jeu jeu, String nom){
		if (nom==null || nom.equals("")) sauvegarderLeJeu(jeu);
		if (!nom.matches(REGEX_SAVE)) nom=nom+".save";

		jeu.setGameListener(null);
		ObjectOutputStream oos = null;
		try {
			final FileOutputStream fichier = new FileOutputStream("./sauvegardes/"+nom);
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(jeu);
			oos.flush();
		} catch (IOException e) {
			display("Erreur de sauvegarde.");
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException ex) {}
		}		
	}

	protected void sauvegarderLeJeu(Jeu jeu){
		Date aujourdhui=new Date(System.currentTimeMillis()+3600000); // décallage horaire d'une heure en plus
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		sauvegarderLeJeu(jeu,date.format(aujourdhui)+".save");
	}
}