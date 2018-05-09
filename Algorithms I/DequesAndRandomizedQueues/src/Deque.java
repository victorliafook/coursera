import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node last;
	private int size;

	// construct an empty deque
	public Deque() {
		this.size = 0;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return this.size == 0;
	}

	// return the number of items on the deque
	public int size() {
		return this.size;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node oldFirst = this.first;
		Node newFirst = new Node();

		newFirst.item = item;
		newFirst.next = oldFirst;
		newFirst.previous = null;

		// if so, this means the deque was empty
		if (oldFirst == null) {
			this.last = newFirst;
		} else {
			oldFirst.previous = newFirst;
		}

		this.first = newFirst;
		this.size++;
	}

	// add the item to the end
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node oldLast = this.last;
		Node newLast = new Node();

		newLast.item = item;
		newLast.next = null;
		newLast.previous = oldLast;

		// if so, this means the deque was empty
		if (oldLast == null) {
			this.first = newLast;
		} else {
			oldLast.next = newLast;
		}

		this.last = newLast;
		this.size++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		Node first = this.first;

		this.first = first.next;
		if (this.first != null) {
			this.first.previous = null;
		}

		first.next = null;
		this.size--;
		if (isEmpty()) {
			this.last = null;
		}
		return first.item;
	}

	// remove and return the item from the end
	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		Node last = this.last;

		this.last = last.previous;
		if (this.last != null) {
			this.last.next = null;
		}

		last.previous = null;
		this.size--;
		if (isEmpty()) {
			this.first = null;
		}
		return last.item;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	// unit testing (optional)
	public static void main(String[] args) {
	}

	private class Node {
		Item item;
		Node next;
		Node previous;
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current;

		public DequeIterator() {
			this.current = first;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			if (current == null) {
				return false;
			}
			return true;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}

	}
}