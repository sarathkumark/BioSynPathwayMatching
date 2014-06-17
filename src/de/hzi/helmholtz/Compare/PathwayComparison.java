/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hzi.helmholtz.Compare;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import de.hzi.helmholtz.Genes.Gene;
import de.hzi.helmholtz.Genes.GeneSimilarity;
import de.hzi.helmholtz.Pathways.Pathway;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author skondred
 */
public class PathwayComparison {

    private static double finalscore = 0;
    static double s_add = 1.0, s_penality = -1.0;
    static double SCORE_MISMATCH_ERROR = 0.2f;
    JFrame frame;
    //Map<Integer, List<String>> Qmap = new HashMap<Integer, List<String>>();
    //Map<Integer, List<String>> Tmap = new HashMap<Integer, List<String>>();
    private Pathway source;
    private Pathway target;
    private Map<Integer, Integer> srcGeneIdToPositionMap;
    private Map<Integer, Integer> tgtGeneIdToPositionMap;
    // Range of window sizes to be considered for combining domains into single genes --> [1,maxWindowSize]
    private int maxWindowSize;
    private double functionWeight;
    private double statusWeight;
    private double substrateWeight;
    private int GENE_MAX_DISTANCE = 1000;

    public PathwayComparison(Pathway source, Pathway target) {
        this.source = source;
        this.target = target;
        constructBiMaps();
        frame = new JFrame("Matching");
    }

    /* Construct position to geneId bimaps of both source and target pathways */
    private void constructBiMaps() {
        srcGeneIdToPositionMap = new TreeMap<Integer, Integer>();
        int temp = 0;
        for (Gene e : source.getGenes()) {
            srcGeneIdToPositionMap.put(e.getGeneId(), temp++);
        }
        tgtGeneIdToPositionMap = new TreeMap<Integer, Integer>();
        temp = 0;
        for (Gene e : target.getGenes()) {
            tgtGeneIdToPositionMap.put(e.getGeneId(), temp++);
        }
    }

