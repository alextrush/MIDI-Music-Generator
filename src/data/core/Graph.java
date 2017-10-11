package data.core;
public interface Graph<V, E> extends Iterable<V>{
	public Iterable<Vertex<V>> vertices();
	public Iterable<Edge<V,E>> edges();
	public Iterable<Edge<V,E>> incidentEdges(Vertex<V> v);
	public Vertex<V> opposite( Vertex<V> vertex, Edge<V,E> edge );
	public Iterable<Vertex<V>> endVertices( Edge<V,E> edge );
	public boolean areAdjacent( Vertex<V> v, Vertex<V> w );
	public V replace( Vertex<V> v, V vert );
	public E replace( Edge<V,E> e, E edge );
	public Vertex<V> insertVertex( V v );
	public Edge<V,E> insertEdge( Vertex<V> start, Vertex<V> end, E edge );
	public V removeVertex( Vertex<V> v );
	public E removeEdge( Edge<V,E> e );
}
