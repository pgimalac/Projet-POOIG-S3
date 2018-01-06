package jeu.affichage.gui;

import jeu.options.Option;
import jeu.options.questions.Question;

import javax.swing.JPanel;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.io.File;
import java.io.IOException;

public class JPanelUp extends JPanel implements ComponentListener{

	protected final Fenetre parent;
	private final Image img;
	private int formerWidth;	

	public JPanelUp(Fenetre parent){
		this.parent=parent;
		Image i;
		try{
			i=ImageIO.read(new File("assets/menu.JPG"));
		}catch(IOException e){
			i=null;
		}
		img=i;
		formerWidth=parent.getWidth();
		parent.addComponentListener(this);
	}

	public void goTo(){ //comportement par défaut à l'arrivée sur la page
		parent.setSize(parent.getHeight()*129/92,parent.getHeight());
		parent.setMinimumSize(new Dimension(387,276));
	}

	@Override
	public void paintComponent(Graphics g) { 
		g.drawImage(img,0,0,parent.getWidth(),parent.getHeight(),null);
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