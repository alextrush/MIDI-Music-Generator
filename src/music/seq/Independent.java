package music.seq;

import java.util.ArrayList;
import java.util.Comparator;

import data.core.DefComp;
import music.core.Pitch;
import music.core.WalkingQueue;

public class Independent implements Sequence {
	protected WalkingQueue<Pitch> pitches;
	protected WalkingQueue<Double> rhythms;
	protected Comparator<Pitch> cmp;
	protected int group, len;
	
	public Independent() {
		this( new DefComp<Pitch>() );
	}
	
	public Independent(Comparator<Pitch> cmp) {
		this.cmp = cmp;
		init();
	}
	
	protected void init(){
		group = 4;
		len = 30;
		pitches = new WalkingQueue<>(cmp);
		rhythms = new WalkingQueue<>();
	}

	public void add(Pitch note) {
		pitches.add(note);
		rhythms.add(note.note().getRhythmValue());		
	}

	public void add(Iterable<Pitch> notes) {
		for(Pitch note : notes) {
			add( note );
		}
	}

	public void clear() {
		pitches.clear();
		rhythms.clear();
	}

	public int groupSize() {
		return group;
	}

	public void setGroupSize(int group) {
		this.group = group;
	}

	public int genLen() {
		return len;
	}

	public void setGenLen(int len) {
		this.len = len;
	}

	public void replace(Pitch oldP, Pitch newP) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<Pitch> generate() {
		ArrayList<Double> rhythmSet = new ArrayList<>();
		ArrayList<Pitch> notes = new ArrayList<>();
		for( Double r : rhythms.generate(len, group) ){
			rhythmSet.add( r );
		}
		int i = 0;
		for ( Pitch note : pitches.generate(len, group) ){
			notes.add( Pitch.pitchRhythm( note, rhythmSet.get(i) ) );
			i++;
		}
		return notes;
	}
}