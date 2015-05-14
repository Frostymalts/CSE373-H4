package writeupExperiment;

import java.io.IOException;

import providedCode.FileWordReader;
import providedCode.Hasher;
import shake_n_bacon.StringHasher;

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
//				h.hash(word);
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
