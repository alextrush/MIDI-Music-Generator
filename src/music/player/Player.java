package music.player;

import music.core.Pitch;

public interface Player {
	public void changeTempo( int dt );
	public void clear();
	public void loop();
	public void pause( boolean paused );
	public void play();
	public void send(Pitch p);
	public void send(Iterable<Pitch> p);
	public void setTempo( double t );
	public void stop();
	public int curr();
	public double tempo();
}
