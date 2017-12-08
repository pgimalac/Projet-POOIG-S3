public interface JeuFiniListener extends java.util.EventListener{
	void jeuFini(JeuFiniEvent e);
}

protected abstract class JeuFiniEvent extends java.util.EventObject{
	private String raison;
	protected setRaison(String s){ raison=s; }
	public String toString(){ return raison; }
}

protected class JeuFiniNumeriEvent extends JeuFiniEvent{
	public JeuFiniNumeriEvent(Jeu j){ super(j); super.setRaison("Les cases finales sont toutes occupées. "+j.getGagnant()+" a gagné !"); }
}

protected class JeuFiniOieEvent extends JeuFiniEvent{
	public JeuFiniOieEvent(Jeu j){ super(j); super.setRaison(j.getRaisonFin()); }
}