    public void display() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(1400, 200);
        //frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /* Iterate over source genes
     * Compare each source gene against every target gene
     * Keep the comparison scores in forwardScores
     * Keep the best forward scores for gene-gene in forwardBestScores
     * Do the same in the reverse direction
     */
    /*public Multimap<Integer, Multimap<Double, String>> pcompare(Pathway firstPathway, Pathway secondPathway) {

     Multimap<Double, String> forwardScores = ArrayListMultimap.create();
     Multimap<Integer, Multimap<Double, String>> forwardBestScores = ArrayListMultimap.create();

     int currentQueryGene = 0;
     Iterator<Gene> sourceGeneIt = firstPathway.geneIterator(); // for reverse scoring, this becomes targetGeneIt
     while (sourceGeneIt.hasNext()) {
     Gene queryGene = sourceGeneIt.next();
     currentQueryGene++;

     // clear forward scores after one gene is done
     forwardScores.clear();

     Iterator<Gene> targetGeneIt = secondPathway.geneIterator();
     int currentTargetGene = 0;
     while (targetGeneIt.hasNext()) {
     Gene targetGene = targetGeneIt.next();
     currentTargetGene++;

     GeneSimilarity geneSimilarity = new GeneSimilarity();
     if (queryGene.size() == targetGene.size()) {
     // Match one source gene against one target gene with the same index
     List<Gene> targetGenes = new ArrayList<Gene>();
     targetGenes.add(targetGene);
     double score = geneSimilarity.levenshteinSimilarity(queryGene, targetGenes, functionWeight, statusWeight, substrateWeight);
     forwardScores.put(score, currentTargetGene + "");
     } else if (queryGene.size() < targetGene.size()) {
     // Merge multiple source genes and compare to one target gene
     double highestScoreInAllWindows = 0.0f;
     int highestScoringWindow = 0;
     for (int currentWindowSize = 1; currentWindowSize < maxWindowSize; currentWindowSize++) {
     if (currentQueryGene + 1 < firstPathway.size()) {
     // construct list of source genes to compare, list size = currentWindowSize
     List<Gene> mergedGenes = new ArrayList<Gene>();
     List<Gene> sourceGenesList = firstPathway.getGenes();
     for (int i = currentQueryGene; i <= currentWindowSize; i++) {
     mergedGenes.add(sourceGenesList.get(i));
     }
     double score = geneSimilarity.levenshteinSimilarity(targetGene, mergedGenes, functionWeight, statusWeight, substrateWeight);
     if (Math.abs(score) > Math.abs(highestScoreInAllWindows)) {
     highestScoreInAllWindows = score;
     highestScoringWindow = currentWindowSize;
     }
     }
     }
     if (highestScoreInAllWindows < 0) {
     String combinedGenes = "";
     for (int i = currentQueryGene; i < currentQueryGene + highestScoringWindow; i++) {
     combinedGenes += i + "+";
     }
     combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
     forwardScores.put(highestScoreInAllWindows, combinedGenes);
     } else {
     String combinedGenes = "";
     for (int i = currentQueryGene + highestScoringWindow - 1; i > currentQueryGene; i--) {
     combinedGenes += i + "+";
     }
     combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
     forwardScores.put(highestScoreInAllWindows, combinedGenes);
     }
     } else {
     // Merge multiple target genes and compare to one source gene
     double highestScoreInAllWindows = 0.0f;
     int highestScoringWindow = 0;
     for (int currentWindowSize = 1; currentWindowSize < maxWindowSize; currentWindowSize++) {
     if (currentTargetGene + 1 < secondPathway.size()) {
     // construct list of target genes to compare, list size = currentWindowSize
     List<Gene> mergedGenes = new ArrayList<Gene>();
     List<Gene> targetGenesList = secondPathway.getGenes();
     for (int i = currentTargetGene; i <= currentWindowSize; i++) {
     mergedGenes.add(targetGenesList.get(i));
     }
     double score = geneSimilarity.levenshteinSimilarity(queryGene, mergedGenes, functionWeight, statusWeight, substrateWeight);
     if (Math.abs(score) > Math.abs(highestScoreInAllWindows)) {
     highestScoreInAllWindows = score;
     highestScoringWindow = currentWindowSize;
     }
     }
     }
     if (highestScoreInAllWindows < 0) {
     String combinedGenes = "";
     for (int i = currentTargetGene; i < currentTargetGene + highestScoringWindow; i++) {
     combinedGenes += i + "+";
     }
     combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
     forwardScores.put(highestScoreInAllWindows, combinedGenes);
     } else {
     String combinedGenes = "";
     for (int i = currentTargetGene + highestScoringWindow - 1; i > currentTargetGene; i--) {
     combinedGenes += i + "+";
     }
     combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
     forwardScores.put(highestScoreInAllWindows, combinedGenes);
     }
     }
     }
     Multimap<Double, String> forwardscore1 = ArrayListMultimap.create(forwardScores);
     TreeMultimap<Double, String> forwardscore = TreeMultimap.create(Ordering.natural().reverse(), Ordering.natural());
     forwardscore.putAll(forwardscore1);
     forwardBestScores.put(currentQueryGene, forwardscore);
     }
     System.out.println(forwardBestScores);
     return forwardBestScores;
     }*/
    public Multimap<Integer, Multimap<Double, String>> pcompare(Pathway firstPathway, Pathway secondPathway) {

        Multimap<Double, String> forwardScores = ArrayListMultimap.create();
        Multimap<Integer, Multimap<Double, String>> forwardBestScores = ArrayListMultimap.create();

        int currentQueryGene = 0;
        Iterator<Gene> sourceGeneIt = firstPathway.geneIterator(); // for reverse scoring, this becomes targetGeneIt
        while (sourceGeneIt.hasNext()) {
            Gene queryGene = sourceGeneIt.next();
            currentQueryGene++;

            // clear forward scores after one gene is done
            forwardScores.clear();

            Iterator<Gene> targetGeneIt = secondPathway.geneIterator();
            int currentTargetGene = 0;
            while (targetGeneIt.hasNext()) {
                Gene targetGene = targetGeneIt.next();
                currentTargetGene++;

                GeneSimilarity geneSimilarity = new GeneSimilarity();
                if (queryGene.size() == targetGene.size()) {
                    // Match one source gene against one target gene with the same index
                    List<Gene> targetGenes = new ArrayList<Gene>();
                    targetGenes.add(targetGene);
                    double score = geneSimilarity.levenshteinSimilarity(queryGene, targetGenes, functionWeight, statusWeight, substrateWeight);
                    forwardScores.put(score, currentTargetGene + "");
                } else if (queryGene.size() < targetGene.size()) {
                    // Merge multiple source genes and compare to one target gene
                    // store scores for windows of all sizes upto maxWindowSize
                    for (int currentWindowSize = 1; currentWindowSize < maxWindowSize; currentWindowSize++) {  
                        if (currentQueryGene + 1 < firstPathway.size()) {
                            // construct list of source genes to compare, list size = currentWindowSize
                            List<Gene> mergedGenes = new ArrayList<Gene>();
                            List<Gene> sourceGenesList = firstPathway.getGenes();
                            for (int i = currentQueryGene; i <= currentWindowSize; i++) {
                                mergedGenes.add(sourceGenesList.get(i));
                            }
                            double score = geneSimilarity.levenshteinSimilarity(targetGene, mergedGenes, functionWeight, statusWeight, substrateWeight);
                            if (score < 0) {
                                String combinedGenes = "";
                                for (int i = currentQueryGene; i < currentQueryGene + currentWindowSize; i++) {
                                    combinedGenes += i + "+";
                                }
                                combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
                                forwardScores.put(Math.abs(score), combinedGenes);
                            } else {
                                String combinedGenes = "";
                                for (int i = currentQueryGene + currentWindowSize - 1; i > currentQueryGene; i--) {
                                    combinedGenes += i + "+";
                                }
                                combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
                                forwardScores.put(Math.abs(score), combinedGenes);
                            }
                        }
                    }
                } else {
                    // Merge multiple target genes and compare to one source gene
                    // store scores for windows of all sizes upto maxWindowSize
                    for (int currentWindowSize = 1; currentWindowSize < maxWindowSize; currentWindowSize++) {
                        if (currentTargetGene + 1 < secondPathway.size()) {
                            // construct list of target genes to compare, list size = currentWindowSize
                            List<Gene> mergedGenes = new ArrayList<Gene>();
                            List<Gene> targetGenesList = secondPathway.getGenes();
                            for (int i = currentTargetGene; i <= currentWindowSize; i++) {
                                mergedGenes.add(targetGenesList.get(i));
                            }
                            double score = geneSimilarity.levenshteinSimilarity(queryGene, mergedGenes, functionWeight, statusWeight, substrateWeight);
                            if (score < 0) {
                                String combinedGenes = "";
                                for (int i = currentTargetGene; i < currentTargetGene + currentWindowSize; i++) {
                                    combinedGenes += i + "+";
                                }
                                combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
                                forwardScores.put(Math.abs(score), combinedGenes);
                            } else {
                                String combinedGenes = "";
                                for (int i = currentTargetGene + currentWindowSize - 1; i > currentTargetGene; i--) {
                                    combinedGenes += i + "+";
                                }
                                combinedGenes = combinedGenes.substring(0, combinedGenes.length() - 1);
                                forwardScores.put(Math.abs(score), combinedGenes);
                            }
                        }
                    }
                }
            }
            Multimap<Double, String> forwardscore1 = ArrayListMultimap.create(forwardScores);
            TreeMultimap<Double, String> forwardscore = TreeMultimap.create(Ordering.natural().reverse(), Ordering.natural());
            forwardscore.putAll(forwardscore1);
            forwardBestScores.put(currentQueryGene, forwardscore);
        }
        System.out.println(forwardBestScores);
        return forwardBestScores;
    }

