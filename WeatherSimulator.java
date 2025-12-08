// AI Starts code off.

import java.util.Scanner;

public class WeatherSimulator {
    // Reference to the network of stations
    private Network network;
    // Scanner used for reading user input from the console
    private Scanner scanner;

    // Constructor initializes the program.
    // Creates the Network object and loads it with some default stations.
     // Also prepares a Scanner so the simulator can accept user commands.
    public WeatherSimulator() {
        network = new Network();        // Create the station network
        scanner = new Scanner(System.in); // Input reader
        initializeNetwork();            // Load default weather stations
    }
//AI Ends

    //Bryan Brizuela Starts

     //Creates the initial stations in the simulation.
      //These are example locations added to the network at startup.
    private void initializeNetwork() {
        // Four stations added to the network
        network.addStation(new Station("London"));
        network.addStation(new Station("New York"));
        network.addStation(new Station("Tokyo"));
        network.addStation(new Station("Sydney"));
    }
    // Begins the simulation loop.
     //The loop continues until the user selects "Exit".
     // Displays a menu, reads input, and performs actions accordingly.
    public void run() {
        boolean running = true;  // Controls whether the main loop continues

        while (running) {
            printMenu();                  // Show the available options
            String input = scanner.nextLine(); // Read the user's menu choice

            // Respond based on which menu option the user picked
            switch (input) {
                case "1":
                    // Option 1: Print a report listing each station and its current sensor data.
                    System.out.println(network.generateReport());
                    break;

                case "2":
                    // Option 2: Advance time by one tick.
                    // This triggers all sensors across all stations to generate new readings.
                    network.advanceTime();
                    System.out.println("Time advanced by 1 tick.");

                    // For convenience, print the new updated station report right away.
                    System.out.println(network.generateReport());
                    break;

                case "3":
                    // Option 3: View detailed sensor history and statistics for a given station.
                    viewStationHistory();
                    break;

                case "4":
                    // Option 4: Exit the program
                    running = false;
                    System.out.println("Exiting simulation.");
                    break;

                default:
                    // User typed something invalid — remind them to pick a correct option
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
// Bryan Brizuela Ends.


    // Bryan Brizuela Starts

     //Prints the text-based menu that the user sees each time through the loop.
    private void printMenu() {
        System.out.println("\n=== Weather Station Simulator ===");
        System.out.println("1. List City & Status");   // Show current readings and alerts
        System.out.println("2. Advance Time (Tick)");  // Generate new sensor values
        System.out.println("3. View City History");    // Show full history + min/avg/max
        System.out.println("4. Exit");                 // Quit the simulation
        System.out.print("Select an option: ");        // Prompt for input
    }


     //Allows the user to enter a city name and view detailed history
     //for all sensors in that station.
    private void viewStationHistory() {
        System.out.print("Enter City name: ");
        String name = scanner.nextLine();  // Read city name typed by user

        Station target = null;  // Will hold the matching station if found

        // Loop through all stations in the network to find the one requested
        for (Station s : network.getStations()) {
            // Case-insensitive comparison so "london" matches "London"
            if (s.getName().equalsIgnoreCase(name)) {
                target = s;
                break;  // Exit the loop once we find the station
            }
        }

        // If station exists, print its history and statistics
        if (target != null) {
            System.out.println("History for " + target.getName() + ":");

            // Loop through each sensor (Temperature, Humidity, Wind)
            for (Sensor s : target.getSensors()) {
                // Display the sensor type (e.g., "Temperature") and its current value
                System.out.println("  " + s.getType() + " (" + s.getFormattedValue() + ")");

                // Display the rolling history array (last 8 readings, for example)
                System.out.print("    History: [");
                double[] hist = s.getHistory();

                // Print each reading in the history array
                for (int i = 0; i < hist.length; i++) {
                    System.out.printf("%.1f", hist[i]);  // Show value with 1 decimal place
                    if (i < hist.length - 1) System.out.print(", ");
                }
                System.out.println("]");

                // Print minimum, average, and maximum readings for this sensor
                System.out.printf("    Stats - Min: %.1f, Avg: %.1f, Max: %.1f\n",
                        s.getMin(), s.getAvg(), s.getMax());
            }
        } else {
            // No station by that name in the system
            System.out.println("City not found.");
        }
    }


     //Program entry point — creates a simulator instance and starts it.
    public static void main(String[] args) {
        new WeatherSimulator().run(); // Create and run the program
    }
}
// Bryan Brizuela Ends.

// Ai usage was engaged When errors occured.