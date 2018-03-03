import java.util.List;

/**
 * Created by lucas on 2/9/2017.
 */
public class DataLibrary {
    public static double average(List<Integer> input){
        double result = 0;
        for (int i = 0; i < input.size(); i ++) {
            result += input.get(i);
        }
        return result/input.size();
    }

    public static int processInteger (String text){
        text = text.replace(',', '.');
        String digits = text.replaceAll("[^0-9.]", "");
        return Integer.parseInt(digits);
    }

    public static double processDouble (String text){
        text = text.replace(',', '.');
        String digits = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(digits);
    }
}
