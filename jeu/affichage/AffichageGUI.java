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

import java.io.File;
import java.io.IOException;

public class AffichageGUI extends Affichage{

	public void afficher(){}

	public AffichageGUI(){
		Fenetre fenetre=new Fenetre();
	}

	protected AffichagePlateau getDefaultAffichagePlateau(){
		return null;
	}

	public void question(Question q){

	}

	protected void display(String s){

	}

	class Fenetre extends JFrame{

		CardLayout cardLayout = new CardLayout();
		Container content;

		MenuPanel menu;
		JeuPanel jeu;
		JPanel parametres;
		JPanel charger;

		public Fenetre(){
			super();
			content=this.getContentPane();
			this.setTitle("Un super jeu de qualité");
			this.setSize(1161,828);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);

			cardLayout = new CardLayout();

			jeu=new JeuPanel();
			jeu.setBackground(Color.red);
			menu=new MenuPanel();
			parametres=new JPanel();
			charger=new JPanel();

			content.setLayout(cardLayout);

			content.add(menu,"menu");
			content.add(jeu,"jeu");
			content.add(charger,"charger");
			content.add(parametres,"parametres");

			this.setVisible(true);
		}

		class JeuPanel extends JPanel{

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
		}

		class MenuPanel extends JPanel{

			public JButton continuer,sauvegarder,recommencer,nouveau,charger,modifier,credits,quitter;
			private Image img = null;

			public void paintComponent(Graphics g) { 
				g.drawImage(img,0,0,null);
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
						cardLayout.show(content,"jeu");
					}
				});

				recommencer=new MenuButton(box,"Recommencer",false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.super.getJeu().recommencer();
					}
				});

				nouveau=new MenuButton(box,"Nouvelle partie",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						cardLayout.show(content,"nouveau");
					}
				});

				sauvegarder=new MenuButton(box,"Sauvegarder",false,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.super.sauvegarderLeJeu();
					}
				});

				charger=new MenuButton(box,"Charger une partie",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						cardLayout.show(content,"charger");
					}
				});

				modifier=new MenuButton(box,"Paramètres",true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						cardLayout.show(content,"parametres");
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