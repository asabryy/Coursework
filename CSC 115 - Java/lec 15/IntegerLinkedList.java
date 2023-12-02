public class IntegerLinkedList implements IntegerList {
    Node head;
    int  count;
    
	public IntegerLinkedList() {
	    head = null;
	    count= 0;
	}

    public void add (int value) {
        addFront(value);
    }
    
	public void addFront (int value) {
	    Node n = new Node();
	    n.value = value;
	    n.next = head;
	    head = n;
	    count ++;
	}

	public void addBack (int value) {
	    Node n = new Node();
	    n.value = value;
	    n.next = null;
	    
	    if (head == null) {
	        head = n;
	    }
	    else {
	        Node p = head;
	        while (p.next != null) {
	            p = p.next;
	        }
	        p.next = n;
	    }
	    count++;
	    	
	}
	
	public int size() {
		return count;
	}
	
	public void clear() {
	    head = null;
	    count = 0;
	}

    // Purpose: return the ith element in the list
    //          where the first element is a position 0
    //
    // Pre-conditions:
    //      index >= 0 && index < l.size()
    //
    //  Examples:
    //
    //  if l is {1,2,3} then l.get(0) returns 1
    //  if l is {9,2,3,1,23} then l.get(4) returns 23
    //  if l is {1} then l.get(99) violates preconditions, so
    //  the result is undefined.
	public int get (int index) {
	    Node p = head;
	    int pos = 0;
	    
	    while (pos != index) {
	        pos++;
	        p = p.next;
	    }
	    return p.value;    
	}
	

    // I've changed this to use StringBuilder instead
    // of using String because with large lists, the
    // repeated copying of the String due to:
    // s = s + "abc"
    // makes this method very slow.
	public String toString() {
	    StringBuilder s = new StringBuilder();
        s.append("{");	    
	    Node p = head;
	    
	    while (p != null ) {
	        s.append(p.value);
	        if (p.next != null)
	            s.append(",");
	    
	        p = p.next;
	    }
	    s.append("}");
	    return s.toString();
    }
    
    // Pre-Conditions:
    //  l.size() > 0
    public int max () {
        return maxR(head);
   
    }
    
    private int maxI() {
        int maxVal = Integer.MIN_VALUE;
         
        return maxVal;
    }
    
    private int maxR(Node n) {
        int maxVal = Integer.MIN_VALUE;
		if(n.next == null){
			maxVal = n.value;
		}else{
			int maxRest;
			maxRest = maxR(n.next);
			
			if (n.value > maxRest) {
				maxVal = n.value; 
			}else {
				maxVal = maxRest;
			}
		}
        
        return maxVal; 
    }
}
