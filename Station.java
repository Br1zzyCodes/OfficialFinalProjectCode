import java.util.ArrayList;
import java.util.List;
 // Represents a single weather station within the network.

//Each station contains:
// A city name
// Three sensors (Temperature, Humidity, Wind)

 // This class is responsible for:
// Creating sensors for the station
// Updating all sensors every tick
// Checking for alerts based on sensor thresholds
// Providing formatted output for the UI report

public class Station {

    // Name of the city or station
    private String name;

    // List holding the three required sensors for the station
    private List<Sensor> sensors;

     // Constructor initializes a station with three sensors.
     // Default values give the simulation a realistic starting point.

    public Station(String name) {
        this.name = name;
        this.sensors = new ArrayList<>();

        // Requirement: Each station has 3 sensors (Temperature, Humidity, Wind)
        // Learning outcome: Using arrays/lists to store sensor types
        // The three sensors below are required for every station.
        sensors.add(new Sensor("Temperature", 20.0)); // Start at 20°C (comfortable baseline)
        sensors.add(new Sensor("Humidity", 50.0));    // Start at 50% (moderate humidity)
        sensors.add(new Sensor("Wind", 5.0));         // Start at 5 m/s (light breeze)
    }

     // Called once during every simulation tick.
     // Loops through each sensor and updates its reading.
    public void updateAll() {

        // Requirement: Simulation loop advances time and generates new readings
        // Learning outcome: Using loops to simulate time and roll readings
        // Each sensor handles its own random drift behavior.
        for (Sensor s : sensors) {
            s.update(); // Each sensor performs a random walk update
        }
    }

     // Scans all sensors and checks whether any have reached alert thresholds.

    // Alerts correspond to the project guidelines:
     //    • Temperature ≥ 35°C → HEAT ALERT
     //   • Humidity ≤ 20% → DRY ALERT
     //   • Wind ≥ 20 m/s → GALE ALERT

     //Returns a list of any alerts triggered.

    public List<String> checkAlerts() {

        List<String> alerts = new ArrayList<>();

        // Requirement: Trigger alerts when thresholds are crossed
        // Learning outcome: Using if-else logic to detect conditions
        for (Sensor s : sensors) {

            double val = s.getCurrentValue();

            // Temperature alert (too hot)
            if (s.getType().equalsIgnoreCase("Temperature") && val >= 35.0) {
                alerts.add("HEAT ALERT: " + s.getFormattedValue()); // HEAT alert
            }

            // Humidity alert (too dry)
            if (s.getType().equalsIgnoreCase("Humidity") && val <= 20.0) {
                alerts.add("DRY ALERT: " + s.getFormattedValue());  // DRY alert
            }

            // Wind alert (dangerously strong)
            if (s.getType().equalsIgnoreCase("Wind") && val >= 20.0) {
                alerts.add("GALE ALERT: " + s.getFormattedValue()); // GALE alert
            }
        }

        return alerts;
    }

     // Returns station name.
     //Used widely throughout the UI and search functions.
    public String getName() {
        // Requirement: Cities are identifiable by name
        return name;
    }

    // Returns all sensors belonging to this station.
     // Used when displaying history or generating detailed reports.
    public List<Sensor> getSensors() {
        // Requirement: Network stores Cities and their sensors
        return sensors;
    }

     // Creates a formatted summary containing:
     // Station/city name
     // Abbreviated sensor names (Temp, Humi, Wind)
     // Current readings for each sensor

     // Used by the Network class to generate the overall report.
    public String getStatus() {

        StringBuilder sb = new StringBuilder();

        // Requirement: Console UI shows latest readings
        // Learning outcome: Formatting output for user interface
        sb.append(String.format("City: %-15s | ", name));

        for (Sensor s : sensors) {
            // Show abbreviated sensor type and latest value
            // Example: "Temp: 21.3°C"
            sb.append(String.format(
                    "%s: %s  ",
                    s.getType().substring(0, 4),   // First 4 letters (Temp, Humi, Wind)
                    s.getFormattedValue()
            ));
        }

        return sb.toString();
    }
}