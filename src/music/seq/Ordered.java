package music.seq;

//import game.io.MidiUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

import music.core.Pitch;
import data.io.FileUtil;

public class Ordered implements Sequence {
	protected ArrayList<Pitch> notes;
	protected Comparator<Pitch> cmp;
	protected int index, genLen;
	
	public Ordered() {
		init();
	}

	public Ordered(Iterable<Pitch> notes) {
		init();
		add( notes );
	}
	
	public Ordered(Comparator<Pitch> cmp) {
		init();
	}

	public Ordered(Iterable<Pitch> notes, Comparator<Pitch> cmp) {
		init();
		add( notes );
	}

	protected void init() {
		this.notes = new ArrayList<Pitch>();
		index = 0;
		genLen = 20;
	}

	public void add( Pitch note ){
		if( note != null ){
			if( this.notes == null ){
				this.notes = new ArrayList<Pitch>();
			}
			notes.add(note);
		}
	}
	
	public void add( Iterable<Pitch> notes ){
		if( notes == null ){
			return;
		}
		for( Pitch note : notes ){
			add(note);
		}
	}

	public void clear() {
		notes.clear();
		index = 0;
	}

	public int genLen() {
		return genLen;
	}

	public void setGenLen(int len) {
		this.genLen = len;
	}

	public static void main(String[] args){
		final File root = new File( System.getProperty("user.dir") + "\\src\\midi" );
		ArrayList<String> files = FileUtil.filesAtDir(root, root.getAbsolutePath() + "\\", true);
		
		for( String file : files ){
			if( file.endsWith("mid") || file.endsWith("Mid") || file.endsWith("midi") || file.endsWith("MIDI")){
			}
		}
	}

	public int groupSize() {
		return 0;
	}

	public Pitch note() {
		if( notes == null ){
			return Pitch.rest();
		}
		if( notes.size() == 0 ){
			return Pitch.rest();
		}
		//loop to the beginning of the list
		if( index >= notes.size() ){
			index = 0;
		}
		return notes.get(index++);
	}

	public void replace(Pitch oldP, Pitch newP) {
		// TODO Auto-generated method stub
	}

	public void setGroupSize(int groupSize) {
	}

	@Override
	public Iterable<Pitch> generate() {
		ArrayList<Pitch> t = new ArrayList<>();
		for( int i = 0 ; i < genLen ; i++ ){
			t.add( note() );
		}
		return t;
	}
}
