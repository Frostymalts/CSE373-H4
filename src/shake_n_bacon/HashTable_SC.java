package shake_n_bacon;

import providedCode.*;

/**
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144
 * @email maltersj@uw.edu
 * 
 * This HashTable class extends DataCounter to be used as a container for
 * DataCount objects. It utilizes a hashing sequence and separate chaining
 * to store the data. The hashtable will expand if the load factor exceeds 1.
 * Has its own iterator that can be returned.
 */

class HashNode {
	public DataCount dataCount;
	public HashNode next;

	public HashNode(DataCount dataCount, HashNode node) {
		this.dataCount = dataCount;
		this.next = node;
	}
}

public class HashTable_SC extends DataCounter {
	private int primeIndex;
	private int size;
	private Comparator<String> c;
	private Hasher h;
	private HashNode[] table;
	private int[] primes = {101, 199, 401, 809, 1601, 3203, 6473,
			12043, 25037, 51001, 100057, 200003};

	public HashTable_SC(Comparator<String> c, Hasher h) {
		this.size = 0;
		this.c = c;
		this.h = h;
		this.table = new HashNode[primes[primeIndex]];
	}

	/*
	 * Insert operation. If the string already exists within the hashtable,
	 * then the relevant DataCount will increase the count of that string
	 * by one.
	 * @param data The String that is to be inserted into the hashtable or
	 * incremented.
	 */
	@Override
	public void incCount(String data) {
		int index = h.hash(data) % table.length;

		if (table[index] == null) {
			table[index] = new HashNode(new DataCount(data, 1), null);
			size++;
		} else {
			HashNode node = findNode(index, data);
			if (node == null) {
				node = new HashNode(new DataCount(data, 1), null);
				size++;
			} else {
				node.dataCount.count++;
			}
		}
		
		if ( (double) size / (double) table.length > 1)
			rehash();
	}

	/*
	 * @return the current number of unique words in the table
	 */
	@Override
	public int getSize() {
		return size;
	}

	/*
	 * @return the number of times a String has been inserted
	 * into the hashtable
	 */
	@Override
	public int getCount(String data) {
		int index = h.hash(data) % table.length;
		HashNode node = findNode(index, data);
		if (node == null)
			return 0;
		return node.dataCount.count;
	}

	/*
	 * Provides a SimpleIterator object to the client.
	 * Do not increment or insert strings until the the use of
	 * the iterator has terminated.
	 * @return SimpleIterator object.
	 */
	@Override
	public SimpleIterator getIterator() {
		SimpleIterator itr = new SimpleIterator() {
			private HashNode node = null;
			private int index;
			private int foundNodes;
			
			/*
			 * Returns the next available DataCount object.
			 * @return the next available DataCount object in the hashtable.
			 */
			@Override
			public DataCount next() {
				if (node != null && node.next != null) {
					node = node.next;
					foundNodes++;
					return node.dataCount;
				}
				while (index < table.length) {
					if (table[index] != null) {
						node = table[index];
						foundNodes++;
						return node.dataCount;
					}
					index++;
				}
				return null;
			}
			
			/*
			 * Returns whether or not there exists another DataCount object.
			 * @return returns a boolean of whether or not there exists another
			 * DataCount object in the hashtable.
			 */
			@Override
			public boolean hasNext() {
				return foundNodes < size;
			}
		};
		return itr;
	}
	
	/*
	 * Given an index and a String, will search the list at the given index
	 * to determine if the corresponding DataCount to that String exists.
	 * Returns null if the end of the list is reached without finding a match.
	 */
	private HashNode findNode(int index, String data) {
		HashNode node = table[index];
		while (node != null) {		
			DataCount dataCount = node.dataCount;
			if (c.compare(dataCount.data, data) == 0) {
				return node;
			} else
				node = node.next;	
		}	
		return node;
	}
	
	/*
	 * Expands the table size to the nearest prime thats twice the size
	 * of the prexisting size. Also relocates the DataCounts to their new
	 * index locations.
	 */
	private void rehash() {
		SimpleIterator itr = getIterator();
		primeIndex++;
		HashNode[] biggerTable = new HashNode[primes[primeIndex]];
	
		while(itr.hasNext()){
			DataCount dataCount = itr.next();
			int index = h.hash(dataCount.data) % biggerTable.length;
			if (biggerTable[index] == null)
				biggerTable[index] = new HashNode(dataCount, null);
			else {
				HashNode node = biggerTable[index];
				while(node.next != null)
					node = node.next;
				node.next = new HashNode(dataCount, null);
			}
		}
		table = biggerTable;
	}

}