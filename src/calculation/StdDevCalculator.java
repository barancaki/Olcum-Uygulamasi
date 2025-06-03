package calculation;

import model.MeasurementData;
import java.util.*;

public class StdDevCalculator implements Calculator {
    @Override
    public String getName() {
        return "standart_sapma";
    }

    private double calcStdDev(List<Double> values) {
        double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = values.stream()
                .mapToDouble(v -> Math.pow(v - avg, 2))
                .average().orElse(0);
        return Math.sqrt(variance);
    }

    @Override
    public List<String> calculate(List<MeasurementData> dataList, boolean global) {
        List<String> result = new ArrayList<>();

        if (global) {
            List<Double> allValues = new ArrayList<>();
            for (MeasurementData data : dataList) {
                allValues.addAll(data.getValues());
            }
            result.add("global standart sapma: " + calcStdDev(allValues));
        } else {
            for (MeasurementData data : dataList) {
                result.add(data.getSummary() + " , standart sapma: " + calcStdDev(data.getValues()));
            }
        }

        return result;
    }
}
