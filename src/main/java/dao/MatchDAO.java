package dao;

import java.util.ArrayList;
import java.util.List;
import model.Match;

public class MatchDAO extends DAO {
    public MatchDAO() {
        super();
    }

    public Match getMatchById(String matchId) {
        List<Match> matches = new ArrayList<>();
        matches.add(new Match("M1", "R1", "2025-05-01"));
        matches.add(new Match("M2", "R1", "2025-05-01"));
        matches.add(new Match("M3", "R2", "2025-05-02"));
        matches.add(new Match("M4", "R2", "2025-05-02"));
        matches.add(new Match("M5", "R3", "2025-05-03"));
        matches.add(new Match("M6", "R3", "2025-05-03"));

        for (Match match : matches) {
            if (match.getId().equals(matchId)) {
                return match;
            }
        }
        return null;
    }
}