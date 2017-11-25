/**
 *
 *
 */

import jeu.Jeu;
import jeu.JeuOie;

import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Jouer{

	public static void main(String[] args){
		Jeu jeuOie=new JeuOie();
		System.out.println(jeuOie.getClass());
	}


	private Jeu chargerLeJeu(String nom){
		return null;
	}

	private void sauvegarderLeJeu(Jeu jeu){
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