package sim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import data.io.FileUtil;
import data.io.MidiUtil;

import music.core.Composer;
import music.core.NoteComparer;
import music.core.Pitch;

import processing.core.PApplet;
import processing.core.PShape;

public class Tester extends PApplet {
	private static LinkedList<String> nameSet;
	private static LinkedList<Integer> testSet;
	private final int numTests = 8;
	private ArrayList<ArrayList<Pitch>> noteQueue;
	private HashMap<String, ArrayList<Pitch>> melList;
	private ArrayList<PShape> phraseLibrary, noteBuffer;
	private ArrayList<String> algoList, songNames;
	private Composer comp;
	private final File root = new File( System.getProperty("user.dir") + "\\src\\midi\\" );
	private float spacing = 2;
	private int selectedBuffer;
	private int currAlgo;
	private final int TRANSPORT_WIDTH = 120;
	private boolean changeHappened;
	public void settings(){
		fullScreen();
		//size(800,600);
	}
	public void setup(){
		//creates the list of melodies the algorithm chooses from
		melList = new HashMap<String,ArrayList<Pitch>>();
		noteQueue = new ArrayList<ArrayList<Pitch>>();
		// create the list of notes to draw on the canvas
		phraseLibrary = new ArrayList<PShape>();
		noteBuffer = new ArrayList<PShape>();
		// create the list to store our options of algorithms to run
		algoList = new ArrayList<String>();
		// create the list to store our options of algorithms to run
		songNames = new ArrayList<String>();
		//set up our mixer to actually output the sound
		comp = new Composer( new NoteComparer() );
		//set up the virtual instrument that will be played on
		addAlgorithm( "In Order", "Order" );
		addAlgorithm( "Random", "Random" );
		addAlgorithm( "Queue", "Queue" );
		comp.addSynth( "1", "Saw" );
		comp.setTempo(90);
		comp.setGenLen(42);
		colorMode(HSB,360,100,100);
		frameRate(12);
		load();
		textFont( createFont("Arial", 24) );
		selectedBuffer = 0;
		changeHappened = true;
	}
	private void addAlgorithm(String key, String type) {
		currAlgo = algoList.size();
		algoList.add( key );
		comp.addSeq( key, type );
	}
	protected void load(File dir){
		//read all the files in the midi directory
		songNames.clear();
		String s;
		ArrayList<String> files = FileUtil.filesAtDir(dir, dir.getAbsolutePath() + "\\", true);
		//add every midi file as a separate melody in the program
		for( String file : files ){
			s = file.substring( dir.getAbsolutePath().length() + 1 );
			if( FileUtil.isMIDI( file ) ){
				songNames.add( s );
				for( ArrayList<Pitch> notes : MidiUtil.midiToParts(file) ){
					melList.put( s, notes );
				}
			} 
		}
		updateMelodyList();
	}
	protected void load(){
		if( !nameSet.isEmpty() ){
			int test = testSet.getFirst();
			if( test == 0 ){
				load( new File( this.root.getAbsolutePath() + File.separator + "tests" + File.separator + nameSet.getFirst() ) );
			} else {
				load( test );
			}
		} else {
			System.out.println( "No More Tests To Run" );
			exit();
		}
	}
	protected void load( int session ){
		load( new File( this.root.getAbsolutePath() + File.separator + "outputs" + File.separator + nameSet.getFirst() + File.separator + "session " + session ) );
	}
	private void save(ArrayList<ArrayList<Pitch>> pitches){
		if( changeHappened ){
			if( pitches.size() > 1 ){
				String root = this.root.getAbsolutePath() + "\\outputs\\" + nameSet.getFirst() + "\\";
				File test = new File( root );
				if( !test.exists() ){
					test.mkdirs();
				}
				File outFile = new File( root + "session count.txt" );
				int num;
				try {
					num = new Scanner( outFile ).nextInt();
				} catch (FileNotFoundException e ) {			
					num = 0;
					try {
						outFile.createNewFile();
					} catch (IOException e1) {}
				}
				File f = new File( root + "session " + ++num );
				System.out.println( "Saved to: " + f.getAbsolutePath() );
				if( f.mkdir() ){
					MidiUtil.writeMidi(f + File.separator + "input.mid", pitches.get(0) );
					//MidiUtil.writeMidi( f + File.separator + "output full.mid", pitches);
					for( int i = 1 ; i < pitches.size() ; i++ ){
						ArrayList<Pitch> p = pitches.get(i);
						MidiUtil.writeMidi(f + File.separator + "output " + i + ".mid", p);
					}
				}
				try {
					PrintWriter out = new PrintWriter( outFile );
					out.println( num );
					out.close();
				} catch (FileNotFoundException e) {
				}
			}
		}
		changeHappened = false;
	}
	private PShape getNoteShape(Pitch p, float x, float y, float w, float h, boolean highlight) {
		int hue, bright;
		hue = (int) map( p.note().getPitch() % 12, 0, 11, 0, 360 );
		bright = (int) map( p.note().getPitch() / 12, 3, 9, 60, 90 );
		if( highlight ){
			bright *= 0.6f;
		}
		PShape sh = createShape( RECT, x, y, w, h );
		int alpha = p.isRest() ? 0 : highlight ? 150 : 255;
		sh.setFill( color( hue, 80, bright, alpha ) );
		sh.setStroke( color( hue, 80, bright*0.6f, alpha ) );
		sh.setStrokeWeight( 1 );
		return sh;
	}
	private void updateList(ArrayList<Pitch> notes, ArrayList<PShape> shapes, float xOffset, float yOffset, float wid, float hi, boolean highlight){
		PShape sh;
		Pitch p;
		float w, x;
		double len = 0;
		for( Pitch t : notes ){
			len += t.note().getDuration();
		}
		x = xOffset;
		int curr = comp.lastNote();
		if( highlight ){
			stroke(255);
			fill(255);
			rect( xOffset, yOffset, wid, hi );
		}
		for( int i = 0 ; i < notes.size() ; i++ ){
			p = notes.get( i );
			w = (float) ((wid-((notes.size()-1)*2*spacing)) * p.note().getDuration() / len );
			sh = getNoteShape( p, x, spacing+yOffset, w, hi-2*spacing, i == curr ? highlight : false );
			sh.setStrokeWeight(1);
			shapes.add( sh );
			x += w + 2*spacing;
		}
	}

