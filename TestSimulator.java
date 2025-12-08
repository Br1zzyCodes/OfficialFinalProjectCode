// !DO NOT SUBMIT TO FINAL!
// !TEST ONLY!

//Bryan Brizuela Starts test code

public class TestSimulator {
    public static void main(String[] args) {
        testSensorHistory();
        testAlerts();
        System.out.println("All tests passed!");
    }

    private static void testSensorHistory() {
        System.out.println("Testing Sensor History...");
        Sensor s = new Sensor("Temperature", 20.0);

        // Update 10 times
        for (int i = 0; i < 10; i++) {
            s.update();
        }
        double[] history = s.getHistory();
        if (history.length != 8) {
            throw new RuntimeException("History size incorrect: " + history.length);
        }
        System.out.println("  History size OK.");
        System.out.println("  Current value: " + s.getCurrentValue());
    }

     private static void testAlerts() {
        System.out.println("Testing Alerts...");
        Station st = new Station("Test City");

        // Force high temp
        Sensor tempSensor = st.getSensors().get(0); // Temp is first
         st.checkAlerts();
        System.out.println("  Alert check ran without error.");
    }
}
//Bryan Brizuela ends test code

// !DO NOT SUBMIT TO FINAL!
// !TEST ONLY!