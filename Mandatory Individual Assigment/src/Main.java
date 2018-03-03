import AprioriAlg.Apriori;
import AprioriAlg.ItemSet;
import DataHandling.DataManager;
import DataHandling.Student;
import Enums.GamesPlayed;
import KMeansCluster.KMeanCluster;
import KMeansCluster.KMeans;
import KNearestNeighbours.KNearest;

import java.util.*;

/**
 * Created by lucas on 3/20/2017.
 */
public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        // extract the relevant attributes for the analasys
        List<Student> parsedData = DataManager.parseData();


        // perform the k-nearest-neighbours classification on the degree
        System.out.println("-----------------------------------K NEAREST NEIGHBOURS RESULTS-------------------------------------------\n");
        List<Double> individualResults = new ArrayList<>();
        for (int i = 1; i < 50; i++){ // try using different neighbours
            for (int j = 0; j < 200; j++){ // run 100 times and take the average
                Collections.shuffle(parsedData); // first shuffle the data
                // takes 2/3 to be the training set and the rest test set
                List<Student> trainingSet = new ArrayList<>();
                List<Student> testSet = new ArrayList<>();
                for (int k = 0; k < parsedData.size(); k++) {
                    if (k < parsedData.size() * 2 / 3) trainingSet.add(parsedData.get(k));
                    else testSet.add(parsedData.get(k));
                }
                KNearest myModel = new KNearest(trainingSet, i);
                individualResults.add(myModel.accuracy(testSet));
            }
            System.out.println( i + ";"
                    + individualResults.stream().mapToDouble(o->o.doubleValue()).average().getAsDouble());
        }

        // apply the apriori algorithm on the games played
        // first of all, remove students who marked "I have not played any of these games" and
        // create an double dimensional array with each subarray representing a student
        System.out.println("-----------------------------------APRIORI RESULTS-------------------------------------------\n");
        List<Student> trimedStudents = DataManager.removeInvalidGames(parsedData);
        int[][] allGames = new int[trimedStudents.size()][];
        for (int i = 0; i < allGames.length; i ++) {
            List<GamesPlayed> currentGames = trimedStudents.get(i).s_games_played;
            allGames[i] = new int[currentGames.size()];
            for (int j = 0; j < currentGames.size(); j ++) {
                allGames[i][j] = currentGames.get(j).getNumVal();
            }
        }
        Hashtable<ItemSet, List<ItemSet>> associationRules;
        associationRules = Apriori.apriori(allGames, 0.14, 0.85 );
        for (ItemSet key: associationRules.keySet()) {
            for (ItemSet value: associationRules.get(key)){
                System.out.println(key.toString() + " -> " + value.toString());
            }
        }


        // apply the clustering algorithm

        // first fill in missing values and remove the ones without gender
        List<Student> cleanedData = DataManager.fillMissingValues(parsedData);

        // normalize data
        List<Student> normalizedData = DataManager.normalizeNumericAttributes(cleanedData);

        // generate clusters
        System.out.println("-----------------------------------CLUSTER RESULTS-------------------------------------------\n");
        ArrayList<KMeanCluster> FoundClusters_KMeans = KMeans.KMeansPartition(2, normalizedData);
        System.out.println("Clusters from K-means");
        for (KMeanCluster c : FoundClusters_KMeans) {
            System.out.println(c.toString());
        }

    }
}
