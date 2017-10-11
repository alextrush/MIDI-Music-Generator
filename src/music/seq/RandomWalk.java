package music.seq;

import java.util.ArrayList;
import java.util.Comparator;

import data.core.DefComp;
import music.core.Pitch;
import music.core.WalkingQueue;

public class RandomWalk implements Sequence{
	protected WalkingQueue<Pitch> gen;
	protected Comparator<Pitch> cmp;
	protected int len, group;
	
	public RandomWalk() {
		this( new ArrayList<Pitch>(), new DefComp<Pitch>() );
	}

	public RandomWalk(Iterable<Pitch> notes) {
		this( notes, new DefComp<Pitch>() );
	}

	public RandomWalk(Comparator<Pitch> cmp) {
		this( new ArrayList<Pitch>(), cmp );
	}

	public RandomWalk(Iterable<Pitch> notes, Comparator<Pitch> cmp) {
		this.cmp = cmp;
		init();
		add( notes );
	}

	protected void init(){
		gen = new WalkingQueue<Pitch>(cmp);
		group = 2;
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
	
	public void clear() {
		gen.clear();
	}

	public Iterable<Pitch> generate() {
		return gen.generate(len, group);
	}

	public int groupSize(){
		return group;
	}
	
	public void replace(Pitch oldP, Pitch newP) {
		// TODO Auto-generated method stub
		
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

	public void setGroupSize(int group) {
		this.group = group;
		System.out.println("Group Size: " + group);
	}
}
