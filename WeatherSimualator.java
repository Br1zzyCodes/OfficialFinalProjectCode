import java.util.Scanner;

public class WeatherSimualator {
    private Network network;
    private Scanner scanner;

    public WeatherSimualator() {
        network = new Network();
        scanner = new Scanner(System.in);
        initializeNetwork();
    }

    private void initializeNetwork() {
        // Create some initial stations
        network.addStation(new Station("London"));
        network.addStation(new Station("New York"));
        network.addStation(new Station("Tokyo"));
        network.addStation(new Station("Sydney"));
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println(network.generateReport());
                    break;
                case "2":
                    network.advanceTime();
                    System.out.println("Time advanced by 1 tick.");
                    // Show report after tick for convenience
                    System.out.println(network.generateReport());
                    break;
                case "3":
                    viewStationHistory();
                    break;
                case "4":
                    running = false;
                    System.out.println("Exiting simulation.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Weather Station Simulator ===");
        System.out.println("1. List Stations & Status");
        System.out.println("2. Advance Time (Tick)");
        System.out.println("3. View Station History");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    private void viewStationHistory() {
        System.out.print("Enter station name: ");
        String name = scanner.nextLine();

        Station target = null;
        for (Station s : network.getStations()) {
            if (s.getName().equalsIgnoreCase(name)) {
                target = s;
                break;
            }
        }

        if (target != null) {
            System.out.println("History for " + target.getName() + ":");
            for (Sensor s : target.getSensors()) {
                System.out.println("  " + s.getType() + " (" + s.getFormattedValue() + ")");
                System.out.print("    History: [");
                double[] hist = s.getHistory();
                for (int i = 0; i < hist.length; i++) {
                    System.out.printf("%.1f", hist[i]);
                    if (i < hist.length - 1) System.out.print(", ");
                }
                System.out.println("]");
                System.out.printf("    Stats - Min: %.1f, Avg: %.1f, Max: %.1f\n",
                        s.getMin(), s.getAvg(), s.getMax());
            }
        } else {
            System.out.println("Station not found.");
        }
    }

    public static void main(String[] args) {
        new WeatherSimualator().run();
    }
}
import java.util.Scanner;

public class WeatherSimulator {
    private Network network;
    private Scanner scanner;

    public WeatherSimulator() {
        network = new Network();
        scanner = new Scanner(System.in);
        initializeNetwork();
    }

    private void initializeNetwork() {
        // Create some initial stations
        network.addStation(new Station("London"));
        network.addStation(new Station("New York"));
        network.addStation(new Station("Tokyo"));
        network.addStation(new Station("Sydney"));
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println(network.generateReport());
                    break;
                case "2":
                    network.advanceTime();
                    System.out.println("Time advanced by 1 tick.");
                    // Show report after tick for convenience
                    System.out.println(network.generateReport());
                    break;
                case "3":
                    viewStationHistory();
                    break;
                case "4":
                    running = false;
                    System.out.println("Exiting simulation.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Weather Station Simulator ===");
        System.out.println("1. List Stations & Status");
        System.out.println("2. Advance Time (Tick)");
        System.out.println("3. View Station History");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    private void viewStationHistory() {
        System.out.print("Enter station name: ");
        String name = scanner.nextLine();

        Station target = null;
        for (Station s : network.getStations()) {
            if (s.getName().equalsIgnoreCase(name)) {
                target = s;
                break;
            }
        }

        if (target != null) {
            System.out.println("History for " + target.getName() + ":");
            for (Sensor s : target.getSensors()) {
                System.out.println("  " + s.getType() + " (" + s.getFormattedValue() + ")");
                System.out.print("    History: [");
                double[] hist = s.getHistory();
                for (int i = 0; i < hist.length; i++) {
                    System.out.printf("%.1f", hist[i]);
                    if (i < hist.length - 1) System.out.print(", ");
                }
                System.out.println("]");
                System.out.printf("    Stats - Min: %.1f, Avg: %.1f, Max: %.1f\n",
                        s.getMin(), s.getAvg(), s.getMax());
            }
        } else {
            System.out.println("Station not found.");
        }
    }

    public static void main(String[] args) {
        new WeatherSimualator().run();
    }
}
