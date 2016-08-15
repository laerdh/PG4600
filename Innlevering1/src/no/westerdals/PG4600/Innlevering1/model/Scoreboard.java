package no.westerdals.PG4600.Innlevering1.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Scoreboard {
    private static Scoreboard instance;
    private static final String timeZone = "GMT+1:00";
    private static ArrayList<String> results = new ArrayList<>();

    public Scoreboard() {
        // Using singleton to save results
    }

    public static Scoreboard getInstance() {
        if (instance == null) {
            instance = new Scoreboard();
        }
        return instance;
    }

    public static void addResult(String result) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone(timeZone));
        String localTime = date.format(currentLocalTime);

        result = localTime + " - " + result;
        results.add(result);
    }

    public static List<String> getResults() {
        return results;
    }
}
