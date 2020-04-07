/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =100; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		try {
			list1.remove(3);
			fail("Check out of bounds");
		}catch(IndexOutOfBoundsException e) {};
		// TODO: Add more tests here
		try {
			list1.remove(-1);
			fail("Check out of bounds");
		}catch(IndexOutOfBoundsException e) {};
		for(int i = 0;i<5;i++)
			a = longerList.remove(4);
		assertEquals("Remove: check contiguous removals",(Integer)9,longerList.get(4));
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		for(int i = 200;i<250;i++) {
			longerList.add(i);
		}
		assertEquals("testAddEnd: check last is correct", (Integer)249, longerList.get(longerList.size()-1));
		try{
			longerList.add(null);
		}catch(NullPointerException e) {}
		assertEquals("testAddEnd: check mid elements", (Integer) 201, longerList.get(101));
		assertEquals("AddeEnd: chck siz", (Integer)150, (Integer)longerList.size());
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("Size: check size for longerList", 100, longerList.size());
		assertEquals(2, shortList.size());
		assertEquals(3, list1.size());
		
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		try {
			longerList.add(-1, 123);
			fail("Out of bounds");
		}catch(IndexOutOfBoundsException e) {}
		try {
			longerList.add(102, 123);
			fail("Out of bounds");
		}catch(IndexOutOfBoundsException e) {}
		
		longerList.add(longerList.size(),123);
		assertEquals("Add at index: check last element", (Integer)123, longerList.get(longerList.size()-1));
		longerList.add(50, 50);
		assertEquals("AddAtIndex: check mid", (Integer)50, longerList.get(50));
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		try {
			longerList.set(0, null);
			fail("Unchecked null addition");
		}catch(NullPointerException e) {}
		try {
			longerList.set(-1, 123);
			fail("Unchecked out of bounds");
		}catch(IndexOutOfBoundsException e) {}
		try {
			longerList.set(longerList.size(), 123);
			fail("Unchecked out of bounds");
		}catch(IndexOutOfBoundsException e) {}
		for(int i = 0;i<100;i++) {
			longerList.set(i, 0);
			assertEquals("Set: check ele", (Integer)0, longerList.get(i));
		}
		
	    
	}
	
	
	// TODO: Optionally add more test methods.
	
}
