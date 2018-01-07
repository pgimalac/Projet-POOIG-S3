package jeu.affichage.gui;

import jeu.Joueur;
import jeu.plateau.cases.Case;

import java.awt.Image;
import java.awt.Graphics;

public class PionG{

	private final Joueur joueur;
	private Case c;
	private final Image image;
	private boolean affNum;
	private int num;

	public PionG(Joueur joueur, Case c, Image image, int num, boolean affNum){
		this.joueur=joueur;
		this.c=c;
		this.image=image;
		this.affNum=affNum;
		this.num=num;		
	}

	public void update(){
		this.c=joueur.getCase(num);
	}

	public Image getImage(){
		return image;
	}

	public Joueur getJoueur(){
		return joueur;
	}

	public void setCase(Case c){
		this.c=c;
	}

	public Case getCase(){
		return c;
	}

	public void paint(Graphics g, int x, int y, int size){
		g.drawImage(image,x,y,size,size,null);
		if (affNum)
			g.drawString(""+num,x+size/2,y+size/2);
	}
}