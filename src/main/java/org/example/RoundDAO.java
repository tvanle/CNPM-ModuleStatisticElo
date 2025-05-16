package org.example;

import java.util.ArrayList;
import java.util.List;

public class RoundDAO {
    public Round getRoundById(String roundId) {
        List<Round> rounds = new ArrayList<>();
        rounds.add(new Round("R1", "T1", 1, "2025-05-01", "2025-05-01"));
        rounds.add(new Round("R2", "T1", 2, "2025-05-02", "2025-05-02"));
        rounds.add(new Round("R3", "T1", 3, "2025-05-03", "2025-05-03"));

        for (Round round : rounds) {
            if (round.getId().equals(roundId)) {
                return round;
            }
        }
        return null;
    }
}