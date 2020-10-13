
import java.util.Hashtable;

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
     */
    public double cosineSimilarity(Hashtable<String, Double> vectorA, Hashtable<String, Double> vectorB) {

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (String word : vectorA.keySet()) {
            dotProduct += vectorA.get(word) * vectorB.get(word);
            normA += Math.pow(vectorA.get(word), 2);
            normB += Math.pow(vectorB.get(word), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
