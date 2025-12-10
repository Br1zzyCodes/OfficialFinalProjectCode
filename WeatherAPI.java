// Collaborators: Bryan Brizuela, Nathan Bal, and Mohammed Kasana
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherAPI {

    public static class WeeklyWeather {
        public String[] date = new String[7];
        public double[] maxTemp = new double[7];
        public double[] minTemp = new double[7];
        public double[] humidity = new double[7];
        public double[] wind = new double[7];
        public String[] conditionText = new String[7]; // NEW: condition text for each day
    }

    private static final String API_KEY = "b872aec586b24491bca35401251012";

    // Fetch 7-day forecast for a city
    public static WeeklyWeather getWeeklyWeather(String city) {
        WeeklyWeather week = new WeeklyWeather();

        try {
            String cityEncoded = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String urlStr = "http://api.weatherapi.com/v1/forecast.json?key=" + API_KEY
                    + "&q=" + cityEncoded + "&days=7&aqi=no&alerts=no";

            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) sb.append(line);
            in.close();

            String json = sb.toString();

            if (!json.contains("\"forecastday\":[")) throw new Exception("Invalid API response");
            String[] days = json.split("\"forecastday\":\\[")[1].split("\\},\\{");

            for (int i = 0; i < 7 && i < days.length; i++) {
                String day = days[i];
                week.date[i] = parseString(day, "date");
                week.maxTemp[i] = parseDouble(day, "maxtemp_c");
                week.minTemp[i] = parseDouble(day, "mintemp_c");
                week.humidity[i] = parseDouble(day, "avghumidity");
                week.wind[i] = parseDouble(day, "maxwind_kph");

                // Parse condition text from JSON
                String cond = parseString(day, "text"); // "text" is inside "condition":{"text":"Sunny"}
                if(cond.equals("N/A")) {
                    // fallback: try inside "condition"
                    cond = parseString(day, "condition_text");
                }
                week.conditionText[i] = cond;
            }

        } catch (Exception e) {
            System.out.println("Error fetching weather for " + city + ": " + e.getMessage());
            for (int i = 0; i < 7; i++) {
                week.date[i] = "N/A";
                week.maxTemp[i] = 20 + i;
                week.minTemp[i] = 15 + i;
                week.humidity[i] = 50;
                week.wind[i] = 5;
                week.conditionText[i] = "N/A";
            }
        }

        return week;
    }

    private static double parseDouble(String s, String key) {
        try {
            String search = "\"" + key + "\":";
            int idx = s.indexOf(search);
            if (idx == -1) return 0;
            int start = idx + search.length();
            int end = s.indexOf(",", start);
            if (end == -1) end = s.length();
            return Double.parseDouble(s.substring(start, end));
        } catch (Exception e) {
            return 0;
        }
    }

    private static String parseString(String s, String key) {
        try {
            String search = "\"" + key + "\":\"";
            int idx = s.indexOf(search);
            if (idx == -1) return "N/A";
            int start = idx + search.length();
            int end = s.indexOf("\"", start);
            return s.substring(start, end);
        } catch (Exception e) {
            return "N/A";
        }
    }

    // ===== NEW METHOD: Get 7-day condition texts =====
    public static String[] getWeeklyConditions(String city) {
        String[] conditions = new String[7];
        try {
            WeeklyWeather ww = getWeeklyWeather(city);
            for (int i = 0; i < 7; i++) {
                conditions[i] = ww.conditionText[i];
            }
        } catch (Exception e) {
            for (int i = 0; i < 7; i++) conditions[i] = "N/A";
        }
        return conditions;
    }
}
