package shake_n_bacon;

import java.io.IOException;

import providedCode.*;

/**
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144, 1124703
 * @email maltersj@uw.edu, mnevans@uw.edu
 * 
 * This class determines the Euclidean distance of the relative frequencies
 * of words in two separate files. Can be used to determine if two works are
 * written by the same author.
 */
public class Correlator {
	private static int countWords(String file, DataCounter counter) {
		int totalWords = 0;
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				totalWords++;
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
		return totalWords;
	}

	/*
	 * Print error message and exit
	 */
	private static void usage() {
		System.err
		.println("Usage: [-s | -o] <filename> <filename>");
		System.err.println("-s for hashtable with separate chaining");
		System.err.println("-o for hashtable with open addressing");
		System.exit(1);
	}

	/**
	 * Entry of the program
	 * 
	 * @param args
	 *            the input arguments of this program
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			usage();
		}

		String firstArg = args[0].toLowerCase();
		DataCounter firstCounter = null;
		DataCounter secondCounter = null;
		if (firstArg.equals("-s")) {
			firstCounter = new HashTable_SC(new StringComparator(),
					new StringHasher());
			secondCounter = new HashTable_SC(new StringComparator(),
					new StringHasher());
		} else if (firstArg.equals("-o")) {
			firstCounter = new HashTable_OA(new StringComparator(),
					new StringHasher());
			secondCounter = new HashTable_OA(new StringComparator(),
					new StringHasher());
		} else {
			usage();
		}
		
		int firstTotal = countWords(args[1], firstCounter);
		int secondTotal = countWords(args[2], secondCounter);
		SimpleIterator itr = firstCounter.getIterator();
		double variance = 0.0;
		
		/*
		 * Takes the difference of the normalized frequencies for every word 
		 * that appears in both documents, squares that difference, and adds 
		 * the result to a running sum. 
		 */
		while (itr.hasNext()) {
			DataCount dataCount = itr.next();
			if (secondCounter.getCount(dataCount.data) > 0) {
				double firstFreq = (double) dataCount.count / (double) firstTotal;
				double secondFreq = (double) secondCounter.getCount(dataCount.data) 
						/ (double) secondTotal;
				if ((firstFreq < 0.01 && firstFreq > 0.0001) &&
						(secondFreq < 0.01 && secondFreq > 0.0001)) {
					variance += Math.pow(firstFreq - secondFreq, 2.0);
				}
			}
		}
		
		System.out.println(variance);
	}

}
