public class WordFrequencyBST {
	private TreeNode root;
	private int numElements;
	
	public WordFrequencyBST() {
		root = null;
		numElements = 0;
	}
	
	/*
	 * Purpose: Update the BST by handling input word 
	 * Parameters: String word - The new word to handle
	 * Returns: Nothing
	 * Explanation: If there is no entry in the tree 
	 *   representing the word, then the a new entry 
	 *   should be created and placed into the correct 
	 *   location of the BST. Otherwise, the existing
	 *   entry for the word should have its frequency
	 *   value incremented. 
	 */	
	public void handleWord(String word) {
		root = handleWord(root, word);
	}
	
	public TreeNode handleWord(TreeNode cur, String word) {
		if (cur == null) {
			cur = new TreeNode(new Entry(word));
			numElements++;
		} else if (cur.compareTo(word) == 0) {
			cur.addToFrequency();
		} else if (cur.compareTo(word) < 0) {
			cur.right = handleWord(cur.right, word);
		} else {
			cur.left = handleWord(cur.left, word);
		}
		return cur;
	}
	
	/*
	 * Purpose: Get the frequency value of the given word
	 * Parameters: String word - the word to find
	 * Returns: int - the word's associated frequency
	 */	
	public int getFrequency(String word) {
		return frequencyHelper(root, word);
	}
	
	public int frequencyHelper(TreeNode cur, String word) {
		if (cur == null) {
			return 0;
		} else if (cur.compareTo(word) == 0) {
			return cur.getData().getFrequency();
		} else if (cur.compareTo(word) < 0) {
			return frequencyHelper(cur.right, word);
		} else {
			return frequencyHelper(cur.left, word);
		}
	}

	/****************************************************
	* Helper functions for Insertion and Search testing *
	****************************************************/
	
	public String inOrder() {
		if (root == null) {
			return "empty";
		}
		return "{" + inOrderRecursive(root) + "}";
	}
	
	public String inOrderRecursive(TreeNode cur) {
		String result = "";
		if (cur.left != null) {
			result = inOrderRecursive(cur.left) + ",";
		} 
		result += cur.getData().getWord();
		if (cur.right != null) {
			result += "," + inOrderRecursive(cur.right);
		}
		return result;
	}
	
	public String preOrder() {
		if (root == null) {
			return "empty";
		}
		return "{" + preOrderRecursive(root) + "}";
	}
	
	public String preOrderRecursive(TreeNode cur) {
		String result = cur.getData().getWord();
		if (cur.left != null) {
			result += "," + preOrderRecursive(cur.left);
		} 
		if (cur.right != null) {
			result += "," + preOrderRecursive(cur.right);
		}
		return result;
	}
	
	/****************************************************
	* Helper functions to populate a Heap from this BST *
	****************************************************/
	
	public MaxFrequencyHeap createHeapFromTree() {
		MaxFrequencyHeap maxHeap = new MaxFrequencyHeap(numElements+1);
		addToHeap(maxHeap, root);
		return maxHeap;
	}
	
	public void addToHeap(MaxFrequencyHeap h, TreeNode n) {
		if (n != null) {
			addToHeap(h, n.left);
			h.insert(n.getData());
			addToHeap(h, n.right);
		}
	}		
}