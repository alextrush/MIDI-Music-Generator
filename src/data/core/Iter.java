package data.core;

public class Iter<E> implements java.util.Iterator<E>{
	protected Iterator<E> iter;
	public boolean hasNext() {
		return !iter.isDone();
	}

	@Override
	public E next() {
		return iter.next().elem();
	}

	public void remove() {
	}
	
}
