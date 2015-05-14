package shake_n_bacon;

import java.io.IOException;

import providedCode.*;

/**
 * @author Joshua Malters, Morgan Evans
 * @UWNetID maltersj, mnevans
 * @studentID 1336144
 * @email maltersj@uw.edu
 * 
 *        TODO: REPLACE this comment with your own as appropriate.
 * 
 *        This should be done using a *SINGLE* iterator. This means only 1
 *        iterator being used in Correlator.java, *NOT* 1 iterator per
 *        DataCounter (You should call dataCounter.getIterator() just once in
 *        Correlator.java). Hint: Take advantage of DataCounter's method.
 * 
 *        Although you can share argument processing code with WordCount, it
 *        will be easier to copy & paste it from WordCount and modify it here -
 *        it is up to you. Since WordCount does not have states, making private
 *        method public to share with Correlator is OK. In general, you are not
 *        forbidden to make private method public, just make sure it does not
 *        violate style guidelines.
 * 
 *        Make sure WordCount and Correlator do not print anything other than
 *        what they are supposed to print (e.g. do not print timing info, input
 *        size). To avoid this, copy these files into package writeupExperiment
 *        and change it there as needed for your write-up experiments.
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
		
		// IMPORTANT: Do not change printing format. Just print the double.
		System.out.println(variance);
	}

}
