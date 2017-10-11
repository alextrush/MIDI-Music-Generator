package data.io;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {
	public static ArrayList<String> filesAtDir(final File folder, String root, boolean recurse){
		ArrayList<String> files = new ArrayList<String>();
		if( folder == null ){
			return files;
		}
		if ( !folder.exists() ){
			return files;
		}
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory() && recurse) {
	        	for( String file : FileUtil.filesAtDir(fileEntry, root + fileEntry.getName() + "\\", recurse) ){
	        		files.add(file);
	        	}
	        } else {
	            files.add( root + fileEntry.getName() );
	        }
	    }
	    return files;
	}
	public static boolean isMIDI( String file ){
		return file.endsWith("mid") || file.endsWith("Mid") || file.endsWith("midi") || file.endsWith("MIDI");
	}
	
	public static void main(String[] args){
		final File groot = new File("/home/alex/documents/csound/Algorithmic Composition Tutorial/01_DiceGame");
		ArrayList<String> files = FileUtil.filesAtDir(groot, "", true);
		for( String file : files ){
			System.out.println( file );
		}
	}
}