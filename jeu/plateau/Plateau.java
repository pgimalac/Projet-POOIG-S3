package jeu.plateau;

/**
 *
 *
 */

import jeu.plateau.cases.*;
import java.util.ArrayList;
import java.io.Serializable;

public class Plateau implements Serializable{

	private static final long serialVersionUID = 2520277206423394352L;

	private ArrayList<Case> cases;

	public final static Plateau OIE;
	public final static Plateau NUMERI;

	static{
		OIE=new Plateau();
		for (int i=0;i<63;i++){
			if (i==0) OIE.add(new CaseDepart(i+1)); 
			else if (i==62) OIE.add(new CaseGagnante(i+1));
			else if (i==18) OIE.add(new CaseHotel(i+1));
			else if (i==30) OIE.add(new CasePuit(i+1));
			else if (i==41) OIE.add(new CaseLabyrinthe(i+1,30));
			else if (i==51) OIE.add(new CasePrison(i+1));
			else if (i==57) OIE.add(new CaseMort(i+1));
			else if ((i+1)%9==0) OIE.add(new CaseOie(i+1));
			else OIE.add(new Case(i+1));
		}

		NUMERI=new Plateau();
		int[] t= {-3,-3,-3,-2,-2,-1,-1,0,0,1,0,0,0,2,0,3,0,4,0,5,6,0,0,7,0,0,8,0,9,10,0,11,12,0,13,15,0,20,25,30};
		for (int i=0;i<t.length;i++) {
			if (t[i]==0) NUMERI.add(new Case(i+1));
			else NUMERI.add(new CaseScore(i+1,t[i]));
		}
	}

	public Plateau(){ cases=new ArrayList<Case>(); }
	public Plateau(ArrayList<Case> c){ cases=c; }

	public void remove(Case c){}
	public void remove(int i){}
	public void add(Case c){}
	public void inserer(int i, Case c){

	}


}