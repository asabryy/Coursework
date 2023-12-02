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
	    // a bit wrong, does it handle empty list?
	    Node n = new Node();
	    n.value = value;
	    n.next = null;
		
		if (head == null){
			head = n;
		}else{
			Node p = head;
			while (p.next != null) {
				p = p.next;
			}
			p.next = n;
		}
	    count++;
	    	
	}
	
	public int size() {
		return -999;
	}
	
	public void clear() {
	}

	public int get (int index) {
	    Node p = head;
		for (int i = 0; i<index; i++){
			p = p.next;
		}
		
		return p.getValue(); 
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
}
