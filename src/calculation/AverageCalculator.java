package calculation;

import model.MeasurementData;

import java.util.*;

public class AverageCalculator implements Calculator {

    @Override
    public String getName() {
        return "ortalama";
    }

    @Override
    public List<String> calculate(List<MeasurementData> dataList, boolean global) {
        List<String> result = new ArrayList<>();

        if (global) {
            List<Double> allValues = new ArrayList<>();
            for (MeasurementData data : dataList) {
                allValues.addAll(data.getValues());
            }
            double avg = allValues.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            result.add("global ortalama: " + avg);
        } else {
            for (MeasurementData data : dataList) {
                double avg = data.getValues().stream().mapToDouble(Double::doubleValue).average().orElse(0);
                result.add(data.getSummary() + " , ortalama: " + avg);
            }
        }

        return result;
    }
}
