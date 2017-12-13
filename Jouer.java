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
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Jouer j=new Jouer();
			}
		});
	}

	public Jouer(){
		if (args!=null && args.length==1 && args[0].toLowerCase().equals("gui")) affichage=new AffichageGUI();
		else affichage=new AffichageCUI();
		affichage.afficher();
	}

	private Affichage affichage;

}