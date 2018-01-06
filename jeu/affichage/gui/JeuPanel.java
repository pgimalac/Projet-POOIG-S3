package jeu.affichage.gui;

import jeu.Jeu;
import jeu.Joueur;

import jeu.affichage.Affichage;

import jeu.events.GameEvent;
import jeu.events.GameOverEvent;
import jeu.events.CannotPlayEvent;
import jeu.events.PlayEvent;

import jeu.options.Option;

import jeu.affichage.AffichageGUI;
import jeu.affichage.AffichageGUI.JeuModifiedStateListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneLayout;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.GridLayout;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


public class JeuPanel extends JSplitPane implements JeuModifiedStateListener, ComponentListener{

	private JPanel infos;
	private JPanel plateauIn;
	private JPanel titre;
	private JPanel liste;
	private JButton de;

	private final Fenetre parent;
	private int formerWidth;	

	public JeuPanel(Affichage affichage, Fenetre parent){
		super(JSplitPane.HORIZONTAL_SPLIT);

		plateauIn=new JPanel();

		JScrollPane plateau=new JScrollPane(plateauIn,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		plateau.add(plateauIn);
		infos=new JPanel();

		addImpl(infos,JSplitPane.LEFT,1);
		addImpl(plateau,JSplitPane.RIGHT,2);

		infos.setLayout(new GridBagLayout());
		Box box=Box.createVerticalBox();
		infos.add(box,new GridBagConstraints());
		infos.setMinimumSize(new Dimension(150,parent.getHeight()));
		infos.setMaximumSize(new Dimension(150,parent.getHeight()));

		titre=new JPanel();
		Box box1=Box.createHorizontalBox();
		box1.add(titre);
		box.add(box1);

		liste=new JPanel();
		JScrollPane joueursListe=new JScrollPane(liste,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		joueursListe.add(liste);
		box1=Box.createHorizontalBox();
		box1.add(joueursListe);
		box.add(box1);
		joueursListe.setMinimumSize(new Dimension(100,100));

		de=new JButton("Lancer les dés !");
		box1=Box.createHorizontalBox();
		box1.add(de);
		box.add(box1);
		de.setForeground(Color.BLACK);
		de.setHorizontalAlignment(SwingConstants.CENTER);
		de.setHorizontalTextPosition(SwingConstants.CENTER);
		de.addActionListener( event -> {
			setEnabled(false);
			Jeu jeu=affichage.getJeu();
			setBackground(jeu.lancerDes());
			while(jeu.choix()){
				while (!jeu.choix(parent.ask(jeu.getChoix())))	// tant que la réponse au choix n'est pas valide, on demande une réponse
					parent.display("Entrée invalide !");
			}
System.out.println("lancer");
			jeu.jouer();
			setEnabled(true);
		});

		this.setOneTouchExpandable(true);
		this.setDividerLocation(150);

		this.parent=parent;
		formerWidth=parent.getWidth();
		parent.addComponentListener(this);
	}

	public void modifiedState(Jeu jeu){ // appelée lorsque le jeu est modifié : une partie a été chargée ou créée
		plateauIn.removeAll();
		titre.removeAll();
		liste.removeAll();
		plateauIn.revalidate();
		titre.revalidate();
		liste.revalidate();

		if (jeu!=null){
			// on affiche le titre (numéro du tour, joueur en train de jouer)
			titre.add(new JLabel("Tour "+jeu.getNumeroDuTour()));
			titre.add(new JLabel("C'est au tour de "+jeu.joueurEnTrainDeJouer()));

			// on affiche la liste des joueurs
			liste.setLayout(new GridLayout(jeu.nombreDeJoueurs,1));
			int n=0;
			for(Joueur joueur: jeu){
				liste.add(new JLabel(joueur.toString(),new ImageIcon("assets/pions/pion"+(n%15+1)),SwingConstants.LEFT));
			}

			// on dessine le plateau
			// TODO
		}
	}

	public void goTo(){
		parent.setSize(parent.getHeight()*129/92,parent.getHeight());
		parent.setMinimumSize(new Dimension(1161,828));
	}

	public void gameOver(GameOverEvent e) {
		parent.display(e.toString());
    }

    public void cannotPlay(CannotPlayEvent e){
    	parent.display(e.toString());
    }

    public void play(PlayEvent e){

    }

    public void changeDesValue(int i){
    	//afficher la valeur du dé ?
    }

	@Override
	public void paintComponent(Graphics g) { 
//		g.drawImage(img,0,0,parent.getWidth(),parent.getHeight(),null);
	}

	public void componentResized(ComponentEvent evt){
		if (this==parent.getAffiche()){
			int width=parent.getWidth();
			int height=parent.getHeight();
			if (width==formerWidth){ // changement vertical
				parent.setSize(height*129/92,height);
			}else{ // changement horizontal
				parent.setSize(width,width*92/129);
			}
			formerWidth=parent.getWidth();
		}
	}
	public void componentShown(ComponentEvent evt){}
	public void componentHidden(ComponentEvent evt){}
	public void componentMoved(ComponentEvent evt){}

}