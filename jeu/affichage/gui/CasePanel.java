package jeu.affichage.gui;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Dimension;

import java.util.Random;

public class CasePanel extends JPanel{

	private final Image background;

	public static int cote=0;
	private static final int MINIMUM=100; // taille minimale d'une case

	public CasePanel(String nom){
		super();
		setMinimumSize(new Dimension(MINIMUM,MINIMUM));	
		if (nom.startsWith("score(")){
//			int num=Integer.parseInt(nom.subString(6,nom.length()-1));
			Random rand=new Random();
			int n=rand.nextInt(9)+1;

			background=(new ImageIcon("assets/cases/nature"+n+".png")).getImage();


/*			Graphics2D g2d = (Graphics2D)img.getGraphics();
	        g2d.setFont(new Font("Serif",Font.PLAIN,12));
	        g2d.setColor(Color.BLACK);
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        g2d.drawString(String.valueOf(num),45,700);
	        g2d.dispose();
*/		}else if (nom.equals("normale")){
			Random rand=new Random();
			int n=rand.nextInt(9)+1;

			background=(new ImageIcon("assets/cases/nature"+n+".png")).getImage();
		}else{
			background=(new ImageIcon("assets/cases/"+nom+".png")).getImage();
		}

	}
	
}