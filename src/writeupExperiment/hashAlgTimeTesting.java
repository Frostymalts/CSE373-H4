package writeupExperiment;

import java.io.IOException;

import providedCode.FileWordReader;
import providedCode.Hasher;
import shake_n_bacon.StringHasher;

/*
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144, 1124703
 * @email maltersj@uw.edu, mnevans@uw.edu
 * 
 * This class tests the run time of the hash algorithm used in the hashtables.
*/ 

public class hashAlgTimeTesting {

	public static void main(String[] args) {
		Hasher h = new StringHasher();
		String file = "hamlet.txt";
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			long elapsedTime = 0;

			while (word != null) {
				long startTime = System.nanoTime();
//				h.hash(word); // uncomment this and comment the next 4 lines to switch hashes
				int hash = 0;
				for (int i = 0; i < word.length(); i++) {
					hash += 37 * word.charAt(i);
				}
				long endTime = System.nanoTime();
				elapsedTime += endTime - startTime;
				word = reader.nextWord();
			}
			System.out.println(elapsedTime); 
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
	}

}
