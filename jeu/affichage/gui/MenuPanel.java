package jeu.affichage.gui;

import jeu.Jeu;

import jeu.affichage.Affichage;
import jeu.affichage.AffichageGUI;

import jeu.options.questions.Question;

import jeu.affichage.AffichageGUI;
import jeu.affichage.AffichageGUI.JeuModifiedStateListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.Box;


public class MenuPanel extends JPanelUp implements JeuModifiedStateListener{

	public ButtonUp continuer,sauvegarder,recommencer,nouveau,charger,modifier,credits,quitter;

	@Override
	public void modifiedState(Jeu j){
		continuer.setEnabled(j!=null && !j.estFini());
		sauvegarder.setEnabled(j!=null && !j.estFini());
		recommencer.setEnabled(j!=null);
	}

	public MenuPanel(AffichageGUI affichage, Fenetre parent){
		super(parent);
		setLayout(new GridBagLayout());
		Box box = Box.createVerticalBox();
		add(box,new GridBagConstraints());
		affichage.add(this);

		continuer=new ButtonUp("Continuer",true,false,event -> super.parent.fireGoToJeu());
		continuer.addToBox(box,false,5,5);

		recommencer=new ButtonUp("Recommencer",true,false, event -> affichage.getJeu().recommencer());
		recommencer.addToBox(box,false,5,5);

		nouveau=new ButtonUp("Nouvelle partie",true,true, event -> super.parent.fireGoToNouveau());
		nouveau.addToBox(box,false,5,5);

		sauvegarder=new ButtonUp("Sauvegarder",true,false, event -> affichage.sauvegarderLeJeu());
		sauvegarder.addToBox(box,false,5,5);

		charger=new ButtonUp("Charger une partie",true,true, event -> super.parent.fireGoToCharger());
		charger.addToBox(box,false,5,5);

		modifier=new ButtonUp("Paramètres",true,true, event -> super.parent.fireGoToParam());
		modifier.addToBox(box,false,5,5);

		credits=new ButtonUp("Credits",true,true, event -> super.parent.display(Affichage.credits));
		credits.addToBox(box,false,5,5);

		quitter=new ButtonUp("Quitter",true,true, event -> System.exit(0));
		quitter.addToBox(box,false,5,5);
	}
}