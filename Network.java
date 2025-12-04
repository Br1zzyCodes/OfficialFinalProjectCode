import java.util.ArrayList;
import java.util.List;

public class Network {
    private List<Station> stations;

    public Network() {
        this.stations = new ArrayList<>();
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public void advanceTime() {
        for (Station s : stations) {
            s.updateAll();
        }
    }

    public List<Station> getStations() {
        return stations;
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Network Report ---\n");
        for (Station s : stations) {
            sb.append(s.getStatus()).append("\n");
            List<String> alerts = s.checkAlerts();
            if (!alerts.isEmpty()) {
                for (String alert : alerts) {
                    sb.append("  !!! ").append(alert).append(" !!!\n");
                }
            }
        }
        return sb.toString();
    }
}
