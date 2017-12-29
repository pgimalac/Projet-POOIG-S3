package jeu.plateau;

import jeu.plateau.cases.*;
import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 *
 */

public class Plateau implements Serializable{

	private static final long serialVersionUID = 2520277206423394352L;

	private final Case[] cases;

	private Plateau(int i){ cases=new Case[i]; }
	private Plateau(Case[] c){ cases=c; }

	public Case getCase(int i){ return cases[i]; }

	public int getCase(Case c){
		int i=1;
		for (Case cc : cases){
			if (cc==c)
				break;
			i++;
		}
		return i;
	}

	public int size(){ return cases.length; }

	public static final Plateau getDefaultOie() {
		Case[] c=new Case[63];
		for (int i=0;i<63;i++){
			if (i==0) c[i]=new CaseDepart();
			else if (i==6) c[i]=null;
			else if (i==62) c[i]=new CaseGagnante();
			else if (i==18) c[i]=new CaseHotel();
			else if (i==30) c[i]=new CasePuit();
			else if (i==41) c[i]=new CaseLabyrinthe(c[30]);
			else if (i==51) c[i]=new CasePrison();
			else if (i==57) c[i]=new CaseMort();
			else if ((i+1)%9==0) c[i]=new CaseOie();
			else c[i]=new Case();
		}
		c[6]=new CasePont(c[12]);
		return new Plateau(c);
	}
	public static final Plateau getDefaultNumeri() {
		int[] t={-3,-3,-3,-2,-2,-1,-1,0,0,1,0,0,0,2,0,3,0,4,0,5,6,0,0,7,0,0,8,0,9,10,0,11,12,0,13,15,0,20,25,30};
		Case[] c=new Case[t.length];
		for (int i=0;i<t.length;i++){
			if (t[i]==0) c[i]=new Case();
			else c[i]=new CaseScore(t[i]);
		}
		return new Plateau(c);
	}

	public Case getLast(){
		return this.cases[cases.length-1];
	}

	public void recommencer(){
		for (Case c : cases)
			c.recommencer();
	}

}