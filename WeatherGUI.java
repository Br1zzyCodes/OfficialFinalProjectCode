// Collaborators: Bryan Brizuela, Nathan Bal, and Mohammed Kasana
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.time.LocalTime;

public class WeatherGUI extends JFrame {

    private Network network;
    private JComboBox<String> citySelector;
    private JPanel forecastPanel;
    private JLabel lastUpdated;
    private JButton historyBtn;

    private final String TEMP_EMOJI = "🌡️";
    private final String HUMI_EMOJI = "💧";
    private final String WIND_EMOJI = "💨";
    private final String[] weekdays = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

    private double[][] displayedValues = new double[7][3]; // current displayed
    private double[][] targetValues = new double[7][3];    // live target
    private Random random = new Random();

    public WeatherGUI(Network network) {
        this.network = network;
        setTitle("Ultimate Weather App");
        setSize(1400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        getContentPane().setBackground(new Color(30,144,255));

        // ===== Top Bar =====
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(30,144,255));
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        citySelector = new JComboBox<>();
        for(Station s : network.getStations()) citySelector.addItem(s.getName());
        topBar.add(citySelector);

        JButton refreshBtn = new JButton("Refresh");
        styleButton(refreshBtn);
        topBar.add(refreshBtn);

        historyBtn = new JButton("History");
        styleButton(historyBtn);
        topBar.add(historyBtn);

        lastUpdated = new JLabel("Last updated: N/A");
        lastUpdated.setForeground(Color.WHITE);
        lastUpdated.setFont(new Font("SansSerif", Font.BOLD, 16));
        topBar.add(lastUpdated);

        add(topBar, BorderLayout.NORTH);

        // ===== Forecast Panel =====
        forecastPanel = new JPanel();
        forecastPanel.setBackground(new Color(30,144,255));
        forecastPanel.setLayout(new GridLayout(1,7,15,0));
        add(forecastPanel, BorderLayout.CENTER);

        for(int i=0;i<7;i++){
            forecastPanel.add(createDayCard(weekdays[i]));
            for(int j=0;j<3;j++) {
                displayedValues[i][j] = 0;
                targetValues[i][j] = 0;
            }
        }

        // ===== Actions =====
        refreshBtn.addActionListener(e -> fetchTargetValues());
        citySelector.addActionListener(e -> fetchTargetValues());
        historyBtn.addActionListener(e -> showHistoryPanel());

        // Timer for smooth live numbers (~30FPS)
        Timer animationTimer = new Timer(33, e -> animateNumbers());
        animationTimer.start();

        // Auto fetch live weather every 60 seconds
        Timer fetchTimer = new Timer(60000, e -> fetchTargetValues());
        fetchTimer.start();

        fetchTargetValues();
        setVisible(true);
    }

