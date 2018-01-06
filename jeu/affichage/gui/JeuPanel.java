package jeu.affichage.gui;

import jeu.Jeu;
import jeu.Joueur;

import jeu.plateau.cases.Case;

import jeu.affichage.Affichage;

import jeu.events.GameEvent;
import jeu.events.GameOverEvent;
import jeu.events.CannotPlayEvent;
import jeu.events.PlayEvent;

import jeu.options.Option;

import jeu.affichage.AffichageGUI;
import jeu.affichage.AffichageGUI.JeuModifiedStateListener;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

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

	private JPanel plateauIn;
	private JLabel tourLabel;
	private JLabel joueurLabel;
	private JScrollPane joueursListe;
	private JPanel liste;

	private JButton de;
	private JLabel desImage1;
	private JLabel desImage2;

	private final Fenetre parent;
	private final Affichage affichage;
	private int formerWidth;	
	private int numTour;

	private Jeu jeu;

	public JeuPanel(Affichage affichage, Fenetre parent){
		super(JSplitPane.HORIZONTAL_SPLIT);

		plateauIn=new JPanel();
		this.affichage=affichage;

		JScrollPane plateau=new JScrollPane(plateauIn,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		plateau.add(plateauIn);
		JPanel infos=new JPanel();

		addImpl(infos,JSplitPane.LEFT,1);
		addImpl(plateau,JSplitPane.RIGHT,2);

	/* LE PANNEAU INFO */
		infos.setLayout(new GridBagLayout());
		Box box=Box.createVerticalBox();
		infos.add(box,new GridBagConstraints());
		infos.setMinimumSize(new Dimension(150,parent.getHeight()));
		infos.setMaximumSize(new Dimension(150,parent.getHeight()));

	/* BOUTON MENU */
		ButtonUp menu=new ButtonUp("MENU",true,true,event -> parent.fireGoToMenu());
		Box box1=Box.createHorizontalBox();
		box1.add(menu);
		box.add(box1);

	/* LE CONTENU DU TITRE */
		JPanel titre=new JPanel();
		box1=Box.createHorizontalBox();
		box1.add(titre);
		box.add(box1);

		tourLabel=new JLabel();
		joueurLabel=new JLabel();
		box1=Box.createVerticalBox();
		Box box2=Box.createHorizontalBox();
		box2.add(tourLabel);
		box1.add(box2);
		box2=Box.createHorizontalBox();
		box2.add(joueurLabel);
		box1.add(box2);
		titre.setLayout(new GridBagLayout());
		titre.add(box1,new GridBagConstraints());

	/* LA LISTE DE JOUEURS */
		liste=new JPanel();
		joueursListe=new JScrollPane(liste,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		box1=Box.createHorizontalBox();
		box1.add(joueursListe);
		box.add(box1);

	/* LES DES */
		/* IMAGES DES */	
		JPanel desImages=new JPanel();
		desImages.setMinimumSize(new Dimension(160,80));
		desImages.setMaximumSize(new Dimension(160,80));
		desImages.setLayout(new GridLayout(1,2));
		desImage1=new JLabel();
		desImages.add(desImage1);
		desImage1.setMinimumSize(new Dimension(80,80));
		desImage1.setMaximumSize(new Dimension(80,80));
		desImage2=new JLabel();
		desImages.add(desImage2);
		desImage2.setMinimumSize(new Dimension(80,80));
		desImage2.setMaximumSize(new Dimension(80,80));

		/* BOUTON DES */
		de=new JButton("Lancer les dés !");
		box1=Box.createHorizontalBox();
		box1.add(de);
		box.add(box1);
		box.add(desImages);
		de.setForeground(Color.BLACK);
		de.setHorizontalAlignment(SwingConstants.CENTER);
		de.setHorizontalTextPosition(SwingConstants.CENTER);
		de.addActionListener( event -> {
			setEnabled(false);
			Jeu jeu=affichage.getJeu();
			jeu.lancerDes();
			while(jeu.choix() && !jeu.choix(parent.ask(jeu.getChoix())))	// tant que la réponse au choix n'est pas valide, on demande une réponse
				parent.display("Entrée invalide !");
			jeu.jouer();

			if (!jeu.estFini()){
				de.setEnabled(true);
				joueurLabel.setText(jeu.joueurEnTrainDeJouer().toString());
				if (numTour!=jeu.getNumeroDuTour()){
					numTour=jeu.getNumeroDuTour();
					tourLabel.setText("Tour "+numTour);
					if (parent.position())
						parent.display("Positions des joueurs :\n"+getPositions());
				}
			}
		});


		this.setDividerLocation(165);
		this.parent=parent;
		formerWidth=parent.getWidth();
		parent.addComponentListener(this);
	}

	public void modifiedState(Jeu jeu){ // appelée lorsque le jeu est modifié : une partie a été chargée ou créée
		this.jeu=jeu;

		desImage1.setIcon(null);
		desImage2.setIcon(null);

		joueurLabel.setText("");
		tourLabel.setText("");

		liste.removeAll();
		liste.revalidate();

		plateauIn.removeAll();
		plateauIn.revalidate();

		if (jeu!=null){
			de.setEnabled(true);
			numTour=jeu.getNumeroDuTour();
			tourLabel.setText("Tour "+jeu.getNumeroDuTour());
			joueurLabel.setText(jeu.joueurEnTrainDeJouer().toString());

			// on affiche la liste des joueurs
			liste.setLayout(new GridBagLayout());
			Box boite=Box.createVerticalBox();
			GridBagConstraints constraints=new GridBagConstraints();
			constraints.weightx = 1;
			constraints.weighty = 0;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.fill = constraints.BOTH;
			liste.add(boite,constraints);

			int n=0;
			ArrayList<JLabel> tmp=new ArrayList<JLabel>();
			int cote=120;
			joueursListe.setMaximumSize(new Dimension(140,parent.getHeight()-200));
			joueursListe.setMinimumSize(new Dimension(140,((parent.getHeight()-200>jeu.nombreDeJoueurs*135)?jeu.nombreDeJoueurs*135:parent.getHeight()-200)));
			for(Joueur joueur: jeu){
				ImageIcon i=new ImageIcon("assets/pions/pion"+(n%18+1)+".png");
				JLabel l;
				if (i.getIconWidth()>i.getIconHeight())
					l=new JLabel(new ImageIcon(i.getImage().getScaledInstance(cote,i.getIconHeight()*cote/i.getIconWidth(),Image.SCALE_DEFAULT)));
				else
					l=new JLabel(new ImageIcon(i.getImage().getScaledInstance(i.getIconWidth()*cote/i.getIconHeight(),cote,Image.SCALE_DEFAULT)));
				tmp.add(l);
				l.setText(joueur.toString());
				l.setHorizontalTextPosition(JLabel.CENTER);
				l.setVerticalTextPosition(JLabel.BOTTOM);
				l.setVisible(true);
				l.setMinimumSize(new Dimension(135,cote+15));
				l.setMaximumSize(new Dimension(135,cote+15));
				l.setPreferredSize(new Dimension(135,cote+15));
				Box b=Box.createHorizontalBox();
				b.add(l);
				boite.add(b);
				n++;
			}
			joueursLabels=new CopyOnWriteArrayList<JLabel>(tmp);
			//S'éffondre si trop de joueurs...


			// on dessine le plateau
			// TODO
		}
	}

	private CopyOnWriteArrayList<JLabel> joueursLabels;

	public void goTo(){
		parent.setSize(parent.getHeight()*129/92,parent.getHeight());
		parent.setMinimumSize(new Dimension(1161,828));
	}

	public void gameOver(GameOverEvent e) {
		de.setEnabled(false);
		parent.display("Partie finie ! "+e.toString()+"\n"+e.getClassement());
    }

    public void cannotPlay(CannotPlayEvent e){
    	if (parent.cantPlay())
	    	parent.display(e.toString());
    }

    public void play(PlayEvent e){
    	if (parent.mouvement())
			parent.display(e.getJoueur().toString()+" (score "+e.getJoueur().getScore()+") a fait "+e.getDes()+" :\n"+getPositions(e.getJoueur()));
    }

	private String getPositions(Joueur joueur){
		StringBuilder sb=new StringBuilder();
		Jeu jeu=affichage.getJeu();
		if (joueur.getNombrePions()==1)
			sb.append("case "+(jeu.getCase(joueur.getCase())+1)+" (case "+joueur.getCase()+")");
		else{
			int i=1;
			for (Case c : joueur){
				sb.append("pion "+i+" : "+(jeu.getCase(c)+1)+" (case "+c+"). ");
				i++;
			}
		}
		return sb.toString();
	}

	private String getPositions(){
		StringBuilder sb=new StringBuilder();
		Jeu jeu=affichage.getJeu();
		for (Joueur joueur : jeu)
			sb.append(joueur+" (score "+joueur.getScore()+") : "+getPositions(joueur)+"\n");
		return sb.toString();
	}

    public void changeDesValue(int[] i){
    	desImage1.setIcon(new ImageIcon("assets/des/"+i[0]+".png"));

    	if (i.length==2)
	    	desImage2.setIcon(new ImageIcon("assets/des/"+i[1]+".png"));
	    else
	    	desImage2.setIcon(null);
    }

/*	@Override
	public void paintComponent(Graphics g) { 
//		g.drawImage(img,0,0,parent.getWidth(),parent.getHeight(),null);
	}
*/
	public void componentResized(ComponentEvent evt){
		if (parent.estAffiche(this)){
			int width=parent.getWidth();
			int height=parent.getHeight();

			joueursListe.setMaximumSize(new Dimension(140,height-200));
			joueursListe.setMinimumSize(new Dimension(140,((height-200>jeu.nombreDeJoueurs*135)?jeu.nombreDeJoueurs*135:height-200)));

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