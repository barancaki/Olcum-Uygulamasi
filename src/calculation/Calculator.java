package calculation;

import model.MeasurementData;
import java.util.List;

public interface Calculator {
    String getName(); // Hangi işlem olduğunu belirtecek (örn. "Ortalama")
    List<String> calculate(List<MeasurementData> dataList, boolean global);
}
