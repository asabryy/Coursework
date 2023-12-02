public class Node {
    // This instance variables are "package friendly", which
    // means that any Java code in the same directory as this
    // Node.java file and directly modify this variables like:
    //
    // Node n = new Node();
    //
    // n.next = null;
    // n.value = 22;
    //
    // This is frowned upon in general, but for a class that only
    // serves as a wrapper, it is OK.
    //
    // You are also free to use the set and get methods, if that
    // is easier for you to understand:
    //
    // n.setNext(null);
    // n.setValue(22);
    
	int 	value;
	Node	next;

	void setValue (int v ) {
		value = v;
	}

	void setNext (Node n ) {
		next = n;
	}

	int getValue ()	{
		return value;
	}
	
	Node getNext ()	{
		return next;
	}
}

