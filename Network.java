// Collaborators: Bryan Brizuela, Nathan Bal, and Mohammed Kasana
import java.util.ArrayList;
import java.util.List;

// Stores all stations and allows fetching live weather
public class Network {

    private List<Station> stations;

    public Network() {
        stations = new ArrayList<>();
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public List<Station> getStations() {
        return stations;
    }

    // Fetch live weather for all stations
    public void fetchLiveWeatherAll() {
        for (Station s : stations) {
            s.fetchLiveWeather();
        }
    }

    // Advance all stations (optional, e.g., for simulation)
    public void advanceDay() {
        fetchLiveWeatherAll();
    }
}
