// File: DoubleArraySeq.java from the package edu.colorado.collections

// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.

// Check with your instructor to see whether you should put this class in
// a package. At the moment, it is declared as part of edu.colorado.collections:
//package edu.colorado.collections;

/******************************************************************************
* This class is a homework assignment;
* A DoubleArraySeq is a collection of double numbers.
* The sequence can have a special "current element," which is specified and 
* accessed through four methods that are not available in the sequence class 
* (start, getCurrent, advance and isCurrent).
*
* @note
*   (1) The capacity of one a sequence can change after it's created, but
*   the maximum capacity is limited by the amount of free memory on the 
*   machine. The constructor, addAfter, 
*   addBefore, clone, 
*   and concatenation will result in an
*   OutOfMemoryError when free memory is exhausted.
*   <p>
*   (2) A sequence's capacity cannot exceed the maximum integer 2,147,483,647
*   (Integer.MAX_VALUE). Any attempt to create a larger capacity
*   results in a failure due to an arithmetic overflow. 
*
* @note
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @see
*   <A HREF="../../../../edu/colorado/collections/DoubleArraySeq.java">
*   Java Source Code for this class
*   (www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java)
*   </A>
*
* @version
*   March 5, 2002
******************************************************************************/
public class DoubleArraySeq implements Cloneable
{
	// Invariant of the DoubleArraySeq class:
    //   1. The number of elements in the seqeunces is in the instance variable 
    //      manyItems.
    //   2. For an empty sequence (with no elements), we do not care what is 
    //      stored in any of data; for a non-empty sequence, the elements of the
    //      sequence are stored in data[0] through data[manyItems-1], and we
    //      dont care whats in the rest of data.
    //   3. If there is a current element, then it lies in data[currentIndex];
    //      if there is no current element, then currentIndex equals manyItems. 
    private double[ ] data;
    private int manyItems;
    private int currentIndex; 
   
    /**
    * Initialize an empty sequence with an initial capacity of 10.  Note that
    * the addAfter and addBefore methods work
    * efficiently (without needing more memory) until this capacity is reached.
    * @param - none
    * @postcondition
    *   This sequence is empty and has an initial capacity of 10.
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for: 
    *   new double[10].
    **/   
    public DoubleArraySeq( )
    {
		// 10 slots available by default (0 to 9)
		final int INITIAL_CAPACITY = 10;
	    manyItems = 0;
	    // Current element is the first element by default
	    currentIndex = 0;
	    // Initialize array
	    data = new double[INITIAL_CAPACITY];
    }
     

    /**
    * Initialize an empty sequence with a specified initial capacity. Note that
    * the addAfter and addBefore methods work
    * efficiently (without needing more memory) until this capacity is reached.
    * @param initialCapacity
    *   the initial capacity of this sequence
    * @precondition
    *   initialCapacity is non-negative.
    * @postcondition
    *   This sequence is empty and has the given initial capacity.
    * @exception IllegalArgumentException
    *   Indicates that initialCapacity is negative.
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for: 
    *   new double[initialCapacity].
    **/   
    public DoubleArraySeq(int initialCapacity)
    {
        // 'initialCapacity' slots available (0 to initialCapacity-1)
	    if (initialCapacity < 0)
		    throw new IllegalArgumentException ("initialCapacity is negative: " + initialCapacity);
	    manyItems = 0;
	    // Current element is the first element by default
	    currentIndex = 0;
	    // Initialize array
	    data = new double[initialCapacity];
    }
        
 
    /**
    * Add a new element to this sequence, after the current element. 
    * If the new element would take this sequence beyond its current capacity,
    * then the capacity is increased before adding the new element.
    * @param element
    *   the new element that is being added
    * @postcondition
    *   A new copy of the element has been added to this sequence. If there was
    *   a current element, then the new element is placed after the current
    *   element. If there was no current element, then the new element is placed
    *   at the end of the sequence. In all cases, the new element becomes the
    *   new current element of this sequence. 
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for increasing the sequence's capacity.
    * @note
    *   An attempt to increase the capacity beyond
    *   Integer.MAX_VALUE will cause the sequence to fail with an
    *   arithmetic overflow.
    **/
    public void addAfter(double element)
    {
	    //Check that there is room available
	    if (data.length == manyItems)
		    ensureCapacity(manyItems * 2 + 1);
        // If no current element, set to first empty element
	    if (isCurrent() == false)
	        currentIndex = manyItems;
	    // OR set current element to where new element will go
	    else
	        currentIndex++;
	    // Move items out of the way
	    for (int i = manyItems-1; i >= currentIndex; i--)
	        data[i + 1] = data[i];
	    // Insert new element
	    data[currentIndex] = element;
	    // Extend list
	    manyItems++;
    }


