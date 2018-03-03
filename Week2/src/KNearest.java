import java.util.*;

import static enums.Class_Label.*;

/**
 * Created by lucas on 2/13/2017.
 */
public class KNearest {
    private List<Mushroom> currentSet; // current set will be updated at each observation inserted;
    private List<Mushroom> result;
    private int k;
    private int falsePositive, falseNegative, truePositive, trueNegative;

    public KNearest(List<Mushroom> trainingSet, int k) {
        this.currentSet = trainingSet;
        this.k = k;
        result = new ArrayList<>();
        falsePositive = 0; falseNegative = 0; trueNegative = 0; truePositive = 0;
    }

    public void insertObservation(Mushroom mushroom) {
        LinkedList<NeighbourMushRoom> Neighbours = new LinkedList<>();
        for (int i = 0; i < currentSet.size(); i ++) { // iterate through the current set

            NeighbourMushRoom prospectMushroom = new NeighbourMushRoom();
            prospectMushroom.mushroom = currentSet.get(i);
            double distance = calculateDistance(mushroom, currentSet.get(i)); // calculates distance
            prospectMushroom.distance = distance;

            // if it is empty
            if (Neighbours.isEmpty()) {
                Neighbours.add(prospectMushroom);
                continue;
            }


            for (int j = 0; j < Neighbours.size(); j ++) {

                // if distance is less than one that is already in the list
                if (distance < Neighbours.get(j).distance) { // checks if the calculated distance is less than the current for the specific position of the neighbours array
                    Neighbours.add(j, prospectMushroom); // adds at the specific index
                    if (Neighbours.size() > k) {
                        Neighbours.removeLast();
                    }
                    break;
                }
            }

            if (Neighbours.size() < k) {
                Neighbours.add(prospectMushroom);
                continue;
            }

            Neighbours = new LinkedList<>();

        }

        // counts to see how many close neighbours are of which type and decides how to predict
        int eatable = 0;
        for (int i = 0; i < Neighbours.size(); i ++) {
            if (Neighbours.get(i).mushroom.m_Class == edible) {
                eatable ++;
            }
        }




        // based on the closest neighbours, it assigns if it is eatable or not
        if (eatable > k/2 ) {
            if (mushroom.m_Class.equals(edible)) {
                truePositive++;
            }
            else if (mushroom.m_Class.equals(poisonous)) {
                falsePositive++;
            }
            mushroom.m_Class = edible;
        }
        else {
            if (mushroom.m_Class.equals(poisonous)) {
                trueNegative++;
            }
            else if (mushroom.m_Class.equals(edible)) {
                falseNegative++;
            }
            mushroom.m_Class = poisonous;
        }
        currentSet.add(mushroom);
        result.add(mushroom);


    }

    private class NeighbourMushRoom {
        Mushroom mushroom;
        double distance;
    }

    public List<Mushroom> getResult() {
        return result;
    }

    /**
     * Calculates the euclidean distance between two mushroom objects
     * @param mushroom1
     * @param mushroom2
     * @return
     */
    private double calculateDistance(Mushroom mushroom1, Mushroom mushroom2) {

        double distance = 0;
        List<Object> attributeList = Mushroom.getAttributeList();

        for (int i =1; i < attributeList.size(); i ++) {
            if (!(mushroom1.getAttributeValue(attributeList.get(i)) ==  mushroom2.getAttributeValue(attributeList.get(i)))) {
                distance ++;
            }
        }

        return (1/Math.sqrt(distance)); // normalize
    }

    public int getFalsePositive() {
        return falsePositive;
    }

    public int getFalseNegative() {
        return falseNegative;
    }

    public int getTruePositive() {
        return truePositive;
    }

    public int getTrueNegative() {
        return trueNegative;
    }
}
