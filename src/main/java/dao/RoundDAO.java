package dao;

import java.util.ArrayList;
import java.util.List;
import model.Match;
import model.Round;

public class RoundDAO extends DAO {
    public RoundDAO() {
        super();
    }

    public Round getRoundById(String roundId) {
        List<Round> rounds = new ArrayList<>();
        // Dữ liệu giả lập các vòng đấu
        rounds.add(new Round("R1", "T1", 1, "2025-05-01", "2025-05-01"));
        rounds.add(new Round("R2", "T1", 2, "2025-05-02", "2025-05-02"));
        rounds.add(new Round("R3", "T1", 3, "2025-05-03", "2025-05-03"));

        // Dữ liệu giả lập các trận đấu
        List<Match> matches = new ArrayList<>();
        matches.add(new Match("M1", "R1", "2025-05-01"));
        matches.add(new Match("M2", "R1", "2025-05-01"));
        matches.add(new Match("M3", "R2", "2025-05-02"));
        matches.add(new Match("M4", "R2", "2025-05-02"));
        matches.add(new Match("M5", "R3", "2025-05-03"));
        matches.add(new Match("M6", "R3", "2025-05-03"));

        // Tìm vòng đấu theo ID
        for (Round round : rounds) {
            if (round.getId().equals(roundId)) {
                // Thêm các trận đấu thuộc vòng đấu này vào listMatch
                List<Match> roundMatches = new ArrayList<>();
                for (Match match : matches) {
                    if (match.getRoundId().equals(roundId)) {
                        roundMatches.add(match);
                    }
                }
                round.setListMatch(roundMatches);
                return round;
            }
        }
        return null;
    }

    
}