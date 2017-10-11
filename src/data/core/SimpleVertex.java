package data.core;

public class SimpleVertex<V> implements Vertex<V> {
	protected V elem;
	protected Pos<Vertex<V>> pos;
	public SimpleVertex(V elem){
		this.elem = elem;
	}
	
	public V elem() {
		return elem;
	}

	public Pos<Vertex<V>> pos() {
		return pos;
	}

	public void setPos(Pos<Vertex<V>> pos) {
		this.pos = pos;
	}

	public void setElem(V elem) {
		this.elem = elem;
	}
	
	public String toString(){
		return elem.toString();
	}
}