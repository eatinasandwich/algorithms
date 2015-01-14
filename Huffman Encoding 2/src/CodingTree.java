import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * This class compiles codes for compressing text files.
 * It uses a huffman tree to generate the bit strings.
 *
 */
public class CodingTree {
	
	/**
	 * Codes for each word to be compressed.
	 */
	public MyHashTable<String, String> codes;
	
	/**
	 * The root of the huffman tree.
	 */
	private Node root;
	
	/**
	 * This private inner class is used to create nodes
	 * in the huffman tree.
	 * 
	 * @author t_money
	 *
	 */
	private class Node implements Comparable<Node> {
		
		/**
		 * The word or symbol.
		 */
		private String str;
		
		/**
		 * Frequency of use for this word or symbol.
		 */
		private int freq;
		
		/**
		 * The left child.
		 */
		public Node left;
		
		/**
		 * The right child.
		 */
		public Node right;
		
		/**
		 * Creates a new node with given word or symbol and frequency.
		 * Runtime O(1)
		 * 
		 * @param the_str The word or symbol.
		 * @param the_freq The frequency of use.
		 */
		public Node(final String the_str, final int the_freq) {
			str = the_str;
			freq = the_freq;
		}
		
		/**
		 * Compares current node to another node based on weight.
		 * Runtime: O(n)
		 * (the weight method runs in linear time)
		 * 
		 * @param n The node to be compared.
		 * @return 1 if this node has greater weight, 0 if weights are equal,
		 * -1 otherwise.
		 */
		public int compareTo(final Node n) {
			Node other = (Node) n;
			if (weight() > other.weight()) {
				return 1;
			} else if (weight() == other.weight()) {
				return 0;
			} else {
				return -1;
			}
		}
		
		/**
		 * Return the string representation of the node.
		 * 
		 * @return The string representation of the node.
		 */
		public String toString() {
			return str + " - " + freq;
		}
		
		/**
		 * Recursively finds the weight of the current node.
		 * 
		 * Runtime: O(n)
		 * 
		 * @return The weight.
		 */
		public int weight() {
			int w = freq;
			if (left != null) {
				w += left.weight();
			}
			if (right != null) {
				w += right.weight();
			}
			
			return w;
		}
	}
	
	/**
	 * Creates a new coding tree by reading in a text file
	 * and counting frequencies and creating a huffman tree
	 * using MyHashTable class.
	 * 
	 * Runtime: O(nlogn)
	 * 
	 * @param message The text to generate a coding tree for.
	 */
	public CodingTree(final String message) {
		int index = 0;
		MyHashTable<String, Integer> freq_map = new MyHashTable<String, Integer>(16384);
		codes = new MyHashTable<String, String>(16384); 
		root = null;
		StringBuilder sb = new StringBuilder();
		
		while (index < message.length()) { //count frequencies
			while (isWordChar(message.charAt(index))) {
				sb.append(message.charAt(index));
				index++;
				if (index == message.length()) {
					break;
				}
			}
			if (sb.length() == 0) {
				sb.append(message.charAt(index));
				index++;
			}
			String symbol = sb.toString();
			if (freq_map.containsKey(symbol)) {
				freq_map.put(symbol, freq_map.get(symbol) + 1);
			} else {
				freq_map.put(symbol, 1);
			}
			sb = new StringBuilder();
		}
		
		Iterator<String> itr = freq_map.keySet().iterator();
		PriorityQueue<Node> q = new PriorityQueue<Node>();
		
		while (itr.hasNext()) { //get nodes in priority queue
			String temp = itr.next();
			q.add(new Node(temp, freq_map.get(temp)));
		}
		merge(q); //create tree
		createCodes();
	}
	
	/**
	 * Determines if the given character is a valid character for
	 * a word as defined in the assignment guidelines.
	 * 
	 * Runtime: O(1)
	 * 
	 * @param ch Character to test.
	 * @return True if it is a valid word character, false otherwise.
	 */
	private boolean isWordChar(final Character ch) {
		if((ch.compareTo('A') >= 0 && ch.compareTo('Z') <= 0) || (ch.compareTo('a') >= 0 && ch.compareTo('z') <= 0) 
				|| (ch.compareTo('0') >= 0 && ch.compareTo('9') <= 0) || ch.equals('\'') || ch.compareTo('-') == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Merges the nodes together to create a huffman tree.
	 * 
	 * Runtime: Runtime: O(nlogn)
	 * @param q The priority queue of nodes to merge
	 */
	public void merge(final PriorityQueue<Node> q) {
		boolean merged = false;
		while (!merged) {
			Node temp1 = q.poll();
			if (!q.isEmpty()) {
				Node new_node = new Node("a", 0);
				Node temp2 = q.poll();
				new_node.left = temp1;
				new_node.right = temp2;
				q.add(new_node);
			} else {
				q.add(temp1);
				merged = true;
			}
		}
		
		root = q.poll();
	}
	
	/**
	 * Wrapper method to createCodes.
	 * 
	 * Runtime: O(1)
	 */
	public void createCodes() {
		createCodes(root, "");
	}
	
	/**
	 * Creates codes for the huffman tree where a left branch adds
	 * a 0 and a right branch adds a 1.
	 * 
	 * Runtime: O(n)
	 *
	 * @param current Current node being assessed.
	 * @param code String of 1's and 0's that led to the current node.
	 */
	public void createCodes(final Node current, final String code) {
		if (current.left != null) {
			createCodes(current.left, code + "0");
		}
		if (current.freq != 0) {
			codes.put(current.str, code);
		}
		if (current.right != null) {
			createCodes(current.right, code + "1");
		}
	}
}
