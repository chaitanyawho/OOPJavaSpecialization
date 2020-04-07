package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
		size = 0;
	}
	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if(element==null) {
			throw new NullPointerException();
		}
		LLNode<E> temp = new LLNode<E>(element);
		temp.next = tail;
		tail.prev.next = temp;
		temp.prev = tail.prev;
		tail.prev = temp;
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		LLNode<E> temp = head;
		int c = 0;
		while(temp.next.data!=null&&index>=0) {
			temp = temp.next;
			if(c==index)
				return temp.data;
			c++;
		}
		throw new IndexOutOfBoundsException();
		//if exec reaches this point in the moethod, the index is out of bounds;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		LLNode<E> temp = new LLNode<E>(element);
		LLNode<E> ptr = head.next, prevptr = head;
		int c = 0;
		if(index>size||index<0)
			throw new IndexOutOfBoundsException();
		if(element==null) throw new NullPointerException();
		while(c!=index) {
			prevptr = ptr;
			ptr = ptr.next;
			c++;
		}
		prevptr.next = temp;
		temp.next = ptr;
		ptr.prev = temp;
		temp.prev = prevptr;
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		
		LLNode<E> ptr = head.next;
		if(index>=size||index<0)
			throw new IndexOutOfBoundsException();
		int c = 0;
		while(c!=index) {
			ptr = ptr.next;
			c++;
		}
		ptr.prev.next = ptr.next;
		ptr.next.prev = ptr.prev;
		size--;
		return ptr.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		LLNode<E> ptr = head.next;
		if(index>=size||index<0)
			throw new IndexOutOfBoundsException();
		if (element==null) throw new NullPointerException();
		int c = 0;
		while(c!=index) {
			ptr = ptr.next;
			c++;
		}
		E replaced = ptr.data;
		ptr.data = element;
		return replaced;
	}  
	public void display() {
		System.out.println("Displaying list...");
		LLNode<E> t = head.next;
		while(t!=tail) {
			System.out.println(t.data);
			t = t.next;
		}
	}
	/*public static void main(String[] args) {
		MyLinkedList<Integer> l = new MyLinkedList<Integer>();
		l.add(1);
		
		l.add(2);
		System.out.println("Here");
		l.add(1,10);
		//l.add(3);
		
		l.display();
		try {
			System.out.println(l.get(3));
		}catch(IndexOutOfBoundsException e) {}
		l.remove(1);
		System.out.println(l.get(1));
		System.out.println("Replaced "+l.set(1,100));
		
	}*/
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	public LLNode() {
		this.data = null;
		this.prev = null;
		this.next = null;
	}

}


