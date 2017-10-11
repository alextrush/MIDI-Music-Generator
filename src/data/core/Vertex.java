package data.core;

public interface Vertex<V> extends Pos<V> {
	public Pos<Vertex<V>> pos();
	public void setPos(Pos<Vertex<V>> pos);
}
