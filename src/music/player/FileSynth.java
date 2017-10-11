package music.player;

import java.util.ArrayList;
import java.util.HashMap;

import music.core.Pitch;

public class FileSynth implements Player {
	protected String filename;
	protected HashMap<Integer, Double> tempoChanges;
	protected ArrayList<Pitch> buffer;
	protected double currTempo;
	protected int currNote;
	public FileSynth( String filename ){
		filename += ".mid";
		init();
		tempoChanges = new HashMap<>();
		buffer = new ArrayList<>();
		setTempo(currTempo);
	}
	
	protected void init(){
		currNote = 0;
		currTempo = 120;
	}
	
	public void changeTempo(int dt) {
		setTempo( currTempo + dt );
	}

	public void clear() {
		tempoChanges.clear();
		buffer.clear();
		init();
	}

	/**
	 * Looping has no meaning. Needed for Player interface
	 */
	public void loop() {}

	/**
	 * Pausing has no meaning. Needed for Player interface
	 */
	public void pause( boolean paused ) {}

	public void play() {

	}

	public void send(Pitch p) {
		buffer.add( p );
	}

	public void send(Iterable<Pitch> notes) {
		for( Pitch p : notes ) {
			send( p );
		}
	}

	public void setTempo(double t) {
		tempoChanges.put(currNote, t);
	}

	public double tempo() {
		return currTempo;
	}

	/**
	 * Stopping has no meaning. Needed for Player interface
	 */
	public void stop() {}

	@Override
	public int curr() {
		// TODO Auto-generated method stub
		return 0;
	}

}
