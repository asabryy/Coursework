class ListTester {

    public static void main (String[] args) {
        IntegerList l = new IntegerLinkedList();
        
        l.addBack(10);
        l.addBack(20);
        l.addBack(2);
        l.addFront(4);
        
        System.out.println(l);
        System.out.println("Max is: " + l.max());
    }
}
