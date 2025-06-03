package manager;

import calculation.*;
import model.MeasurementData;

import java.util.*;

public class CalculationManager {

    private final List<Calculator> selectedCalculators = new ArrayList<>();

    public void addCalculator(Calculator calculator) {
        selectedCalculators.add(calculator);
    }

    public void clearCalculators() {
        selectedCalculators.clear();
    }

    public Map<String, List<String>> calculateAll(List<MeasurementData> dataList, boolean global) {
        Map<String, List<String>> allResults = new HashMap<>();

        for (Calculator calculator : selectedCalculators) {
            List<String> results = calculator.calculate(dataList, global);
            allResults.put(calculator.getName(), results);
        }

        return allResults;
    }
}
