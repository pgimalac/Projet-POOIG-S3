package jeu.affichage;

import javax.swing.JTextArea;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.Dimension;
import java.awt.Component;


public class LabelSized extends JTextArea implements ComponentListener{

	private final Component fenetre;
	public LabelSized(String label, Component listened){
		super(label,10,1);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setEditable(false);
		this.setVisible(true);
		this.fenetre=listened;
		this.setMinimumSize(new Dimension(4*fenetre.getWidth()/10,5));
		this.setMaximumSize(new Dimension(4*fenetre.getWidth()/10,fenetre.getHeight()));
		listened.addComponentListener(this);
	}
	public void componentResized(ComponentEvent evt){
		this.setMinimumSize(new Dimension(4*fenetre.getWidth()/10,5));
		this.setMaximumSize(new Dimension(4*fenetre.getWidth()/10,fenetre.getHeight()));
	}
	public void componentHidden(ComponentEvent evt){}
	public void componentShown(ComponentEvent evt){}
	public void componentMoved(ComponentEvent evt){}

}