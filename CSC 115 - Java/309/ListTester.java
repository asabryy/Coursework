class ListTester {

    public static void main (String[] args) {
        IntegerList l = new IntegerLinkedList();
        Node head = new Node(7, null);
		Node n = new Node(6, head);
		head = n;
		n = new Node(5, head);
		head = n;
		
		while(head != null){
			System.out.println(head.getValue());
			head = head.next;
		}
    }
}
