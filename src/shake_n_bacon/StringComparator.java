package shake_n_bacon;

import providedCode.*;

/**
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144, 1124703
 * @email maltersj@uw.edu, mnevans@uw.edu
 * 
 * Comparator class that compares two String objects, and
 * returns an integer indicating which String comes before the other
 * alphabetically. 
 * 
 */
public class StringComparator implements Comparator<String> {

	/*
	 * Compares two string objects and returns a value based of their characters
	 * @param s1 First String that will be used in the comparison
	 * @param s2 Second String that will be used in the comparison
	 * @return returns an integer value less than zero if the first string comes alphabetically before
	 * the second string, zero if the strings are alphabetically equal, greater than
	 * zero if the first string comes alphabetically after the second string.
	 */
	@Override
	public int compare(String s1, String s2) {
		int length = Math.min(s1.length(), s2.length());
		int i = 0;
		int dif = 0;
		
		while (length > 0) {
			dif += s1.charAt(i) - s2.charAt(i);
			i++;
			length--;
			if (dif != 0)
				return dif;
		}
		
		return s1.length() - s2.length();
	}
}
