// Collaborators: Bryan Brizuela, Nathan Bal, and Mohammed Kasana
import javax.swing.*;

public class WeatherSimulator {

    public Network network;

    public WeatherSimulator() {
        network = new Network();
        initializeNetwork();
    }

    private void initializeNetwork(){
        network.addStation(new Station("London"));
        network.addStation(new Station("New York"));
        network.addStation(new Station("Tokyo"));
        network.addStation(new Station("Sydney"));
    }

    public static void main(String[] args){
        WeatherSimulator sim = new WeatherSimulator();
        SwingUtilities.invokeLater(() -> new WeatherGUI(sim.network));
    }
}
