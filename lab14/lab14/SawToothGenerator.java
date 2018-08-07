package lab14;
import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        ++state;
        return normalize(state);
    }

    private double normalize(double value) {
        return  ( value % this.period) * 2 / this.period;
    }

}
