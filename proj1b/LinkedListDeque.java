public class LinkedListDeque<Type> implements Deque<Type>{
	private class Node {
		Type item;
		Node next;
		Node prev;

		Node(Node m, Type i, Node n) {
			item = i;
			prev = m;
			next = n;
		}
	}
	private Node sentinel; 
	private int size; 

	public LinkedListDeque() {
		sentinel = new Node(sentinel, null, sentinel);
		size = 0;
	}

	public LinkedListDeque(Type item) {
		sentinel = new Node(null, null, null);
		sentinel.next = new Node(sentinel, item, sentinel);
		sentinel.prev = sentinel.next;
		size = 1;
	}

	@Override
	public void addFirst(Type item) {
		if (this.size == 0) {
			this.sentinel.next = new Node(this.sentinel, item, this.sentinel);
			this.sentinel.prev = this.sentinel.next;
		} else {
			Node originalNext = this.sentinel.next;
			this.sentinel.next = new Node(sentinel, item, originalNext);
			originalNext.prev = this.sentinel.next;
		}
		this.size += 1; 
	}

	@Override
	public void addLast(Type item) {
		if (this.size == 0) {
			this.sentinel.prev = new Node(this.sentinel, item, this.sentinel);
			this.sentinel.next = this.sentinel.prev;
		} else {
			Node originalPrev = this.sentinel.prev;
			this.sentinel.prev = new Node(originalPrev, item, sentinel);
			originalPrev.next = this.sentinel.prev;
		}
		this.size += 1;

	}

	@Override
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void printDeque() {
		Node current = this.sentinel.next;
		for (int i = 1; i <= this.size; i ++) {
			System.out.print(current.item + " ");
			current = current.next;
		}
		System.out.println("");

	}

	@Override
	public Type removeFirst() {
		if (this.size == 0) {return null;}
		Type copy = this.sentinel.next.item;
		this.sentinel.next = this.sentinel.next.next;
		this.sentinel.next.prev = this.sentinel;
		this.size -= 1;
		return copy;
	}

	@Override
	public Type removeLast() {
		if (this.size == 0) {return null;}
		Type copy = this.sentinel.prev.item;
		this.sentinel.prev = this.sentinel.prev.prev;
		this.sentinel.prev.next = this.sentinel;
		this.size -= 1;
		return copy;
	}

	@Override
	public Type get(int index) {
		if (index >= this.size) {
			return null;
		}
		Node selection = this.sentinel.next;
		for (int i = 0; i < index; i += 1) {
			selection = selection.next;
		}
		return selection.item;
	}

	public Type getRecursive(int index) {
		if ((index >= this.size) || (-index > size)) {return null;}
		if (index < 0) {
			Node last = this.sentinel.prev;
			return negRecurse(last, index);
		} else {
			Node first = this.sentinel.next;
			return posRecurse(first, index);
		}
	}

	private Type posRecurse(Node first, int index) {
		if (index == 0) {
			return first.item;
		} else {
			return posRecurse(first.next, index -= 1);
		}
	}

	private Type negRecurse(Node last, int index) {
		if (index == -1) {
			return last.item;
		} else {
			return negRecurse(last.prev, index += 1);
		}
	}
}

	/*public Type getRecursive(int index) {
		public Node first;
		if (index >= this.length) {return null;}
		return Type recurse(this.sentinel.first, index);

		public static Type recurse(Node first, int index) {
			if (index == 0) {
				return first;
			} else {return helper(first.next, index -= 1);}
		}

	}	
} */