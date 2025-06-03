package calculation;

import model.MeasurementData;
import java.util.*;

public class MedianCalculator implements Calculator {
    @Override
    public String getName() {
        return "medyan";
    }

    private double calcMedian(List<Double> values) {
        Collections.sort(values);
        int size = values.size();
        if (size == 0) return 0;
        if (size % 2 == 0) {
            return (values.get(size/2 - 1) + values.get(size/2)) / 2.0;
        } else {
            return values.get(size/2);
        }
    }

    @Override
    public List<String> calculate(List<MeasurementData> dataList, boolean global) {
        List<String> result = new ArrayList<>();

        if (global) {
            List<Double> allValues = new ArrayList<>();
            for (MeasurementData data : dataList) {
                allValues.addAll(data.getValues());
            }
            result.add("global medyan: " + calcMedian(allValues));
        } else {
            for (MeasurementData data : dataList) {
                result.add(data.getSummary() + " , medyan: " + calcMedian(data.getValues()));
            }
        }

        return result;
    }
}
