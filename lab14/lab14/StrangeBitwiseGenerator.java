package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;
    int weirdState;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        ++state;
        weirdState = state | (state >> 3) | (state >> 8) | ((state >> 11) % period)  % period;
        return normalize(weirdState);
    }

    private double normalize(double value) {
        return  ( value % this.period) * 2 / this.period;
    }
}
