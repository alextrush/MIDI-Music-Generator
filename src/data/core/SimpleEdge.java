package data.core;

public class SimpleEdge<V, E> implements Edge<V, E> {
	protected Vertex<V> start, end;
	protected E elem;
	protected Pos<Edge<V,E>> pos;
	public SimpleEdge( E elem, Vertex<V> start, Vertex<V> end ){
		this.elem = elem;
		this.start = start;
		this.end = end;
		pos = null;
	}
	
	public E elem() {
		return elem;
	}

	public Vertex<V> start() {
		return start;
	}

	public Vertex<V> end() {
		return end;
	}

	public Pos<Edge<V,E>> pos() {
		return pos;
	}

	public void setElem(E elem) {
		this.elem = elem;
	}

	public void setStart(Vertex<V> start) {
		this.start = start;
	}

	public void setEnd(Vertex<V> end) {
		this.end = end;
	}

	public void setPos(Pos<Edge<V,E>> pos) {
		this.pos = pos;
	}

	public String toString(){
		return elem.toString() + ", [ " + start.toString() + " : " + end.toString()+ " ]";
	}
}