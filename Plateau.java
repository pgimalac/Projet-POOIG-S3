import cases.*;
import java.util.Iterator;
class Plateau implements Iterable<Case>{
	private LinkedList<Case> cases;
	public Iterator<Case> iterator(){ return cases; }

}