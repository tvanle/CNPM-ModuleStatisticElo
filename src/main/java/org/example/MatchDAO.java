package org.example;

import java.util.ArrayList;
import java.util.List;

public class MatchDAO {
    public Match getMatchById(String matchId) {
        List<Match> matches = new ArrayList<>();
        matches.add(new Match("M1", "R1", "2025-05-01", "W"));
        matches.add(new Match("M2", "R1", "2025-05-01", "D"));
        matches.add(new Match("M3", "R2", "2025-05-02", "L"));
        matches.add(new Match("M4", "R2", "2025-05-02", "W"));
        matches.add(new Match("M5", "R3", "2025-05-03", "D"));
        matches.add(new Match("M6", "R3", "2025-05-03", "L"));

        for (Match match : matches) {
            if (match.getId().equals(matchId)) {
                return match;
            }
        }
        return null;
    }
}