	private void updateMelodyList() {
		float h = (melList.isEmpty()) ? 0 : height/2;
		h /= melList.size();
		//float h = 10;
		phraseLibrary.clear();
		for( int i = 0 ; i < melList.size() ; i++ ){
			updateList( melList.get( songNames.get( i ) ), phraseLibrary, 0, i*h, width, h, false );
		}
	}
	private void updateNoteQueue() {
		if( noteQueue.isEmpty() ){
			noteQueue.add( comp.sequence() );
		} else {
			noteQueue.set( 0, comp.sequence() );
		}
		float y = height/2;
		// no division by zero since noteQueue is guaranteed to have a list
		float h = height/2/noteQueue.size();
		for( int i = 0 ; i < noteQueue.size() ; i++ ){
			updateList(noteQueue.get(i), noteBuffer, 0, y, width, h, i == selectedBuffer );
			y += h;
		}
	}
	public void keyPressed(){
		switch( key ){
		case 'w':
			break;
		case 'a':
			prevAlgorithm();
			break;
		case 'd':
			nextAlgorithm();
			break;
		case 's':
			save(noteQueue);
			break;
		case ' ':
			removeTest();
			load();
			break;
		}
		if( keyCode == ENTER ){
			nextGeneration();
		}
	}
	private void prevAlgorithm() {
		setAlgorithm( --currAlgo );
	}
	private void nextAlgorithm() {
		setAlgorithm( ++currAlgo );
	}
	private void setAlgorithm(int i) {
		currAlgo = (i + algoList.size() ) % algoList.size();
		comp.setAlgorithm( algoList.get( currAlgo ) );
	}	
	private void playSelected() {
		comp.clearSynth();
		comp.send( noteQueue.get( selectedBuffer ) );
		comp.pause();
	}
	private void nextGeneration() {
		for( int i = 1 ; i <= numTests ; i++ ){
			comp.setGroupSize( i );
			comp.clearSynth();
			comp.send();
			addBuffer();
			selectedBuffer = 0;
		}
		comp.clearSynth();
		changeHappened = true;
	}
	private void addBuffer() {
		noteBuffer.clear();
		ArrayList<Pitch> buffer = comp.buffer(), t;
		if( selectedBuffer == 0 ){
			noteQueue.add( buffer );
			selectedBuffer = noteQueue.size() - 1;
		} else {
			t = noteQueue.get( selectedBuffer );
			for( Pitch p : buffer ){
				t.add( p );
			}
		}
		changeHappened = true;
	}
	protected void clearQueue() {
		comp.clearSynth();
		comp.clearQueue();
		if( !noteQueue.isEmpty() ){
			noteQueue.get(0).clear();
			if( noteQueue.size() > 1 ){
				selectedBuffer = 1;
			}
		}
	}
	protected void clearCurrent() {
		noteBuffer.clear();
		if( selectedBuffer == 0 ){
			clearQueue();
			for( int i = noteQueue.size()-1 ; i > 0 ; i-- ){
				noteQueue.remove(i);
			}
		} else {
			noteQueue.remove(selectedBuffer);
			if( selectedBuffer >= noteQueue.size() ){
				selectedBuffer = noteQueue.size() - 1;
			}
			comp.clearSynth();			
		}
	}
	protected void addRandom() {
		if( !melList.isEmpty() ){
			Random gen = new Random();
			int num = gen.nextInt( melList.size() );
			comp.add( melList.get( songNames.get(num) ) );
		}
	}
	protected void addNumbered( int i ) {
		noteBuffer.clear();
		if( i >= 0 && i < melList.size() ){
			comp.add( melList.get( songNames.get( i ) ) );
		}
	}

