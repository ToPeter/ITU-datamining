package KNearestNeighbours;

import DataHandling.DataManager;
import DataHandling.Student;
import org.junit.*;

import java.util.List;

/**
 * Created by lucas on 3/23/2017.
 */
public class KNearestTest {
    List<Student> parsedData;
    @Before
    public void setUp() throws Exception {
        parsedData = DataManager.parseData();

    }

    @After
    public void tearDown() throws Exception {
        parsedData = null;
    }

    @org.junit.Test
    public void calculateDistances() throws Exception {
        Student s1 = parsedData.get(0);
        Student s2 = parsedData.get(1);
        double distance = KNearest.calculateDistances(s1, s2);
        Assert.assertEquals(1.944, distance, 0.01);
    }



}