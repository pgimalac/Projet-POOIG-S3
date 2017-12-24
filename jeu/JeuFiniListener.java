package jeu;

import jeu.Jeu;

public interface JeuFiniListener extends java.util.EventListener{
	void jeuFini(JeuFiniEvent e);
}

abstract class JeuFiniEvent extends java.util.EventObject{
	private String raison;

	protected void setRaison(String s){
		raison=s;
	}

	public String toString(){
		return raison;
	}
}

class JeuFiniNumeriEvent extends JeuFiniEvent{
	public JeuFiniNumeriEvent(Jeu j){
		super();
		super.setRaison("Les cases finales sont toutes occupées. "+j.getGagnant()+" a gagné !");
	}
}

class JeuFiniOieEvent extends JeuFiniEvent{
	public JeuFiniOieEvent(Jeu j){
		super();
		super.setRaison(j.getRaisonFin());
	}
}