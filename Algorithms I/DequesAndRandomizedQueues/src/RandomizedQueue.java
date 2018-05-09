import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size;
	private Node first;

	// construct an empty randomized queue
	public RandomizedQueue() {
		this.size = 0;
		this.first = null;
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		}
		return false;
	}

	// return the number of items on the randomized queue
	public int size() {
		return this.size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node oldFirst = this.first;
		Node newFirst = new Node();

		newFirst.item = item;
		newFirst.next = oldFirst;

		this.first = newFirst;
		this.size++;
	}

	// remove and return a random item
	public Item dequeue() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		int n = StdRandom.uniform(this.size); // from 0 to size-1
		Node current = this.first;

		if (n == 0) {
			this.first = current.next;
			current.next = null;
			this.size--;
			return current.item;
		}

		Node prev = this.first;
		int count = 0;
		while (count < n) {
			if (count == n - 1) {
				prev = current;
			}
			current = current.next;
			count++;
		}

		prev.next = current.next;
		current.next = null; // no loitering
		this.size--;
		return current.item;

		/*
		 * Node oldFirst = current; this.first = this.first.next; oldFirst.next = null;
		 * return oldFirst.item;
		 */
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		int n = StdRandom.uniform(this.size);
		int count = 0;
		Node current = this.first;
		while (count < n) {
			current = current.next;
			count++;
		}
		return current.item;
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedIterator();
	}

	// unit testing (optional)
	public static void main(String[] args) {

	}

	private class Node {
		Item item;
		Node next;
	}

	private class RandomizedIterator implements Iterator<Item> {

		private Item[] rndSeq;
		private int i;

		public RandomizedIterator() {
			i = 0;
			rndSeq = (Item[]) new Object[size];
			// TODO run through the list, inserting into the array, then randomize it
			int count = 0;
			Node current = first;
			while (count < size) {
				rndSeq[count] = current.item;
				current = current.next;
				count++;
			}
			StdRandom.shuffle(rndSeq);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			return i < size;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return rndSeq[i++];
		}

	}

}