package shake_n_bacon;

import providedCode.*;

/**
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144, 1124703
 * @email maltersj@uw.edu, mnevans@uw.edu
 * 
 * This HashTable class extends DataCounter to be used as a container for
 * DataCount objects. It utilizes a hashing sequence and quadratic probing
 * to store the data. The hashtable will expand if the load factor exceeds 0.5.
 * Has its own iterator that can be returned.
 */
public class HashTable_OA extends DataCounter {
	private DataCount[] table;
	private int primeIndex;
	private int[] primes = {101, 199, 401, 809, 1601, 3203, 6473,
			12043, 25037, 51001, 100057, 200003, 400009, 800311};
	private Comparator<String> c;
	private Hasher h;
	private int size;


	public HashTable_OA(Comparator<String> c, Hasher h) {
		this.table = new DataCount[primes[primeIndex]];
		this.c = c;
		this.h = h;
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
		int index = findPos(data, table);

		if (table[index] == null) {
			table[index] = new DataCount(data, 1);
			size++;
		} else {
			table[index].count++;
		}

		if ((double) size > (double) table.length / 2.0)
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
		int index = findPos(data, table);
		if (table[index] == null)
			return 0;
		return table[index].count;
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
			private int index = -1;
			private int foundElements;

			/*
			 * Returns the next available DataCount object.
			 * @return the next available DataCount object in the hashtable.
			 */
			@Override
			public DataCount next() {
				if (!hasNext())
					return null;

				index++;
				while(table[index] == null)
					index++;
				foundElements++;
				return table[index];
			}

			/*
			 * Returns whether or not there exists another DataCount object.
			 * @return returns a boolean of whether or not there exists another
			 * DataCount object in the hashtable.
			 */
			@Override
			public boolean hasNext() {
				return foundElements < size;
			}
		};
		return itr;
	}

	/*
	 * Expands the table size to the nearest prime thats twice the size
	 * of the prexisting size. Also relocates the DataCounts to their new
	 * index locations.
	 */
	private void rehash() {
		primeIndex++;
		DataCount[] biggerTable = new DataCount[primes[primeIndex]];

		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				DataCount dataCount = table[i];
				int index = findPos(dataCount.data, biggerTable);
				biggerTable[index] = dataCount;
			}
		}
		table = biggerTable;
	}

	/*
	 * Returns the index of the given String object in the given array. If
	 * the string does not exist in the array, returns the index that the
	 * string should be hashed to.
	 */
	private int findPos(String data, DataCount[] array) {
		int i = 0;
		int index = h.hash(data) % array.length;
		int tempIndex = 0;

		while(true) {
			try {
				tempIndex = (int) (index + Math.pow(i, 2));
				if (tempIndex >= array.length)
					tempIndex -= array.length;
				if (tempIndex < 0)
				     tempIndex += array.length;
				DataCount dataCount = array[tempIndex];
				if (dataCount == null) {
					return tempIndex;
				} else if (c.compare(dataCount.data, data) == 0) {
					return tempIndex;
				}
				i++;
			} catch (Exception e) {
				System.out.println("data = " + data);
				System.out.println("hash = " + h.hash(data));
				System.out.println("i = " + i);
				System.out.println("index = " + index);
				System.out.println("tempIndex = " + tempIndex);
				System.out.println("tableSize = " + array.length);
				System.exit(1);
			}
		}
	}
}