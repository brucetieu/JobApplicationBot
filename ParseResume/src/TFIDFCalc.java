import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Class which calculates the tf-idf of a document.
 * 
 * @author bruce
 *
 */
public class TFIDFCalc {

    private TextDocument _textDocumentA, _textDocumentB;

    /**
     * This initializes two TextDocument objects so that the tf-idf can be
     * calculated between them.
     * 
     * @param textDocumentA The first document.
     * @param textDocumentB The second document.
     * @throws IOException Catch any file errors.
     */
    public TFIDFCalc(TextDocument textDocumentA, TextDocument textDocumentB) throws IOException {
        _textDocumentA = textDocumentA;
        _textDocumentB = textDocumentB;
    }

    /**
     * This computes the term frequency of each word.
     * 
     * @param listOfWords The list of words for each document (w/o union).
     * @return A word frequency hash table.
     */
    private Hashtable<String, Double> computeTF(List<String> listOfWords) {

        Hashtable<String, Double> tfHash = new Hashtable<String, Double>();

        int termsInDoc = listOfWords.size();

        // Compute the term frequency of each word.
        // TF(t) = (Number of times term t appears in a document) / (Total number of
        // terms in the document).
        for (String word : _textDocumentA.getFrequencyByWord(listOfWords).keySet()) {
            tfHash.put(word, ((double) _textDocumentA.getFrequencyByWord(listOfWords).get(word) / (double) termsInDoc));

        }

        return tfHash;
    }

    /**
     * This computes the inverse document frequency of a word.
     * 
     * @param tfDoc1 The term frequency hash table for document A.
     * @param tfDoc2 The term frequency hash table for document B.
     * @return A idf hash table.
     */
    private Hashtable<String, Double> computeIDF() {

        Hashtable<String, Double> idfHash = new Hashtable<String, Double>();

        Hashtable<String, Double> wordFreqDocA = _textDocumentA
                .getFrequencyByWord(_textDocumentA.getWordsFromDocument());
        Hashtable<String, Double> wordFreqDocB = _textDocumentB
                .getFrequencyByWord(_textDocumentB.getWordsFromDocument());

        List<Hashtable<String, Double>> listOfHashes = new ArrayList<Hashtable<String, Double>>();

        listOfHashes.add(wordFreqDocA);
        listOfHashes.add(wordFreqDocB);

        int numOfDocuments = 2;

        for (String word : listOfHashes.get(0).keySet()) {
            idfHash.put(word, 0.0);
        }

        for (Hashtable<String, Double> hashtable : listOfHashes) {
            for (String word : hashtable.keySet()) {
                if (hashtable.get(word) > 0) {
                    idfHash.put(word, (double) idfHash.get(word) + 1);
                }
            }
        }

        for (String word : idfHash.keySet()) {
            idfHash.put(word, (double) Math.log((double) numOfDocuments / (double) idfHash.get(word)));
        }

        return idfHash;
    }

    /**
     * Calculate the tf-idf of a document.
     * 
     * @param tf  The term frequency hash table.
     * @param idf The inverse document frequency hash table.
     * @return A tf-idf hash table.
     * 
     */
    private Hashtable<String, Double> computeTFIDF(Hashtable<String, Double> tf) {
        Hashtable<String, Double> tfidfHash = new Hashtable<String, Double>();

        // tfidf(t, d, D) = tf(t,d) * idf(t, D).
        for (String word : tf.keySet()) {
            tfidfHash.put(word, tf.get(word) + (tf.get(word) * computeIDF().get(word)));
        }

        return tfidfHash;
    }

    /**
     * This method executes all previous methods in the class.
     * 
     * @return A TFIDFResultContainer which passes two hash tables.
     * @throws IOException Checks if there are any file reading errors.
     * 
     */
    public TFIDFResultContainer runTFIDFCalc() throws IOException {

        // Compute the term frequency of each document.
        Hashtable<String, Double> tfDocA = computeTF(_textDocumentA.getWordsFromDocument());
        Hashtable<String, Double> tfDocB = computeTF(_textDocumentB.getWordsFromDocument());

        // Create tf-idf vectors of each document.
        Hashtable<String, Double> tfidfDocA = computeTFIDF(tfDocA);
        Hashtable<String, Double> tfidfDocB = computeTFIDF(tfDocB);

        return new TFIDFResultContainer(tfidfDocA, tfidfDocB);

    }

}
