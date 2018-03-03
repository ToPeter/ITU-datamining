package data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by lucas on 2/27/2017.
 */
public class IrisTest {
    private Iris iris1;
    private Iris iris2;

    @Before
    public void setUp() throws Exception {
        ArrayList<Iris> irisData = DataLoader.LoadAllIrisData();
        iris1 = irisData.get(0);
        iris2 = irisData.get(1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void distanceTo() throws Exception {
        Assert.assertEquals(0.53, iris1.distanceTo(iris2), 0.1);
    }

}