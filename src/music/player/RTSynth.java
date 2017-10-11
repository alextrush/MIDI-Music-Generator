package music.player;

import java.util.ArrayList;

import music.core.Pitch;
import music.synth.SawSynth;
import jm.audio.Instrument;
import jm.audio.RTMixer;
import jm.music.data.Note;
import jm.music.data.Rest;
import jm.music.rt.RTLine;

public class RTSynth extends RTLine implements Player {
	protected RTMixer mixer;
	protected Instrument[] instr;
	protected static Instrument[] staticInstr;
	protected ArrayList<Pitch> buffer;
	protected int curr;
	protected double tempo;
	protected boolean loop;

	public RTSynth(){
		this( instr() );
	}
	
	public RTSynth(Instrument[] instArr) {
		super(instArr);
		init();
	}

	protected static Instrument[] instr(){
		staticInstr = new Instrument[1];
		staticInstr[0] = new SawSynth();
		return staticInstr;
	}
	
	protected void init(){
		buffer = new ArrayList<>();
		curr = 0;
		this.instr = new Instrument[staticInstr.length];
		for( int i = 0 ; i < staticInstr.length ; i++ ){
			instr[i] = staticInstr[i];
		}
		RTSynth[] arr = new RTSynth[1];
		arr[0] = this;
		mixer = new RTMixer( arr );
		mixer.pause();
		mixer.begin();
	}

	// uses the setTempo and tempo methods from RTLine
	public void changeTempo(int dt) {
		setTempo( tempo() + dt );
	}

	public void clear() {
		buffer.clear();
		curr = 0;
	}

	public void loop() {
		loop = true;
		mixer.unPause();
	}

	public void pause(boolean paused) {
		if( paused ){
			mixer.pause();
		} else {
			mixer.unPause();
		}
	}

	public void play() {
		loop = false;
		mixer.begin();
	}

	public void send(Pitch p) {
		buffer.add(p);
	}

	public void send(Iterable<Pitch> p) {
		for( Pitch t : p ){
			buffer.add(t);
		}
	}
	
	public void setTempo( double tempo ){
		this.tempo = tempo;
		super.setTempo(this.tempo);
	}

	public double tempo(){
		return tempo;
	}

	public Note getNextNote() {
		if( buffer == null ){		return new Rest();	}
		if( buffer.size() == 0 ){	return new Rest();	}
		if( curr >= buffer.size() ){	
			curr = 0;
			if( !loop ){
				mixer.pause();
				return new Rest();
			}
		}
		return buffer.get(curr++).note();
	}

	@Override
	public int curr() {
		return buffer.isEmpty() ? 0 : (curr + buffer.size() - 1) % buffer.size();
	}
}