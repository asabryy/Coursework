

import java.util.*;
import ods.*;


public class Compare{
	
	public static void main(String[] args){
		List<Integer> list;
		long start;
		
		list = new ArrayList<>();
		System.out.print("Adding to ArrayList: ");
		start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			list.add(i);
		}
		System.out.println((System.currentTimeMillis()-start)+"ms");
		
		list = new ArrayStack<>(Integer.class);
		System.out.print("Adding to ArrayStack: ");
		start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			list.add(i);
		}
		System.out.println((System.currentTimeMillis()-start)+"ms");
		
		
		
		list = new FastArrayStack<>(Integer.class);
		System.out.print("Adding to FastArrayStack: ");
		start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			list.add(i);
		}
		System.out.println((System.currentTimeMillis()-start)+"ms");
		
		
		list = new SlowArrayStack<>(Integer.class);
		System.out.print("Adding to SlowArrayStack: ");
		start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			list.add(i);
		}
		System.out.println((System.currentTimeMillis()-start)+"ms");
	}
}