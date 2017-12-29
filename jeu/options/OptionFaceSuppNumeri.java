package jeu.options;

public class OptionFaceSuppNumeri extends Option{
	public OptionFaceSuppNumeri(){
		super.option="arrivée à la fin du plateau";
		String[] s=new String[2];
		s[0]="on fait demi tour sur le nombre de case qu'il reste à parcourir";
		s[1]="on s'arrête sur la dernière case";
		super.valeurs=s;
		super.setValue(0);
	}
}