    /**
    * Add a new element to this sequence, before the current element. 
    * If the new element would take this sequence beyond its current capacity,
    * then the capacity is increased before adding the new element.
    * @param element
    *   the new element that is being added
    * @postcondition
    *   A new copy of the element has been added to this sequence. If there was
    *   a current element, then the new element is placed before the current
    *   element. If there was no current element, then the new element is placed
    *   at the start of the sequence. In all cases, the new element becomes the
    *   new current element of this sequence. 
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for increasing the sequence's capacity.
    * @note
    *   An attempt to increase the capacity beyond
    *   Integer.MAX_VALUE will cause the sequence to fail with an
    *   arithmetic overflow.
    **/
    public void addBefore(double element)
    {
        //Check that there is room available
	    if (data.length == manyItems)
		    ensureCapacity(manyItems * 2 + 1);
        // If no current element, set to first element
	    if (isCurrent() == false)
	        currentIndex = 0;
	    // Move items out of the way
	    for (int i = manyItems-1; i >= currentIndex; i--)
	        data[i + 1] = data[i];
	    // Insert new element
	    data[currentIndex] = element;
	    // Extend list
	    manyItems++;
    }
   
   
    /**
    * Place the contents of another sequence at the end of this sequence.
    * @param addend
    *   a sequence whose contents will be placed at the end of this sequence
    * @precondition
    *   The parameter, addend, is not null. 
    * @postcondition
    *   The elements from addend have been placed at the end of 
    *   this sequence. The current element of this sequence remains where it 
    *   was, and the addend is also unchanged.
    * @exception NullPointerException
    *   Indicates that addend is null. 
    * @exception OutOfMemoryError
    *   Indicates insufficient memory to increase the size of this sequence.
    * @note
    *   An attempt to increase the capacity beyond
    *   Integer.MAX_VALUE will cause an arithmetic overflow
    *   that will cause the sequence to fail.
    **/
    public void addAll(DoubleArraySeq addend)
    {
        ensureCapacity(manyItems + addend.manyItems);
		System.arraycopy(addend.data, 0, data, manyItems, addend.manyItems);
		manyItems += addend.manyItems;
    }   
   
   
    /**
    * Move forward, so that the current element is now the next element in
    * this sequence.
    * @param - none
    * @precondition
    *   isCurrent() returns true. 
    * @postcondition
    *   If the current element was already the end element of this sequence 
    *   (with nothing after it), then there is no longer any current element. 
    *   Otherwise, the new element is the element immediately after the 
    *   original current element.
    * @exception IllegalStateException
    *   Indicates that there is no current element, so 
    *   advance may not be called.
    **/
    public void advance( )
    {
        if (isCurrent() == true)
	        currentIndex++;
	    else
		    throw new IllegalStateException ("There is no current element");
    }
   
   
    /**
    * Generate a copy of this sequence.
    * @param - none
    * @return
    *   The return value is a copy of this sequence. Subsequent changes to the
    *   copy will not affect the original, nor vice versa.
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for creating the clone.
    **/ 
    public DoubleArraySeq clone( )
    {  // Clone a DoubleArraySeq object.
        DoubleArraySeq answer;
      
        try
        {
            answer = (DoubleArraySeq) super.clone( );
        }
        catch (CloneNotSupportedException e)
        {   // This exception should not occur. But if it does, it would probably
            // indicate a programming error that made super.clone unavailable.
            // The most common error would be forgetting the "Implements Cloneable"
            // clause at the start of this class.
            throw new RuntimeException ("This class does not implement Cloneable");
        }
      
        answer.data = data.clone( );
      
        return answer;
    }
   

