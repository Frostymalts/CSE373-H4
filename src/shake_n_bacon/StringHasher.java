package shake_n_bacon;

import providedCode.Hasher;

/*
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144, 1124703
 * @email maltersj@uw.edu, mnevans@uw.edu
 * 
 * This class provides a method to hash a string to an integer
 * value.
 */
public class StringHasher implements Hasher {

	/**
	 * Returns an integer representation of the provided string.
	 * @param str String object that will be hashed
	 * @return integer representation of the hashed string
	 */
	@Override
	public int hash(String str) {
		int hash = 0;
		
		for (int i = 0; i < str.length(); i++) {
		  hash += str.charAt(i);
		}
		
		hash *= str.length();
		
		return hash;
	}
}
