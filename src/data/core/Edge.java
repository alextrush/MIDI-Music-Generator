package data.core;

public interface Edge<V, E> extends Pos<E> {
	public Vertex<V> start();
	public Vertex<V> end();
	public Pos<Edge<V,E>> pos();
	public void setStart( Vertex<V> start );
	public void setEnd( Vertex<V> end );
	public void setPos( Pos<Edge<V,E>> pos );
}
