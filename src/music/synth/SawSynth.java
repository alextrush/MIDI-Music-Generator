package music.synth;

import jm.audio.AOException;
import jm.audio.Instrument;
import jm.audio.synth.Envelope;
import jm.audio.synth.Filter;
import jm.audio.synth.Oscillator;
import jm.audio.synth.StereoPan;
import jm.audio.synth.Volume;

/**
 * A monophonic sawtooth waveform instrument implementation
 * which includes a static low pass filter.
 * @author Andrew Brown
 */

public final class SawSynth extends Instrument{
    //----------------------------------------------
    // Instance variables
    //----------------------------------------------
    private int sampleRate;
    private int filterCutoff;
    private int channels;
    private Filter filt;

    //----------------------------------------------
    // Constructor
    //----------------------------------------------
    public SawSynth(){
        this(44100);
    }
    /**
     * Basic default constructor to set an initial 
     * sampling rate and use a default cutoff.
     * @param sampleRate 
     */
    public SawSynth(int sampleRate){
        this(sampleRate, 1000);
    }
        
     /**
    *  Constructor that sets sample rate and the filter cutoff frequency.
     * @param sampleRate The number of samples per second (quality)
     * @param filterCutoff The frequency above which overtones are cut
     */
     public SawSynth(int sampleRate, int filterCutoff){
        this(sampleRate, filterCutoff, 1);
    }
     
    /**
    *  Constructor that sets sample rate, filter cutoff frequency, and channels.
    * @param sampleRate The number of samples per second (quality)
    * @param filterCutoff The frequency above which overtones are cut
    * @param channels 1 = mono, 2 = stereo.
    */
     public SawSynth(int sampleRate, int filterCutoff, int channels){
        this.sampleRate = sampleRate;
        this.filterCutoff = filterCutoff;
        this.channels = channels;
    }

    //----------------------------------------------
    // Methods 
    //----------------------------------------------   
    /**
     * Initialisation method used to build the objects that
     * this instrument will use and specify their interconnections.
     */
    public void createChain() throws AOException{
        Oscillator wt = new Oscillator(this, 
            Oscillator.SAWTOOTH_WAVE, this.sampleRate, this.channels);
        filt = new Filter(wt, this.filterCutoff, Filter.LOW_PASS);
        Envelope env = new Envelope(filt, 
            new double[] {0.0, 0.0, 0.02, 1.0, 0.2, 0.5, 0.8, 0.3, 1.0, 0.0});
        Volume vol = new Volume(env);
        StereoPan pan = new StereoPan(vol);
    }    
    
    /** Changes the specified controller when called */
    public void setController(double[] controlValues){
        filt.setCutOff(controlValues[0]);
    }
}

