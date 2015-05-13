/**
 * @author Morgan Evans, Joshua Malters
 * @UWNetID mnevans, 
 * @studentID 1124703, 
 * @email mnevans@uw.edu, 
 */ 

package writeupExperiment;

import providedCode.*;
import shake_n_bacon.*;

public class HasherTest {
	public static void main(String[] args) {

		Comparator c = new StringComparator();
		Hasher h = new StringHasher();

		DataCounter sc = new HashTable_SC(c, h);
		DataCounter oa = new HashTable_OA(c, h);

// 		System.out.println("Separate Chaining tests:");
// 		testSize(sc);
// 		testSize(sc);
//       testCount(sc);
//       testIterator(sc);

		System.out.println("Open Addressing tests:");
		testSize(oa);
		testSize(oa);
      testCount(oa);
		testIterator(oa);
	}

	public static void testSize(DataCounter table) {
		System.out.println("Inserting 20000 elements.");
		for (int i = 0; i < 20000; i++) {
			table.incCount("" + i);
		}
		
		System.out.println("Reported size = " + table.getSize());
		if (table.getSize() == 20000) {
			System.out.println("Size test passed.");
		} else {
			System.out.println("Incorrect size.");
		}
	}

	public static void testCount(DataCounter table) {
		if (table.getCount("0") == 2) {
			System.out.println("Single increment count test passed.");
		} else {
			System.out.println("Incorrect count.");
		}

		for (int i = 0; i < 50; i++) {
			table.incCount("" + i);
		}

		boolean flag = true;
		for (int i = 0; i < 50; i++) {
			if (table.getCount("" + i) != 3) {
				flag = false;
            System.out.println(table.getCount("" + i));
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
         itr.next();
         i++;
      }
      System.out.println("Iterated through " + i + " elements.");
   } 
}