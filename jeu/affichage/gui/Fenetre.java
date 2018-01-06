package jeu.affichage.gui;

import jeu.affichage.Affichage;
import jeu.affichage.AffichageGUI;

import jeu.events.GameEvent;
import jeu.events.GameOverEvent;
import jeu.events.CannotPlayEvent;
import jeu.events.PlayEvent;

import jeu.options.questions.Question;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Container;
import java.awt.CardLayout;
import java.awt.Container;

public class Fenetre extends JFrame{

	public final CardLayout cardLayout;
	public final Container content;

	public final MenuPanel menu;
	public final JeuPanel jeu;
	public final NouveauPanel nouveau;
	public final JPanelUp parametres;
	public final JPanelUp charger;

	private Container affiche;

	public Container getAffiche(){
		return affiche;
	}

	public void display(String s){
		JOptionPane.showMessageDialog(null,s,"Information",JOptionPane.INFORMATION_MESSAGE);
	}

	public String question(Question q){
		return ask(q.getQuestion());
	}

	public String ask(String q){
		return (String)JOptionPane.showInputDialog(
			null,q,"Question",JOptionPane.QUESTION_MESSAGE,
			null,null,"");		
	}

	private final Affichage affichage;

	public Fenetre(AffichageGUI affichage){
		super();
		this.content=this.getContentPane();
		this.affichage=affichage;
		this.setSize(900,642);
		this.setTitle("Un super jeu de qualité");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		cardLayout = new CardLayout();

		jeu=new JeuPanel(affichage,this);
		menu=new MenuPanel(affichage,this);
		nouveau=new NouveauPanel(affichage,this);
		parametres=new JPanelUp(this){
			public void goTo(){} //TODO
		};
		charger=new JPanelUp(this){
			public void goTo(){} //TODO
		};

		affichage.add(jeu); // on doit refaire le panneau jeu lorsque le jeu est modifié

		content.setLayout(cardLayout);

		content.add(menu,"menu");
		content.add(jeu,"jeu");
		content.add(nouveau,"nouveau");
		content.add(charger,"charger");
		content.add(parametres,"parametres");

		this.setVisible(true);

		fireGoToMenu();
	}

	public void fireGoToJeu(){ affiche=jeu; jeu.goTo(); cardLayout.show(content,"jeu"); }
	public void fireGoToNouveau(){ affiche=nouveau; nouveau.goTo(); cardLayout.show(content,"nouveau"); }
	public void fireGoToParam(){ affiche=parametres; parametres.goTo(); cardLayout.show(content,"parametres"); }
	public void fireGoToCharger(){ affiche=charger; charger.goTo(); cardLayout.show(content,"charger"); }
	public void fireGoToMenu(){ affiche=menu; menu.goTo(); cardLayout.show(content,"menu"); }

	public void gameOver(GameOverEvent e) {
		jeu.gameOver(e);
    }

    public void cannotPlay(CannotPlayEvent e){
		jeu.cannotPlay(e);
    }

    public void play(PlayEvent e){
		jeu.play(e);
    }

    public void changeDesValue(int[] i){
		jeu.changeDesValue(i);
    }	

}