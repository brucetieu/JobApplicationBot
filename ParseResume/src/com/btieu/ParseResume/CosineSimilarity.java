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
     * @param docA The TextDocument object storing the text of the first document.
     * @param docA The TextDocument object storing the text of the second document.
     * @return The cosine similarity of two documents.
     * @throws IOException Catch any file errors.
     */
    public static double cosineSimilarity(TextDocument docA, TextDocument docB) throws IOException {

        // First, get the tf-idf of the TextDocuments.
        TFIDFCalc tfidfCalc = new TFIDFCalc(docA, docB);
        TFIDFResultContainer runTFIDF = tfidfCalc.runTFIDFCalc();

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // Then compute the cosine similarity between the documents.
        for (String word : runTFIDF.getTFIDFHashDocA().keySet()) {
            dotProduct += runTFIDF.getTFIDFHashDocA().get(word) * runTFIDF.getTFIDFHashDocB().get(word);
            normA += Math.pow(runTFIDF.getTFIDFHashDocA().get(word), 2);
            normB += Math.pow(runTFIDF.getTFIDFHashDocB().get(word), 2);
        }
        return normA != 0 || normB != 0 ? dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)) : 0.0;
    }
}
