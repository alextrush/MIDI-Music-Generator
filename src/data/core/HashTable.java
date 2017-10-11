package data.core;
import java.util.ArrayList;
import java.util.Iterator;
public class HashTable<K, V> implements Map<K, V> {
	protected ArrayList<Entry<K,V>>[] elements;
	protected int capacity, size, scale, shift;
	
	public HashTable(){
		this( 203 );
	}
	
	@SuppressWarnings("unchecked")
	public HashTable( int capacity ){
		this.capacity = capacity;
		size = 0;
		elements = (ArrayList<Entry<K,V>>[]) new ArrayList[capacity];	
		scale = 13;
		shift = 35;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public V get(K key) {
		if( key == null ){
			return null;
		}
		Entry<K,V> test = findEntry( key );
		if( test == null ){
			return null;
		}
		return test.value();
	}

	public V put(K key, V value) {
		if( key == null ){
			return null;
		}
		Entry<K,V> test = findEntry( key );
		if( test == null ){
			insert( key, value );
			return null;
		} else {
			try{
				if( test instanceof HashEntry<?,?> ){
					HashEntry<K,V> hashEntry = (HashEntry<K,V>) test;
					return hashEntry.setValue( value );
				}
				System.err.println( "Whatever ended up here is not a HashEntry" );
				return null;
			} catch ( ClassCastException e ) {
				System.err.println( "Whatever ended up here is not a HashEntry" );
				return null;
			}
		}
	}

	public V remove(K key) {
		if( key == null || elements == null ){
			return null;
		}
		int index = hashValue( key );
		if( index >= elements.length ){
			System.err.println( "Array Index Out of Bounds" );
			return null;
		}
		if( elements[index] == null ){
			System.out.println( "No element with key '" + key + "' was found." );
			return null;			
		}
		for( Entry<K,V> entry : elements[index] ){
			if( entry.key() == key ){
				V temp = entry.value();
				elements[index].remove( entry );
				if( elements[index].size() <= 0 ){
					elements[index] = null;
				}
				return temp;
			}
		}
		System.out.println( "No element with key '" + key + "' was found." );
		return null;
	}

	public Iterable<K> keys() {
		ArrayList<K> keys = new ArrayList<K>();
		for( Entry<K,V> entry : entrySet() ){
			keys.add( entry.key() );
		}
		return keys;
	}

	public Iterable<V> values() {
		ArrayList<V> values = new ArrayList<V>();
		for( Entry<K,V> entry : entrySet() ){
			values.add( entry.value() );
		}
		return values;
	}

	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> temp = new ArrayList<Entry<K,V>>();
		for( int i = 0 ; i < capacity ; i++ ){
			if( elements[i] != null ){
				for( Entry<K,V> entry : elements[i] ){
					temp.add( entry );
				}
			}
		}
		return temp;
	}

	public Iterator<V> iterator() {
		return values().iterator();
	}

	protected Entry<K,V> findEntry( K key ){
		if( elements == null ){
			return null;
		}
		int index = hashValue( key );
		if( index >= elements.length ){
			System.err.println( "Array Index Out of Bounds" );
			return null;
		}
		for( Entry<K,V> entry : elements[index] ){
			if( entry.key() == key ){
				return entry;
			}
		}
		return null;
	}
	
	protected V insert( K key, V value ){
		int index = hashValue( key );
		Entry<K,V> entry = new HashEntry<K,V>( key, value );
		//create the array list since it doesn't exist yet
		if( elements[index] == null ){
			elements[index] = new ArrayList<Entry<K,V>>();
			elements[index].add( entry );
			return null;
		} else {
			V oldValue = null;
			for( Entry<K,V> hashEntry : elements[index] ){
				if( hashEntry.key() == key ){
					oldValue = hashEntry.value();
					try{
						((HashEntry<K,V>) hashEntry).setValue( value );
						return oldValue;
					} catch ( ClassCastException e ) {
						System.err.println( "Whatever ended up here is not a HashEntry" );
						return null;
					}
				}
			}
			elements[index].add( new HashEntry<K,V>( key, value ) );
			return null;
		}
	}
	
	protected int hashValue( K key ){
		return ( key.hashCode() * shift + scale ) % capacity;
	}
	
	public static void main(String[] args) {
		HashTable<Integer, Character> test = new HashTable<Integer,Character>();
		for( int i = 65 ; i < 91 ; i++){
			test.insert(i, (char)i);
		}
		for( Character ch : test ){
			System.out.println( ch );
		}
		for( Entry<Integer,Character> entry : test.entrySet() ){
			System.out.println( entry );
		}
		System.out.println( test.put( 65, 'a' ) );
		System.out.println( test.remove( 66 ) );		
		System.out.println( test.remove( 6 ) );		
	}
}