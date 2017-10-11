package data.core;

public class IncidentVertex<V,E> extends SimpleVertex<V> implements Vertex<V>{
	protected DoubleList<IncidentEdge<V,E>> incident;
	public IncidentVertex(V elem){
		super(elem);
		incident = new DoubleList<IncidentEdge<V,E>>();
	}
	
	public DoubleList<IncidentEdge<V,E>> incident(){
		return null;
	}
}
