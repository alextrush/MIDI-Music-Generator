package data.core;
import java.util.Iterator;

public class ElementIterator<T> implements Iterator<T> {

	protected Pos<T> current;
	protected List<T> list;
	
	public ElementIterator( List<T> list ){
		this.list = list;
		current = list.first();
	}
	
	public boolean hasNext(){
		return current != null;
	}
	
	public T next(){
		Pos<T> temp = current;
		if ( current == list.last() ){
			current = null;
		} else {
			current = list.next( current );
		}
		return temp.elem();
	}
	
	public void remove(){}
}
