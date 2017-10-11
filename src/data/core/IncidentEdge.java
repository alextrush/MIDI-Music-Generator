package data.core;

public class IncidentEdge<V, E> extends SimpleEdge<V,E> implements Edge<V, E> {
	protected Pos<IncidentEdge<V,E>> incStart, incEnd;
	public IncidentEdge(E elem, Vertex<V> start, Vertex<V> end){
		super(elem, start, end);
	}
	public Pos<IncidentEdge<V, E>> incStart() {
		return incStart;
	}
	public void setIncStart(Pos<IncidentEdge<V, E>> incStart) {
		this.incStart = incStart;
	}
	public Pos<IncidentEdge<V, E>> incEnd() {
		return incEnd;
	}
	public void setIncEnd(Pos<IncidentEdge<V, E>> incEnd) {
		this.incEnd = incEnd;
	}
}