	public void draw(){
		background(0);
		for( PShape s : phraseLibrary ){
			shape( s, 0, 0 );
		}
		fill(0,50,50,200);
		rect(width-TRANSPORT_WIDTH,0,TRANSPORT_WIDTH/2,2*textAscent());
		fill(280,50,50,200);
		rect(width-TRANSPORT_WIDTH/2,0,TRANSPORT_WIDTH/2,2*textAscent());
		fill(0,0,255);
		for( int i = 0 ; i < songNames.size() ; i++ ){
			text(songNames.get(i), 10,i*(height/2.0f)/songNames.size()+textAscent());
		}
		text("Tempo: "+comp.tempo(),width-TRANSPORT_WIDTH,textAscent());
		text("Gen: "+comp.genLen(),width-TRANSPORT_WIDTH,2*textAscent());
		updateNoteQueue();
		for( PShape s : noteBuffer){
			shape( s, 0, 0 );
		}
	}
	
	public void mouseReleased(){
		float h = height/2;
		if( mouseY < h ){
			if (mouseX < width - TRANSPORT_WIDTH ){
				float f = mouseY/h * melList.size();
				addNumbered( (int) f );
				selectedBuffer = 0;
			}
		} else {
			int i = (int)((mouseY-h)/h * noteQueue.size());
			if( i >= 0 && i < noteQueue.size() ) {
				selectedBuffer = i;
			}
			if( mouseButton == LEFT ){
				playSelected();
			} else if ( mouseButton == RIGHT ) {
				clearCurrent();
			}
		}
	}
	
	public void mouseDragged(){
		if( mouseX > width - TRANSPORT_WIDTH ){
			if( mouseY < textAscent() ){
				comp.setTempo( (int)Math.max(0, comp.tempo() + (mouseX-pmouseX)*0.7 ) );
			} else if( mouseY < 2*textAscent() ){
				comp.setGenLen( comp.genLen() + mouseX-pmouseX );
			}
		}
	}
	
	private static void addTest( String name ){
		addTest( name, 0 );
	}
	
	private static void addTest( String name, int test ){
		nameSet.add( name );
		testSet.add( test );
	}
	
	private static void addTest( String name, int[] tests ){
		for( int i = 0 ; i < tests.length ; i++ ){
			addTest( name, tests[i] );
		}
	}
	
	private void removeTest(){
		nameSet.removeFirst();
		testSet.removeFirst();
		clearCurrent();
		phraseLibrary.clear();
		melList.clear();
		selectedBuffer = 0;
	}
	
	public static void main(String[] args) {
		nameSet = new LinkedList<String>();
		testSet = new LinkedList<Integer>();
//		addTest( "summer", new int[]{7} );
		addTest( "ultimate koopa", 1 );
		addTest( "spring" );
		addTest( "fugue d minor", 25 );
		addTest( "winter", new int[]{6,0} );
		addTest( "autumn", new int[]{1,0} );
		addTest( "summer" );
		addTest( "moonlight" );
		PApplet.main( new String[] {"sim.Tester"} );
	}

}
