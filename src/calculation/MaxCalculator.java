package calculation;

import model.MeasurementData;
import java.util.*;

public class MaxCalculator implements Calculator {
    @Override
    public String getName() {
        return "maksimum";
    }

    @Override
    public List<String> calculate(List<MeasurementData> dataList, boolean global) {
        List<String> result = new ArrayList<>();

        if (global) {
            double max = dataList.stream()
                    .flatMap(d -> d.getValues().stream())
                    .mapToDouble(Double::doubleValue)
                    .max().orElse(0);
            result.add("global maksimum: " + max);
        } else {
            for (MeasurementData data : dataList) {
                double max = data.getValues().stream().mapToDouble(Double::doubleValue).max().orElse(0);
                result.add(data.getSummary() + " , maksimum: " + max);
            }
        }

        return result;
    }
}
