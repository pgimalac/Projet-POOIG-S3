package jeu.affichage;

import jeu.Jeu;
import jeu.JeuOie;
import jeu.JeuNumeri;
import jeu.Joueur;

import jeu.affichage.ButtonUp;

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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
		if (((JeuOie)super.getJeu()).repondre(fenetre.question(q))){
			display("Bonne réponse ! 1 point en plus !");
		}else{
			display("Mauvaise réponse ! La bonne réponse était "+q.getReponse()+".");
		}
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

		public void display(String s){
			JOptionPane.showMessageDialog(null,s,"Information",JOptionPane.INFORMATION_MESSAGE);
		}

		public String question(Question q){
			return (String)JOptionPane.showInputDialog(
				null,q.getQuestion(),"Question",JOptionPane.QUESTION_MESSAGE,
				null,null,"");
		}

		public Fenetre(){
			super();
			content=this.getContentPane();
			this.setSize(900,642);
			this.setTitle("Un super jeu de qualité");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);

			cardLayout = new CardLayout();

			jeu=new JeuPanel();
			menu=new MenuPanel();
			nouveau=new NouveauPanel();
			parametres=new JPanelUp(){
				public void goTo(){} //TODO
			};
			charger=new JPanelUp(){
				public void goTo(){} //TODO
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
				this.revalidate();

				Fenetre.this.setMinimumSize(new Dimension(781,557));

				// on affiche les deux choix possibles :
				this.setLayout(new GridBagLayout());
				Box page=Box.createVerticalBox();
				Box jeux = Box.createHorizontalBox();
				Box bouton=Box.createHorizontalBox();

				ButtonUp oie=new ButtonUp("Jeu de l'oie",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						choisirJoueurs(true);
					}
				});
				ButtonUp numeri=new ButtonUp("Numéri",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						choisirJoueurs(false);
					}
				});
				ButtonUp menu=new ButtonUp("Retourner au menu",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToMenu();
					}
				});

				
				oie.addToBox(jeux,true,Fenetre.this.getWidth()/8,5);
				numeri.addToBox(jeux,true);
				menu.addToBox(page,false);

				bouton.add(menu);
				page.add(jeux);
				page.add(Box.createRigidArea(new Dimension(5,25)));
				page.add(bouton);

				this.add(page,new GridBagConstraints());

				// description des jeux
				Box descriptions=Box.createHorizontalBox();

				JLabel label=new JLabel("Jeu de l'oie");
				LabelSized labelT=new LabelSized(JeuOie.description,Fenetre.this);
				labelT.setOpaque(false);

				Box oieB=Box.createVerticalBox();
				Box tmp=Box.createHorizontalBox();
				tmp.add(label);
				oieB.add(tmp);
				tmp=Box.createHorizontalBox();
				tmp.add(labelT);
				oieB.add(tmp);

				label=new JLabel("Numéri");
				labelT=new LabelSized(JeuNumeri.description,Fenetre.this);
				labelT.setOpaque(false);

				Box numeriB=Box.createVerticalBox();
				tmp=Box.createHorizontalBox();
				tmp.add(label);
				numeriB.add(tmp);
				tmp=Box.createHorizontalBox();
				tmp.add(labelT);
				numeriB.add(tmp);

				descriptions.add(oieB);
				descriptions.add(Box.createRigidArea(new Dimension(20,5)));
				descriptions.add(numeriB);

				page.add(descriptions);

				this.repaint();
			}

			private void choisirJoueurs(boolean oie){
				int min=((oie)?JeuOie.getMinimumJoueurs():JeuNumeri.getMinimumJoueurs());
				int max=((oie)?JeuOie.getMaximumJoueurs():JeuNumeri.getMaximumJoueurs());
				this.removeAll();
				this.revalidate();

				namesPlayer=new ArrayList<JTextField>();
				numberPlayers=0;

				Box box=Box.createVerticalBox();
				this.add(box,new GridBagConstraints());

				Box titre=Box.createHorizontalBox();
				JLabel jl=new JLabel("Entrez les joueurs (entre "+min+" et "+max+") :");
				titre.add(jl);
				box.add(titre);
				box.add(Box.createRigidArea(new Dimension(5,20)));

				Box liste=Box.createVerticalBox();
				box.add(liste);

				JButton add=new JButton("Ajouter un joueur");
				add.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
						if (numberPlayers<max){
							numberPlayers++;
							Box b=Box.createHorizontalBox();

							JTextField jtf=new JTextField(getNextValidName());
							jtf.setBorder(BorderFactory.createLineBorder(Color.GREEN,2,true));
							jtf.getDocument().addDocumentListener(new DocumentListener(){
								public void changedUpdate(DocumentEvent e) {
									if (validName(jtf.getText())==1)
										jtf.setBorder(BorderFactory.createLineBorder(Color.GREEN,2,true));
									else
										jtf.setBorder(BorderFactory.createLineBorder(Color.RED,2,true));
									checkValid(min,max);
								}
								public void removeUpdate(DocumentEvent e) { changedUpdate(e); }
								public void insertUpdate(DocumentEvent e) { changedUpdate(e); }
							});

							JButton jb=	new JButton(new ImageIcon(new ImageIcon("assets/suppr.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
							jb.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent event){
									liste.remove(b);
									namesPlayer.remove(jtf);
									numberPlayers--;
									checkValid(min,max);
									revalidate();
									repaint();
								}
							});
							b.add(jb);

							namesPlayer.add(jtf);
							b.add(jtf);
							liste.add(b);

							Fenetre.this.revalidate();
							Fenetre.this.repaint();
							checkValid(min,max);
						}else{
							Fenetre.this.display("Vous ne pouvez plus ajouter de joueur !");
						}
					}
				});
				Box tmp=Box.createHorizontalBox();
				tmp.add(add);
				liste.add(tmp);


				Box boutons=Box.createHorizontalBox();
				tmp=Box.createVerticalBox();
				tmp.add(new ButtonUp("Retourner au menu",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToMenu();
					}
				}));
				boutons.add(tmp);
				boutons.add(Box.createRigidArea(new Dimension(20,5)));
				tmp=Box.createVerticalBox();
				param=new ButtonUp("Paramétrer la partie",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						Jeu jeu;
						if (oie)
							jeu=new JeuOie(namesPlayer.size());
						else
							jeu=new JeuNumeri(namesPlayer.size());
						for (int i=0;i<namesPlayer.size();i++)
							jeu.setNom(i,namesPlayer.get(i).getText());
						parametrerPartie(jeu);
					}
				});
				param.setOpaque(true);
				tmp.add(param);
				boutons.add(tmp);
				box.add(boutons);


				checkValid(min,max);
				this.repaint();
			}

			private JButton param;

			private void checkValid(int min, int max){
				boolean b=true;
				if (namesPlayer.size()<min || namesPlayer.size()>max)
					b=false;
				for (int i=0;b && i<namesPlayer.size();i++)
					b=(validName(namesPlayer.get(i).getText())==1);
				param.setEnabled(b);
				if (b){
					param.setBackground(Color.GREEN);
				}else{
					param.setBackground(Color.RED);
				}
			}

			private String getNextValidName(){
				int n=1;
				while (validName("Joueur "+n)!=0)
					n++;
				return "Joueur "+n;
			}

			private int validName(String s){ // renvoie le nombre de joueurs qui ont ce nom
				int n=0;
				for (JTextField j : namesPlayer){
					if (j.getText().equals(s))
						n++;
				}
				return n;
			}
			
			private ArrayList<JTextField> namesPlayer;
			private int numberPlayers;

			private void parametrerPartie(Jeu jeu){
				this.removeAll();
				this.revalidate();

				Box box=Box.createVerticalBox();
				Box options=Box.createHorizontalBox();
				Box boutons=Box.createHorizontalBox();

				Box b=Box.createVerticalBox();
				options.add(b);
				box.add(options);
				box.add(Box.createRigidArea(new Dimension(5,20)));
				box.add(boutons);
				this.add(box,new GridBagConstraints());

				ArrayList<Option> op=jeu.getOptions();
				for (Option o : op){
					Box tmp=Box.createVerticalBox();
					JLabel label=new JLabel(o.toString().toUpperCase());
					Box tmp2=Box.createVerticalBox();
					tmp2.add(label);
					tmp.add(tmp2);
					ButtonGroup bg=new ButtonGroup();
					String[] t=o.getValues();
					for (int n=0;n<t.length;n++){
						JRadioButtonUp jr=new JRadioButtonUp(t[n],(n==o.getIntValue()),o,n);
						jr.setOpaque(false);

						bg.add(jr);
						tmp2.add(jr);
					}
					tmp.add(tmp2);
					b.add(Box.createRigidArea(new Dimension(5,15)));
					b.add(tmp);
				}

				Box tmp=Box.createVerticalBox();
				tmp.add(new ButtonUp("Retourner au menu",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						fireGoToMenu();
					}
				}));
				boutons.add(tmp);
				boutons.add(Box.createRigidArea(new Dimension(20,5)));
				tmp=Box.createVerticalBox();
				tmp.add(new ButtonUp("Créer la partie",true,true,new ActionListener(){
					public void actionPerformed(ActionEvent event){
						AffichageGUI.this.setJeu(jeu);
						Fenetre.this.fireGoToJeu();
					}
				}));
				boutons.add(tmp);

				this.repaint();
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