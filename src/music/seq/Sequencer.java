package music.seq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import data.core.DefComp;
import music.core.Pitch;

public class Sequencer implements Iterable<Pitch> {
	protected HashMap<String,Sequence> parts;
	protected ArrayList<Pitch> seed;
	protected Comparator<Pitch> cmp;
	protected Sequence active;
	public Sequencer() {
		this( new DefComp<Pitch>() );
	}

	public Sequencer( Comparator<Pitch> cmp ) {
		this.cmp = cmp;
		init();
	}

	protected void init(){
		parts = new HashMap<>();
		seed = new ArrayList<>();
		active = null;
	}
	
	public void add( String key, String id ){
		switch( id ){
		case "Order":
			parts.put( key, new Ordered( cmp ) );
			break;
		case "Random":
			parts.put( key, new RandOrder( cmp ) );
			break;
		case "Queue":
			parts.put( key, new RandomWalk( cmp ) );
			break;
		case "Ind":
			parts.put( key, new Independent( cmp ) );
			break;
		case "Iter":
			parts.put( key, new IteratedRandomWalk( cmp ) );
			break;
		}
		active = parts.get( key );
	}
	
	public void add(Pitch note){
		for( Sequence s : parts.values() ){
			s.add( note );
		}
		seed.add( note );
	}
	
	public void add(Iterable<Pitch> notes){
		for( Pitch p : notes ){
			add( p );
		}
	}
	
	public void changeGenLen( int len ){
		for( Sequence s : parts.values() ){
			s.setGenLen( s.genLen() + len );
		}		
	}
	
	public void clear(){
		for( Sequence s : parts.values() ){
			s.clear();
		}
		seed.clear();
	}
	
	public Iterable<Pitch> generate(){
		return active.generate();
	}
	
	public Iterator<Pitch> iterator(){
		return seed.iterator();
	}
	
	public int remove(String id) {
		return 0;
	}

	public void replace( Pitch oldP, Pitch newP ){
		for( Sequence s : parts.values() ){
			s.replace( oldP, newP );
		}
		Pitch p;
		for( int i = 0 ; i < seed.size() ; i++ ){
			p = seed.get(i);
			if( cmp.compare( p, oldP ) == 0 ){
				seed.set( i, newP );
			}
		}
	}
	
	public int genLen(){
		return (active != null)?active.genLen() : 0;
	}
	
	public int groupSize(){
		return (active != null)?active.groupSize() : 0;
	}
	
	public void setGenLen( int len ){
		for( Sequence s : parts.values() ){
			s.setGenLen( len );
		}		
	}

	public void setGroupSize(int group){
		for( Sequence s : parts.values() ){
			s.setGroupSize( group );
		}
	}
	
	public void setSeq( String key ){
		if( parts.containsKey( key ) ){
			active = parts.get( key );
		}
	}
	
	public ArrayList<Pitch> notes() {
		ArrayList<Pitch> a = new ArrayList<Pitch>();
		for( Pitch p : seed ){
			a.add( p );
		}
		return a;
	}
}