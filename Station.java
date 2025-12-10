// Collaborators: Bryan Brizuela, Nathan Bal, and Mohammed Kasana
import java.util.ArrayList;
import java.util.List;

// Represents a single weather station
public class Station {

    private String name;             // City name
    private List<Sensor> sensors;    // 3 sensors: Temp, Humidity, Wind

    public Station(String name) {
        this.name = name;
        this.sensors = new ArrayList<>();

        // Initialize sensors with default values
        sensors.add(new Sensor("Temperature", 20.0));
        sensors.add(new Sensor("Humidity", 50.0));
        sensors.add(new Sensor("Wind", 5.0));
    }

    // Fetch live weather from WeatherAPI and update sensors
    public void fetchLiveWeather() {
        WeatherAPI.WeeklyWeather ww = WeatherAPI.getWeeklyWeather(name);
        if (ww == null) return;

        // Update each sensor with the 7-day forecast
        for (int i = 0; i < 7; i++) {
            sensors.get(0).update(ww.maxTemp[i]);   // Temperature
            sensors.get(1).update(ww.humidity[i]);  // Humidity
            sensors.get(2).update(ww.wind[i]);      // Wind
        }
    }

    // Returns a 7x3 array for GUI: [day][metric]
    public double[][] getWeeklyForecast() {
        double[][] forecast = new double[7][3];
        for (int i = 0; i < 7; i++) {
            forecast[i][0] = sensors.get(0).getHistory()[i]; // Temp
            forecast[i][1] = sensors.get(1).getHistory()[i]; // Humidity
            forecast[i][2] = sensors.get(2).getHistory()[i]; // Wind
        }
        return forecast;
    }

    public String getName() { return name; }
    public List<Sensor> getSensors() { return sensors; }
}
