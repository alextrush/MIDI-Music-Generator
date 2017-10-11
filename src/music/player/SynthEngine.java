package music.player;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import music.core.Pitch;

public class SynthEngine {
	protected ArrayList<Pitch> buffer;
	protected HashMap<String,Player> mixer;
	
	public SynthEngine(){
		buffer = new ArrayList<>();
		mixer = new HashMap<>();
		addRealtime("Saw");
	}
	
	public void addRealtime( String id ){
		Player p = new TriangleSaw();
		mixer.put( id, p );
	}
	
	public void addOSC( String id, InetAddress ip, int port ){
		
	}
	
	public void addOffline( String id ){
		
	}
	
	public void addFile( String id, String filename ){
		
	}
	
	public void changeTempo( int dt ){
		for( Player p : players() ){
			p.changeTempo(dt);
		}
	}
	
	public void clear(){
		for( Player p : players() ){
			p.clear();
		}
		buffer.clear();
	}
	
	public void loop(){
		for( Player p : players() ){
			p.loop();
		}
	}
	
	public void pause( boolean paused ){
		for( Player p : players() ){
			p.pause( paused );
		}
	}
	
	public void play(){
		for( Player p : players() ){
			p.play();
		}		
	}
	
	protected Iterable<Player> players() {
		return mixer.values();
	}

	public void remove( String id ){
		//TODO do
	}
	
	public void send( Pitch p ){
		buffer.add( p );
		for( Player play : players() ){
			play.send( p );
		}
	}
	
	public void send( Iterable<Pitch> p ){
		for( Pitch t : p ){
			buffer.add( t );
		}
		for( Player play : players() ){
			play.send( p );
		}
	}
	
	public void setTempo( int tempo ){
		for( Player p : players() ){
			p.setTempo( tempo );
		}
	}
	
	public void stop(){
		for( Player p : players() ){
			p.stop();
		}		
	}
	public ArrayList<Pitch> buffer(){
		ArrayList<Pitch> a = new ArrayList<Pitch>();
		for( Pitch p : buffer ){
			a.add( p );
		}
		return a;
	}
	public int lastNote(){
		return mixer.get("Saw").curr();
	}
}
