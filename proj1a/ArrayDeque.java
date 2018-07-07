import java.util.Objects;

public class ArrayDeque<Type> {

    private int size;
    private Type[] items;

    //Variables to signal position of next insert at beginning or end of deque.
    private int firstNext;
    private int lastNext;

    //Variables to track beginning and end of Deque
    private int first;
    private int last;

    public ArrayDeque() {
        //Initializes empty Deque within array of size 8
        items = (Type[]) new Object[8];
        size = 0;
        firstNext = items.length / 2;
        lastNext = firstNext;
    }

    private void resizeChecker() {
        //Checks if Containing array is too large or too small for Deque.

        if (size == items.length) {
            //Determines if size of deque has met limits of array size
            //Calls for a resize of array
            //Default factor of 3
            resizeIncr(10);
        } else {
            //Determines if deque is too small for array and resizes to conserve memory (only tracked for length>16).
            //Default factor of 2.
            float rFactor = (float) size / items.length;
            if ((rFactor < .25) && (items.length >= 16)) {
                resizeDecr(2);
            }
        }
    }


    private void resizeIncr(int factor) {
        /*Resize array by scale of factor
            Then takes values from beginning of deque to end of array and places at middle of new array.
            Then takes values from beginning of array to end of deque and places at middle of new array.
            Ex:
                OldArray - [000L|F000]
                New Array - [------|------]
                -->  [----F0000|000000L----]

                ----  F = Beginning of Deque
                ----- L = End of Deque
                ----- 000000 = Deque items
                ----- ---- = Null values
                ----- | = Middle of array
                ----- [] Array.
         */
        Type [] newArray = (Type[]) new Object[size * factor];
        //Creates new Array

        int midpoint = newArray.length / 2;
        int firstToEndLength = items.length - first;


        System.arraycopy(items, first, newArray, midpoint - firstToEndLength,
                firstToEndLength);
        //Copies elements from Deque.first to end of original array.  Then places in new array
        //  such that Deque items end before middle of the new Array.

        System.arraycopy(items, 0, newArray, midpoint, first);
        //Copies remaining deque items from beginning of array to last item in array
        //  then positions starting at middle of new array.  Length would be position of Last + 1,
        // which is equivalent to first;

        first = midpoint - firstToEndLength;
        last = first + size - 1; //1 + relative final index = size.  relative final index = true last - true first
                                 // 1 + true last - true first = size --> true last = size + true first - 1;

        firstNext = first - 1;  //Adjustments not necessary as in addLast addFirst
        lastNext = last + 1;    //  as size of newArray will always be larger than case
                                //  of a full former deque in one direction.  That is
                                //  1/2 Array size of Deque*3 (Length of deque extending from midpoint in one direction
                                //  Will be length 3/2 Deque which is >> Deque size.
        items = newArray;
    }

    private void resizeDecr(int factor) {
        /*Resize array by scale of factor or, if factor is negative, by 1 / factor
        *   then migrates Deque data into new array.
        *
        * As a small array will need a selective use of array, two base cases are considered.
        *
        * 1.
        *   Deque - [---F00|00L---]
        *   Array - [---|--]
        *       --->
        *           [---F00|00L---}
        *
        *           Case requires simple copy from first to last into array from
        *           array.midpoint - length from deque midpoint to first (size / 2)
        *                           to
        *           array.midpoint + length from deque midpoint to last. (size / 2)
        *
        * 2. Deque - [000L--|--F000]
        *    Array - [------|------]
        *       --->
        *           [---F000|000L--]
        *
        *           Case Requires two copies:
        *               one from first to end of deque array to array midpoint - length of copy
        *               second from beginning to last of deque array to array midpoint + length of copy.
        *
        *
        *       ----  F = Beginning of Deque
                ----- L = End of Deque
                ----- 000000 = Deque items
                ----- ---- = Null values
                ----- | = Middle of array
                ----- [] Array.
        *
        * */
        Type [] newArray = (Type[]) new Object[Math.round(items.length / factor)];
        //Initializes Array
        int midpoint = newArray.length / 2;

        if (last > first) {
            //First case copy
            System.arraycopy(items, first, newArray, midpoint - size / 2, size);
            first = midpoint - size / 2;
            last = first + size - 1;     //1 + relative final index = size.  relative final index = true last - true first
                                        // 1 + true last - true first = size --> true last = size + true first - 1;
        } else {
            int firstToEndLength = items.length - first;
            System.arraycopy(items, first, newArray, midpoint - firstToEndLength, firstToEndLength);
            System.arraycopy(items, 0, newArray, midpoint, lastNext); //LastNext gives length as it is already last + 1;
            first = midpoint - firstToEndLength;
            last = midpoint + last;
        }

        firstNext = first - 1; //As deque was already < 1/4 original array and new array is 1/2 original array,
        lastNext = last + 1;   // maximum next variance is < Half size of new array.
        items = newArray;
    }

    private void emptyDequeCheck() {
        if (size == 0) {
            //Resets pointers in deque when size == 0 to offset moving pointers from addFirst and addLast.
            first = last = lastNext = firstNext = items.length/2;
        }
    }

    private void firstItemDeque(Type item) {
    	//Assigns values for creation of Deque of size = 1; (Largely exists to clean up code an remove redundancies)
    	//in terms of designating pointers from switch from empty array. 
    	items[items.length / 2] = item;
    	first = last = items.length / 2;
    	firstNext = --first;
    	lastNext = ++last;
    	++size;
    }


	public void addFirst(Type item) {
        //Adds item to nextFirst position in Array then updates first and nextFirst accordingly.
        //Then checks if array needs to be resized.
        if (size == 0) {
            firstItemDeque(item);
            return;
        }
        items[firstNext] = item;
        first = firstNext;
        --firstNext;
        ++size;
        if (firstNext == -1) {
            firstNext = items.length - 1;
        }
        resizeChecker();
    }

    public void addLast(Type item) {
        //Adds item to nextLast position in Deque then updates last and nextLast accordingly
        //Then checks if deque needs to be resized.
        if (size == 0) {
            firstItemDeque(item);
            return;
        }
        items[lastNext] = item;
        last = lastNext;
        ++lastNext;
        ++size;
        if (lastNext == items.length) {
            lastNext = 0;
        }
        resizeChecker();
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; ++i) {
            System.out.print(get(i) + " ");
        }
        System.out.println("");
    }

    public Type removeFirst() {
        //Removes item from removeFirst position in array.  Then updates and (in cases of empty array) resets pointers
        //Then checks if array needs resizing.
        if (size == 0) {
            return null;
        }
        Type orig = items[first];
        items[first] = null;
        firstNext = first;
        ++first;
        if (first == items.length) {
            first = 0;
        }
        --size;
        emptyDequeCheck();
        resizeChecker();
        return orig;
    }

    public Type removeLast() {
        //Removes item from removeLast position in array.  Then updates and (in cases of empty array) resets pointers
        //Then checks if array needs resizing.
        if (size == 0) {
            return null;
        }
        Type orig = items[last];
        items[last] = null;
        lastNext = last;
        --last;
        if (last == -1) {
            last = items.length - 1;
        }
        --size;
        emptyDequeCheck();
        resizeChecker();
        return orig;
    }

    public Type get(int index) {
        if (index >= size) {
            return null;
        }
        int kIndex = index + first;
        if (kIndex >= items.length) {
            kIndex -= items.length;
        }
        return items[kIndex];
    }
}
