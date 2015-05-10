package writeupExperiment;

import shake_n_bacon.StringComparator;

public class StringComparatorTest {

	public static void main(String[] args) {
		StringComparator sc = new StringComparator();			
		String str1 = "cat";
		String str2 = "cd";
		
		System.out.println("Java compare value = " + str1.compareTo(str2));
		System.out.println("sc value = " + sc.compare(str1, str2));
	}

}
