import java.util.LinkedList;
import java.util.List;

public class DataSet {
    private List<Row> myData;

    public DataSet(List<Row> data) {
        myData = data;
    }

    public List<Integer> getAges() {
        List<Integer> ages = new LinkedList<>();
        for (int i = 0; i < myData.size(); i ++){
            ages.add(myData.get(i).getAge());
        }
        return ages;
    }

    public void RemoveAge(int age) {
        for (int i = 0; i < getAges().size(); i++){
            if (getAges().get(i).equals(age) ) myData.remove(i);
        }
    }
}
