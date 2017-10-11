package data.core;

public interface Iterator<E> {
	public Pos<E> first();
	public Pos<E> next();
	public Pos<E> curr();
	public boolean isDone();
}