import java.util.ArrayList;
import java.util.List;

 //This class manages:
 // A dynamic list of Station objects
 // Adding new weather stations to the system
 // Advancing time for ALL stations (triggering sensor updates)
 // Generating a full network-wide report including alerts

 // The WeatherSimulator uses this class to fulfill major project

 // requirements such as:
 // Multiple stations stored in a collection
 // Advancing time across the entire system
 // Producing summaries and alerts for all stations

public class Network {

    // List storing all weather stations in the network
    private List<Station> stations;


     // Constructor initializes an empty list of stations.
     // ArrayList is used so stations can be added dynamically.

    public Network() {
        this.stations = new ArrayList<>();
    }

     // Adds a new Station to the network's list.
     // Called during initialization in WeatherSimulator.
    public void addStation(Station station) {
        stations.add(station);
    }

     // Called when the simulation advances one “tick.”
     // This method loops through each station and tells it to update its sensors.

    public void advanceTime() {
        for (Station s : stations) {
            s.updateAll();  // Trigger update on each station's sensors
        }
    }


    // Returns the full list of stations.
     // Used by the WeatherSimulator when searching for a city or displaying history.
    public List<Station> getStations() {
        return stations;
    }


     // Creates a formatted text report showing:
     // Each station’s name and latest sensor readings
     // Any active alerts triggered by its sensors

    // This method fulfills project requirements related to:
    // “Summaries” across all stations
    // Displaying alerts such as HEAT, DRY, GALE

    public String generateReport() {
        StringBuilder sb = new StringBuilder();

        sb.append("--- Network Report ---\n");

        // Loop through each station and append its status summary
        for (Station s : stations) {

            // Status includes name + latest sensor readings
            sb.append(s.getStatus()).append("\n");

            // Check for alerts at the station (heat, wind, humidity)
            List<String> alerts = s.checkAlerts();

            // If any alerts were raised, print them under the station
            if (!alerts.isEmpty()) {
                for (String alert : alerts) {
                    sb.append("  !!! ").append(alert).append(" !!!\n");
                }
            }
        }

        return sb.toString();
    }
}
