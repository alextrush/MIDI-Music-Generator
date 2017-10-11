package data.core;
public class HashEntry<K,V> implements Entry<K,V>{
	protected K key;
	protected V value;
	public HashEntry( K key, V value ){
		this.key = key;
		this.value = value;
	}
	public K key(){
		return key;
	}
	public V value(){
		return value;
	}
	public V setValue( V value ){
		V temp = null;
		if( this.value != null ){
			temp = this.value;
		}
		this.value = value;
		return temp;
	}
	
	public String toString(){
		return "[ " + key.toString() + " : " + value.toString() + " ]";
	}
}