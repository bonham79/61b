import javax.swing.text.html.HTMLDocument;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */

    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int max = 0;
       for (String ascii : asciis) {
           max = max < ascii.length() ? ascii.length() : max;
       }

       String[] newAsccis = new String[asciis.length];
       for (int i = 0; i < newAsccis.length; ++i) {
           newAsccis[i] = asciis[i];
       }

       for (int i = 0; i < max; ++i) {
           newAsccis = sortHelperLSD(newAsccis, max - i - 1);
           //going in reverse to assit helper function
       }
       return newAsccis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        int[] counts = new int[256];
        int value;

        for (String ascii : asciis) {
            if (index > ascii.length() - 1) {
                value = 0;
            } else {
                value = (int) ascii.charAt(index);
            }
            ++counts[value];
        }

        //assigns start positions
        int next;
        int now = 0;
        for (int i = 0; i < counts.length; ++i) {
            next = now + counts[i];
            counts[i] = now;
            now = next;
        }

        String[] temp = new String[asciis.length];
        for (String ascii : asciis) {
            if (index > ascii.length() - 1) {
                temp[counts[0]] = ascii;
                ++counts[0];
            } else {
                temp[counts[(int) ascii.charAt(index)]] = ascii;
                ++counts[(int) ascii.charAt(index)];
            }
        }
        return temp;
    }

    public static void main (String[] args) {
        String[] stuff = new String[3];
        stuff[0] = "b";
        stuff[1] = "aac";
        stuff[2] = "dc";
        String[] now = sort(stuff);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
