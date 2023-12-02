/* PrimVsKruskal.java
   CSC 226 - Summer 2020
   Assignment 2 - Prim MST versus Kruskal MST Template
   
   The file includes the "import edu.princeton.cs.algs4.*;" so that yo can use
   any of the code in the algs4.jar file. You should be able to compile your program
   with the command
   
	javac -cp .;algs4.jar PrimVsKruskal.java
	
   To conveniently test the algorithm with a large input, create a text file
   containing a test graphs (in the format described below) and run
   the program with
   
	java -cp .;algs4.jar PrimVsKruskal file.txt
	
   where file.txt is replaced by the name of the text file.
   
   The input consists of a graph (as an adjacency matrix) in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry G[i][j] >= 0.0 of the adjacency matrix gives the weight (as type double) of the edge from 
   vertex i to vertex j (if G[i][j] is 0.0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that G[i][j]
   is always equal to G[j][i].
*/

 import edu.princeton.cs.algs4.*;
 import java.util.Scanner;
 import java.io.File;
 import java.util.ArrayList; 
 import java.util.List;  
 import java.util.Iterator;
 import java.util.Collections;
 import java.util.Set;
 import java.util.HashSet;

//Do not change the name of the PrimVsKruskal class
//Do not change the name of the PrimVsKruskal class
public class PrimVsKruskal{

	/* PrimVsKruskal(G)
		Given an adjacency matrix for connected graph G, with no self-loops or parallel edges,
		determine if the minimum spanning tree of G found by Prim's algorithm is equal to 
		the minimum spanning tree of G found by Kruskal's algorithm.
		
		If G[i][j] == 0.0, there is no edge between vertex i and vertex j
		If G[i][j] > 0.0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static boolean PrimVsKruskal(double[][] G){
		int n = G.length;
		//int v = n*n;
		Edge temp;
		int e = 0;
		int count = 0;
		boolean pvk = false;
		boolean compare = true;
		Iterable<Edge> pmst;
		Iterable<Edge> kmst;
		boolean[][] check = new boolean[n][n];
		
		//System.out.println(G[2][0]);
		
		EdgeWeightedGraph weighted_g = new EdgeWeightedGraph(n);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(G[i][j] > 0 && check[i][j] == false){
					temp = new Edge(i, j, G[i][j]);
					weighted_g.addEdge(temp);
					check[j][i] = true;
				}
			
			}
		}
		
		//System.out.println(weighted_g.V());
		
		PrimMST primGraph = new PrimMST(weighted_g);
		KruskalMST kruskalGraph = new KruskalMST(weighted_g);
		
		double primw = primGraph.weight();
		double kruskaw = kruskalGraph.weight();
		
		pmst = primGraph.edges();
		kmst = kruskalGraph.edges();
		Iterator<Edge> plist = pmst.iterator();
		Iterator<Edge> klist = kmst.iterator();
		List<Edge> plist_list = new ArrayList<Edge>();
		List<Edge> klist_list = new ArrayList<Edge>();
		
		//pmst.forEach(plist::add);
		//kmst.forEach(klist::add);
		
		System.out.println("Prim Tree:");
		while(plist.hasNext()){
			temp = plist.next();
			plist_list.add(temp);
			System.out.println(temp);
		}
		System.out.println(primw);
		System.out.println(" ");
		System.out.println("Kruskal Tree:");
		while(klist.hasNext()){
			temp = klist.next();
			klist_list.add(temp);
			System.out.println(temp);
		}
		System.out.println(kruskaw);
		
		//Collections.sort(plist_list);
		//Collections.sort(klist_list);
		
		
		
		
		
		if(plist_list.size() == klist_list.size()){
			//System.out.println("works");
			for(int x=0; x<plist_list.size(); x++){
				for(int y=0; y<klist_list.size(); y++){
					if(plist_list.get(x).either() == klist_list.get(y).either() && plist_list.get(x).other(plist_list.get(x).either()) == klist_list.get(y).other(klist_list.get(y).either()) ){
						count++;
						if(plist_list.get(x).compareTo(klist_list.get(y)) != 0){
							compare = false;
						}
					}
				}
			}
			if(count == plist_list.size() && compare == true){
				pvk = true;
			}
		}
		
		//System.out.println(primw+" "+kruskaw);
		/* Build the MST by Prim's and the MST by Kruskal's */
		/* (You may add extra methods if necessary) */
		
		/* ... Your code here ... */
		
		
		/* Determine if the MST by Prim equals the MST by Kruskal */
		/* ... Your code here ... */

		return pvk;	
	}
		
	/* main()
	   Contains code to test the PrimVsKruskal function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below. 
	*/
   public static void main(String[] args) {
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int n = s.nextInt();
		double[][] G = new double[n][n];
		int valuesRead = 0;
		for (int i = 0; i < n && s.hasNextDouble(); i++){
			for (int j = 0; j < n && s.hasNextDouble(); j++){
				G[i][j] = s.nextDouble();
				if (i == j && G[i][j] != 0.0) {
					System.out.printf("Adjacency matrix contains self-loops.\n");
					return;
				}
				if (G[i][j] < 0.0) {
					System.out.printf("Adjacency matrix contains negative values.\n");
					return;
				}
				if (j < i && G[i][j] != G[j][i]) {
					System.out.printf("Adjacency matrix is not symmetric.\n");
					return;
				}
				valuesRead++;
			}
		}
		
		if (valuesRead < n*n){
			System.out.printf("Adjacency matrix for the graph contains too few values.\n");
			return;
		}	
		
        boolean pvk = PrimVsKruskal(G);
        System.out.printf("Does Prim MST = Kruskal MST? %b\n", pvk);
    }
}