    /**
    * Create a new sequence that contains all the elements from one sequence
    * followed by another.
    * @param s1
    *   the first of two sequences
    * @param s2
    *   the second of two sequences
    * @precondition
    *   Neither s1 nor s2 is null.
    * @return
    *   a new sequence that has the elements of s1 followed by the
    *   elements of s2 (with no current element)
    * @exception NullPointerException.
    *   Indicates that one of the arguments is null.
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for the new sequence.
    * @note
    *   An attempt to create a sequence with a capacity beyond
    *   Integer.MAX_VALUE will cause an arithmetic overflow
    *   that will cause the sequence to fail.
    **/   
    public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2)
    {
        DoubleArraySeq answer = new DoubleArraySeq(s1.getCapacity() + s2.getCapacity());
		
		System.arraycopy(s1.data, 0, answer.data, 0, s1.manyItems);
		System.arraycopy(s2.data, 0, answer.data, s1.manyItems, s2.manyItems);
		answer.manyItems = s1.manyItems + s2.manyItems;
		answer.currentIndex = answer.manyItems;
		
		return answer;
    }


    /**
    * Change the current capacity of this sequence.
    * @param minimumCapacity
    *   the new capacity for this sequence
    * @postcondition
    *   This sequence's capacity has been changed to at least minimumCapacity.
    *   If the capacity was already at or greater than minimumCapacity,
    *   then the capacity is left unchanged.
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for: new int[minimumCapacity].
    **/
    public void ensureCapacity(int minimumCapacity)
    {
	    double[ ] extendedArray;
	  
	    // Check that input minimumCapacity is greater than current capacity
	    if (getCapacity() < minimumCapacity)
	    {
		    extendedArray = new double[minimumCapacity];
		    // Copies old array to new array (Object src, int srcPos, Object dest, int destPos, int length)
		    System.arraycopy(data, 0, extendedArray, 0, manyItems);
		    data = extendedArray;
	    }
    }

   
    /**
    * Accessor method to get the current capacity of this sequence. 
    * The add method works efficiently (without needing
    * more memory) until this capacity is reached.
    * @param - none
    * @return
    *   the current capacity of this sequence
    **/
    public int getCapacity( )
    {
        // Get current capacity
	    return data.length;
    }


    /**
    * Accessor method to get the current element of this sequence. 
    * @param - none
    * @precondition
    *   isCurrent() returns true.
    * @return
    *   the current element of this sequence
    * @exception IllegalStateException
    *   Indicates that there is no current element, so 
    *   getCurrent may not be called.
    **/
    public double getCurrent( )
    {
        // Test if there is a current element
	    if (isCurrent() == false)
        {
		    throw new IllegalStateException("There is no current Element");
	    }
	    else
	    {
		    // Get current element
		    return (data[currentIndex]);
	    }
    }


    /**
    * Accessor method to determine whether this sequence has a specified 
    * current element that can be retrieved with the 
    * getCurrent method. 
    * @param - none
    * @return
    *   true (there is a current element) or false (there is no current element at the moment)
    **/
    public boolean isCurrent( )
    {
        // Test for current element
	    return ((currentIndex >= 0) && (currentIndex < manyItems));
    }
              
    /**
    * Remove the current element from this sequence.
    * @param - none
    * @precondition
    *   isCurrent() returns true.
    * @postcondition
    *   The current element has been removed from this sequence, and the 
    *   following element (if there is one) is now the new current element. 
    *   If there was no following element, then there is now no current 
    *   element.
    * @exception IllegalStateException
    *   Indicates that there is no current element, so 
    *   removeCurrent may not be called. 
    **/
    public void removeCurrent( ) //REFER TO 'GET CURRENT' IF THIS CAUSES ERROR
    {
	    if (isCurrent() == false)
	        throw new IllegalStateException("There is no current Element");

        for (int i = currentIndex; i <= manyItems-2; i++)
	        data[i] = data[i + 1];
			
	    manyItems--;
    }
                 
   
    /**
    * Determine the number of elements in this sequence.
    * @param - none
    * @return
    *   the number of elements in this sequence
    **/ 
    public int size( )
    {
        return manyItems;
    }
   
   
    /**
    * Set the current element at the front of this sequence.
    * @param - none
    * @postcondition
    *   The front element of this sequence is now the current element (but 
    *   if this sequence has no elements at all, then there is no current 
    *   element).
    **/ 
    public void start( )
    {
        currentIndex = 0;
    }
   
   
    /**
    * Reduce the current capacity of this sequence to its actual size (i.e., the
    * number of elements it contains).
    * @param - none
    * @postcondition
    *   This sequence's capacity has been changed to its current size.
    * @exception OutOfMemoryError
    *   Indicates insufficient memory for altering the capacity. 
    **/
    public void trimToSize( )
    {
        double[ ] trimmedArray;
      
        if (data.length != manyItems)
        {
            trimmedArray = new double[manyItems];
            System.arraycopy(data, 0, trimmedArray, 0, manyItems);
            data = trimmedArray;
        }
    }      
}
           
