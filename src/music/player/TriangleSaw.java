package music.player;

import jm.audio.Instrument;
import music.synth.SawSynth;
import music.synth.TriSynth;

public class TriangleSaw extends RTSynth {
	protected static Instrument[] instr(){
		staticInstr = new Instrument[2];
		staticInstr[0] = new SawSynth();
		staticInstr[1] = new TriSynth();
		return staticInstr;
	}
}
