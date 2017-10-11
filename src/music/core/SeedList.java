package music.core;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import data.core.DefComp;

public class SeedList<E extends Comparable<E>> implements Iterable<E>{
	protected LinkedList<LinkedList<E>> seed;
	protected LinkedList<E> group;
	protected Comparator<E> cmp;
	protected int size;
	protected Random gen;
	
	public SeedList(){
		init();
		cmp = new DefComp<E>();
	}
	
	public SeedList( Comparator<E> cmp ){
		init();
		this.cmp = cmp;
	}
	
	public SeedList( Iterable<E> seed ){
		this();
		add( seed );
	}

	public SeedList( Comparator<E> cmp, Iterable<E> seed ){
		this( cmp );
		add( seed );
	}

	protected void init(){
		seed = new LinkedList<LinkedList<E>>();
		group = new LinkedList<E>();
		gen = new Random();
		size = 0;
	}
	
	public boolean isEmpty() {
		return size() <= 0;
	}

	public int size(){
		return size;
	}
	
	public void add( E item ){
		add( item, seed.size() - 1 );
	}
	
	public void add( E item, int list ){
		add( item, list, seed.get(list).size() - 1 );
	}
	
	public void add( E item, int list, int index ){
		seed.get( list ).add( index, item );
	}
	
	public void add( Iterable<E> items ){
		if( items == null ){
			return;
		}
		LinkedList<E> t = new LinkedList<E>();
		this.seed.add( t );
		for( E item : items ){
			t.add( item );
		}
	}
	public Iterator<E> iterator() {
		//return seed.iterator();
		return null;
	}

	/**	
	public E replace( int index, E elem ){
		if( index < 0 || index >= seed.size() ){
			return null;
		}
		E temp = seed.get(index);
		seed.set(index, elem);
		return temp;
	}
	public void replaceAll( E old, E next ){
		for( E note : this ){
			if( note.equals(old) ){
				seed.set( seed.indexOf(old), next );
			}
		}
	}
	
	public int remove(int index) {
    	if(index < size() ){
    		seed.remove(index);
    	}
    	//ensure that the selected group doesn't have a nonexistent element
    	group.clear();
    	size--;
		return seed.size();
	}

	public void clear(){
		group.clear();
		seed.clear();
		size = 0;
	}
	
	public Iterable<E> generate( int length, int groupSize ){
		ArrayList<E> temp = new ArrayList<E>();
		if( groupSize < 1 || length < 1 || seed.isEmpty() ){
			return temp;
		}
		//add a random element from the seed to the group if empty group is empty
		if( group.isEmpty() ){
			group.add( seed.get( gen.nextInt( seed.size() ) ) );
		}
		//shrink the initial group if necessary
		while( group.size() > groupSize ){
			group.remove();
		}
		E next;
		while( temp.size() < length ){
			//choose the next element
			next = next();
			group.add( next );
			if( group.size() > groupSize ){
				group.remove();
			}
			temp.add( next );
		}
		return temp;
	}
	
	protected E next(){
		if( group.isEmpty() || seed.isEmpty() ){
			return null;
		}
		//get random starting point
		int index = gen.nextInt( size() );
		int list, i;
		int start = index, matches = 0;
		//loop through all potential starting notes
		do{
			//loop until all elements match
			while ( matches < group.size() ) {
				//compare each element, break the check if there isn't a match
				if( cmp.compare( seed.get( ( index + matches ) % seed.size() ), group.get( matches ) ) != 0 ){
					matches = 0;
					break;
				} else {
					matches++;
				}
			}
			//add new note
			if( matches >= group.size() ){
				int addIndex = (index + matches) % seed.size();
				E temp = seed.get( addIndex );
				return temp;
			}
			//increment the search
			index = ( index + 1 ) % seed.size();
		} while( index != start );
		return null;
	}
	
	public static void main(String[] args) {
		LinkedList<Integer> test = new LinkedList<Integer>();
		Random gen = new Random();
		int temp;
		for( int i = 0 ; i < 40 ; i++ ){
			temp = gen.nextInt( 12 ) + 1;
			test.add( temp );
			System.out.print( temp + "  " );
		}
		System.out.println();
		//GroupedMelody<Integer> melGen = new GroupedMelody<Integer>();
		SeedList<Integer> melGen2 = new SeedList<Integer>(test);
		for( Integer item : melGen2.generate( 10, 1 ) ){
			System.out.print( item + " " );
		}
		System.out.print(": ");
		for( Integer item : melGen2.generate( 10, 3 ) ){
			System.out.print( item + " " );
		}
		System.out.print(": ");
		for( Integer item : melGen2.generate( 10, 4 ) ){
			System.out.print( item + " " );
		}
		for( int i = 0 ; i < 50 ; i++ ){
			System.out.println();
			for( Integer item : melGen2.generate( 10, 2 ) ){
				System.out.print( item + " " );
			}
			System.out.println();
			System.out.println(melGen2.group);
		}
	}
*/
}