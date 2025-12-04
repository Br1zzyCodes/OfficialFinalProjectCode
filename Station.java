import java.util.ArrayList;
import java.util.List;

public class Station {
    private String name;
    private List<Sensor> sensors;

    public Station(String name) {
        this.name = name;
        this.sensors = new ArrayList<>();

        // Initialize default sensors
        sensors.add(new Sensor("Temperature", 20.0)); // Start at 20C
        sensors.add(new Sensor("Humidity", 50.0));    // Start at 50%
        sensors.add(new Sensor("Wind", 5.0));         // Start at 5m/s
    }

    public void updateAll() {
        for (Sensor s : sensors) {
            s.update();
        }
    }

    public List<String> checkAlerts() {
        List<String> alerts = new ArrayList<>();
        for (Sensor s : sensors) {
            double val = s.getCurrentValue();
            if (s.getType().equalsIgnoreCase("Temperature") && val >= 35.0) {
                alerts.add("HEAT ALERT: " + s.getFormattedValue());
            }
            if (s.getType().equalsIgnoreCase("Humidity") && val <= 20.0) {
                alerts.add("DRY ALERT: " + s.getFormattedValue());
            }
            if (s.getType().equalsIgnoreCase("Wind") && val >= 20.0) {
                alerts.add("GALE ALERT: " + s.getFormattedValue());
            }
        }
        return alerts;
    }

    public String getName() {
        return name;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Station: %-15s | ", name));
        for (Sensor s : sensors) {
            sb.append(String.format("%s: %s  ", s.getType().substring(0, 4), s.getFormattedValue()));
        }
        return sb.toString();
    }
}
