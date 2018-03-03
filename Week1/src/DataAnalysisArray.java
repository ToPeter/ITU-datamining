import java.io.IOException;
import java.util.Arrays;

/**
 * Created by lucas on 2/9/2017.
 */
public class DataAnalysisArray {
    public static void main(String[] args) throws IOException {
        String[][] data = CSVFileReader.readDataFile("./data/data.csv",";", "-",false);

        /*for (String[] line : data) {
            System.out.println(Arrays.toString(line));
        }*/


        for (int i = 0; i < data.length; i ++){
            System.out.print(data[i][1]);
        }

    }
}
