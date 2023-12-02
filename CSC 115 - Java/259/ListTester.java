class ListTester {
	public static void progressPercentage(int remain, int total) {
    if (remain > total) {
        throw new IllegalArgumentException();
    }
    int maxBareSize = 10; // 10unit for 100%
    int remainProcent = ((100 * remain) / total) / maxBareSize;
    char defaultChar = '-';
    String icon = "*";
    String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
    StringBuilder bareDone = new StringBuilder();
    bareDone.append("[");
    for (int i = 0; i < remainProcent; i++) {
        bareDone.append(icon);
    }
    String bareRemain = bare.substring(remainProcent, bare.length());
    System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
    if (remain == total) {
        System.out.print("\n");
    }
	}

    public static void main (String[] args) {
        //IntegerList l = new IntegerLinkedList();
        IntegerList l = new IntegerArrayList();
       
        l.addFront(10);
        l.addFront(20);
        l.addFront(256);
        l.addFront(1);

        System.out.println(l);
        System.out.println(l.size()); 
		
		long startTime = System.nanoTime();



		System.out.println("Start");
		for (int i=0; i<100000; i++){
			l.addFront(i);
			progressPercentage(i,99999);
		}
		System.out.println("End");
		
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);
		System.out.println(duration/1000000 +"ms");
    }
}