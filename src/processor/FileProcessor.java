package processor;

import model.MeasurementData;

import java.io.*;
import java.util.*;

public class FileProcessor {

    public static List<MeasurementData> readMeasurements(String folderPath, String type) {
        List<MeasurementData> dataList = new ArrayList<>();

        File typeFolder = new File(folderPath, type);
        if (!typeFolder.exists() || !typeFolder.isDirectory()) {
            return dataList;
        }

        File[] files = typeFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) return dataList;

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String header = reader.readLine(); // ilk satır
                if (header == null) continue;

                // Header bilgilerini parse edelim
                String[] parts = header.split(" - ");
                String id = parts[0].split(":")[1].trim();
                String location = parts[1].split(":")[1].trim();
                String date = parts[2].split(":")[1].trim();

                MeasurementData data = new MeasurementData(id, type, location, date);

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens.length == 2) {
                        String time = tokens[0].trim();
                        double value = Double.parseDouble(tokens[1].trim());
                        data.addMeasurement(time, value);
                    }
                }

                dataList.add(data);

            } catch (Exception e) {
                System.out.println("Hata: " + file.getName() + " dosyası okunamadı.");
            }
        }

        return dataList;
    }
}
