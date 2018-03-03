import enums.Cap_Shape;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Main class to run program from.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// First step - Load data and convert to Mushroom objects.
		ArrayList<Mushroom> mushrooms = DataManager.LoadData();
		System.out.println("DataManager loaded "+mushrooms.size() + " mushrooms");
		int trainingSize = mushrooms.size() * 2/3;
		int testSize = mushrooms.size() - trainingSize;


		// training and testing set
		List<Mushroom> trainingSet = new ArrayList<>();
		for (int i = 0; i < trainingSize; i ++) {
			trainingSet.add(mushrooms.get(i));
		}

		List<Mushroom> testSet = new ArrayList<>();
		for (int i = trainingSize; i < testSize + trainingSize; i ++) {
			testSet.add(mushrooms.get(i));
		}


		// K nearest implementation
		KNearestHashMap myKnearest = new KNearestHashMap(trainingSet, 25);

		for (int i = 0; i < testSet.size(); i ++) {
			Mushroom currentMushroom = testSet.get(i);
			myKnearest.classifyNewMushroom(currentMushroom);
		}

		//for (int j = 0; j < myKnearest.getDatabase().size(); j ++) {
		//	System.out.println(myKnearest.getDatabase().get(j).m_Class);
		//}


		double accuracy = (double) myKnearest.getCorrect() / (myKnearest.getCorrect() + myKnearest.getIncorrect());

		System.out.println("Accuracy: " + accuracy);

	}

}
