
import java.io.IOException;

/**
 * Class which computes the cosine similarity of two documents.
 * 
 * @author bruce
 *
 */
public class CosineSimilarity {

    /**
     * This calculates the cosine similarity of two documents.
     * 
     * @param vectorA The vector of tf-idfs for document 1.
     * @param vectorB The vector of tf-idfs for document 2.
     * @return The cosine similarity of two documents.
     * @throws IOException Catch any file errors.
     */
    public static double cosineSimilarity(String doc1, String doc2) throws IOException {

        // First get the tf-idf vectors for doc1 and doc2.
        TFIDFCalc tfidfObj = TFIDFCalc.runTFIDFCalc(doc1, doc2);
        
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        
        // Then compute the cosine similarity between the documents.
        for (String word : tfidfObj.vectorA.keySet()) {
            dotProduct += tfidfObj.vectorA.get(word) * tfidfObj.vectorB.get(word);
            normA += Math.pow(tfidfObj.vectorA.get(word), 2);
            normB += Math.pow(tfidfObj.vectorB.get(word), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
