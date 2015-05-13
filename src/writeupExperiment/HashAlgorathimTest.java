package writeupExperiment;

import providedCode.*;
import shake_n_bacon.*;


public class HashAlgorathimTest {

	public static void main(String[] args) {
		Hasher h = new StringHasher();
		Comparator c = new StringComparator();
		
		System.out.println(h.hash("1") % 101);
		System.out.println(h.hash("16") % 101);
		System.out.println(c.compare("0", "15"));
	}

}
