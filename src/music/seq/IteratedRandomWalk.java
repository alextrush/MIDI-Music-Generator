package music.seq;

import java.util.Comparator;

import music.core.Pitch;
import music.core.WalkingQueue;
import jm.music.data.Note;
import jm.music.data.Rest;

public class IteratedRandomWalk extends Ordered {
	private WalkingQueue<Pitch> gen;
	protected int len, group;
	
	public IteratedRandomWalk() {
		super();
	}

	public IteratedRandomWalk(Iterable<Pitch> notes) {
		super(notes);
	}

	public IteratedRandomWalk(Comparator<Pitch> cmp) {
		super(cmp);
	}

	public IteratedRandomWalk(Iterable<Pitch> notes, Comparator<Pitch> cmp) {
		super(notes, cmp);
	}

	protected void init(){
		super.init();
		gen = new WalkingQueue<Pitch>(cmp);
		group = 4;
		len = 10;
	}
	
	public void add( Pitch note ){
		gen.add(note);
	}
	
	public void add( Iterable<Pitch> notes ){
		for( Pitch note : notes ){
			add( note );
		}
	}
	
	protected void newSeed( int length, int groupSize ){
		notes.clear();
		for ( Pitch note : gen.generate(length, groupSize) ){
			notes.add(note);
		}
		index = 0;
	}
	
	public void clear() {
		notes.clear();
		gen.clear();
		index = 0;
	}

	public Note getNextNote() {
		if( notes.isEmpty() ){
			if( !gen.isEmpty() ){
				newSeed(len, group);
			}
			return new Rest();
		}
		//loop to the beginning of the list
		if( index >= notes.size() ){
			index = 0;
			newSeed(len, group);
		}
		return notes.get(index++).note();
	}

	public int group(){
		return group;
	}
	
	public void setGroup(int group) {
		this.group = group;
		System.out.println("Group Size: " + group);
	}

	public int genLen(){
		return len;
	}
	
	public void setGenLen(int len) {
		if( len > 0 ){
			this.len = len;
			System.out.println("Generation Length: " + len);
		}
	}
}
