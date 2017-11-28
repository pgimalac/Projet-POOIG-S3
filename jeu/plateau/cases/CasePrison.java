package jeu.plateau.cases;

/**
 *	
 */

public class CasePrison extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1341834908599164419L;

	public CasePrison(){super();}

	public void arriveCase(int i){
		if(ilYAQqun()!=-1){
			joueurs[ilYAQqun()]=0;
		}else { joueurs[i]=1;}
	}

	public boolean peutJouer(int i){
		if(super.peutJouer()){return (joueurs[i]==0);}
		return false;
	}

	private int ilYAQqun(){
		for(int i=0; i<joueurs.length; i++){
			if(joueurs[i]!=0) return i;
		}
		return -1;
	}
	


}