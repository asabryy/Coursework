package comp2402a2;

import java.util.AbstractList;
import java.util.List;

/**
 */
public class Treque<T> extends AbstractList<T> {
	/**
	 * You decide on the instance variables you need.
	 */
	 
	public Factory<T> tre;
	public T[] head;
	public T[] tail; 
	public int first; 
	public int last; 
	public int c;
	public int back;
	public int n2;


	public Treque(Class<T> t) {
		// Put your own code here
		tre = new Factory<T>(t);
		head = tre.newArray(1);
		tail = tre.newArray(1);
		first=0;last=0;c=0;back=2;
	}

	public T get(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		// Put your own code here instead of throwing this exception
		n2 = (int)Math.round(c/2);
		if(i<n2){
		return head[i];}
		else{return tail[i-n2];
		}
	}

	public T set(int i, T x) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		// Put your own code here instead of throwing this exception
		T p;
		n2 = (int)Math.round(c/2);
		if(i<n2){
			p = head[i];
			head[i] = x;}
			else{
		p=tail[i-n2];
		tail[i-n2] = x;
	}
		return p;
	}
	
	public T[] insert(int i, T[] cArray){
		if (i< 0 || i> cArray.length-1) throw new IndexOutOfBoundsException();
						T[] a = cArray;
						T citem = null;
						T litem = null;
						for(int k=i; k<a.length; k++){
							if(k==i){
								citem = a[k];
								a[k]= null;
								litem = citem;
							}else{
								citem = a[k];
								a[k]= litem;
								litem = citem;
			}
		}

		return a;
	}

	public void add(int i, T x) {
		if (i < 0 || i > size()) throw new IndexOutOfBoundsException();
		// Put your own code here
		n2 = (int)Math.round(c/2);
		if(first==last){
			if(i<n2){
				if(i!=first){
					head = insert(i,head);
				}
				head[i] = x;
				first++;
				c++;}
			else{
				head[first] = tail[0];	
				for(int j =1; j<(i-n2); j++){
					tail[j-1] = tail[j];
				}
				tail[i-n2] = x;
				first++;
				c++;
			}
		}else if(first > last){
			if(i<n2){
				tail = insert(0,tail);
				tail[0] = head[first-1];
				head = insert(i,head);
				head[i] = x;
				last++;
				c++;
			}else{
				tail = insert(i-n2,tail);
				tail[i-n2] = x;
				last++;
				c++;
			}
		}
	}
	
	public T[] erase(int i, T[] cArray){
		T[] a = cArray;
		int b = i;
		for(int j = b; j < a.length-1; j++){
			a[j] = a[j+1];
		}
		return a;
	}

	public T remove(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		// Put your own code here
		
		T p = null;
		n2 = (int)Math.round(c/2);

		if (first == last){
			if(i < n2){
				p = head[i];
				head = erase(i,head);
				first--;
				head[first] = tail[0];
				first++;
				tail = erase(0,tail);
				last--;
				c--;
			}else{
				p = tail[i-n2];
				tail = erase(i-n2,tail);
				last--;
				c--;
			}
		}
		if(first > last){
			if (i < n2){
				p = head[i];
				head = erase(i, head);
				first--;
				c--;
			}else{
				p = tail[i-n2];
				tail = erase(i-n2,tail);
				last--;
				tail = insert(0, tail);
				tail[0] = head[first-1];
				last++;
				first--;
				c--;
			}
		}
		return p;


	}

	public int size() {
		// Put your own code here
		return c;
	}

	public static void main(String[] args) {
		//List<Integer> tr = new ArrayDeque<Integer>(Integer.class);
		List<Integer> tr = new Treque<Integer>(Integer.class);
		int K = 1000000;
		Stopwatch s = new Stopwatch();
		System.out.print("Appending " + K + " items...");
		System.out.flush();
		s.start();
		for (int i = 0; i < K; i++) {
			tr.add(i);
		}
		s.stop();
		System.out.println("done (" + s.elapsedSeconds() + "s)");

		System.out.print("Prepending " + K + " items...");
		System.out.flush();
		s.start();
		for (int i = 0; i < K; i++) {
			tr.add(0, i);
		}
		s.stop();
		System.out.println("done (" + s.elapsedSeconds() + "s)");

		System.out.print("Midpending(?!) " + K + " items...");
		System.out.flush();
		s.start();
		for (int i = 0; i < K; i++) {
			tr.add(tr.size()/2, i);
		}
		s.stop();
		System.out.println("done (" + s.elapsedSeconds() + "s)");


		System.out.print("Removing " + K + " items from the back...");
		System.out.flush();
		s.start();
		for (int i = 0; i < K; i++) {
			tr.remove(tr.size()-1);
		}
		s.stop();
		System.out.println("done (" + s.elapsedSeconds() + "s)");

		System.out.print("Removing " + K + " items from the front...");
		System.out.flush();
		s.start();
		for (int i = 0; i < K; i++) {
			tr.remove(0);
		}
		s.stop();
		System.out.println("done (" + s.elapsedSeconds() + "s)");

		System.out.print("Removing " + K + " items from the middle...");
		System.out.flush();
		s.start();
		for (int i = 0; i < K; i++) {
			tr.remove(tr.size()/2);
		}
		s.stop();
		System.out.println("done (" + s.elapsedSeconds() + "s)");
	}



}
