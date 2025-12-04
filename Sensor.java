import java.util.Arrays;
import java.util.Random;

public class Sensor {
    private String type;
    private String unit;
    private double currentValue;
    private double[] history;
    private int historySize = 8;
    private Random random;

    public Sensor(String type, double initialValue) {
        this.type = type;
        this.currentValue = initialValue;
        this.history = new double[historySize];
        this.random = new Random();

        // Initialize history with the starting value
        Arrays.fill(this.history, initialValue);

        // Set units based on type
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
                this.unit = "";
        }
    }

    public void update() {
        // Shift history
        System.arraycopy(history, 1, history, 0, historySize - 1);
        history[historySize - 1] = currentValue;

        // Random walk logic
        double change = (random.nextDouble() - 0.5) * 2.0; // -1.0 to 1.0

        if (type.equalsIgnoreCase("temperature")) {
            currentValue += change;
            // Clamp reasonable bounds (-20 to 50)
            currentValue = Math.max(-20, Math.min(50, currentValue));
        } else if (type.equalsIgnoreCase("humidity")) {
            currentValue += change * 2; // Faster change for humidity
            currentValue = Math.max(0, Math.min(100, currentValue));
        } else if (type.equalsIgnoreCase("wind")) {
            currentValue += change * 3; // Wind changes more erratically
            currentValue = Math.max(0, Math.min(50, currentValue));
        }
    }

    public String getType() {
        return type;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public String getFormattedValue() {
        return String.format("%.1f%s", currentValue, unit);
    }

    public double[] getHistory() {
        return history;
    }

    public double getMin() {
        double min = Double.MAX_VALUE;
        for (double v : history) min = Math.min(min, v);
        return min;
    }

    public double getMax() {
        double max = Double.MIN_VALUE;
        for (double v : history) max = Math.max(max, v);
        return max;
    }

    public double getAvg() {
        double sum = 0;
        for (double v : history) sum += v;
        return sum / historySize;
    }
}
