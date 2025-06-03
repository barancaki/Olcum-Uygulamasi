package calculation;

import model.MeasurementData;
import java.util.*;

public class FrequencyCalculator implements Calculator {
    @Override
    public String getName() {
        return "frekans";
    }

    private Map<Double, Integer> countFreq(List<Double> values) {
        Map<Double, Integer> freq = new TreeMap<>();
        for (double v : values) {
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }
        return freq;
    }

    @Override
    public List<String> calculate(List<MeasurementData> dataList, boolean global) {
        List<String> result = new ArrayList<>();

        if (global) {
            List<Double> allValues = new ArrayList<>();
            for (MeasurementData data : dataList) {
                allValues.addAll(data.getValues());
            }
            result.add("GLOBAL FREKANS:\n" + freqMapToString(countFreq(allValues)));
        } else {
            for (MeasurementData data : dataList) {
                result.add(data.getSummary() + "\n" + freqMapToString(countFreq(data.getValues())) + "\n------------------");
            }
        }

        return result;
    }

    private String freqMapToString(Map<Double, Integer> freqMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Double, Integer> entry : freqMap.entrySet()) {
            sb.append(entry.getKey()).append(" derece ").append(entry.getValue()).append(" defa ölçüldü\n");
        }
        return sb.toString();
    }
}
