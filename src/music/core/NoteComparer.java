package music.core;

import java.util.Comparator;

public class NoteComparer implements Comparator<Pitch> {
	protected boolean pitchClass, rhythm;
	protected double precision;
	// options
	// two notes are the same if they have same pitch class (pitchClass=true)
	// two notes are the same if they are the same note		(pitchClass=false)
	// two notes need to be the same rhythmic value			(rhythm=true)
	// note needs to be exactly the same					(pitchClass=false,rhythm=true)
	public NoteComparer(){
		this( true, false );
	}
	public NoteComparer(boolean pitch, boolean rhythm){
		this( pitch, rhythm, 0.125 );
	}
	public NoteComparer(boolean pitch, boolean rhythm, double precision){
		this.pitchClass = pitch;
		this.rhythm = rhythm;
		this.precision = precision;
	}
	
	public int compare(Pitch n1, Pitch n2) {
		if( pitchClass ){ 
			// if the notes are not from the same pitch class, they are not equal
			if( !n1.note().getNote().equals( n2.note().getNote() ) ){
				return 1;
			}
			// notes need to have same rhythm and pitch class
		} else {
			// only considers if notes are the exact same pitch
			if ( n1.note().getPitch() != n2.note().getPitch() ){
				return 1;
			}
		}
		if( rhythm ){
			double r1 = n1.note().getRhythmValue();
			double r2 = n2.note().getRhythmValue();
			// rhythms are not the same
			if( r1 != r2 ){
				return 1;
			}
		}
		return 0;
	}
}
