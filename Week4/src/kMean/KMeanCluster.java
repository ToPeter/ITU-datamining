package kMean;
import java.util.ArrayList;

import data.Iris;
import data.IrisClass;

public class KMeanCluster {

	private ArrayList<Iris> clusterMembers;
	private Iris centroid;

	public KMeanCluster(Iris centroid)
	{
		this.clusterMembers = new ArrayList<Iris>();
		this.centroid = centroid;
	}

	public KMeanCluster(KMeanCluster c) {
		this.clusterMembers = c.getMembers();
		this.centroid = c.getCentroid();
	}


	public Iris getCentroid() {
		return centroid;
	}


	@Override
	public int hashCode() {
		int result = clusterMembers != null ? clusterMembers.hashCode() : 0;
		result = 31 * result + (centroid != null ? centroid.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		KMeanCluster that = (KMeanCluster) o;

		if (clusterMembers != null ? !clusterMembers.equals(that.clusterMembers) : that.clusterMembers != null)
			return false;
		return centroid != null ? centroid.equals(that.centroid) : that.centroid == null;

	}

	@Override
	public String toString() {
		String toPrintString = "-----------------------------------CLUSTER START------------------------------------------" + System.getProperty("line.separator");
		
		for(Iris i : this.clusterMembers)
		{
			toPrintString += i.toString() + System.getProperty("line.separator");
		}
		toPrintString += "-----------------------------------CLUSTER END-------------------------------------------" + System.getProperty("line.separator");
		
		return toPrintString;
	}

	public void add(Iris i) {
		clusterMembers.add(i);
	}

	public void recalculateCentroid() {
		float pentalLengthAvg = (float) clusterMembers.stream().mapToDouble(Iris::getPetal_Length).average().getAsDouble();
		float pentalWidthAvg = (float) clusterMembers.stream().mapToDouble(Iris::getPetal_Width).average().getAsDouble();
		float sepalWidthAvg = (float) clusterMembers.stream().mapToDouble(Iris::getSepal_Width).average().getAsDouble();
		float sepalLengthAvg = (float) clusterMembers.stream().mapToDouble(Iris::getSepal_Length).average().getAsDouble();
		centroid = new Iris(sepalLengthAvg, sepalWidthAvg, pentalLengthAvg, pentalWidthAvg, IrisClass.Iris_setosa);
	}

	public ArrayList<Iris> getMembers() {
		return clusterMembers;
	}

	public void removeElements() {
		clusterMembers = new ArrayList<>();
	}
}
