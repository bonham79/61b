package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    public double next() {
        if (state == period) {
            this.period = (int) (this.period * this.factor);
            state = 0;
        }
        ++state;
        return normalize(state);
    }

    private double normalize(double value) {
        return  (value % this.period) * 2 / this.period;
    }
}