    // ===== Style Buttons =====
    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(30,144,255));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ===== Create Weather Card =====
    private JPanel createDayCard(String dayName){
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(5,1));
        card.setBackground(new Color(255,255,255,220));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY,1,true),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));

        JLabel dayLabel = new JLabel(dayName,SwingConstants.CENTER);
        dayLabel.setFont(new Font("SansSerif", Font.BOLD,18));

        JLabel iconLabel = new JLabel("❓",SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN,28));

        JLabel tempLabel = new JLabel(TEMP_EMOJI+" -- °C",SwingConstants.CENTER);
        tempLabel.setFont(new Font("SansSerif", Font.PLAIN,16));

        JLabel humLabel = new JLabel(HUMI_EMOJI+" -- %",SwingConstants.CENTER);
        humLabel.setFont(new Font("SansSerif", Font.PLAIN,16));

        JLabel windLabel = new JLabel(WIND_EMOJI+" -- km/h",SwingConstants.CENTER);
        windLabel.setFont(new Font("SansSerif", Font.PLAIN,16));

        card.add(dayLabel);
        card.add(iconLabel);
        card.add(tempLabel);
        card.add(humLabel);
        card.add(windLabel);

        return card;
    }

    // ===== Fetch Target Values (Live Prediction) =====
    private void fetchTargetValues() {
        String city = (String) citySelector.getSelectedItem();
        if(city==null) return;

        new SwingWorker<double[][], Void>() {
            String[] conditions;
            @Override
            protected double[][] doInBackground() {
                Station st = network.getStations().stream()
                        .filter(s->s.getName().equalsIgnoreCase(city))
                        .findFirst().orElse(null);
                if(st==null) return null;

                st.fetchLiveWeather(); // fetch live forecast
                conditions = WeatherAPI.getWeeklyConditions(city);
                return st.getWeeklyForecast();
            }

            @Override
            protected void done() {
                try{
                    double[][] forecast = get();
                    if(forecast==null) return;

                    for(int i=0;i<7;i++){
                        for(int j=0;j<3;j++){
                            targetValues[i][j] = forecast[i][j];
                        }
                        // Update icon immediately
                        JPanel card = (JPanel) forecastPanel.getComponent(i);
                        ((JLabel)card.getComponent(1)).setText(getWeatherEmoji(conditions[i]));
                    }
                }catch(Exception e){ e.printStackTrace(); }
            }
        }.execute();
    }

    // ===== Animate numbers smoothly =====
    private void animateNumbers(){
        for(int i=0;i<7;i++){
            for(int j=0;j<3;j++){
                double newValue = displayedValues[i][j] + (targetValues[i][j]-displayedValues[i][j])*0.05;
                newValue += (random.nextDouble()-0.5)*0.2;

                if(Math.abs(newValue-displayedValues[i][j])>0.05){
                    displayedValues[i][j] = newValue;
                    JPanel card = (JPanel) forecastPanel.getComponent(i);
                    switch(j){
                        case 0 -> ((JLabel)card.getComponent(2)).setText(TEMP_EMOJI+" "+String.format("%.1f °C",newValue));
                        case 1 -> ((JLabel)card.getComponent(3)).setText(HUMI_EMOJI+" "+String.format("%.1f %%",newValue));
                        case 2 -> ((JLabel)card.getComponent(4)).setText(WIND_EMOJI+" "+String.format("%.1f km/h",newValue));
                    }
                }
            }
        }
        lastUpdated.setText("Last updated: "+ LocalTime.now().withNano(0));
    }

    // ===== Show Past Week History Panel =====
    private void showHistoryPanel(){
        String city = (String) citySelector.getSelectedItem();
        if(city==null) return;

        Station st = network.getStations().stream()
                .filter(s->s.getName().equalsIgnoreCase(city))
                .findFirst().orElse(null);
        if(st==null) return;

        double[][] history = new double[7][3];
        for(int i=0;i<7;i++){
            history[i][0] = st.getSensors().get(0).getHistory()[i];
            history[i][1] = st.getSensors().get(1).getHistory()[i];
            history[i][2] = st.getSensors().get(2).getHistory()[i];
        }

        JFrame historyFrame = new JFrame("Past Week History - "+city);
        historyFrame.setSize(1400,500);
        historyFrame.setLayout(new GridLayout(1,7,15,0));
        historyFrame.getContentPane().setBackground(new Color(30,144,255));

        for(int i=0;i<7;i++){
            JPanel card = createDayCard(weekdays[i]);
            ((JLabel)card.getComponent(2)).setText(TEMP_EMOJI+" "+String.format("%.1f °C",history[i][0]));
            ((JLabel)card.getComponent(3)).setText(HUMI_EMOJI+" "+String.format("%.1f %%",history[i][1]));
            ((JLabel)card.getComponent(4)).setText(WIND_EMOJI+" "+String.format("%.1f km/h",history[i][2]));
            historyFrame.add(card);
        }

        historyFrame.setVisible(true);
    }

    // ===== Weather Emoji Mapping =====
    private String getWeatherEmoji(String condition){
        if(condition==null) return "❓";
        condition = condition.toLowerCase();
        if(condition.contains("sun")||condition.contains("clear")) return "☀️";
        if(condition.contains("cloud")) return "☁️";
        if(condition.contains("rain")||condition.contains("shower")) return "🌧️";
        if(condition.contains("storm")||condition.contains("thunder")) return "⛈️";
        if(condition.contains("snow")) return "❄️";
        if(condition.contains("fog")||condition.contains("mist")) return "🌫️";
        return "🌈";
    }

    public static void main(String[] args){
        Network network = new Network();
        network.addStation(new Station("London"));
        network.addStation(new Station("New York"));
        network.addStation(new Station("Tokyo"));
        network.addStation(new Station("Sydney"));

        SwingUtilities.invokeLater(() -> new WeatherGUI(network));
    }
}
