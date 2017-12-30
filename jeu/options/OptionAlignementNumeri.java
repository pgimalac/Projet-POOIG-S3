package jeu.options;

public class OptionAlignementNumeri extends Option{
	public OptionAlignementNumeri(){
		super.option="alignement de 3 pions";
		String[] s=new String[2];
		s[0]="on joue normalement";
		s[1]="lorsque l'on aligne 3 pions on peut rejouer";
		super.valeurs=s;
		super.setValue(0);
	}
}