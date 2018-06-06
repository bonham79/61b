public class ArrayDeque<Type> {
    private int size;
    private Type[] items;
    private int firstNext;
    private int lastNext;

    public ArrayDeque() {
        items = (Type[]) new Object[8];
        size = 0;

        firstNext = 3;
        lastNext = 4;
    }

    private Sentinel() {
        int first = 3;
        int last = 4;
    }

    private void resize(double factor) {
        //Type[] newDeque = (Type[]) new Object[size*factor];
        //System.arraycopy(items, , newDeque , , );
    }

    private boolean resizeCheck() {
        return false;
    }

	public void addFirst(Type item) {
        items[firstNext] = item;
        first = firstNext;
        --firstNext;
        ++size;
        if (firstNext == -1) {
            firstNext = items.length - 1;
        }
        if (resizeCheck()) {resize(5);}
    }

    public void addLast(Type item) {
        items[lastNext] = item;
        last = lastNext;
        ++lastNext;
        ++size;
        if (lastNext == items.length) {
            lastNext = 0;
        }
        if (resizeCheck()) {resize(5);}
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
        return orig;
    }

    public Type removeLast() {
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

    public static void main(String[] args) {
        ArrayDeque x = new ArrayDeque();
        for (int i = 0; i < 8; ++i) {
            x.addLast(i);
        }
        x.printDeque();
        int holder = x.size;
        for (int i = 0; i < holder; ++i) {
            System.out.println(x.removeFirst());
        }
    }
}