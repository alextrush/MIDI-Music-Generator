package data.io;

import java.io.File;
import java.util.ArrayList;

import music.core.Pitch;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import jm.util.Write;

public class MidiUtil {
	public static ArrayList<Note> midiToNotes(String midiFile){
		ArrayList<Note> notes = new ArrayList<Note>();
		Score score = new Score();
		Part part; 
		Phrase phr;
		try{
			Read.midi(score, midiFile);
			for(int i = 0 ; i < score.getSize() ; i++){
				part = score.getPart(i);
				for(int j = 0 ; j < part.getSize() ; j++){
					phr = part.getPhrase(j);
					for(int k = 0 ; k < phr.length() ; k++ ){
						if( !phr.getNote(k).isRest() ){
							notes.add( phr.getNote(k) );
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notes;
	}
	
	public static ArrayList<ArrayList<Pitch>> midiToParts(String midiFile){
		ArrayList<ArrayList<Pitch>> parts = new ArrayList<>();
		Score score = new Score();
		Part part; 
		Phrase phr;
		ArrayList<Pitch> notes;
		try{
			Read.midi(score, midiFile);
			for(int i = 0 ; i < score.getSize() ; i++){
				part = score.getPart(i);
				for(int j = 0 ; j < part.getSize() ; j++){
					phr = part.getPhrase(j);
					notes = new ArrayList<>();
					for(int k = 0 ; k < phr.length() ; k++ ){
						if( !phr.getNote(k).isRest() ){
							notes.add( new Pitch( phr.getNote(k) ) );
						}
					}
					System.out.println("part written, size: " + notes.size());
					parts.add(notes);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parts;
	}
	
	public static Phrase notesToPhrase(Iterable<Note> notes){
		Phrase phr = new Phrase();
		for( Note note : notes ){
			phr.add(note);
		}
		return phr;
	}
	
	public static void writeMidi(String file, Iterable<Pitch> notes){
		Phrase phr = new Phrase();
		for( Pitch note : notes ){
			phr.addNote(note.note());
		}
		try{
			Write.midi(phr, file);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		final File root = new File( System.getProperty("user.dir") + "\\src\\midi" );
		ArrayList<String> files = FileUtil.filesAtDir(root, root.getAbsolutePath() + "\\", true);
		Score score = new Score();
		Part part = new Part();
		for( String file : files ){
			if( file.endsWith("mid") || file.endsWith("Mid") || file.endsWith("midi") || file.endsWith("MIDI")){
				part.add( MidiUtil.notesToPhrase( MidiUtil.midiToNotes(file) ) );
				System.out.println(file);
				MidiUtil.midiToParts(file);
			}
		}
		score.add(part);
	}
}
