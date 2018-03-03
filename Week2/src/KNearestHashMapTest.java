import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lucas on 2/25/2017.
 */
public class KNearestHashMapTest {

    private KNearestHashMap myKnearest;
    private Mushroom mushroom1;
    private Mushroom mushroom2;

    @Before
    public void setUp() {
        ArrayList<Mushroom> mushrooms = DataManager.LoadData();
        mushroom1 = mushrooms.get(0);
        mushroom2 = mushrooms.get(1);

        List<Mushroom> trainingSet = new ArrayList<>();

        // K nearest implementation
        myKnearest = new KNearestHashMap(trainingSet, 3);
    }
    @After
    public void tearDown() {

    }

    @Test
    public void testDistance() {
        Assert.assertEquals(0.277,myKnearest.calculateDistance(mushroom1, mushroom2), 0.01);
    }

}