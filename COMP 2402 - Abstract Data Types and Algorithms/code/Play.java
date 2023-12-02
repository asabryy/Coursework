//import java.util.List;
//import java.util.ArrayList;
import java.util.*;

public class Play{
	public static void main(String[] args){
		String[] a = {"Gandalf","Frodo","Sam","Merry","Pippin","Gimli","Legolas","Aragorn","Boromir"};
		
		System.out.println("Contents of a: ");
		for(String s:a) //for each string s in a
			System.out.println(s);
		

		System.out.println("--ArrayLists--");
		List<String> alist = new ArrayList<>();
		
		for(String s:a)
			//alist.add(alist.size(),s);
			alist.add(s);
			//alist.add(0,s);
		
		System.out.println("Contents of alist: ");
		for(String s:alist)
			System.out.println(s);
		
		
		List<Integer> intList = new ArrayList<>();
		int n=1000000;
		System.out.print("Adding to intlist at back... ");
		System.out.flush();
		for(int i=0;i<n;i++){
			intList.add(i);
		}
		System.out.println("Done!");
		
		/*System.out.print("Adding to intlist at front... ");
		System.out.flush();
		for(int i=0;i<n;i++){
			intList.add(0,i);
		}
		System.out.println("Done!");*/
		
		alist.add(2,"Gollum");
		alist.remove(alist.size()-1);
		System.out.println("Contents of alist: ");
		for(String s:alist)
			System.out.println(s);
		
		Set<String> uset = new LinkedHashSet<>();
		for(String s:a)
			uset.add(s);
		
		System.out.println("Contents of uset: ");
		for(String s:uset)
			System.out.println(s);
		
		
		uset.add("Gollum");
		uset.add("Gollum");
		uset.remove("Boromir");
		
		System.out.println("Contents of uset: ");
		for(String s:uset)
			System.out.println(s);
		
		System.out.println("Is Frodo still in the group? "+uset.contains("Frodo"));
		
		System.out.println("Is Sauron still in the group? "+uset.contains("Sauron"));
		
		System.out.println("Is 3 in the group?" + uset.contains(3));
		
		intList = new ArrayList<>();
		Set<Integer> intSet = new HashSet<>();
		n = 10000;
		for(int i=0;i<n;i++){
			intList.add(i);
			intSet.add(i);
		}
		
		
		System.out.print("Searching a set...");
		System.out.flush();
		long t = System.nanoTime(); //grab start time
		for(int i=0;i<n*2;i++){
			intSet.contains(i);
		}
		t = System.nanoTime() - t; //measure the difference
		System.out.println("Done in "+t+" nanoseconds");
		
		System.out.print("Searching a list...");
		System.out.flush();
		t = System.nanoTime();
		for(int i=0;i<n*2;i++){
			intList.contains(i);
		}
		t = System.nanoTime() - t; 
		//System.currentTimeMillis()
		System.out.println("Done in "+t+" nanoseconds");		
		
		SortedSet<String> sset = new TreeSet<>();
		for(String s:a)
			sset.add(s);
		//sset.addAll(a);
		
		System.out.println("Contents of sset...");
		for(String s:sset)
			System.out.println(s);
		
		System.out.println("Contents of tailset: ");
		SortedSet<String> tail = sset.tailSet("Go");
		sset.add("Gollum");
		for(String s:tail)
			System.out.println(s);
		
		//Map<String,Integer> map = new HashMap<>();
		SortedMap<String,Integer> map = new TreeMap<>();
		map.put("Java",9);
		map.put("Javascript",6);
		map.put("Python", 7);
		map.put("C++", 10);
		map.put("BASIC",0);
		map.put("HTML/css", 6);
		map.put("Java",7);
		
		System.out.println("Contents of map: ");
		for(String k:map.keySet()){
			System.out.println(k+" => "+map.get(k));
		}
		System.out.println("Prolog => "+map.get("Prolog"));
		
		List<Map.Entry<String,Integer>> entryList = new ArrayList<>();
		entryList.addAll(map.entrySet());
		System.out.println("Contents of entryList: ");
		for(Map.Entry<String,Integer> e:entryList){
			System.out.println(e.getKey()+" => "+e.getValue());
		}
		
		System.out.println("Before sorting...");
		for(String s:alist){
			System.out.println(s);
		}
		
		Collections.sort(alist,new Comparator<String>(){
			public int compare(String x, String y){
				return x.length()-y.length();
			}
		});
		
		System.out.println("After sorting...");
		for(String s:alist){
			System.out.println(s);
		}
		
		Deque<String> dq = new ArrayDeque<>();
		dq.addLast("are");
		dq.addFirst("structures");
		dq.addLast("fun");
		dq.addFirst("data");
		System.out.println("Contents of deque...");
		for(String s:dq)
			System.out.println(s);
		
		dq.removeLast();
		dq.removeLast();
		dq.addFirst("<3");
		dq.addFirst("I");
		System.out.println("Contents of deque...");
		for(String s:dq)
			System.out.println(s);
		
		Queue<String> q = new LinkedList<>();
		
		Queue<String> pq = new PriorityQueue<>(new Comparator<String>(){
			public int compare(String x, String y){
				return x.length()-y.length();
			}
		});
		pq.addAll(alist);
		
		System.out.println("Contents of priorityqueue");
		
		//bad! 
		/*for(String s:pq){
			System.out.println(s);
		}*/
		
		int size = pq.size();
		for(int i=0;i<size;i++){
			System.out.println(pq.remove());
		}
	}
}