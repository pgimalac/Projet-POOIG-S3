package jeu.options;

/**
 *	Va contenir toutes les options d'un jeu (séparées en sous classes)
 *	
 */
		
import java.util.LinkedList;

public final class ListeDOptions{

	public static final ListeDOptions OIE;
	public static final ListeDOptions NUMERI;
	
	static {
		OIE=new ListeDOptions();

		NUMERI=new ListeDOptions();
	}
	
	private LinkedList<Option> options;

	public ListeDOptions(){ options=new LinkedList<Option>(); } 

	public void add(Option o){
		for (Option e : options){
			if (o.getClass().equals(e.getClass())) {
				options.remove(e);
				options.add(o);
				return;
			}
		}
		options.add(o);
	}

	void remove(Option o){

	}

	Option get(Class<? extends Option> c){
		return null;
	}

	
	
}