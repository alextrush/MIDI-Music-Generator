package data.core;

public class DNode<E> implements Pos<E> {
	protected E elem;
	protected DNode<E> next, prev;
	public DNode(E elem, DNode<E> prev, DNode<E> next){
		this.elem = elem;
		this.prev = prev;
		this.next = next;
	}
	public E elem() {
		return elem;
	}
	public void setElem(E elem) {
		this.elem = elem;
	}
	public DNode<E> next() {
		return next;
	}
	public void setNext(DNode<E> next) {
		this.next = next;
	}
	public DNode<E> prev() {
		return prev;
	}
	public void setPrev(DNode<E> prev) {
		this.prev = prev;
	}
	public String toString(){
		return elem.toString();
	}
}
