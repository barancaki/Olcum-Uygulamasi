package calculation;

import model.MeasurementData;
import java.util.*;

public class MinCalculator implements Calculator {
    @Override
    public String getName() {
        return "minimum";
    }

    @Override
    public List<String> calculate(List<MeasurementData> dataList, boolean global) {
        List<String> result = new ArrayList<>();

        if (global) {
            double min = dataList.stream()
                    .flatMap(d -> d.getValues().stream())
                    .mapToDouble(Double::doubleValue)
                    .min().orElse(0);
            result.add("global minimum: " + min);
        } else {
            for (MeasurementData data : dataList) {
                double min = data.getValues().stream().mapToDouble(Double::doubleValue).min().orElse(0);
                result.add(data.getSummary() + " , minimum: " + min);
            }
        }

        return result;
    }
}
