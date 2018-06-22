import synthesizer.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static HashMap<Character, GuitarString> NOTES = new HashMap<>(keyboard.length());

    private static void initializeKeyboard() {
        for (int i = 0; i < keyboard.length(); ++i) {
            GuitarString note = new GuitarString(440 * Math.pow(2, (i - 24) / 12.0));
            NOTES.put(keyboard.charAt(i), note);
        }
    }

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        initializeKeyboard();
        while (true) {

            /* check if the user has typed a key; if so, process it */
            try {
                if (StdDraw.hasNextKeyTyped()) {
                    char key = StdDraw.nextKeyTyped();
                    NOTES.get(key).pluck();
                }

                /* compute the superposition of samples */
                double sample = 0.0;

                for (HashMap.Entry<Character, GuitarString> sounds : NOTES.entrySet()) {
                    sample += sounds.getValue().sample();
                }

                /* play the sample on standard audio */
                StdAudio.play(sample);

                /* advance the simulation of each guitar string by one step */
                for (HashMap.Entry<Character, GuitarString> sounds : NOTES.entrySet()) {
                    sounds.getValue().tic();
                }
            } catch (NullPointerException noNote) {
                continue;
            }
        }
    }
}


