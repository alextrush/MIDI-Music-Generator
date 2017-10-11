package music.seq;

import music.core.Pitch;

public interface Sequence {
	public void add(Pitch p);
	public void add(Iterable<Pitch> p);
	public void clear();
	public int genLen();
	public int groupSize();
	public void replace( Pitch oldP, Pitch newP );
	public void setGenLen( int genLen );
	public void setGroupSize( int groupSize );
	public Iterable<Pitch> generate();
}
