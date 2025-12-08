import java.util.ArrayList;
import java.util.List;

public class Station {
    private String name;
    private List<Sensor> sensors;

    public Station(String name) {
        this.name = name;
        this.sensors = new ArrayList<>();

        // Requirement: Each station has 3 sensors (Temperature, Humidity, Wind)
        // Learning outcome: Using arrays/lists to store sensor types
        sensors.add(new Sensor("Temperature", 20.0)); // Start at 20°C
        sensors.add(new Sensor("Humidity", 50.0));    // Start at 50%
        sensors.add(new Sensor("Wind", 5.0));         // Start at 5 m/s
    }

    public void updateAll() {
        // Requirement: Simulation loop advances time and generates new readings
        // Learning outcome: Using loops to simulate time and roll readings
        for (Sensor s : sensors) {
            s.update(); // Each sensor performs a random walk update
        }
    }

    public List<String> checkAlerts() {
        List<String> alerts = new ArrayList<>();
        // Requirement: Trigger alerts when thresholds are crossed
        // Learning outcome: Using if-else logic to detect conditions
        for (Sensor s : sensors) {
            double val = s.getCurrentValue();
            if (s.getType().equalsIgnoreCase("Temperature") && val >= 35.0) {
                alerts.add("HEAT ALERT: " + s.getFormattedValue()); // HEAT alert
            }
            if (s.getType().equalsIgnoreCase("Humidity") && val <= 20.0) {
                alerts.add("DRY ALERT: " + s.getFormattedValue());  // DRY alert
            }
            if (s.getType().equalsIgnoreCase("Wind") && val >= 20.0) {
                alerts.add("GALE ALERT: " + s.getFormattedValue()); // GALE alert
            }
        }
        return alerts;
    }

    public String getName() {
        // Requirement: Cities are identifiable by name
        return name;
    }

    public List<Sensor> getSensors() {
        // Requirement: Network stores Cities and their sensors
        return sensors;
    }

    public String getStatus() {
        // Requirement: Console UI shows latest readings
        // Learning outcome: Formatting output for user interface
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("City: %-15s | ", name));
        for (Sensor s : sensors) {
            // Show abbreviated sensor type and latest value
            sb.append(String.format("%s: %s  ", s.getType().substring(0, 4), s.getFormattedValue()));
        }
        return sb.toString();
    }
}
