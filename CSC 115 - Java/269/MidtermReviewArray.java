class MidtermReviewArray {
    /* 
     * Purpose:
     *   Return the number of times val occurs in l
     *
     * Pre-conditions:
     *   None.
     *
     * Examples:
     *   If l is {99,2,99,2,7,99} then count(l,99) returns 3 
     *   If l is {} then count(l,32) returns 0
     *
     */ 
    public static int  count (int[] l, int val) {
        int count = 0;
		for(int i=0; i<l.length; i++) {
			if(l[i] == val){
				count++;
			}
		}
		return count;
    }
    
    public static void printArray (int[] l) {
        System.out.print("{");
        for (int i =0; i < l.length;i++) {
            System.out.print(l[i]);
            if (i != l.length-1)
                System.out.print(",");
        }
        System.out.println("}");
    }
    
    public static void main (String[] args) {
        int[] l = {99,2,99,2,7,99};
        
        printArray(l);
        System.out.println(count(l,99));
    }
}
