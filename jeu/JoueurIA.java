package jeu;
public class JoueurIA extends Joueur{

	public JoueurIA(int numero){
		super(numero);
		super.setNom("IA "+numero);
	}

	public boolean estHumain(){ return false; }

}