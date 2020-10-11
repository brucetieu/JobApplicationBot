
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;


/**
 * Class which calculates the tf-idf of a document.
 * @author bruce
 *
 */
public class TFIDFCalc {

    /**
     * This gets the union of the two documents, and returns an ArrayList of just
     * unique words
     * 
     * @param resumeWords  The first list of words from the resume.
     * @param jobDescWords The second list of words from the job description.
     * @return an array list with unique words from both lists.
     */
    public List<String> getUnionOfLists(List<String> resumeWords, List<String> jobDescWords) {
        Set<String> set = new HashSet<>();
        set.addAll(resumeWords);
        set.addAll(jobDescWords);

        return new ArrayList<>(set);
    }

    /**
     * This keeps track of how many times the unique word shows up in each document.
     * 
     * @param set         The array list of unique words.
     * @param cleanedList The list of words generated from the resume or job
     *                    description.
     * @return a hashtable which counts the frequency of each unique word. Need one
     *         for the resume and another for the job description.
     */
    public Hashtable<String, Integer> countUniqueWords(List<String> set, List<String> cleanedList) {
        Hashtable<String, Integer> freqUniqueWords = new Hashtable<String, Integer>();

        // Initialize the value of each key to be 0.
        for (int i = 0; i < set.size(); i++) {
            freqUniqueWords.put(set.get(i), 0);
        }

        // Count how many times the word appears in the cleanedList, populate those
        // counts as values in the Hashtable.
        for (String word : cleanedList) {
            freqUniqueWords.put(word, freqUniqueWords.get(word) + 1);
        }

        return freqUniqueWords;

    }

    /**
     * This computes the term frequency of each word.
     * 
     * @param hash        The hashtable of the frequency of unique words in each
     *                    document.
     * @param listOfWords The list of words for respective documents (w/o union)
     * @return a term frequency hashtable.
     */
    public Hashtable<String, Double> computeTF(Hashtable<String, Integer> hash, List<String> listOfWords) {
        Hashtable<String, Double> tfHash = new Hashtable<String, Double>();
        // TF(t) = (Number of times term t appears in a document) / (Total number of
        // terms in the document).
        int termsInDoc = listOfWords.size();

        // Compute the term frequency of each word.
        for (String word : hash.keySet()) {
            tfHash.put(word, ((double) hash.get(word) / (double) termsInDoc));

        }

        return tfHash;
    }

    /**
     * This computes the inverse document frequency of a word.
     * 
     * @param countA Document 1
     * @param countB Document 2
     * @return The idf hashtable for both documents.
     */
    public Hashtable<String, Double> computeIDF(Hashtable<String, Double> countA, Hashtable<String, Double> countB) {
        // IDF(t) = log_e(Total number of documents / Number of documents with term t in
        // it).
        Hashtable<String, Double> idfHash = new Hashtable<String, Double>();
        int numOfDocuments = 2;
        int numDocWTermT = 0;

        for (String word : countA.keySet()) {
            if (countA.get(word) > 0 || countB.get(word) > 0) {
                numDocWTermT += 1;
                idfHash.put(word, (double) Math.log((double) numOfDocuments / (double) numDocWTermT));
                numDocWTermT = 0;
            }
        }

        return idfHash;
    }

    /**
     * Calculate the tf-idf of a document.
     * 
     * @param tf  The term frequency.
     * @param idf The inverse document frequency.
     * @return A hashtable with the tf-idf for each word in a document.
     */
    public Hashtable<String, Double> computeTFIDF(Hashtable<String, Double> tf, Hashtable<String, Double> idf) {
        Hashtable<String, Double> tfidfHash = new Hashtable<String, Double>();

        // tfidf(t, d, D) = tf(t,d) * idf(t, D).
        for (String word : tf.keySet()) {
            tfidfHash.put(word, tf.get(word) * idf.get(word));
        }
        return tfidfHash;

    }

}