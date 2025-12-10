// Collaborators: Bryan Brizuela, Nathan Bal, and Mohammed Kasana
import java.util.Arrays;

// Represents a single weather sensor attached to a station
public class Sensor {

    private String type;           // Type of sensor (Temperature, Humidity, Wind)
    private String unit;           // Unit of measurement (°C, %, km/h)
    private double currentValue;   // Current reading
    private double[] history;      // Rolling history of last 7 readings
    private int historySize = 7;

    public Sensor(String type, double initialValue) {
        this.type = type;
        this.currentValue = initialValue;
        this.history = new double[historySize];
        Arrays.fill(this.history, initialValue);

        switch (type.toLowerCase()) {
            case "temperature": this.unit = "°C"; break;
            case "humidity": this.unit = "%"; break;
            case "wind": this.unit = "km/h"; break;
            default: this.unit = ""; break;
        }
    }

    // Shift history and update current value
    public void update(double newValue) {
        System.arraycopy(history, 1, history, 0, historySize - 1);
        history[historySize - 1] = newValue;
        currentValue = newValue;
    }

    public String getType() { return type; }
    public double getCurrentValue() { return currentValue; }
    public String getFormattedValue() { return String.format("%.1f%s", currentValue, unit); }
    public double[] getHistory() { return history; }
}
