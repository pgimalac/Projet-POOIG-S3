package jeu.affichage;

import jeu.Jeu;
import jeu.JeuOie;
import jeu.JeuNumeri;
import jeu.Joueur;

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

	public AffichageGUI(){
		Fenetre fenetre=new Fenetre();
	}

	@Override
	protected AffichagePlateau getDefaultAffichagePlateau(){
		return null;
	}

	@Override
	public void question(Question q){

	}

	@Override
	protected void display(String s){

	}

	@Override
	protected void setJeu(Jeu jeu){
		super.setJeu(jeu);
		fireJeuModifState(jeu);
	}

	private void fireJeuModifState(Jeu jeu){ //TODO

	}

	class Fenetre extends JFrame{

		CardLayout cardLayout;
		Container content;

		MenuPanel menu;
		JeuPanel jeu;
		JPanelListener nouveau;
		JPanelListener parametres;
		JPanelListener charger;

		JPanelListener affiche;

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
			parametres=new JPanelListener(){
				public void goTo(){} 
				public void componentResized(ComponentEvent evt){}
			};
			charger=new JPanelListener(){
				public void goTo(){} 
				public void componentResized(ComponentEvent evt){}
			};
			nouveau=new JPanelListener(){
				public void goTo(){
					Fenetre.this.setSize(10,10);
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

		class JPanelListener extends JPanel implements ComponentListener{
			public void goTo(){}

			public void componentResized(ComponentEvent evt){}
			public void componentHidden(ComponentEvent evt){}
			public void componentShown(ComponentEvent evt){}
			public void componentMoved(ComponentEvent evt){}
		}

		class JeuPanel extends JPanelListener{

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

		class MenuPanel extends JPanelListener{

			public JButton continuer,sauvegarder,recommencer,nouveau,charger,modifier,credits,quitter;
			private Image img = null;

			@Override
			public void paintComponent(Graphics g) { 
				g.drawImage(img,0,0,Fenetre.this.getWidth(),Fenetre.this.getHeight(),null);
			}

			@Override
			public void goTo(){
				Fenetre.this.setSize(Fenetre.this.getHeight()*129/92,Fenetre.this.getHeight());
				Fenetre.this.setMinimumSize(new Dimension(387,276));
			}

			@Override
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

			public MenuPanel(){
				super();
				setLayout(new GridBagLayout());
				Box box = Box.createVerticalBox();
				add(box,new GridBagConstraints());

				try{
					img=ImageIO.read(new File("assets/menu.JPG"));
				}catch(IOException e){}

				continuer=new MenuButton(box,"Continuer",false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToJeu();
					}
				});

				recommencer=new MenuButton(box,"Recommencer",false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.super.getJeu().recommencer();
					}
				});

				nouveau=new MenuButton(box,"Nouvelle partie",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToNouveau();
					}
				});

				sauvegarder=new MenuButton(box,"Sauvegarder",false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.super.sauvegarderLeJeu();
					}
				});

				charger=new MenuButton(box,"Charger une partie",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToCharger();
					}
				});

				modifier=new MenuButton(box,"Paramètres",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToParam();
					}
				});

				credits=new MenuButton(box,"Credits",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.this.display(Affichage.credits);
					}
				});

				quitter=new MenuButton(box,"Quitter",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						System.exit(0);
					}
				});
			}

			class MenuButton extends JButton{
				public MenuButton(Box box, String etiquette, boolean enabled, ActionListener listener){
					super(etiquette);
					setForeground(Color.BLACK);
					setVisible(true);
					setEnabled(enabled);
					setHorizontalAlignment(SwingConstants.CENTER);
					setHorizontalTextPosition(SwingConstants.CENTER);
					box.add(Box.createRigidArea(new Dimension(5,5)));
					Box box1=Box.createHorizontalBox();
					box1.add(this);
					box.add(box1);
					addActionListener(listener);

					setOpaque(false);
					setContentAreaFilled(true);
					setBorderPainted(true);
					setFocusPainted(true);
				}
			}
		}
	}
}