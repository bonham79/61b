package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {return null;}

        int comp = p.key.compareTo(key); //Just so we don't type the same function 20 times
        if (comp == 0) {return p.value;} //p has our key

        if (comp > 0) {return getHelper(key, p.left);} //left
        return getHelper(key, p.right);//right
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {return getHelper(key, this.root);}

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null){p = new Node(key, value); ++size;}

        int comp = p.key.compareTo(key);
        if (comp == 0) {p.value = value;}
        if (comp > 0) {p.left = putHelper(key, value, p.left);}
        if (comp < 0 ) {p.right = putHelper(key, value, p.right);}
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) { this.root = putHelper(key, value, this.root); }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySetHelper(this.root, new HashSet());
    }

    private Set<K> keySetHelper(Node p, HashSet s) {
        if (p == null) {return null;}
        s.add(p.key);
        if (p.left != null){s.addAll(keySetHelper(p.left, s));}
        if (p.right != null){s.addAll(keySetHelper(p.right, s));}
        return s;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        return removeHelper(key, null, this.root);
    }

    private V removeHelper(K key, Node p, Node c) {//p = parent, c = child
        if (c == null) {return null;}

        int comp = c.key.compareTo(key); //Just so we don't type the same function 20 times

        if (comp == 0) {//found value
            V returnValue = c.value;
            Node leftBranch = c.left;
            Node rightBranch = c.right;
            --size;//takes care of size
            c = null;//deletes value

            graft(leftBranch, p); //Adds the old trees connected to c onto the parent.
            graft(rightBranch, p);

            return returnValue;
        } //p has our key

        if (comp > 0) {return removeHelper(key, c, c.left);} //left
        return removeHelper(key, c, c.right);//right
    }

    private void graft(Node branch, Node root) {//connects root node of branch with the root or one of its subsidiaries
        if (branch == null) {return;}
        if (root == null) {root = branch;}

        int comp = root.key.compareTo(branch.key);
        if (comp > 0) {graft(branch, root.left);}
        if (comp < 0 ) {graft(branch, root.right);}
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V trueValue = this.get(key);
        if(trueValue == value){return remove(key);}
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
