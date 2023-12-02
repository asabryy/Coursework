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
		//a bit wrong, just for lec
		Node n = new Node();
		n.value = value;
		n.next = null;
		
		Node P = head;
		while (p.next != null) {
			p = p.next;
			
		|}
	}
	
	public int size() {
		return -999;
	}
	
	public void clear() {
	}

	public int get (int index) {
	    return -9999;
	}

	public String toString() {
		String s = "{";
		Node p = head;
		
		while(p!=null) {
			s += p.value;
			if(p.next !=null) {
				s+=",";
				p = p.next;
		}
		s+= ",";
		return s;
	}
}
