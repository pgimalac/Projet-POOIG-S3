package jeu.options;

/**
 *	Va contenir toutes les options d'un jeu (séparées en sous classes)
 *	
 */
		

class ListeDOptions{

	private LinkedList<Option> options;

	ListeDOptions(){ options=new LinkedList<Option>(); } 

	void add(Option o){
		for (Option e : options){

		}
	}

	void remove(Option o){

	}

	Option get(Class<? extends Option> c){
		return null;
	}

}