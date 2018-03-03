import enums.Class_Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static enums.Class_Label.edible;
import static enums.Class_Label.poisonous;

/**
 * Created by lucas on 2/25/2017.
 */
public class KNearestHashMap {
    private Map<Double, List<Mushroom>> mushroomDistances;
    private List<Mushroom> mushroomsDataBase;
    private int k;
    private int correct, incorrect;

    public KNearestHashMap(List<Mushroom> trainingSet, int k) {
        mushroomsDataBase = trainingSet;
        this.k = k;
        mushroomDistances = new TreeMap<>();
    }

    public double calculateDistance(Mushroom mushroom1, Mushroom mushroom2) {

        double distance = 0;
        List<Object> attributeList = Mushroom.getAttributeList();

        for (int i =0; i < attributeList.size(); i ++) {
            if (!(mushroom1.getAttributeValue(attributeList.get(i)) ==  mushroom2.getAttributeValue(attributeList.get(i)))) {
                distance ++;
            }
        }

        return (Math.sqrt(distance)); // normalize
    }

    public void classifyNewMushroom(Mushroom mushroom) {
        for (int i = 0; i < mushroomsDataBase.size(); i ++) {
            Mushroom currentMushroom = mushroomsDataBase.get(i);
            Double distance = calculateDistance(mushroom, currentMushroom);
            if (!mushroomDistances.containsKey(distance)) {
                List<Mushroom> currentList = new ArrayList<>();
                currentList.add(currentMushroom);
                mushroomDistances.put(distance, currentList);
            }
            else {
                mushroomDistances.get(distance).add(currentMushroom);
            }

        }

        Mushroom predictedMushroom =  predictMushroomType(mushroom, mushroomDistances);
        mushroomsDataBase.add(predictedMushroom);
        mushroomDistances = new TreeMap<>();

    }

    public Mushroom predictMushroomType(Mushroom mushroom, Map<Double, List<Mushroom>> mushroomDistances) {
        int neighbours = k;
        int eatable = 0;

        for (double key : mushroomDistances.keySet()) {
            List<Mushroom> currentList = mushroomDistances.get(key);
            for (int i = 0; i < currentList.size(); i ++){
                if (currentList.get(i).getAttributeValue(Class_Label.class).equals(edible)) {
                    eatable++;
                }
                neighbours--;
                if (neighbours <= 0) break;
            }
            if (neighbours <= 0) break;
        }

        // based on the closest neighbours, it assigns if it is eatable or not
        if (eatable > k/2 ) {
            if (mushroom.getAttributeValue(Class_Label.class).equals(edible)) {
                correct++;
            }
            else {
                incorrect++;
            }
            mushroom.m_Class = edible;
        }
        else {
            if (mushroom.getAttributeValue(Class_Label.class).equals(poisonous)) {
                correct++;
            }
            else {
                incorrect++;
            }
            mushroom.m_Class = poisonous;
        }
        return mushroom;
    }

    public int getCorrect() {
        return correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

}
