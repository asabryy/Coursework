package comp2402a5;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.lang.reflect.Array;

/**
* An unfinished implementation of an Countdown tree (for exercises)
* @author morin
*
* @param <T>
*/
public class CountdownTree<T> extends
BinarySearchTree<CountdownTree.Node<T>, T> implements SSet<T> {

	// countdown delay factor
	double d;

	public static class Node<T> extends BSTNode<Node<T>,T> {
		int timer;  // the height of the node
	}

	public CountdownTree(double d) {
		this.d = d;
		sampleNode = new Node<T>();
		c = new DefaultComparator<T>();
	}

	public boolean add(T x) {
		Node<T> u = new Node<T>();
		u.timer = (int)Math.ceil(d);
		u.x = x;
		if (super.add(u)) {
			u = u.parent; 
			while (u != nil) {
				u.timer--;
				if (u.timer == 0) {
					this.explode(u);
				}
				u = u.parent;
			}
			return true;
		}
		return false;
	}

	public void splice(Node<T> u) {
		Node<T> newd = u.parent;
		super.splice(u);
		while (newd != nil) {
			newd.timer--;
			if (newd.timer == 0){
				this.explode(newd);
			}
			newd = newd.parent; //iterate
		}
	}

	protected void explode(Node<T> u) {
		this.rebuild(u);
	}
	
	void rebuild(Node<T> u) {
		int news = size(u);
		Node<T> temp = u.parent;
		Node<T>[] newb = (Node<T>[]) Array.newInstance(Node.class, news);

		packIntoArray(u, newb, 0);

		if (temp == nil) {
			r = buildBalanced(newb, 0, news);
			r.parent = nil;
		} else if (temp.right == u) {
			temp.right = buildBalanced(newb, 0, news);
			temp.right.parent = temp;
		} else {
			temp.left = buildBalanced(newb, 0, news);
			temp.left.parent = temp;
			}
	}

	int packIntoArray(Node<T> u, Node<T>[] v, int i) {
		if (u == nil) {
			return i;
		}

		i = packIntoArray(u.left, v, i);
		v[i++] = u;

		return packIntoArray(u.right, v, i);
	}

	Node<T> buildBalanced(Node<T>[] v, int i, int news) {
		if (news == 0){
			return nil;
		}
		
		int m = news / 2;
		v[i + m].left = buildBalanced(v, i, m);

		if (v[i + m].left != nil){
			v[i + m].left.parent = v[i + m];
		}
		
		v[i + m].right = buildBalanced(v, i + m + 1, news - m - 1);

		if (v[i + m].right != nil){
			v[i + m].right.parent = v[i + m];
		}
		
		v[i+m].timer = (int) Math.ceil(d*size(v[i+m]));

		return v[i + m];
	}

	// Here is some test code you can use
	public static void main(String[] args) {
		Testum.sortedSetSanityTests(new SortedSSet<Integer>(new CountdownTree<Integer>(1)), 1000);
		Testum.sortedSetSanityTests(new SortedSSet<Integer>(new CountdownTree<Integer>(2.5)), 1000);
		Testum.sortedSetSanityTests(new SortedSSet<Integer>(new CountdownTree<Integer>(0.5)), 1000);

		java.util.List<SortedSet<Integer>> ell = new java.util.ArrayList<SortedSet<Integer>>();
		ell.add(new java.util.TreeSet<Integer>());
		ell.add(new SortedSSet<Integer>(new CountdownTree<Integer>(1)));
		ell.add(new SortedSSet<Integer>(new CountdownTree<Integer>(2.5)));
		ell.add(new SortedSSet<Integer>(new CountdownTree<Integer>(0.5)));
		Testum.sortedSetSpeedTests(ell, 1000000);
	}
}
