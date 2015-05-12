package shake_n_bacon;

import providedCode.Hasher;

/**
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144
 * @email maltersj@uw.edu>
 * 
 * This class provides a method to hash a string to an integer
 * value.
 */
public class StringHasher implements Hasher {

	/**
	 * Returns an integer representation of the provided string.
	 * @param str String object that will be hashed
	 * @return integer representation of the hased string
	 */
	@Override
	public int hash(String str) {
		int hashVal = 0;
		int tableSize = 26053; //arbitrary primary number since table size is unavailable. 
		
		for (int i = 0; i < str.length(); i++)
			hashVal = 37 * hashVal + str.charAt(i);
		
		return hashVal;
	}
}
