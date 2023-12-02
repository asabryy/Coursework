import java.util.*;
import ods.*;


public class ArrayStackSpeedTest{
	
	public static void main(String[] args){
		List<Integer> list = new FastArrayStack<>(Integer.class);
		long start;
		int n = 10000000; //number of operations
		
		//==========Fill up the list a bit========
		for(int i=0;i<100000;i++)list.add(i);
		
		//==========Stack operations===========
		System.out.print("Stack operations: ");
		start = System.currentTimeMillis();
		for(int i=0;i<n;i++){
			int r = (int)Math.round(Math.random()); //{0,1}
			
			if(r==0){
				list.add(i);
			}else if(list.size()>0){
				list.remove(list.size()-1);
			}			
		}
		System.out.println((System.currentTimeMillis()-start)+"ms");
		
		
		//==========Get/Set (list operations)=======
		System.out.print("Get/Set operations: ");
		start = System.currentTimeMillis();
		for(int i=0;i<n;i++){
			int r = (int)Math.round(Math.random()); //{0,1}
			if(r==0){
				list.get((int)(Math.random()*list.size())); //get random element
			}else if(list.size()>0){
				list.set((int)(Math.random()*list.size()),i); //set random element to i
			}			
		}
		System.out.println((System.currentTimeMillis()-start)+"ms");
		
		
		//==========Queue operations===========
		System.out.print("Queue operations: ");
		start = System.currentTimeMillis();
		for(int i=0;i<n;i++){
			int r = (int)Math.round(Math.random()); //{0,1}
			//System.out.println(r);
			if(r==0){
				list.add(i);  //add at one end
			}else if(list.size()>0){
				list.remove(0); //remove from the other
			}			
		}
		System.out.println((System.currentTimeMillis()-start)+"ms");
		

		
	}
}