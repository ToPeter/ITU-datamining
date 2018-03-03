package kMean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.*;


public class KMeans {


	public static ArrayList<KMeanCluster> KMeansPartition(int k, ArrayList<Iris> data) throws IllegalAccessException {
		ArrayList<KMeanCluster> clusters;
		ArrayList<Iris> normalizedData;
		normalizedData = Normalizer.normalize(data);
		clusters = generateInitialClusters(k, normalizedData);
		ArrayList<KMeanCluster> oldClusters;
		do{
			oldClusters = new ArrayList<>();
			for (KMeanCluster c : clusters) {
				KMeanCluster oldCluster = new KMeanCluster(c);
				oldClusters.add(oldCluster);
			}
			clusters = assignClusters(normalizedData, clusters);
			clusters = recalculateCentroid(clusters);

		} while (!clusters.equals(oldClusters));


		return clusters;
	}

	private static ArrayList<KMeanCluster> recalculateCentroid(ArrayList<KMeanCluster> clusters) {
		for (int i = 0; i < clusters.size(); i ++) {
			clusters.get(i).recalculateCentroid();
		}
		return clusters;
	}

	public static ArrayList<KMeanCluster> generateInitialClusters(int k, ArrayList<Iris> data) {

		ArrayList<KMeanCluster> clusters = new ArrayList<>();
		Integer random = randInt(0, data.size() - 1); // initialize random number
		List<Integer> usedIndexes = new ArrayList<>();

		for (int i = 0; i < k ; i ++) {
			while (usedIndexes.contains(random)) {
				random = randInt(0, data.size() - 1);
			}
			usedIndexes.add(random); // include the index used in order for the next cluster not to use as a centroid again
			KMeanCluster cluster = new KMeanCluster(data.get(random));
			clusters.add(cluster);
		}

		return clusters;
	}

	/**
	 * assign values to clusters based on minimum distances
	 * @param data
	 * @param clusters
	 * @return
	 */
	public static ArrayList<KMeanCluster> assignClusters(ArrayList<Iris> data, ArrayList<KMeanCluster> clusters ) throws IllegalAccessException {

		clusters = removeElements(clusters);

		for (Iris i: data){
			double minDistance = Double.MAX_VALUE;
			int clusterIndex = 0;
			for (int j = 0; j < clusters.size(); j ++) {
				double distance = i.distanceTo(clusters.get(j).getCentroid());
				if (distance < minDistance){ // if distance less, then save cluster position and update mindistance
					minDistance = distance;
					clusterIndex = j;
				}
			}
			clusters.get(clusterIndex).add(i);
		}

		return clusters;
	}

	private static ArrayList<KMeanCluster> removeElements(ArrayList<KMeanCluster> clusters) {
		for (KMeanCluster c : clusters) {
			c.removeElements();
		}

		return clusters;
	}


	public static int randInt(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

}
