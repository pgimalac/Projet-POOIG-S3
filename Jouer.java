/**
 *
 *
 */

import jeu.*;
import jeu.affichage.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Jouer{

	public static void main(String[] args){
		Affichage a;
//		if (args!=null && args.length==1 && args[0].toLowerCase().equals("gui")) a=new AffichageGUI();
//		else a=new AffichageCUI();
		a=new AffichageCUI();
		Jeu jeu=a.getJeu();
		jeu.jouer();
	}


	public Jeu chargerLeJeu(String nom){
		ObjectInputStream ois = null;
		Jeu jeu=null;
		try {
			final FileInputStream fichier = new FileInputStream(nom);
			ois = new ObjectInputStream(fichier);
			jeu = (Jeu) ois.readObject();
		} catch (final java.io.IOException e) {
System.out.println("Chargement impossible !");
System.out.println("Débug 03 : ");
e.printStackTrace();
		} catch (final ClassNotFoundException e) {
System.out.println("Chargement impossible !");
System.out.println("Débug 04 : ");
e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
System.out.println("Débug 05 : ");
ex.printStackTrace();
			}
		}
		return jeu;
	}

	public void sauvegarderLeJeu(Jeu jeu){
		Date aujourdhui=new Date();
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

		ObjectOutputStream oos = null;
		try {
			final FileOutputStream fichier = new FileOutputStream("sauvegardes/"+date.format(aujourdhui)+".save");
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(jeu);
			oos.flush();
		} catch (IOException e) {
System.out.println("Sauvegarde impossible !");
System.out.println("Débug 01 : ");
e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException ex) {
System.out.println("Débug 02 : ");
ex.printStackTrace();
			}
		}
	}

}