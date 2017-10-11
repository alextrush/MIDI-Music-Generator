package music.core;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import data.core.DefComp;

public class WalkingQueue<E extends Comparable<E>> implements Iterable<E>{
	protected LinkedList<E> seed;
	protected LinkedList<E> group;
	protected Comparator<E> cmp;
	protected Random gen;
	
	public WalkingQueue(){
		init();
		cmp = new DefComp<E>();
	}
	
	public WalkingQueue( Comparator<E> cmp ){
		init();
		this.cmp = cmp;
	}
	
	public WalkingQueue( Iterable<E> seed ){
		this();
		add( seed );
	}

	public WalkingQueue( Comparator<E> cmp, Iterable<E> seed ){
		this( cmp );
		add( seed );
	}

	protected void init(){
		seed = new LinkedList<E>();
		group = new LinkedList<E>();
		gen = new Random();
	}
	
	public boolean isEmpty() {
		return size() <= 0;
	}

	public int size(){
		return seed.size();
	}
	
	public void add( E item ){
		this.seed.add( item );
	}
	
	public void add( E item, int index ){
		this.seed.add( index, item );
	}
	
	public void add( Iterable<E> items ){
		if( items == null ){
			return;
		}
		for( E item : items ){
			this.seed.add( item );
		}
	}
	
	public E replace( int index, E elem ){
		if( index < 0 || index >= seed.size() ){
			return null;
		}
		E temp = seed.get(index);
		seed.set(index, elem);
		return temp;
	}
	
	public void replaceAll( E old, E next ){
		for( E note : seed ){
			if( note.equals(old) ){
				seed.set( seed.indexOf(old), next );
			}
		}
	}
	
	public int remove(int index) {
    	if(index < seed.size()){
    		seed.remove(index);
    	}
    	//ensure that the selected group doesn't have a nonexistent element
    	group.clear();
		return seed.size();
	}

	public void clear(){
		group.clear();
		seed.clear();
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
		int index = gen.nextInt( seed.size() );
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
	
	public Iterator<E> iterator() {
		return seed.iterator();
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
		WalkingQueue<Integer> melGen2 = new WalkingQueue<Integer>(test);
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
}