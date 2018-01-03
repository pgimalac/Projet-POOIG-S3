package jeu.affichage;

import jeu.Jeu;
import jeu.JeuOie;
import jeu.JeuNumeri;
import jeu.Joueur;

import jeu.affichage.MenuButton;

//import jeu.listeners.GameListener;
//import jeu.listeners.GameOverListener;
//import jeu.listeners.CannotPlayListener;
//import jeu.listeners.PlayListener;

//import jeu.events.GameEvent;
//import jeu.events.GameOverEvent;
//import jeu.events.CannotPlayEvent;
//import jeu.events.PlayEvent;

import jeu.options.Option;
import jeu.options.questions.Question;

import jeu.plateau.Plateau;
import jeu.plateau.cases.Case;

import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.SwingConstants;

import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.io.File;
import java.io.IOException;

public class AffichageGUI extends Affichage{

	public void afficher(){}

	private Fenetre fenetre;

	public AffichageGUI(){
		modifiedStateListeners=new ArrayList<JeuModifiedStateListener>();
		fenetre=new Fenetre();
		setJeu(null); // pas necéssaire mais on ne sait jamais
	}

	@Override
	public void question(Question q){

	}

	@Override
	protected void display(String s){
		fenetre.display(s);
	}

	@Override
	protected void setJeu(Jeu jeu){
		super.setJeu(jeu);
		fireJeuModifState(jeu);
	}

	interface JeuModifiedStateListener extends EventListener{
		public void modifiedState(Jeu jeu);
	}

	private ArrayList<JeuModifiedStateListener> modifiedStateListeners;

	public void add(JeuModifiedStateListener j){
		if (j!=null)
			modifiedStateListeners.add(j);
	}

	private void fireJeuModifState(Jeu jeu){
		for (JeuModifiedStateListener j : modifiedStateListeners)
			j.modifiedState(jeu);
	}

	class Fenetre extends JFrame{

		CardLayout cardLayout;
		Container content;

		MenuPanel menu;
		JeuPanel jeu;
		NouveauPanel nouveau;
		JPanelUp parametres;
		JPanelUp charger;

		JPanelUp affiche;

		public void display(String s){}

		public Fenetre(){
			super();
			content=this.getContentPane();
			this.setSize(1161,828);
			this.setTitle("Un super jeu de qualité");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);

			cardLayout = new CardLayout();

			jeu=new JeuPanel();
			menu=new MenuPanel();
			nouveau=new NouveauPanel();
			parametres=new JPanelUp(){
				public void goTo(){}
				public void componentResized(ComponentEvent evt){}
			};
			charger=new JPanelUp(){
				public void goTo(){

				}
				public void componentResized(ComponentEvent evt){}
			};

			content.setLayout(cardLayout);

			content.add(menu,"menu");
			content.add(jeu,"jeu");
			content.add(nouveau,"nouveau");
			content.add(charger,"charger");
			content.add(parametres,"parametres");

			this.setVisible(true);
			this.addComponentListener(jeu);
			this.addComponentListener(menu);
			this.addComponentListener(parametres);
			this.addComponentListener(charger);
			this.addComponentListener(nouveau);

			formerWidth=getWidth();
			fireGoToMenu();
		}

		private int formerWidth;

		public void fireGoToJeu(){ affiche=jeu; jeu.goTo(); cardLayout.show(content,"jeu"); }
		public void fireGoToNouveau(){ affiche=nouveau; nouveau.goTo(); cardLayout.show(content,"nouveau"); }
		public void fireGoToParam(){ affiche=parametres; parametres.goTo(); cardLayout.show(content,"parametres"); }
		public void fireGoToCharger(){ affiche=charger; charger.goTo(); cardLayout.show(content,"charger"); }
		public void fireGoToMenu(){ affiche=menu; menu.goTo(); cardLayout.show(content,"menu"); }

		class JPanelUp extends JPanel implements ComponentListener{
			public void goTo(){
				Fenetre.this.setSize(Fenetre.this.getHeight()*129/92,Fenetre.this.getHeight());
				Fenetre.this.setMinimumSize(new Dimension(387,276));
			}

			private Image img = null;

			{
				try{
					img=ImageIO.read(new File("assets/menu.JPG"));
				}catch(IOException e){}
			}

