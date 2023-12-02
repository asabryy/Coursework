// 
// An interface is a specification of behavior.  
//
public interface IntegerList
{
    // 
    // This is a kludge so I don't have to change my main
    // program when switching between linked list and
    // array list (add back easy for array list, add front
    // easy for linked list)
    // 
    
    void add (int value);
    
    // Purpose:
    //  add value to the end of the list
    //
    // Pre-conditions:
    //  none
    //
    // Returns:
    //  none
    //
    // Examples:
    //  if the list l is {1,2,3} and l.addBack(4) is called
    //  the resulting list is {1,2,3,4}
    //  
    //  if the list l is {} and l.addBack(1) is called
    //  the resulting list is {1}
    //
	void addBack (int value);

    // Purpose:
    //  add value to the front of the list
    //
    // Pre-conditions:
    //  none
    //
    // Returns:
    //  none
    //
    // Examples:
    //  if the list l is {1,2,3} and l.addFront(4) is called
    //  the resulting list is {4,1,2,3}
    //  
    //  if the list l is {} and l.addFront(1) is called
    //  the resulting list is {1}
    //	
	void addFront(int value);
	
	// Purpose:
	//  return the number of elements in the list
	// 
	// Pre-conditions:
	//  none
	//
	// Returns:
	//  an integer which is the number of elements in the list
	//  returns 0 if the list is empty.
	//
	// Examples:
	//  if the list l is {4,7,10} then l.size() returns 3
	//  if the list l is {7,23} then l.size() returns 2
	//  if the list l is {} then l.size() returns 0
	int size();
	
	// Purpose:
	//  remove all elements from the list
	//
	// Pre-conditions:
	//  none
	//
	// Returns:
	//  none
	//
	// Examples:
	//  if the list l is {2,3,1,23} then after l.clear() the list l is {}
	//  if the list l is {} then after l.clear() the list l is {}
	void clear();
	
	// Purpose:
	//   returns the element at position index in the list
	//   the first element in the list l is at index 0
	//   the last element in the list l is at index l.size() - 1
	//
	// Pre-conditions:
	//   for list l:  index >= 0 AND index < l.size()
	//
	// Examples:
	//  if the list l is {9,4,2} then l.get(0) returns 9
	//  if the list l is {2,3,4} then l.get(2) returns 4
	//  if the list l is {1,2,3} then the result of l.get(905) is undefined
	int get (int index);
	
	int max ();	
}
