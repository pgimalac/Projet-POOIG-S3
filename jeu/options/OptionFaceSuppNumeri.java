package jeu.options;

public class OptionFaceSuppNumeri extends Option{
	public OptionFaceSuppNumeri(){
		super.option="possibilite de tirer 0 au de";
		String[] s=new String[2];
		s[0]="on tire un nombre entre 1 et 6";
		s[1]="on tire un nombre entre 0 et 6 et lorsaue l'on fait 0 on peut faire reculer un pion ennemi";
		super.valeurs=s;
		super.setValue(0);
	}
}