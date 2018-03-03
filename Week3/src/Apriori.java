import java.util.*;


public class Apriori {
	/***
	 * The TRANSACTIONS 2-dimensional array holds the full data set for the lab
	 */
    static final int[][] TRANSACTIONS = new int[][] { { 1, 2, 3, 4, 5 }, { 1, 3, 5 }, { 2, 3, 5 }, { 1, 5 }, { 1, 3, 4 }, { 2, 3, 5 }, { 2, 3, 5 },
                    { 3, 4, 5 }, { 4, 5 }, { 2 }, { 2, 3 }, { 2, 3, 4 }, { 3, 4, 5 } };
                    
    static final int[][] BOOK_TRANSACTIONS = new int[][] { { 1, 2, 5 }, {2, 4}, { 2, 3 }, { 1, 2, 4 }, { 1, 3 }, { 2, 3 }, { 1, 3 },
                    { 1, 2, 3, 5 }, { 1, 2, 3 }};

    public static void main( String[] args ) {
        // TODO: Select a reasonable support threshold via trial-and-error. Can either be percentage or absolute value.
        final int supportThreshold = 3;
        Hashtable<ItemSet, ItemSet> associationRules = new Hashtable<>();
        associationRules = apriori(TRANSACTIONS, supportThreshold );
        for (ItemSet key: associationRules.keySet()) {
            System.out.println(key + " -> " + associationRules.get(key));
        }

    }

    public static Hashtable<ItemSet, ItemSet> apriori(int[][] transactions, int supportThreshold ) {
        int k;
        Hashtable<ItemSet, Integer> allFrequentItemSets = new Hashtable<>();

        Hashtable<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1( transactions, supportThreshold );



        for (k = 1; frequentItemSets.size() > 0; k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) + "…" );
            frequentItemSets = generateFrequentItemSets( supportThreshold, transactions, frequentItemSets );


            System.out.println( " found " + frequentItemSets.size() );
            for (ItemSet key: frequentItemSets.keySet()){
                allFrequentItemSets.put(key, frequentItemSets.get(key));
            }
        }
        // TODO: create association rules from the frequent itemsets

        Hashtable<ItemSet, ItemSet> result = generateAssociationRules(allFrequentItemSets);

        // TODO: return something useful
        return result;
    }

    // TODO:
    private static Hashtable<ItemSet, ItemSet> generateAssociationRules(Hashtable<ItemSet, Integer> ItemSets) {
        double confidenceThreshold = 0.6;
        Hashtable<ItemSet, ItemSet> rules = new Hashtable<>();

        // iterate through all the generated association rules
        for (ItemSet key: ItemSets.keySet()) {
            // get all the subsets
            List<ItemSet> subsets = new ArrayList<>();
            subsets = generateSubSets(ItemSets, key);

            // generate all the rules
            for (int i = 0; i < subsets.size(); i ++) {
                int subSetSupport = ItemSets.get(subsets.get(i));
                int supportOfKey = ItemSets.get(key);
                double confidence = (double) supportOfKey/ (double) subSetSupport;
                if (confidence >= confidenceThreshold ){
                    rules.put(subsets.get(i), key.restSubset(subsets.get(i)));
                }
            }
        }
        return rules;
    }

    public static List<ItemSet> generateSubSets(Hashtable<ItemSet, Integer> ItemSets, ItemSet itemset) {
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


    private static Hashtable<ItemSet, Integer> generateFrequentItemSets( int supportThreshold, int[][] transactions,
                    Hashtable<ItemSet, Integer> lowerLevelItemSets ) {
        // TODO: first generate candidate itemsets from the lower level itemsets
        List<ItemSet> candidateItemSets = new ArrayList<>();
        List<ItemSet> lowerLevelItemSetsList = new ArrayList<>();
        lowerLevelItemSetsList.addAll(lowerLevelItemSets.keySet());

        for (int i = 0; i < lowerLevelItemSetsList.size(); i ++) { // iterate through all previous itesets
            for (int j = i + 1; j < lowerLevelItemSetsList.size(); j ++){ // iterate through all possible combinations of join
                ItemSet candidate = joinSets(lowerLevelItemSetsList.get(i), lowerLevelItemSetsList.get(j)); // bug modifing list

                if (!candidateItemSets.contains(candidate) && candidate != null) candidateItemSets.add(candidate);
            }
        }
        // prunning ?

        // now that we have the candidates, lets check the support
        Hashtable<ItemSet, Integer> result = new Hashtable<>();

        for (int i = 0; i < candidateItemSets.size(); i++){
            int support = countSupport(candidateItemSets.get(i),TRANSACTIONS);
            if (support >= supportThreshold) {
                result.put(candidateItemSets.get(i), support);
            }
        }

        // TODO: return something useful
        return result;
    }

    public static ItemSet joinSets( ItemSet first, ItemSet second ) {

        ItemSet result = null;

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

    private static Hashtable<ItemSet, Integer> generateFrequentItemSetsLevel1( int[][] transactions, int supportThreshold ) {

        Hashtable<ItemSet, Integer> initialCandidates = new Hashtable<>();
        Hashtable<ItemSet, Integer> result = new Hashtable<>();

        for (int i = 0; i < transactions.length; i ++){
            for (int j = 0; j < transactions[i].length; j ++ ) { // iterate through the double array
                ItemSet item = new ItemSet(new int[] {transactions[i][j]});
                if(!initialCandidates.containsKey(item)) {
                    initialCandidates.put(item, 0);
                }
                initialCandidates.put(item, initialCandidates.get(item) + 1);
            }
        }
        Iterator<ItemSet> hashIterator = initialCandidates.keySet().iterator();

        while (hashIterator.hasNext()){
            ItemSet currentItem = hashIterator.next();
            if (initialCandidates.get(currentItem) >= supportThreshold) result.put(currentItem, initialCandidates.get(currentItem)); // if the result is lower than the threshold, delete
        }
        return result;
    }

    public static int countSupport( ItemSet itemSet, int[][] transactions ) {

        int support = 0;
        for (int i = 0; i < transactions.length; i ++) { // iterate through all combination of transactions
            int foundElements = 0;
            for (int j = 0; j < itemSet.getElements().length; j ++){ // iterate through all items in the itemset
                for (int k = 0; k < transactions[i].length; k ++) { // compare each item in the item set with the ones in the transactions
                    if (itemSet.getElements()[j] == transactions[i][k]){
                        foundElements ++; // if it finds, breaks out of the loop and increases the counter
                        break;
                    }

                }
            }
            if (foundElements == itemSet.getElements().length) support++; // if found all items then increase the support
        }

        return support;
    }

}
