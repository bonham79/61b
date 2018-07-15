package hw3.hash;

import java.util.List;
import java.util.ArrayList;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        ArrayList[] buckets = new ArrayList[M];
        for (int i = 0; i < M; ++i) {buckets[i] = new ArrayList();}
        for (Oomage o : oomages) {
            int hash = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[hash].add(o);
        }
        for (ArrayList bucket : buckets) {
            if (bucket.size() < oomages.size() / 50) {return false;}
            if (bucket.size() > oomages.size() / 2.5) {return false;}
        }
        return true;
    }
}
