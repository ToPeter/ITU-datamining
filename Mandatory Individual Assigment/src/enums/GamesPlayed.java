package Enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 3/26/2017.
 */
public enum GamesPlayed {
    candy_crush (1),
    wordfeud (2),
    minecraft (3),
    farmVille (4),
    fifa_2017 (5),
    star_wars_battlefield (6),
    life_is_strange (7),
    battlefield_4 (8),
    journey (9),
    gone_home (10),
    stanley_parable (11),
    call_of_duty_black_ops (12),
    rocket_league (13),
    bloodthorne (14),
    rise_of_the_tomb_raider (15),
    the_witness (16),
    her_story (17),
    fallout_4 (18),
    dragon_age_inquisition (19),
    counter_strike_go (20),
    angry_birds (21),
    the_last_of_us (22),
    the_magic_circle (23),
    none(24);

    private int numVal;

    GamesPlayed(int numVal) {
        this.numVal = numVal;
    }

    private static Map<Integer, GamesPlayed> map = new HashMap<Integer, GamesPlayed>();

    static {
        for (GamesPlayed gamesEnum : GamesPlayed.values()) {
            map.put(gamesEnum.numVal, gamesEnum);
        }
    }

    public static GamesPlayed getGameName (int numValue) {
        return map.get(numValue);
    }

    public int getNumVal(){
        return numVal;
    }
}
