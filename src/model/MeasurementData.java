package model;

import java.util.*;

public class MeasurementData {
    private String id;
    private String type; // "sicaklik" veya "nem"
    private String location;
    private String date;
    private Map<String, Double> timeValueMap; // Zaman - Değer

    public MeasurementData(String id, String type, String location, String date) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.date = date;
        this.timeValueMap = new LinkedHashMap<>();
    }

    public void addMeasurement(String time, double value) {
        timeValueMap.put(time, value);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public Map<String, Double> getMeasurements() {
        return timeValueMap;
    }

    public List<Double> getValues() {
        return new ArrayList<>(timeValueMap.values());
    }

    public String getSummary() {
        return String.format("id:%s ölçüm: %s - yer: %s - tarih: %s", id, type, location, date);
    }
}
