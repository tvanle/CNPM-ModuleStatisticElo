package org.example;

import java.util.ArrayList;
import java.util.List;

public class MatchPlayerDAO {
    public List<Match> getMatches(String chessPlayerId, String tournamentId) {
        List<MatchPlayer> matchPlayers = new ArrayList<>();
        matchPlayers.add(new MatchPlayer("MP1", "M1", "P1", "Player1", 20));
        matchPlayers.add(new MatchPlayer("MP2", "M1", "P2", "Player2", -20));
        matchPlayers.add(new MatchPlayer("MP3", "M2", "P3", "Player1", 5));
        matchPlayers.add(new MatchPlayer("MP4", "M2", "P4", "Player2", -5));
        matchPlayers.add(new MatchPlayer("MP5", "M3", "P1", "Player1", -30));
        matchPlayers.add(new MatchPlayer("MP6", "M3", "P3", "Player2", 30));
        matchPlayers.add(new MatchPlayer("MP7", "M4", "P2", "Player1", 20));
        matchPlayers.add(new MatchPlayer("MP8", "M4", "P4", "Player2", -20));
        matchPlayers.add(new MatchPlayer("MP9", "M5", "P1", "Player1", 60));
        matchPlayers.add(new MatchPlayer("MP10", "M5", "P4", "Player2", -5));
        matchPlayers.add(new MatchPlayer("MP11", "M6", "P2", "Player1", -20));
        matchPlayers.add(new MatchPlayer("MP12", "M6", "P3", "Player2", -15));

        List<Match> matches = new ArrayList<>();
        MatchDAO matchDAO = new MatchDAO();
        for (MatchPlayer mp : matchPlayers) {
            if (mp.getChessPlayerId().equals(chessPlayerId)) {
                Match match = matchDAO.getMatchById(mp.getMatchId());
                if (match != null) {
                    matches.add(match);
                }
            }
        }
        return matches;
    }

    public List<MatchPlayer> getMatchPlayersByMatch(String matchId) {
        List<MatchPlayer> matchPlayers = new ArrayList<>();
        matchPlayers.add(new MatchPlayer("MP1", "M1", "P1", "Player1", 20));
        matchPlayers.add(new MatchPlayer("MP2", "M1", "P2", "Player2", -20));
        matchPlayers.add(new MatchPlayer("MP3", "M2", "P3", "Player1", 5));
        matchPlayers.add(new MatchPlayer("MP4", "M2", "P4", "Player2", -5));
        matchPlayers.add(new MatchPlayer("MP5", "M3", "P1", "Player1", -30));
        matchPlayers.add(new MatchPlayer("MP6", "M3", "P3", "Player2", 30));
        matchPlayers.add(new MatchPlayer("MP7", "M4", "P2", "Player1", 20));
        matchPlayers.add(new MatchPlayer("MP8", "M4", "P4", "Player2", -20));
        matchPlayers.add(new MatchPlayer("MP9", "M5", "P1", "Player1", 60));
        matchPlayers.add(new MatchPlayer("MP10", "M5", "P4", "Player2", -5));
        matchPlayers.add(new MatchPlayer("MP11", "M6", "P2", "Player1", -20));
        matchPlayers.add(new MatchPlayer("MP12", "M6", "P3", "Player2", -15));

        List<MatchPlayer> result = new ArrayList<>();
        for (MatchPlayer mp : matchPlayers) {
            if (mp.getMatchId().equals(matchId)) {
                result.add(mp);
            }
        }
        return result;
    }
}