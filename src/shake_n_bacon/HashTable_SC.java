package shake_n_bacon;

import providedCode.*;

/**
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144
 * @email maltersj@uw.edu
 * 
 *        TODO: Replace this comment with your own as appropriate.
 * 
 *        1. You may implement HashTable with separate chaining discussed in
 *        class; the only restriction is that it should not restrict the size of
 *        the input domain (i.e., it must accept any key) or the number of
 *        inputs (i.e., it must grow as necessary).
 * 
 *        2. Your HashTable should rehash as appropriate (use load factor as
 *        shown in the class).
 * 
 *        3. To use your HashTable for WordCount, you will need to be able to
 *        hash strings. Implement your own hashing strategy using charAt and
 *        length. Do NOT use Java's hashCode method.
 * 
 *        4. HashTable should be able to grow at least up to 200,000. We are not
 *        going to test input size over 200,000 so you can stop resizing there
 *        (of course, you can make it grow even larger but it is not necessary).
 * 
 *        5. We suggest you to hard code the prime numbers. You can use this
 *        list: http://primes.utm.edu/lists/small/100000.txt NOTE: Make sure you
 *        only hard code the prime numbers that are going to be used. Do NOT
 *        copy the whole list!
 * 
 *        TODO: Develop appropriate tests for your HashTable.
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
	private int primeIndex = 0;
	private int size;
	private Comparator c;
	private Hasher h;
	private HashNode[] table;
	private int[] primes = {101, 199, 401, 809, 1601, 3203, 6473,
			12043, 25037, 51001, 100057, 200003};

	public HashTable_SC(Comparator<String> c, Hasher h) {
		this.size = 0;
		this.c = c;
		this.h = h;
		this.table = new HashNode[primeIndex];
	}

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

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getCount(String data) {
		int index = h.hash(data) % table.length;
		HashNode node = findNode(index, data);
		if (node == null)
			return 0;
		return node.dataCount.count;
	}

	@Override
	public SimpleIterator getIterator() {
		SimpleIterator itr = new SimpleIterator() {
			private HashNode node = null;
			private int index;
			private int foundNodes;

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

			@Override
			public boolean hasNext() {
				return foundNodes <= size;
			}
		};
		return itr;
	}

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
	
	private void rehash() {
		SimpleIterator itr = getIterator();
		primeIndex++;
		HashNode[] biggerTable = new HashNode[primeIndex];
	
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