    /*
     * Obtains the higher of the scores of matching genes in forward direction or reverse direction on per gene basis
     */
    public String getmax(Collection<Multimap<Double, String>> collection, Multimap<Integer, Multimap<Double, String>> reverse) {
        String geneToBeCompared = "";
        String maxScoringGene = ""; // can be one gene or combination of many genes
        double max = 0;
        boolean isGenePresent = true;

        if (collection.size() > 0) {
            while (isGenePresent) {
                for (Multimap<Double, String> gene : collection) {
                    try {
                        max = gene.keySet().iterator().next();
                        finalscore = max;
                        for (String geneCombinationWithBestScore : gene.get(max)) {
                            if (geneCombinationWithBestScore.contains("+")) {
                                String[] individualGenes = geneCombinationWithBestScore.split("\\+");
                                for (String individualGene : individualGenes) {
                                    if (reverse.containsKey(Integer.parseInt(individualGene))) {
                                        if (geneToBeCompared.equals("")) {
                                            geneToBeCompared = individualGene;
                                        } else {
                                            geneToBeCompared += "+" + individualGene;
                                        }
                                    }
                                }
                            } else {
                                if (reverse.containsKey(Integer.parseInt(geneCombinationWithBestScore))) {
                                    if (geneToBeCompared.equals("")) {
                                        geneToBeCompared = geneCombinationWithBestScore;
                                    } else {
                                        geneToBeCompared += ";" + geneCombinationWithBestScore;
                                    }
                                }
                            }
                        }
                        if (geneToBeCompared.trim().equals("")) {
                            gene.asMap().remove(max);
                        }
                    } catch (Exception ds) {
                        ds.printStackTrace();
                    }
                }
                if (!geneToBeCompared.trim().equals("")) {
                    isGenePresent = false;
                }
                double roundOff = (double) Math.round(max * 100) / 100; // set score precision to two decimal places
                maxScoringGene = roundOff + "=" + geneToBeCompared;
            }
        }
        return maxScoringGene;
    }

