package no.westerdals.PG4600.Innlevering1.model;

import java.util.HashMap;

/**
 * Created by larsdahl on 14.02.2016.
 */
public class Score {
    public static HashMap<String, Integer> scores = new HashMap<String, Integer>();
    public static int scoreX = 0;
    public static int scoreO = 0;

    public static void addScore(Player player, int score) {
        scores.put(player.getPlayerName(), score);
    }

}
