package music.core;

import jm.music.data.Note;
import jm.music.data.Rest;

public class Pitch implements Comparable<Pitch> {
	protected Note note;
	public Pitch( Note note ) { 
		this.note = note;
	}
	public Pitch( Pitch note ) {
		this.note = new Note( note.note().toString() );
	}
	public Note note() {
		return note;
	}
	public static Pitch rest(){
		return new Pitch( new Rest() );
	}
	public int compareTo(Pitch p) {
		return (this.note.equals(p.note)) ? 0 : 1;
	}
	public String toString() {
		return note.toString();
	}
	public static Pitch pitchRhythm(Pitch note, Double dur) {
		Pitch p = new Pitch( note );
		p.note().setDuration( dur );
		return p;
	}
	public boolean isRest() {
		return note instanceof Rest;
	}
}
