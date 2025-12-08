import java.util.Arrays;
import java.util.Random;

//presents one weather sensor attached to a station.
 // Each sensor stores:
 // A type ("temperature", "humidity", or "wind")
 // A unit of measurement
 // A current reading

public class Sensor {

    // Type of sensor (Temperature, Humidity, Wind)
    private String type;

    // Unit of measurement (°C, %, m/s)
    private String unit;

    // Most recent reading
    private double currentValue;

    // Stores the last N readings (fixed-size rolling history)
    private double[] history;

    // The number of readings to remember (project suggests 8)
    private int historySize = 8;

    // Random number generator used for simulated sensor movement
    private Random random;


     //Constructor initializes the sensor with:
     // A type (determines unit and update behavior)
     // An initial reading
     // A history array pre-filled with this initial value

    public Sensor(String type, double initialValue) {
        this.type = type;
        this.currentValue = initialValue;
        this.history = new double[historySize];
        this.random = new Random();

        // Fill the history array with the starting value
        Arrays.fill(this.history, initialValue);

        // Set unit depending on sensor type
        switch (type.toLowerCase()) {
            case "temperature":
                this.unit = "°C";
                break;
            case "humidity":
                this.unit = "%";
                break;
            case "wind":
                this.unit = "m/s";
                break;
            default:
                this.unit = "";  // Fallback for unknown types
        }
    }


    public void update() {

        // ---- HISTORY SHIFT ----
        // Move all values one position left:
        // history[1] → history[0], history[2] → history[1], etc.
        System.arraycopy(history, 1, history, 0, historySize - 1);

        // Store the value BEFORE update as the newest historical entry
        history[historySize - 1] = currentValue;

        // ---- RANDOM WALK ----
        // Generates a number from -1.0 to +1.0
        double change = (random.nextDouble() - 0.5) * 2.0;

        // Behavior depends on sensor type
        if (type.equalsIgnoreCase("temperature")) {
            currentValue += change;
            // Constrain temperatures to the range -20 to 50 °C
            currentValue = Math.max(-20, Math.min(50, currentValue));

        } else if (type.equalsIgnoreCase("humidity")) {
            currentValue += change * 2;  // Humidity changes faster
            currentValue = Math.max(0, Math.min(100, currentValue));

        } else if (type.equalsIgnoreCase("wind")) {
            currentValue += change * 3;  // Wind changes aggressively
            currentValue = Math.max(0, Math.min(50, currentValue));
        }
    }

    // Returns the sensor name/type (Temperature / Humidity / Wind)
    public String getType() {
        return type;
    }

    // Returns the raw numeric value
    public double getCurrentValue() {
        return currentValue;
    }

    // Returns the value with units for display (e.g., "24.3°C")
    public String getFormattedValue() {
        return String.format("%.1f%s", currentValue, unit);
    }

    // Gives access to the full history array
    public double[] getHistory() {
        return history;
    }

    // Computes the minimum historical value
    public double getMin() {
        double min = Double.MAX_VALUE;
        for (double v : history) min = Math.min(min, v);
        return min;
    }

    // Computes the maximum historical value
    public double getMax() {
        double max = Double.MIN_VALUE;
        for (double v : history) max = Math.max(max, v);
        return max;
    }

    // Computes the average historical value
    public double getAvg() {
        double sum = 0;
        for (double v : history) sum += v;
        return sum / historySize;
    }
}
