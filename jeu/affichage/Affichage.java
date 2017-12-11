package jeu.affichage;

/**
 *	Une interface définissant les fonctions que chaque affichage doit implémenter
 */

import jeu.Jeu;

public abstract class Affichage implements Serializable{

	private Jeu jeu;
	private AffichagePlateau typeDAffichage;

	public int getMaximumLargeur();
	public int getMaximumHauteur();

	protected abstract AffichagePlateau getDefaultAffichagePlateau(); 

	{
		jeu=null;
		typeDAffichage=this.getDefaultAffichagePlateau();
	}

	interface AffichagePlateau{
		public void afficher(Plateau plateau);
	}

	protected void setAffichage(AffichagePlateau affichage){
		typeDAffichage=affichage;
	}


	protected void setJeu(Jeu j){
		jeu=j;
	}

	protected Jeu getJeu(){
		return jeu;
	}

	static protected Jeu chargerLeJeu(String nom){
		ObjectInputStream ois = null;
		Jeu jeu=null;
		try {
			final FileInputStream fichier = new FileInputStream(nom);
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

	static protected void sauvegarderLeJeu(Jeu jeu){
		Date aujourdhui=new Date();
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

		ObjectOutputStream oos = null;
		try {
			final FileOutputStream fichier = new FileOutputStream("sauvegardes/"+date.format(aujourdhui)+".save");
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(jeu);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}