class MidtermReviewADT {
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
    public static int  count (IntegerList l, int val) {
         int count = 0;
		for(int i=0; i<l.size(); i++) {
			if(l.get(i) == val){
				count++;
			}
		}	
		return count;
    }
	
	public static IntegerList doubllist (IntegerList l){
		IntegerList lnew = new IntegerArrayList();
		
		for(int i=0; i<l.size(); i++){
			lnew.addBack(l.get(i));
			lnew.addBack(l.get(i));
		}
		
		l = lnew;
		return l;
	}
    
    public static void main (String[] args) {
        IntegerList l = new IntegerArrayList();
        l.addBack(99);
        l.addBack(2);
        l.addBack(99);
        l.addBack(2);
        l.addBack(7);
        l.addBack(99);
		l=doubllist(l);
		System.out.println(l);
        System.out.println(count(l,99));
    }
}
