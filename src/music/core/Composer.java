package music.core;

import java.util.ArrayList;
import java.util.Comparator;

import data.core.DefComp;
import music.player.SynthEngine;
import music.seq.Sequencer;

public class Composer {
	protected SynthEngine synth;
	protected Sequencer seq;
	protected double controlRate;
	protected boolean paused;
	protected int tempo;

	public Composer(){
		this(new DefComp<Pitch>());
	}
	public Composer( Comparator<Pitch> cmp ){
		this( 0.01, cmp );
	}
	public Composer( double controlRate, Comparator<Pitch> cmp ) {
		synth = new SynthEngine();
		seq = new Sequencer( cmp );
		this.controlRate = controlRate;
		init();
	}
	protected void init(){
		paused = true;
		tempo = 120;
	}
	public void add( Pitch p ){
		seq.add(p);
	}
	public void add( Iterable<Pitch> p ){
		seq.add(p);		
	}
	public void addSeq( String key, String id ){
		seq.add(key, id);
	}
	public void addSynth( String key, String id ){
		synth.addRealtime(key);		
	}
	public void changeGenLen( int dl ){
		seq.changeGenLen(dl);
	}
	
	public void changeTempo( int tempo ){
		this.tempo += tempo;
		synth.changeTempo(tempo);
	}
	
	public void clearQueue(){
		seq.clear();
	}
	public void clearSynth(){
		synth.clear();
	}
	public void loop(){
		synth.loop();
	}
	public void pause(){
		paused = !paused;
		synth.pause( paused );
	}
	
	public void play(){
		synth.play();
	}
	
	public void playAll(){
		synth.clear();
		synth.send( seq );
		synth.play();
	}
	
	public void setAlgorithm( String key ){
		seq.setSeq( key );
	}
	
	public int removeSeq( String id ){
		return seq.remove( id );
	}
	
	public void setGenLen( int genLen ){
		seq.setGenLen( genLen );
	}
	
	public void setGroupSize( int groupSize ){
		seq.setGroupSize( groupSize );
	}
	
	public void setTempo( int tempo ){
		this.tempo = tempo;
		synth.setTempo(tempo);
	}
	
	public void send(){
		synth.send( seq.generate() );
	}
	
	public void send( Pitch p ){
		synth.send( p );
	}

	public void send( Iterable<Pitch> p ){
		synth.send( p );
	}
	public int groupSize() {
		return seq.groupSize();
	}
	public int genLen() {
		return seq.genLen();
	}
	public int tempo(){
		return tempo;
	}
	public ArrayList<Pitch> sequence(){
		return seq.notes();
	}
	public ArrayList<Pitch> buffer(){
		return synth.buffer();
	}
	public int lastNote(){
		return synth.lastNote();
	}
}