			@Override
			public void paintComponent(Graphics g) { 
				g.drawImage(img,0,0,Fenetre.this.getWidth(),Fenetre.this.getHeight(),null);
			}

			public void componentResized(ComponentEvent evt){
				if (this==Fenetre.this.affiche){
					int width=Fenetre.this.getWidth();
					int height=Fenetre.this.getHeight();
					if (width==Fenetre.this.formerWidth){ // changement vertical
						Fenetre.this.setSize(height*129/92,height);
					}else{ // changement horizontal
						Fenetre.this.setSize(width,width*92/129);
					}
					Fenetre.this.formerWidth=Fenetre.this.getWidth();
				}
			}
			public void componentHidden(ComponentEvent evt){}
			public void componentShown(ComponentEvent evt){}
			public void componentMoved(ComponentEvent evt){}
		}

		class NouveauPanel extends JPanelUp{
			public NouveauPanel(){
				super();
			}

			public void goTo(){
				this.removeAll();	
			// on affiche les deux choix possibles :
				setLayout(new GridBagLayout());
				Box bo=Box.createVerticalBox();
				add(bo,new GridBagConstraints());
				bo.add(Box.createRigidArea(new Dimension(5,Fenetre.this.getHeight()/7)));
				Box box = Box.createHorizontalBox();
				bo.add(box);

				ButtonUp oie=new ButtonUp("Jeu de l'oie",true,true,null);
				ButtonUp numeri=new ButtonUp("Numéri",true,true,null);
				oie.addToBox(box,true,Fenetre.this.getWidth()/8,5);
				numeri.addToBox(box,true);

				ButtonUp menu=new ButtonUp("Retourner au menu",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToMenu();
					}
				});
				menu.addToBox(bo,false);

				Box box1=Box.createHorizontalBox();
				box1.add(menu);
				bo.add(Box.createRigidArea(new Dimension(5,50)));
				bo.add(box1);
			}
		}

		class JeuPanel extends JPanelUp{

			public JPanel infos;
			public JPanel plateau;

			public JeuPanel(){
				super();
				setLayout(new BorderLayout());

				infos=new JPanel();
				plateau=new JPanel();

				add(infos,BorderLayout.EAST);
				add(plateau,BorderLayout.CENTER);


				infos.setBackground(Color.RED);
				plateau.setBackground(Color.GREEN);

			}

			@Override
			public void goTo(){}
		}

		class MenuPanel extends JPanelUp implements JeuModifiedStateListener{

			public ButtonUp continuer,sauvegarder,recommencer,nouveau,charger,modifier,credits,quitter;

			@Override
			public void modifiedState(Jeu j){
				continuer.setEnabled(j!=null && !j.estFini());
				sauvegarder.setEnabled(j!=null && !j.estFini());
				recommencer.setEnabled(j!=null);
			}

			public MenuPanel(){
				super();
				setLayout(new GridBagLayout());
				Box box = Box.createVerticalBox();
				add(box,new GridBagConstraints());
				AffichageGUI.this.add(this);

				continuer=new ButtonUp("Continuer",true,false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToJeu();
					}
				});
				continuer.addToBox(box,false,5,5);

				recommencer=new ButtonUp("Recommencer",true,false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.super.getJeu().recommencer();
					}
				});
				recommencer.addToBox(box,false,5,5);

				nouveau=new ButtonUp("Nouvelle partie",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToNouveau();
					}
				});
				nouveau.addToBox(box,false,5,5);

				sauvegarder=new ButtonUp("Sauvegarder",true,false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.super.sauvegarderLeJeu();
					}
				});
				sauvegarder.addToBox(box,false,5,5);

				charger=new ButtonUp("Charger une partie",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToCharger();
					}
				});
				charger.addToBox(box,false,5,5);

				modifier=new ButtonUp("Paramètres",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToParam();
					}
				});
				modifier.addToBox(box,false,5,5);

				credits=new ButtonUp("Credits",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.this.display(Affichage.credits);
					}
				});
				credits.addToBox(box,false,5,5);

				quitter=new ButtonUp("Quitter",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						System.exit(0);
					}
				});
				quitter.addToBox(box,false,5,5);
			}
		}
	}
}