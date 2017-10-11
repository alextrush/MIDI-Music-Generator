package data.core;
public interface Entry<K, V> {
	//return the key stored in the entry
	public K key();
	//return the element stored in the entry
	public V value();
}