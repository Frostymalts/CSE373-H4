package writeupExperiment;

import shake_n_bacon.StringComparator;

/*
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144, 1124703
 * @email maltersj@uw.edu, mnevans@uw.edu
*/ 

public class StringComparatorTest {

	public static void main(String[] args) {
		StringComparator sc = new StringComparator();			
		String str1 = "cat1";
		String str2 = "cat";
		
		System.out.println("Java compare value = " + str1.compareTo(str2));
		System.out.println("sc value = " + sc.compare(str1, str2));
	}

}
