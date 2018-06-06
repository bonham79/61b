public class ArrayDeque<Type> {
    private int size;
    private Type[] items;
    private int firstNext;
    private int lastNext;
    private int first;
    private int last;

    public ArrayDeque() {
        items = (Type[]) new Object[8];
        size = 0;

        first = 4;
        last = first;

        firstNext = 4;
        lastNext = 4;
    }

    private void resize(int factor) {
        Type[] newArray = (Type[]) new Object[Math.round(size*factor)];
        System.arraycopy(items, first, newArray, newArray.length/2 - items.length + first, items.length - first);
        System.arraycopy(items, 0, newArray, newArray.length/2, first);

        first = newArray.length/2 - items.length + first;
        firstNext = first - 1;
        last = first + size -1;
        lastNext = last + 1;
        items = newArray;
    }


	public void addFirst(Type item) {
        if (size == 0) {
            lastNext = last + 1;
        }
        items[firstNext] = item;
        first = firstNext;
        --firstNext;
        ++size;
        if (firstNext == -1) {
            firstNext = items.length - 1;
        }
        if (size == items.length) {
            resize(10);
        }
    }

    public void addLast(Type item) {
        if (size == 0) {
            firstNext = first - 1;
        }
        items[lastNext] = item;
        last = lastNext;
        ++lastNext;
        ++size;
        if (lastNext == items.length) {
            lastNext = 0;
        }
        if (size == items.length) {
            resize(10);
        }
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
        for (int i = 0; i < 2000000; ++i) {
            x.addFirst(i);
            x.addLast(i);
        }
        x.addLast("something");
        x.addFirst("another");
    }
}