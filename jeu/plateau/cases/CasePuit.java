/**
*/

package jeu.plateau.cases;

public class CasePuit extends Case{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4826365605668224665L;


	public CasePuit(){super();}


	public void arriveCase(int i){
		if(i>=0 && i<joueurs.length){
			if(ilYAQqun()!=-1){
				joueurs[ilYAQqun()]=0;
				joueurs[i]=1;
			}else{
				joueurs[i]=1;
			}
		}
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