package AprioriAlg;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Apriori {

    /**
     * The algorithm finds patterns in the dataset (items) according to a given support and confidence threshold
     * @param items the database to be searched
     * @param supportThreshold Threshold of support. Namely, percentage of tuples in the dataset that contain a particular pattern
     * @param confidenceThreshold  Threshold of confidence. Namely, given the notation A -> B, of the tuples that have A, percentage that also have B.
     * @return The association rules with confidence >= confidence threshold for frequent itemsets with frequency >= support threshold
     */
    public static Hashtable<ItemSet, List<ItemSet>> apriori(int[][] items, double supportThreshold, double confidenceThreshold ) {
        Hashtable<ItemSet, Double> allFrequentItemSets = new Hashtable<>();
        Hashtable<ItemSet, Double> frequentItemSets = generateFrequentItemSetsLevel1( items, supportThreshold );

        // stores the frequentItemSets level 1
        for (ItemSet key: frequentItemSets.keySet()){
            allFrequentItemSets.put(key, frequentItemSets.get(key));
        }

        for (int k = 1; frequentItemSets.size() > 0; k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) + "…" );
            frequentItemSets = generateFrequentItemSets( supportThreshold, items, frequentItemSets );
            System.out.println( " found " + frequentItemSets.size() );

            // stores the frequentItemSets found
            for (ItemSet key: frequentItemSets.keySet()){
                allFrequentItemSets.put(key, frequentItemSets.get(key));
            }
        }

        // create association rules
        Hashtable<ItemSet, List<ItemSet>> result = generateAssociationRules(allFrequentItemSets, confidenceThreshold);

        return result;
    }


    /**
     * This function generate the association rules given a confidence Threshold
     * @param ItemSets itemSets from which the rules will be generated
     * @param confidenceThreshold the minimal confidence needed for an association rule to be created
     * @return association rules with confidene at least >= then the confidence Threshold
     */
    private static Hashtable<ItemSet, List<ItemSet>> generateAssociationRules(Hashtable<ItemSet, Double> ItemSets, double confidenceThreshold) {
        Hashtable<ItemSet, List<ItemSet>> rules = new Hashtable<>();

        // iterate through all the generated association rules
        for (ItemSet key: ItemSets.keySet()) {
            // get all the subsets
            List<ItemSet> subsets = generateSubSets(ItemSets, key);

            // generate all the rules according to support(l)/support(s) > threshold
            for (int i = 0; i < subsets.size(); i ++) {
                ItemSet currentSubset = subsets.get(i);
                double subSetSupport = ItemSets.get(currentSubset);
                double supportOfFrequentItemSet = ItemSets.get(key);
                double ruleConfidence = supportOfFrequentItemSet/ subSetSupport;
                ItemSet subtractKey = key.subtract(subsets.get(i));
                if (ruleConfidence >= confidenceThreshold ){
                    if (!rules.containsKey(currentSubset)) {
                        List<ItemSet> list = new ArrayList<>();
                        rules.put(currentSubset, list);
                    }
                    rules.get(currentSubset).add(subtractKey);

                }
            }
        }
        return rules;
    }


    /**
     *  This function returns all the subsets of the given itemSet from a map of ItemSets
     * @param ItemSets The map of ItemSets, in other words the database to be searched into
     * @param itemset The itemSet from which the subsets will be drawn from
     * @return a list of ItemSets representing all the subsets of itemset in ItemSets
     */
    public static List<ItemSet> generateSubSets(Hashtable<ItemSet, Double> ItemSets, ItemSet itemset) {
        List<ItemSet> candidateSubSets = new ArrayList<>();
        List<ItemSet> subSets = new ArrayList<>();
        candidateSubSets.addAll(ItemSets.keySet());
        for (int i = 0; i < candidateSubSets.size(); i ++) {
            if (candidateSubSets.get(i).isItSusbet(itemset)) {
                subSets.add(candidateSubSets.get(i));
            }
        }

        return subSets;
    }


    /**
     * This function generates frequent itemsets given a matrix of itemsets based on lowerlevel itemsets and a support threshold.
     * @param supportThreshold the mininal value of support that each itemSet has to have to be considered frequent
     * @param itemSets the itemSets matrix, namely the database
     * @param lowerLevelItemSets the itemSets for k - 1
     * @return
     */
    private static Hashtable<ItemSet, Double> generateFrequentItemSets(double supportThreshold, int[][] itemSets,
                                                                       Hashtable<ItemSet, Double> lowerLevelItemSets ) {
        Hashtable<ItemSet, Double> candidateItemSets = new Hashtable<>();
        List<ItemSet> lowerLevelItemSetsList = new ArrayList<>();
        lowerLevelItemSetsList.addAll(lowerLevelItemSets.keySet());

        for (int i = 0; i < lowerLevelItemSetsList.size(); i ++) { // iterate through all previous itesets
            for (int j = i + 1; j < lowerLevelItemSetsList.size(); j ++){ // iterate through all possible combinations of join
                ItemSet candidate = joinSets(lowerLevelItemSetsList.get(i), lowerLevelItemSetsList.get(j));

                if (candidate == null) continue;
                if (candidate.lowerSubsetsAreContained(lowerLevelItemSetsList)){ // prune
                    double support = countSupport(candidate,itemSets);
                    if (support >= supportThreshold) candidateItemSets.put(candidate, support); // only include if the support is bigger than the threshold
                }
            }
        }

        return candidateItemSets;
    }


    /**
     * This function join itemsets based on the apriori systematic. If the itemsets are different on the last pos,
     * add the last item of the itemset to the first.
     * @param first first itemset to be joined
     * @param second second itemset to be joined
     * @return
     */
    public static ItemSet joinSets(ItemSet first, ItemSet second ) {

        ItemSet result ;

        // if items have size = 1
        if (first.size() == second.size() && first.size() == 1){
            result = new ItemSet(first);
            result.add(second.get(0));
            return result;
        }


        // if items are size > 1
        for (int i = 0; i < first.size() ; i ++ ) {
            if (i < first.size() - 1) { // check if they are the same up to the last pos
                if (first.get(i) != second.get(i)) return null;
            }
            else { // on the last pos check if they are different, if yes create a new itemset
                if (first.get(i) != second.get(i)) {
                    result = new ItemSet(first);
                    result.add(second.get(i));
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Generate all frequentItemSets for the level 1. In other words, create an itemSet for all individual items.
     * @param items the double dimensional array where each sequence is represented as a row
     * @param supportThreshold the minimun support a itemset has to e to be considreded frequent
     * @return
     */
    private static Hashtable<ItemSet, Double> generateFrequentItemSetsLevel1(int[][] items, double supportThreshold ) {

        Hashtable<ItemSet, Double> initialCandidates = new Hashtable<>();

        for (int i = 0; i < items.length; i ++){
            for (int j = 0; j < items[i].length; j ++ ) { // iterate through the double array
                ItemSet item = new ItemSet(new int[] {items[i][j]});
                double support = countSupport(item, items);
                if( support >= supportThreshold ) { // only add those with support >= threshold
                    initialCandidates.put(item, countSupport(item, items));
                }
            }
        }

        return initialCandidates;
    }

    /**
     * This function counts the support of a given itemset within a given database with various sequences
     * @param itemSet the itemset to have its support counted
     * @param data the list of sequences represented as a double dimensional array where each row is a sequence of items
     * @return
     */
    public static double countSupport(ItemSet itemSet, int[][] data ) {

        int support = 0;
        for (int i = 0; i < data.length; i ++) { // iterate through all combination of transactions
            int foundElements = 0;
            for (int j = 0; j < itemSet.getElements().length; j ++){ // iterate through all items in the itemset
                for (int k = 0; k < data[i].length; k ++) { // compare each item in the item set with the ones in the transactions
                    if (itemSet.getElements()[j] == data[i][k]){
                        foundElements ++; // if it finds, breaks out of the loop and increases the counter
                        break;
                    }

                }
            }
            if (foundElements == itemSet.getElements().length) support++; // if found all items in the current list, then increase the support
        }

        return support / (double) data.length;
    }

}
