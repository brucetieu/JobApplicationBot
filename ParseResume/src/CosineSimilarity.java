
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
     * @param doc1 The raw string contents of document 1.
     * @param doc2 The raw string contents of document 2.
     * @return The cosine similarity of two documents.
     * @throws IOException Catch any file errors.
     */
    public static double cosineSimilarity(String doc1, String doc2) throws IOException {

        // First, get the tf-idf TextDocuments.
        TFIDFCalc tfidfObj = new TFIDFCalc();
        TFIDFCalc runTFIDF = tfidfObj.runTFIDFCalc(doc1, doc2);

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // Then compute the cosine similarity between the documents.
        for (String word : runTFIDF.getVectorA().hashTable.keySet()) {
            dotProduct += runTFIDF.getVectorA().hashTable.get(word) * runTFIDF.getVectorB().hashTable.get(word);
            normA += Math.pow(runTFIDF.getVectorA().hashTable.get(word), 2);
            normB += Math.pow(runTFIDF.getVectorB().hashTable.get(word), 2);
        }
        return normA != 0 || normB != 0 ? dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)) : 0.0;
    }
}
