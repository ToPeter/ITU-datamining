package DataHandling;

import org.junit.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lucas on 4/3/2017.
 */
public class DataManagerTest {
    @Test
    public void testMedian(){
        Double[] data1 = { 1.0,5.0,3.0,8.0,12.3 };
        Double[] data2 = { 1.0,5.0,3.0,8.0 };
        List<Double> list1 = Arrays.asList(data1);
        List<Double> list2 = Arrays.asList(data2);
        Assert.assertEquals(5.0, DataManager.median(list1), 0.2);
        Assert.assertEquals(4.0, DataManager.median(list2), 0.2);
    }

}
