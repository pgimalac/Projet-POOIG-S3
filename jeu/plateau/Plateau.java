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
	public Plateau(Case[] c){ cases=c; }

	public Case getCase(int i){ return cases[i]; }

	public static final Plateau setDefaultOie(Plateau p) {
		p=new Plateau();
		for (int i=0;i<63;i++){
			if (i==0) p.add(new CaseDepart()); 
			else if (i==6) p.add(new CasePont());
			else if (i==62) p.add(new CaseGagnante());
			else if (i==18) p.add(new CaseHotel());
			else if (i==30) p.add(new CasePuit());
			else if (i==41) p.add(new CaseLabyrinthe());
			else if (i==51) p.add(new CasePrison());
			else if (i==57) p.add(new CaseMort());
			else if ((i+1)%9==0) p.add(new CaseOie());
			else p.add(new Case(i+1));
		}
		return p;
	}
	public static final Plateau setDefaultNumeri(Plateau p) {
		p=new Plateau();
		int[] t= {-3,-3,-3,-2,-2,-1,-1,0,0,1,0,0,0,2,0,3,0,4,0,5,6,0,0,7,0,0,8,0,9,10,0,11,12,0,13,15,0,20,25,30};
		for (int i=0;i<t.length;i++) {
			if (t[i]==0) p.add(new Case());
			else p.add(new CaseScore(t[i]));
		}
		return p;
	}
	public final Plateau setDefaultOie() { return setDefaultOie(this); }
	public final Plateau setDefaultNumeri() { return setDefaultNumeri(this); }
	
	private void add(Case c){ int i=0; while(cases[i]!=null) i++; cases[i]=c; }
}