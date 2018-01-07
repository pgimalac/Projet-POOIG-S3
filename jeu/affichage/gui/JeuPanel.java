package jeu.affichage.gui;

import jeu.Jeu;
import jeu.Joueur;

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;

import jeu.affichage.Affichage;
import jeu.affichage.AffichagePlateau;

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
import javax.swing.Icon;
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

public class JeuPanel extends JSplitPane implements JeuModifiedStateListener{

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

			paintPawns();

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


			/* On crée les cases du plateau */
			ArrayList<CasePanel> tmp2=new ArrayList<CasePanel>();
			n=1;
			for (Case c : jeu.getPlateau()){
				tmp2.add(new CasePanel(c,n,jeu.getCase(c.getCase())+1));
				n++;
			}
			cases=new CopyOnWriteArrayList<CasePanel>(tmp2);

			remplirPlateau();
			paintPawns();
		}
	}

	private CopyOnWriteArrayList<JLabel> joueursLabels;
	private CopyOnWriteArrayList<CasePanel> cases; // propre à chaque jeu, contient la liste des cases du plateau du jeu

	public void goTo(){
		parent.setMinimumSize(new Dimension(1161,828));
	}

	private AffichagePlateau affichagePlateau;

	private void remplirPlateau(){
		affichagePlateau.afficher();
	}

	public void setAffichagePlateau(int aff){
		switch(aff){
			case Fenetre.SPIRALE :
				affichagePlateau=new AffichagePlateauSpiraleGUI();
				break;
			case Fenetre.COLONNE :
				affichagePlateau=new AffichagePlateauColonneGUI();
				break;
			case Fenetre.RECTANGLE :
				affichagePlateau=new AffichagePlateauRectangleGUI();
				break;
			case Fenetre.ZIGZAG :
				affichagePlateau=new AffichagePlateauZigzagGUI();
				break;
		}
		parent.setAffichagePlateau(aff);
		if (affichage.getJeu()!=null){
			remplirPlateau();
		}
	}

	private void paintPawns(){
		// utiliser joueursLabels qui contient les icones des joueurs
		// utiliser cases qui contient la liste des casePanel pour les positionner
		Jeu jeu=affichage.getJeu();
		Plateau plateau=jeu.getPlateau();
		int[] nb=new int[plateau.size()];

//		plateauIn.repaint();

		for (Joueur j : jeu){
			for (Case c : j){
				nb[plateau.getCase(c)]++;
			}
		}

		int nombrePionsParJoueur=jeu.getJoueur(0).getNombrePions();
		for (Joueur j : jeu){
			Image i=((ImageIcon)getIcon(joueursLabels,j.toString())).getImage();
			for (Case c : j){
				CasePanel cp=cases.get(plateau.getCase(c));
				cp.getGraphics().drawImage(i,0,0,cp.getWidth()/2,cp.getWidth()/2,null);
			}
		}
	}

	private Icon getIcon (CopyOnWriteArrayList<JLabel> liste, String s){
		for (JLabel jl : liste){
			if (jl.getText().equals(s))
				return jl.getIcon();
		}
		return null;
	}


	class AffichagePlateauSpiraleGUI implements AffichagePlateau{
		@Override
		public void afficher(){
		/*	int taille=plateau.size();
			int l=NOMBRE_CASE_LARGEUR;
			int h=(taille/l)+1;

			// on cherche un rectangle exact pour le plateau
			for (int n=NOMBRE_CASE_LARGEUR;n>NOMBRE_CASE_LARGEUR/2;n--){
				if (taille==(taille/n)*n){
					l=n;
					h=taille/n;
					break;
				}
			}

			if (taille!=l*h){
				int d=(l*h)-taille;
				for(int n=NOMBRE_CASE_LARGEUR-1;n>NOMBRE_CASE_LARGEUR/2;n--){
					if (d>n*((taille/n)+1)-taille){
						l=n;
						h=(taille/l)+1;
						d=(l*h)-taille;
					}
				}
			}
			// on trouve h et l tels que l'écart entre la taille du plateau et h*l soit la plus petite possible (en ayant l pas trop petit)

			String[] debut=new String[h];
			String[] fin=new String[h];

			for (int n=0;n<h;n++){
				debut[n]="";
				fin[n]="";
			}

			int[] lignes=new int[h];
			int[] colonnes=new int[l];

			int directionH=1; // -1 si l'on va à gauche
			int directionV=0; // -1 si l'on va vers le haut
			int coorH=0;
			int coorV=0;

			// c'est couteux mais je ne vois pas comment le faire autrement aussi facilement

			StringBuilder sb=new StringBuilder(WHITEf);
			for (int n=0;n<l*h;n++){
				String s;
				if (n<taille)
					s=COLORS[n%COLORS.length]+centrer(LARGEUR_CASE,(n+1)+"-"+plateau.getCase(n).toString());
				else
					s=WHITEb+centrer(LARGEUR_CASE," ");

				if (directionH==1 || directionV==-1)
					debut[coorV]=debut[coorV]+s;
				else // (directionH==-1 || directionV==-1)
					fin[coorV]=s+fin[coorV];

				lignes[coorV]++;
				colonnes[coorH]++;

				if (lignes[coorV]==l && directionV==0){
					if (directionH==1)
						directionV=1;
					else
						directionV=-1;
					directionH=0;
				}else if (colonnes[coorH]==h && directionH==0){
					if (directionV==1)
						directionH=-1;
					else
						directionH=1;
					directionV=0;
				}

				coorH+=directionH;
				coorV+=directionV;
			}
			for (int n=0;n<h;n++)
				sb.append(debut[n]+fin[n]+RESET+"\n"+WHITEf);

			System.out.println(sb.toString()+RESET);
*/
		}

		@Override
		public String toString(){
			return "spirale";
		}
	}


	class AffichagePlateauZigzagGUI implements AffichagePlateau{
		@Override
		public void afficher(){
			for (CasePanel cp : cases){

			}
		}

		@Override
		public String toString(){
			return "zigzag";
		}
	}
	class AffichagePlateauRectangleGUI implements AffichagePlateau{
		@Override
		public void afficher(){
	 		plateauIn.removeAll();
			plateauIn.revalidate();

			int size=cases.size();
			int w=size;
			int h=size;
			for (int i=10;i>5;i--){
				if (size/i*i==size){
					w=i;
					h=size/i;
					break;
				}
				else if (size-size/i*i<w*h-size){
					w=i;
					h=size/i+1;
				}
			}

			plateauIn.setLayout(new GridLayout(h,w));
			for (CasePanel cp : cases){
				plateauIn.add(cp);
			}
		}

		@Override
		public String toString(){
			return "rectangle";
		}
	}

	class AffichagePlateauColonneGUI implements AffichagePlateau{
		@Override
		public void afficher(){
			plateauIn.removeAll();
			plateauIn.revalidate();

			GridBagConstraints constraints=new GridBagConstraints();
			constraints.weightx = 1;
			constraints.weighty = 0;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.fill = constraints.BOTH;
			Box boite=Box.createVerticalBox();
			plateauIn.setLayout(new GridBagLayout());
			plateauIn.add(boite,constraints);
			for (CasePanel cp : cases){
				cp.setPreferredSize(new Dimension(CasePanel.MINIMUM,CasePanel.MINIMUM));
				cp.setMaximumSize(new Dimension(CasePanel.MINIMUM,CasePanel.MINIMUM));
				boite.add(Box.createHorizontalBox().add(cp));
			}
			plateauIn.setMinimumSize(new Dimension(CasePanel.MINIMUM,affichage.getJeu().getPlateau().size()*CasePanel.MINIMUM));
			plateauIn.setPreferredSize(new Dimension(CasePanel.MINIMUM,affichage.getJeu().getPlateau().size()*CasePanel.MINIMUM));
			plateauIn.setMaximumSize(new Dimension(CasePanel.MINIMUM,affichage.getJeu().getPlateau().size()*CasePanel.MINIMUM));
		}

		@Override
		public String toString(){
			return "colonne";
		}
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


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setSize(new Dimension(parent.getWidth(),parent.getHeight()));
/*		if (parent.estAffiche(this)){
			paintPawns();
		} */
	} 
}