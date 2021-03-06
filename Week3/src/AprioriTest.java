import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lucas on 2/20/2017.
 */
public class AprioriTest {
    private ItemSet set1;
    private ItemSet set2;
    private ItemSet set3;
    private ItemSet set4;
    private ItemSet set5;
    private ItemSet set6;
    private static final int[][] TRANSACTIONS = new int[][] { { 1, 2, 3, 4, 5 }, { 1, 3, 5 }, { 1, 1, 5 }, { 1, 5 }, { 1, 3, 4 }, { 2, 3, 5 }, { 2, 3, 5 },
            { 3, 4, 5 }, { 4, 5 }, { 2 }, { 2, 3 }, { 2, 3, 4 }, { 3, 4, 5 } };

    @Before
    public void setUp() throws Exception {
        set1 = new ItemSet(new int[] {1,3});
        set2 = new ItemSet(new int[] {1,4});
        set3 = new ItemSet(new int[] {2,1});
        set4 = new ItemSet(new int[] {2});
        set5 = new ItemSet(new int[] {1});
        set6 = new ItemSet(new int[] {2,1,8});
    }

    @Test
    public void countSupportTest() {
        ItemSet item =  new ItemSet(new int[] {1,3});
        Assert.assertEquals(3, Apriori.countSupport(item, TRANSACTIONS));
    }

    @Test
    public void joinSetsTest() {
        ItemSet joinsets = Apriori.joinSets(set1, set2);
        ItemSet dontjoinsets = Apriori.joinSets(set1, set3);
        ItemSet setSize1 = Apriori.joinSets(set4, set5);
        Assert.assertEquals(new ItemSet(new int[] {1,3, 4}).toString(), joinsets.toString() );
        Assert.assertEquals(null, dontjoinsets );
        Assert.assertEquals(new ItemSet(new int[] {2,1}).toString(), setSize1.toString() );

    }

    @Test
    public void subSetTest(){
        Assert.assertEquals(true, set4.isItSusbet(set3));
        Assert.assertEquals(false, set4.isItSusbet(set1));
        Assert.assertEquals(new ItemSet(new int[] {8}).toString(), set6.restSubset(set3).toString());
    }

}