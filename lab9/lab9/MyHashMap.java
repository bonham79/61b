package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hash = hash(key);
        if (hash < this.buckets.length) {
            return buckets[hash].get(key);
        } else {return null;}
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int hash = hash(key);
        if (hash < this.buckets.length) {
            if (!this.buckets[hash].containsKey(key)) {
                ++this.size;
            }
            buckets[hash].put(key, value);
        }

        //Checks Load Factor
        if (loadFactor() > MAX_LF) {
            //Create New Buckets
            ArrayMap<K, V>[] newBuckets = new ArrayMap[this.buckets.length * 2];
            ArrayMap<K, V>[] oldBuckets = this.buckets;
            this.buckets = newBuckets; //for hashing purposes.
            clear();

            //Take old buckets, give new hash, then throw into new.
            for (int i = 0; i < oldBuckets.length; i += 1) {
                for (K item : oldBuckets[i]) {
                    this.put(item, oldBuckets[i].get(item)); //Adds key, value pair into new hash position }
                }
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet keys = new HashSet();
        for (ArrayMap bucket : buckets) {
            if (bucket.size() > 0) {keys.addAll(bucket.keySet());}
        }
        return keys;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int hashedKey = hash(key);
        V value = get(key);

        if (value != null){
            buckets[hashedKey].remove(key);
            return value;
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int hashedKey = hash(key);
        V returnValue = get(key);
        if (returnValue == value) {
            buckets[hashedKey].remove(key, value);
            return returnValue;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
