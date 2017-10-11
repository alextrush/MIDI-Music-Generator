package data.core;

import java.util.Comparator;

public class DefComp<E extends Comparable<E>> implements Comparator<E> {
	public int compare(E elem1, E elem2) {
		return elem1.compareTo( elem2);
	}
}
