package data.core;
import java.util.ArrayList;
public class DoubleList<L> implements List<L>{
	
	protected int size;
	protected DNode<L> top, bottom;

	public DoubleList (){
		top = new DNode<L>( null, null, null );
		bottom = new DNode<L>( null, top, null );
		top.setNext( bottom );
		size = 0;
	}
	
	protected DNode<L> checkPos( Pos<L> pos ){
		if ( pos == null ){
			System.out.println( "You can't use null positions" );
			return null;
		} else if (pos == top){
			System.out.println( "You can't use the first position of the list" );
			return null;
		} else if (pos == bottom){
			System.out.println( "You can't use the last position of the list" );
			return null;
		} else {
			try {
				DNode<L> newNode = (DNode<L>) pos;
				if ( newNode.prev() == null || newNode.next() == null ){
					System.out.println( "You used the wrong method!" );
					return null;
				}
				return newNode;
			} catch ( ClassCastException e ){
				System.out.println( "This isn't even the right class. Come on." );
				return null;
			}
		}
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size <= 0;
	}

	public Pos<L> first() {
		if ( isEmpty() ){
			return null;
		}
		return top.next();
	}

	public Pos<L> last() {
		if ( isEmpty() ){
			return null;
		}
		return bottom.prev();
	}

	public Pos<L> prev(Pos<L> pos) {
		if ( isEmpty() ){
			return null;
		}
		DNode<L> node = checkPos( pos );
		if ( node == null ){
			return null;
		}
		return node.prev();
	}

	public Pos<L> next(Pos<L> pos) {
		if ( isEmpty() ){
			return null;
		}
		DNode<L> node = checkPos( pos );
		if ( node == null ){
			return null;
		}
		return node.next();
	}

	public Pos<L> addFirst(L elem) {
		DNode<L> newNode = new DNode<L>( elem, top, top.next() );
		top.next().setPrev( newNode );
		top.setNext( newNode );
		size++;
		return newNode;
	}

	public Pos<L> addLast(L elem) {
		DNode<L> newNode = new DNode<L>( elem, bottom.prev(), bottom );
		bottom.prev().setNext( newNode );
		bottom.setPrev( newNode );
		size++;
		return newNode;
	}

	public Pos<L> addBefore(L elem, Pos<L> pos) {
		DNode<L> nextNode = checkPos( pos );
		if ( nextNode == null ){
			return null;
		}
		DNode<L> newNode = new DNode<L>( elem, nextNode.prev(), nextNode );
		nextNode.prev().setNext( newNode );
		nextNode.setPrev( newNode );
		size++;
		return newNode;
	}

	public Pos<L> addAfter(L elem, Pos<L> pos) {
		DNode<L> prevNode = checkPos( pos );
		if ( prevNode == null ){
			return null;
		}
		DNode<L> newNode = new DNode<L>( elem, prevNode, prevNode.next() );
		prevNode.next().setPrev( newNode );
		prevNode.setNext( newNode );
		size++;
		return newNode;
	}

	public L replace( L elem, Pos<L> pos ) {
		if ( isEmpty() ){
			return null;
		}
		DNode<L> node = checkPos( pos );
		if ( node == null ){
			return null;
		}
		L temp = node.elem();
		node.setElem( elem );
		return temp;
	}

	public L remove(Pos<L> pos) {
		if ( isEmpty() ){
			return null;
		}
		DNode<L> oldNode = checkPos( pos );
		if ( oldNode == null ){
			return null;
		}
		L elem = oldNode.elem();
		DNode<L> prevNode = oldNode.prev();
		DNode<L> nextNode = oldNode.next();
		prevNode.setNext( nextNode );
		nextNode.setPrev( prevNode );
		size--;
		return elem;
	}

	public Iterable<Pos<L>> positions() {
		if ( isEmpty() ){
			return new ArrayList<Pos<L>>();
		}
		List<Pos<L>> list = new DoubleList<Pos<L>>();
		Pos<L> temp = first();
		list.addLast( temp );
		while ( temp != last() ){
			temp = next( temp );
			list.addLast( temp );
		}
		return list;
	}

	public java.util.Iterator<L> iterator(){
			return new ElementIterator<L>( this );
	}

	public String toString(){
		String temp = "[ ";
		java.util.Iterator<L> iter = new ElementIterator<L>( this );
		L item;
		while (iter.hasNext()){
			item = iter.next();
			temp += item + " ";
		}
		return temp + "]";
	}
	
	public static void main(String[] args) {
		DoubleList<Integer> list = new DoubleList<Integer>();
		System.out.println( list );
		System.out.println( list.isEmpty() );
		Pos<Integer> one = list.addFirst( 1 );
		System.out.println( one );
		Pos<Integer> two = list.addLast(2);
		list.addLast(3);
		list.addLast(4);
		System.out.println( list.isEmpty() );
		list.addLast(5);
		list.addLast(6);
		System.out.println( list.last() + "\n");
		System.out.println( list );
		list.remove( one );
		System.out.println( list );
		list.replace(4, two);
		one = list.addBefore( 6, two );
		list.addAfter( 5, one );
		System.out.println( list );
		System.out.println( list.size() );
	}
}