import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * The ItemSet class is used to store information concerning a single transaction.
 * Should not need any changes.
 *
 */
public class ItemSet {
	
	/***
	 * The PRIMES array is internally in the ItemSet-class' hashCode method
	 */
	private static final int[] PRIMES = { 2, 3, 5, 7, 11, 13, 17, 23, 27, 31, 37 };
     int[] set;

    /***
     * Creates a new instance of the ItemSet class.
     * @param set Transaction content
     */
    public ItemSet( int[] set ) {
        this.set = set;
    }

    public ItemSet restSubset(ItemSet subtract) {

        int[] resultingArray = new int[this.size() - subtract.size()];
        List<Integer> temp = new ArrayList<>();

        for (int i = 0; i < this.size(); i ++) {
            for (int j = 0 ; j < subtract.size(); j ++) {
                if (this.set[i] == subtract.set[j]) break;
                if (j == subtract.size() - 1) temp.add(this.set[i]);
            }
        }

        for (int i = 0; i < temp.size(); i ++) {
            resultingArray[i] = temp.get(i);
        }

        ItemSet result = new ItemSet(resultingArray);

        return result;
    }

    public ItemSet (ItemSet another) {
        this.set = another.set;
    }

    public boolean isItSusbet(ItemSet theSet) {
        if (this.size() >= theSet.size()) return false;

        boolean result = true;
        for (int i = 0; i < this.size(); i ++) {
            for (int j = 0; j < theSet.size(); j ++) {
                if (this.set[i] == theSet.set[j]) break;
                if (j == theSet.size() - 1) result = false;
            }
        }

        return result;
    }

    @Override
    /**
     * hashCode functioned used internally in Hashtable
     */
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < set.length; i++) {
            code += set[i] * PRIMES[i];
        }
        return code;
    }

    
    @Override
    /**
     * Used to determine whether two ItemSet objects are equal
     */
    public boolean equals( Object o ) {
        if (!(o instanceof ItemSet)) {
            return false;
        }
        ItemSet other = (ItemSet) o;
        if (other.set.length != this.set.length) {
            return false;
        }
        for (int i = 0; i < set.length; i++) {
            if (set[i] != other.set[i]) {
                return false;
            }
        }
        return true;
    }

    public int[] getElements() {
        return set;
    }

    public int get(int index) {
        return set[index];
    }

    public int size() {
        return set.length;
    }

    @Override
    public String toString() {
        return "ItemSet{" +
                "set=" + Arrays.toString(set) +
                '}';
    }

    public void add(int value) {
        int[] newItem = new int[set.length + 1];
        for (int i = 0; i < set.length; i ++) {
            newItem[i] = set[i];
        }
        newItem[newItem.length - 1] = value;
        set = newItem;
    }
}
