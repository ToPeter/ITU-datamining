import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class DataAnalysis {

    public static void main(String[] args) {

        // reads the file
        try {
            String[][] data = CSVFileReader.readDataFile("./data/data.csv",";", "-",false);
            List<Row> rows = new ArrayList<Row>();


            for (int i = 0; i < data.length; i ++){
                int age = DataLibrary.processInteger(data[i][1]); // age
                int height = DataLibrary.processInteger(data[i][4]); // height
                String degree = data[i][5]; // degree
                double shoeSize = DataLibrary.processDouble(data[i][3]); // shoesize
                Row row = new Row(age, height, degree, shoeSize);
                rows.add(row);
            }

            DataSet myData = new DataSet(rows);
            System.out.println(myData.getAges());
            System.out.println(DataLibrary.average(myData.getAges()));
            myData.RemoveAge(999);
            System.out.println(myData.getAges());

        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }
}
