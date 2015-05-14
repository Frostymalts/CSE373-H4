package writeupExperiment;

import providedCode.*;
import shake_n_bacon.*;

/*
 * @author Morgan Evans, Joshua Malters
 * @UWNetID mnevans, maltersj
 * @studentID 1124703, 1336144
 * @email mnevans@uw.edu, maltersj@uw.edu
 * 
 * This class provides test for the individual methods for both Seperate Chaining
 * and Open Addressing HashTables.
 */ 
public class HasherTest {
	public static void main(String[] args) {

		Comparator c = new StringComparator();
		Hasher h = new StringHasher();

		DataCounter sc = new HashTable_SC(c, h);
		DataCounter oa = new HashTable_OA(c, h);

		System.out.println("Separate Chaining tests:");
		testSize(sc);
		testSize(sc);
		testCount(sc);
		testIterator(sc);

//		System.out.println("Open Addressing tests:"); // uncomment to test for open addressing
//		testSize(oa);
//		testSize(oa);
//		testIterator(oa);
	}

	public static void testSize(DataCounter table) {
		int elements = 20000;
		System.out.println("Inserting " + elements + " elements.");
		for (int i = 1; i <= elements; i++) {
			table.incCount("" + i);
		}

		System.out.println("Reported size = " + table.getSize());
		if (table.getSize() == elements) {
			System.out.println("Size test passed.");
		} else {
			System.out.println("Incorrect size.");
		}
	}

	public static void testCount(DataCounter table) {
		if (table.getCount("1") == 2) {
			System.out.println("Single increment count test passed.");
		} else {
			System.out.println("Incorrect count.");
		}

		for (int i = 1; i <= 50; i++) {
			table.incCount("" + i);
		}

		boolean flag = true;
		for (int i = 1; i <= 50; i++) {
			if (table.getCount("" + i) != 3) {
				flag = false;
			}
		}

		if (flag) {
			System.out.println("Multiple increment count test passed.");
		} else {
			System.out.println("Incorrect count.");
		}     
	}

	public static void testIterator(DataCounter table) {
		SimpleIterator itr = table.getIterator();
		int i = 0;
		while (itr.hasNext()) {
			DataCount dataCount = itr.next();
			//			System.out.println("data = " + dataCount.data);
			//			System.out.println("count = " + dataCount.count);
			i++;
		}
		System.out.println("Iterated through " + i + " elements.");
	} 
}