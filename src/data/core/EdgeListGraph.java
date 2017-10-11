package data.core;

import java.util.ArrayList;
import java.util.Iterator;

public class EdgeListGraph<V, E> implements Graph<V, E> {
	DoubleList<Vertex<V>> vertices;
	DoubleList<Edge<V,E>> edges;
	
	public EdgeListGraph(){
		vertices = new DoubleList<Vertex<V>>();
		edges = new DoubleList<Edge<V,E>>();
	}
	
	protected SimpleVertex<V> checkVertex(Vertex<V> v){
		if( v instanceof SimpleVertex ){
			return (SimpleVertex<V>)v;
		} else {
			return null;
		}
	}
	
	protected SimpleEdge<V,E> checkEdge(Edge<V,E> e){
		if( e instanceof SimpleEdge ){
			return (SimpleEdge<V,E>)e;
		} else {
			return null;
		}
	}
	
	public Iterator<V> iterator() {
		ArrayList<V> temp = new ArrayList<V>();
		for( Vertex<V> vert : vertices() ){
			temp.add( vert.elem() );
		}
		return temp.iterator();
	}

	public Iterable<Vertex<V>> vertices() {
		ArrayList<Vertex<V>> temp = new ArrayList<Vertex<V>>();
		for( Vertex<V> vert : vertices ){
			temp.add( vert );
		}
		return temp;
	}

	public Iterable<Edge<V, E>> edges() {
		ArrayList<Edge<V,E>> temp = new ArrayList<Edge<V,E>>();
		for( Edge<V,E> edge : edges ){
			temp.add( edge );
		}
		return temp;
	}

	public Iterable<Edge<V, E>> incidentEdges( Vertex<V> v ) {
		ArrayList<Edge<V,E>> temp = new ArrayList<Edge<V,E>>();
		for( Edge<V,E> edge : edges ){
			if( v == edge.start() || v == edge.end() ){
				temp.add( edge );
			}
		}
		return temp;
	}

	public Vertex<V> opposite(Vertex<V> vertex, Edge<V, E> edge) {
		if( vertex == edge.start() ){
			return edge.end();
		} else {
			return edge.start();
		}
	}

	public Iterable<Vertex<V>> endVertices(Edge<V,E> edge) {
		ArrayList<Vertex<V>> temp = new ArrayList<Vertex<V>>();
		if( edge.start() != null ){
			temp.add(edge.start());
		}
		if( edge.end() != null ){
			temp.add(edge.end());
		}
		return temp;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) {
		for( Edge<V,E> edge : edges ){
			if( (v == edge.start() && w == edge.end()) || (w == edge.start() && v == edge.end())){
				return true;
			}
		}
		return false;
	}

	public V replace(Vertex<V> v, V vert) {
		V old = v.elem();
		SimpleVertex<V> temp = checkVertex( v );
		if( temp != null ){
			temp.setElem(vert);
		}
		return old;
	}

	public E replace(Edge<V, E> e, E edge) {
		E old = e.elem();
		SimpleEdge<V,E> temp = checkEdge( e );
		if( temp != null ){
			temp.setElem(edge);
		}
		return old;
	}

	public Vertex<V> insertVertex(V v) {
		SimpleVertex<V> temp = new SimpleVertex<V>( v );
		Pos<Vertex<V>> pos = vertices.addLast(temp);
		temp.setPos(pos);
		return temp;
	}

	public Edge<V, E> insertEdge(Vertex<V> start, Vertex<V> end, E edge) {
		SimpleEdge<V,E> temp = new SimpleEdge<V,E>(edge, start, end);
		Pos<Edge<V,E>> pos = edges.addLast(temp);
		temp.setPos(pos);
		return temp;
	}

	public V removeVertex(Vertex<V> v) {
		if( v != null ){
			V temp = v.elem();
			if( v.pos() != null ){
				vertices.remove( v.pos() );
				for( Edge<V,E> edge : incidentEdges(v) ){
					if( edge.pos() != null ){
						edges.remove( edge.pos() );
					}
				}
			}
			return temp;
		} else {
			return null;
		}
	}

	public E removeEdge(Edge<V, E> e) {
		if( e != null ){
			E temp = e.elem();
			if( e.pos() != null ){
				edges.remove( e.pos() );
			}
			return temp;
		} else {
			return null;
		}
	}

	public static void main(String[] args){
		EdgeListGraph<Integer,Boolean> graph = new EdgeListGraph<Integer,Boolean>();
		ArrayList<Vertex<Integer>> nums = new ArrayList<Vertex<Integer>>();
		for( int i = 0 ; i <= 20 ; i++ ){
			nums.add( graph.insertVertex(i) );		
		}
		for( Integer i : graph ){
			System.out.println(i);
		}
		graph.insertEdge(nums.get(2), nums.get(4), true);
		graph.insertEdge(nums.get(8), nums.get(1), false);
		graph.insertEdge(nums.get(19), nums.get(10), false);
		graph.insertEdge(nums.get(13), nums.get(6), true);
		graph.insertEdge(nums.get(1), nums.get(3), true);
		graph.insertEdge(nums.get(7), nums.get(7), false);
		graph.insertEdge(nums.get(9), nums.get(10), false);
		graph.insertEdge(nums.get(3), nums.get(6), true);
		graph.insertEdge(nums.get(2), nums.get(14), true);
		graph.insertEdge(nums.get(8), nums.get(1), false);
		graph.insertEdge(nums.get(19), nums.get(10), false);
		graph.insertEdge(nums.get(13), nums.get(6), true);
		
		for( Edge<Integer,Boolean> i : graph.edges() ){
			System.out.println(i);
		}
		for( Vertex<Integer> i : nums ){
			System.out.print( "Vertex " + i.elem() + ":\t" );
			for( Edge<Integer,Boolean> j : graph.incidentEdges(i) ){
				System.out.print(j + "\t");
			}
			System.out.println();
		}
		for( Vertex<Integer> i : nums ){
			System.out.print( "Vertex " + i.elem() + ":\t" );
			for( Edge<Integer,Boolean> j : graph.incidentEdges(i) ){
				System.out.print(j + "\t");
			}
			System.out.println();
		}
	}
}
