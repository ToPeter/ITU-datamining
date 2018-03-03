package data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Normalizer {


    public static ArrayList<Iris> normalize(ArrayList<Iris> data) {

        double pentalLengthAvg = data.stream().mapToDouble(o -> o.getPetal_Length()).average().getAsDouble();
        double pentalWidthAvg = data.stream().mapToDouble(o -> o.getPetal_Width()).average().getAsDouble();
        double sepalWidthAvg = data.stream().mapToDouble(o -> o.getSepal_Width()).average().getAsDouble();
        double sepalLengthAvg = data.stream().mapToDouble(o -> o.getSepal_Length()).average().getAsDouble();

        List<Float> pentalLength = data.stream().map(Iris::getPetal_Length).collect(Collectors.toList());
        List<Float> pentalWidth = data.stream().map(Iris::getPetal_Width).collect(Collectors.toList());
        List<Float> sepalLength = data.stream().map(Iris::getSepal_Length).collect(Collectors.toList());
        List<Float> sepalWidth = data.stream().map(Iris::getSepal_Width).collect(Collectors.toList());

        double pentalLengthStd = standardDeviation(pentalLength, pentalLengthAvg);
        double pentalWidthStd = standardDeviation(pentalWidth, pentalWidthAvg);
        double sepalLengthStd = standardDeviation(sepalLength, sepalLengthAvg);
        double sepalWidthStd = standardDeviation(sepalWidth, sepalWidthAvg);

        for (Iris i: data) {
            i.setPetal_Length((float) ((i.getPetal_Length() - pentalLengthAvg) / pentalLengthStd));
            i.setPetal_Width((float) ((i.getSepal_Width() - pentalWidthAvg) / pentalWidthStd));
            i.setSepal_Length((float) ((i.getSepal_Length() - sepalLengthAvg) / sepalLengthStd));
            i.setSepal_Width((float) ((i.getSepal_Width() - sepalWidthAvg) / sepalWidthStd));
        }

        return data;
    }

    private static double standardDeviation (List<Float> data, double average) {
        double result = 0;
        for (Float a : data) {
            result += (a - average) * (a - average);
        }
        return Math.sqrt(result);
    }


}