    public static void removemax(Multimap<Integer, Multimap<Double, String>> collections, int key) {
        collections.asMap().remove(key);
    }

    public void pathwayComparison() {
        Multimap<Integer, Multimap<Double, String>> forward = pcompare(source, target);
        Multimap<Integer, Multimap<Double, String>> copyOfForward = ArrayListMultimap.create(forward); // make changes to this copy while combining genes
        Multimap<Integer, Multimap<Double, String>> reverse = pcompare(target, source);

        int nearestGene = GENE_MAX_DISTANCE;
        double overallScore = 0;

        String matchingGene;

        for (int i = 0; i < forward.size(); i++) {
            int currentIndex = i + 1; // here i is the id of the current gene
            nearestGene = GENE_MAX_DISTANCE;    // re-assign to max distance

            boolean currentTargetGeneAssigned = false;

            String whichGeneInTarget = "-999";

            while (currentTargetGeneAssigned == false) {
                // collect best scores in the forward direction into a string array (each array element looks like 0.5=1+2)
                String[] target_scores = getmax(copyOfForward.get(currentIndex), reverse).toString().split("=");

                if (copyOfForward.get(currentIndex).size() > 0) {

                    if (target_scores.length >= 1) {

                        double currentTargetGeneScore = Double.parseDouble(target_scores[0].toString().trim());
                        String currentTargetGeneCombination = target_scores[1].trim();
                        currentTargetGeneCombination = currentTargetGeneCombination + ";";  // add a ; symbol in the end to every candidate gene combination in target
                        if (currentTargetGeneScore > 0) {   // has a non-zero score
                            // collect individual gene combinations in a string array
                            String[] candidateTargetGenes = currentTargetGeneCombination.split(";");
                            for (String candidateTargetGene : candidateTargetGenes) {   // are there multiple gene combinations in target having equal scores?
                                candidateTargetGene = candidateTargetGene + "+";    // add a + symbol in the end to every gene and gene combination in the target

                                String[] genesInTargetCombination = candidateTargetGene.split("\\+");
                                for (String geneInTargetCombination : genesInTargetCombination) {

                                    String[] query_scores = getmax(reverse.get(Integer.parseInt(geneInTargetCombination.trim())), forward).split("=");
                                    if (query_scores.length >= 1) {
                                        double currentQueryGeneScore = Double.parseDouble(query_scores[0].toString().trim());
                                        String currentQueryGeneCombination = query_scores[1].trim();

                                        if (currentTargetGeneScore - currentQueryGeneScore <= SCORE_MISMATCH_ERROR) {   // query-to-target edge has higher score than target-to-query edge (e.g., q(1)->t(2+3) > t(2)->q(4))
                                            //  assign
                                            overallScore = currentTargetGeneScore;
                                            whichGeneInTarget = candidateTargetGene;
                                            currentTargetGeneAssigned = true;
                                        } else {    // currentQueryGeneScore <= currentTargetGeneScore
                                            // if both are pointing to each other with different scores, then assign
                                            // else do pos(tgene)-pos(qgene)
                                            //      if +ve then assign
                                            //      if -ve then do not assign
                                            currentQueryGeneCombination = currentQueryGeneCombination + ";";  // add a ; symbol in the end to every candidate gene combination in query
                                            String[] currentQueryGenes = currentQueryGeneCombination.split(";");
                                            for (String currentQueryGene : currentQueryGenes) {
                                                currentQueryGene = currentQueryGene + "+";

                                                String[] genesInQueryCombination = currentQueryGene.split("\\+");
                                                String bestQueryGene = genesInQueryCombination[0];
                                                double scoreOfBestQueryGene = 0.0f;
                                                for (String queryGene : genesInQueryCombination) {
                                                    String[] new_target_scores = getmax(copyOfForward.get(Integer.parseInt(queryGene.trim())), reverse).split("=");

                                                    if (new_target_scores.length >= 1) {
                                                        double newTargetGeneScore = Double.parseDouble(new_target_scores[0].toString().trim());
                                                        String newTargetGeneCombination = new_target_scores[1].trim();
                                                        newTargetGeneCombination = newTargetGeneCombination + ";";
                                                        String[] newTargetGenes = newTargetGeneCombination.split(";");

                                                        for (String newTargetGene : newTargetGenes) {
                                                            newTargetGene = newTargetGene + "+";
                                                            String[] genesInNewTargetCombination = newTargetGene.split("\\+");
                                                            for (String geneInNewTargetCombination : genesInNewTargetCombination) {

                                                                if (currentQueryGeneScore >= newTargetGeneScore) {
                                                                    overallScore = currentQueryGeneScore;
                                                                    whichGeneInTarget = currentQueryGene;
                                                                } else {
                                                                    overallScore = newTargetGeneScore;
                                                                    whichGeneInTarget = newTargetGene;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (Integer.parseInt(bestQueryGene.trim()) == currentIndex) {

                                                    // collect scores of query to target and retrieve which target combination has max value (e.g., 1=2,3+4)


                                                    // Assign to nearest gene if they have same scores (for e.g., if 1=2;3+4, assign 1=2)
                                                    if (Double.parseDouble(query_scores[0].trim()) <= Double.parseDouble(target_scores[0].trim())) {
                                                        overallScore = Double.parseDouble(target_scores[0].trim());
                                                        int distance = Math.abs(Integer.parseInt(bestQueryGene.trim()) - currentIndex);
                                                        if (distance < nearestGene) {
                                                            nearestGene = distance;
                                                            whichGeneInTarget = candidateTargetGene;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    break;
                }

                // add results to finalresults
            }
            if (whichGeneInTarget.equalsIgnoreCase("-999")) {
                whichGeneInTarget = "no";
                overallScore = 0;
            }
        }
    }

    /**
     * @return the finalscore
     */
    public static double getFinalscore() {
        return finalscore;
    }

    /**
     * @param aFinalscore the finalscore to set
     */
    public static void setFinalscore(double aFinalscore) {
        finalscore = aFinalscore;
    }
}