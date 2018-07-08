// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        first = last = this.fillCount = 0;
    }


    ///Adds Iterator Class

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (this.fillCount == this.capacity){throw new RuntimeException("Ring Buffer Overflow");}
        rb[last] = x;
        if (this.fillCount == 0) {
            first = last;
        }
        ++last;
        if (last == this.capacity) {
            last = 0;
        }
        ++this.fillCount;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (this.fillCount == 0){throw new RuntimeException("Ring Buffer Underflow");}
        T item = rb[first];
        ++first;
        if (first == this.capacity) {
            first = 0;
        }
        --this.fillCount;
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    private class RBIterator implements Iterator<T> {
        private int count;
        private int index;
        public RBIterator() {index = first; count = 0;}

        @Override
        public T next(){
            T next = rb[index];
            ++count;
            ++index;
            if (index == capacity){index = 0;}
            return next;
        }

        @Override
        public boolean hasNext(){return count != fillCount;}
    }

    @Override
    public Iterator<T> iterator(){return new RBIterator();}


    public static void main(String[] args) {
        ArrayRingBuffer x = new ArrayRingBuffer(5);
        x.enqueue(5);
        x.enqueue(65);
        x.enqueue(23);
        for (Object element : x) {
            System.out.println(element);
        }
    }
}
