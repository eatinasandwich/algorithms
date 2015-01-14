import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class for a hash table.
 * This is used as a frequency map in the CodingTree class.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class MyHashTable<K, V> {
	
	/**
	 * Number of buckets.
	 */
	private int capacity;
	
	/**
	 * Half of the capacity. Kept so it need not be recalculated.
	 */
	private int half_cap;
	
	/**
	 * List of buckets to put key value pairs in.
	 */
	private ArrayList<Bucket> buckets;
	
	/**
	 * Buckets with stuff in them.
	 */
	private int entries;
	
	/**
	 * Inner class for elements in the list of buckets.
	 * Primarily used to hold key value pairs.
	 * Uses chaining for collisions.
	 */
	private class Bucket {
		K key;
		V value;
		Bucket next;
		
		/**
		 * Creates a new bucket with given key and value pair.
		 * 
		 * Runtime: O(1)
		 * 
		 * @param key The key
		 * @param val The value
		 */
		public Bucket(final K key, final V val) {
			this.key = key;
			value = val;
			next = null;
		}
		
		/**
		 * Creates a string representation of the bucket.
		 * 
		 * Runtime: O(n)
		 * (where n is the number of entries in the bucket)
		 * 
		 * @return String representation of the bucket.
		 */
		public String toString() {
			Bucket current = next;
			StringBuilder sb = new StringBuilder();
			sb.append(key + " = " + value + "\n");
			while (current != null) {
				sb.append(current.key + " = " + current.value + "\n");
				current = current.next;
			}
			return sb.toString();
		}
	}
	
	/**
	 * Creates a new hash table with given capacity.
	 * 
	 * Runtime: O(n)
	 * (where n is the given initial capacity)
	 * 
	 * @param the_capacity The capacity of the hash table.
	 */
	public MyHashTable(final int the_capacity) {
		this.capacity = the_capacity;
		this.half_cap = the_capacity / 2;
		buckets = new ArrayList<Bucket>(capacity);
		for (int i = 0; i < capacity; i++) {
			buckets.add(null);
		}
		entries = 0;
	}
	
	/**
	 * Puts a new key value pair in the hash table.
	 * 
	 * Runtime: O(1)
	 * 
	 * @param key The key for the new entry.
	 * @param value The value for the new entry.
	 */
	public void put(final K key, final V value) {
		boolean placed = false;
		int hash = hash(key);
		if (buckets.get(hash) == null) {
			buckets.set(hash, new Bucket(key, value));
			entries++;
		} else {
			Bucket current = buckets.get(hash);
			if (current.key.equals(key)) {
				buckets.set(hash, new Bucket(key, value));
				if (current.next != null) {
					buckets.get(hash).next = current.next;
				}
			}
			Bucket previous = current;
			current = current.next;
			while (current != null) {
				if (current.key.equals(key)) {
					placed = true;
					previous.next = new Bucket(key, value);
					if (current.next != null) {
						previous.next.next = current.next;
					}
					break;
				}
				current = current.next;
				previous = previous.next;
			}
			if (!placed) {
				previous.next = new Bucket(key, value);
				entries++;
			}
			
			
		}
	}
	
	/**
	 * Returns the value associated with the given key.
	 * 
	 * Runtime: O(1)
	 * 
	 * @param key The key to look up.
	 * @return The value associated with the key.
	 */
	public V get(final K key) {
		int hash = hash(key);
		Bucket current;
		if (buckets.get(hash) == null) {
			return null;
		} else {
			current = buckets.get(hash);
			while (current.next != null && !(current.key.equals(key))) {
				current = current.next;
			}
		}
		return current.value;
	}
	
	/**
	 * Hashes the given key.
	 * 
	 * Runtime: O(1);
	 * 
	 * @param key The key to hash.
	 * @return The integer hash of the key.
	 */
	private int hash(K key) {
		return key.hashCode() % half_cap + half_cap;
	}
	
	/**
	 * Generates a string representation of the hash table.
	 * 
	 * @return String representation of the hash table.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buckets.size(); i++) {
			if (buckets.get(i) == null) {
				continue;
			} else {
				sb.append(buckets.get(i).toString());
			}
		}
		return sb.toString();
	}
	
	/**
	 * Computes stats about the hash table and prints them to the screen.
	 * 
	 * Runtime: O(n)
	 */
	public void stats() {
		StringBuilder sb = new StringBuilder();
		sb.append("Hash Table Stats\n================\n");
		sb.append("Number of Entries: ");
		sb.append(entries);
		sb.append('\n');
		sb.append("Number of Buckets: ");
		sb.append(capacity);
		sb.append('\n');
		ArrayList<Integer> bucket_sizes = bucketSizes();
		sb.append("Histogram of Bucket Sizes: ");
		sb.append(bucket_sizes.toString());
		sb.append('\n');
		sb.append("Fill Percentage: ");
		sb.append(100 * ((double) bucketsFilled() / (double) capacity));
		sb.append("%\n");
		sb.append("Average Non-Empty Bucket Size: ");
		sb.append(average(bucket_sizes));
		sb.append('\n');
		System.out.println(sb.toString());
	}
	
	/**
	 * Computes the sizes of the buckets.
	 * 
	 * Runtime: O(n)
	 * 
	 * @return A list with index associated with bucket sizes
	 * of that index (ie index 0 would have the number of buckets of size 0).
	 */
	private ArrayList<Integer> bucketSizes() {
		ArrayList<Integer> bucket_sizes = new ArrayList<Integer>();
		bucket_sizes.add(0);
		for (int i = 0; i < capacity; i++) {
			if (buckets.get(i) != null) {
				Bucket current = buckets.get(i);
				int number = 1;
				while (current.next != null) {
					number++;
					current = current.next;
				}
				while (number >= bucket_sizes.size()) {
					bucket_sizes.add(0);
				}
				bucket_sizes.set(number, bucket_sizes.get(number) + 1);
			} else {
				bucket_sizes.set(0, bucket_sizes.get(0) + 1);
			}
		}
		return bucket_sizes;
	}
	
	/**
	 * Calculates the amount of buckets that actually have stuff in them.
	 * 
	 * Runtime: O(n)
	 * 
	 * @return The number of buckets filled.
	 */
	private int bucketsFilled() {
		int j = 0;
		for (int i = 0; i < buckets.size(); i++) {
			if (buckets.get(i) != null) {
				j++;
			}
		}
		return j;
	}
	
	/**
	 * Calculates the average non-empty bucket size.
	 * 
	 * Runtime: O(n);
	 * (where n is the size of the list)
	 * 
	 * @param bucket_sizes Histogram of the bucket sizes.
	 * @return Average non-empty bucket size.
	 */
	private double average(final ArrayList<Integer> bucket_sizes) {
		int total = 0;
		for (int i = 0; i < bucket_sizes.size(); i++) {
			total += (i + 1) * bucket_sizes.get(i);
		}
		return (double) total / (double) capacity;
	}
	
	/**
	 * Finds whether or not the given key is in the hash table or not.
	 * 
	 * Runtime: O(1)
	 * 
	 * @param key Key to try to find.
	 * @return True if the map contains the given key, false otherwise.
	 */
	public boolean containsKey(final K key) {
		int hash = hash(key);
		Bucket current = buckets.get(hash);
		while (current != null) {
			if (current.key.equals(key)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	/**
	 * Constructs a set of all keys in the hash table.
	 * 
	 * Runtime: O(n)
	 * 
	 * @return Set of all keys.
	 */
	public HashSet<K> keySet() {
		HashSet<K> set = new HashSet<K>();
		for (int i = 0; i < capacity; i++) {
			Bucket current = buckets.get(i);
			while (current != null) {
				set.add(current.key);
				current = current.next;
			}
		}
		return set;
	}
	
	
	
	
	
	
}
