package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.EmptyListError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list.
	 * Node is defined at the bottom of this file.
	 */
	Node<T> start;
	
	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.next;
		return before;
	}

	@Override
	public T removeBack() {
		Node<T> current = start;
		checkNotEmpty();
		// if there is only one item
		if (start.next == null) {
			T last = start.value;
			start = null; // Not start.next bc start.next IS A NODE so make start = to null and it'll forget start.next
			return last;
		}
		while (current.next != null) {
			if (current.next.next == null) {
				T last = current.next.value;
				current.next = null;
				return last;
			} else {
				current = current.next;
			}
		}
		throw new EmptyListError();
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		if (index == 0) {
			return removeFront();
		}
		if (index == size()-1) {
			return removeBack();
		}
		else {
			Node<T> behind = start;
			for (int i = 0; i < index-1; i++) {
				behind = behind.next;
			}
			Node <T> trash = behind.next; // Creates anew node that points at the node being removed
			behind.next = behind.next.next; // Skip over the node we're deleting
			trash.next = null; // Makes the node we're deleting point to nothings
			return trash.value;
		}
	}

	@Override
	public void addFront(T item) {
		if (start == null) {
			start = new Node<T>(item, null);
		}
		else {
			start = new Node<T>(item, start); //B/c the start on the right hand side of the equals is the old start value
		}
	}

	@Override
	public void addBack(T item) {
		//if there are no nodes in list
		if (start == null) {
			this.start = new Node<T>(item, start);
		}
		//if there is one node
		else {
			Node<T> last = start;
			while (last.next != null) {
				last = last.next;
			}
			last.next = new Node<T>(item, null);
		}
	}

	@Override
	public void addIndex(int index, T item) {
		if (index == 0) {
			addFront(item);
		}
		else if (index < 0) {
			throw new BadIndexError(index);
		}
		else if (index == size()) { //We have elseif's because
			addBack(item);
		}
		else if (index > size()) {
			throw new BadIndexError(index);
		}
		else {
			Node<T> behind = start;
			for (int i = 0; i < index-1; i++) {
				behind = behind.next;
			}
			behind.next = new Node<T>(item, behind.next);
		}
	}
	
	
	
	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return getIndex(size()-1); // because size starts counting from 1 so if we want the last index . . . 
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				n.value = value;
				return;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of SinglyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

}
