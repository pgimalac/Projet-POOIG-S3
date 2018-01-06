package jeu.affichage.gui;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

import java.util.Random;

public class CasePanel extends JPanel{

	private final Image background;

	public static int cote=0;
	private final boolean affScore;
	private final int numero;
	private final int score;

	private static final int MINIMUM=100; // taille minimale d'une case

	public CasePanel(String nom, int numero){
		super();
		this.numero=numero;
		setMinimumSize(new Dimension(MINIMUM,MINIMUM));
		if (nom.startsWith("score(")){
			score=Integer.parseInt(nom.substring(6,nom.length()-1));
			affScore=true;
			Random rand=new Random();
			int n=rand.nextInt(9)+1;
			background=(new ImageIcon("assets/cases/nature"+n+".png")).getImage();
		}else if (nom.equals("normale")){
			Random rand=new Random();
			int n=rand.nextInt(9)+1;
			affScore=true;
			score=0;
			background=(new ImageIcon("assets/cases/nature"+n+".png")).getImage();
		}else{
			affScore=true;
			score=0;
			background=(new ImageIcon("assets/cases/"+nom+".png")).getImage();
		}

	}

	@Override
	public void paintComponent(Graphics g){
//		g.setFont(new Font("Serif",Font.PLAIN,12));
//		g.setColor(Color.BLACK);
		super.paintComponent(g);
		g.drawImage(background,0,0,getWidth(),getHeight(),null);
		g.drawString(""+numero,getWidth()-10,getHeight()-10); // numéro de la case affiché en bas à gauche
		if (affScore)
			g.drawString(""+score,getWidth()-10,0); // score affiché en haut à droite
	}

	
}