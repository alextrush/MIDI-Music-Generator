package music.seq;

import java.util.Comparator;
import java.util.Random;

import music.core.Pitch;
import jm.music.data.Note;
import jm.music.data.Rest;

public class RandOrder extends Ordered {
	protected static Random gen;
	public RandOrder() {
		super();
	}

	public RandOrder(Iterable<Pitch> notes) {
		super(notes);
	}

	public RandOrder(Comparator<Pitch> cmp) {
		super(cmp);
	}

	public RandOrder(Iterable<Pitch> notes, Comparator<Pitch> cmp) {
		super(notes, cmp);
	}
	
	protected void init(){
		super.init();
		gen = new Random();
	}

	public Note getNextNote() {
		if( notes.isEmpty() ){
			return new Rest();
		}
		int i = gen.nextInt( notes.size() );
		return notes.get(i).note();
	}
}
