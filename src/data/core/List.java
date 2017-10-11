package data.core;
public interface List<E> extends Iterable<E> {
	public int size();
	public boolean isEmpty();
	public Pos<E> first();
	public Pos<E> last();
	public Pos<E> prev( Pos<E> next );
	public Pos<E> next( Pos<E> prev );
	public Pos<E> addFirst( E elem );
	public Pos<E> addLast( E elem );
	public Pos<E> addBefore( E elem, Pos<E> next );
	public Pos<E> addAfter( E elem, Pos<E> prev );
	public E replace( E elem, Pos<E> pos );
	public E remove( Pos<E> pos );
	public Iterable<Pos<E>> positions();
}