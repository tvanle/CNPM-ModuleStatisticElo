package dao;

import java.util.ArrayList;
import java.util.List;

import model.ChessPlayer;
import model.Match;
import model.MatchPlayer;

public class MatchPlayerDAO extends DAO {
    public MatchPlayerDAO() {
        super();
    }

    public List<Match> getMatches(String chessPlayerId, String tournamentId) {
        List<MatchPlayer> matchPlayers = new ArrayList<>();
        matchPlayers.add(new MatchPlayer("MP1", "M1", "P1", "Player1", 20, null, "W"));
        matchPlayers.add(new MatchPlayer("MP2", "M1", "P2", "Player2", -20, null, "L"));
        matchPlayers.add(new MatchPlayer("MP3", "M2", "P3", "Player1", 5, null, "D"));
        matchPlayers.add(new MatchPlayer("MP4", "M2", "P4", "Player2", -5, null, "D"));
        matchPlayers.add(new MatchPlayer("MP5", "M3", "P1", "Player1", -30, null, "L"));
        matchPlayers.add(new MatchPlayer("MP6", "M3", "P3", "Player2", 30, null, "W"));
        matchPlayers.add(new MatchPlayer("MP7", "M4", "P2", "Player1", 20, null, "W"));
        matchPlayers.add(new MatchPlayer("MP8", "M4", "P4", "Player2", -20, null, "L"));
        matchPlayers.add(new MatchPlayer("MP9", "M5", "P1", "Player1", 60, null, "D"));
        matchPlayers.add(new MatchPlayer("MP10", "M5", "P4", "Player2", -5, null, "D"));
        matchPlayers.add(new MatchPlayer("MP11", "M6", "P2", "Player1", -20, null, "L"));
        matchPlayers.add(new MatchPlayer("MP12", "M6", "P3", "Player2", -15, null, "W"));

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
        matchPlayers.add(new MatchPlayer("MP1", "M1", "P1", "Player1", 20, null, "W"));
        matchPlayers.add(new MatchPlayer("MP2", "M1", "P2", "Player2", -20, null, "L"));
        matchPlayers.add(new MatchPlayer("MP3", "M2", "P3", "Player1", 5, null, "D"));
        matchPlayers.add(new MatchPlayer("MP4", "M2", "P4", "Player2", -5, null, "D"));
        matchPlayers.add(new MatchPlayer("MP5", "M3", "P1", "Player1", -30, null, "L"));
        matchPlayers.add(new MatchPlayer("MP6", "M3", "P3", "Player2", 30, null, "W"));
        matchPlayers.add(new MatchPlayer("MP7", "M4", "P2", "Player1", 20, null, "W"));
        matchPlayers.add(new MatchPlayer("MP8", "M4", "P4", "Player2", -20, null, "L"));
        matchPlayers.add(new MatchPlayer("MP9", "M5", "P1", "Player1", 60, null, "D"));
        matchPlayers.add(new MatchPlayer("MP10", "M5", "P4", "Player2", -5, null, "D"));
        matchPlayers.add(new MatchPlayer("MP11", "M6", "P2", "Player1", -20, null, "L"));
        matchPlayers.add(new MatchPlayer("MP12", "M6", "P3", "Player2", -15, null, "W"));

        List<MatchPlayer> result = new ArrayList<>();
        for (MatchPlayer mp : matchPlayers) {
            if (matchId == null || mp.getMatchId().equals(matchId)) {
                result.add(mp);
            }
        }
        return result;
    }

    public List<MatchPlayer> getMatchPlayersByChessPlayer(String playerId) {
        List<MatchPlayer> matches = new ArrayList<>();
        List<MatchPlayer> allMatchPlayers = getMatchPlayersByMatch(null);
        ChessPlayerDAO chessPlayerDAO = new ChessPlayerDAO();
        for (MatchPlayer mp : allMatchPlayers) {
            if (mp.getChessPlayerId().equals(playerId)) {
                // Find the opponent in the same match
                for (MatchPlayer opponentMp : allMatchPlayers) {
                    if (opponentMp.getMatchId().equals(mp.getMatchId()) && !opponentMp.getChessPlayerId().equals(playerId)) {
                        ChessPlayer opponent    = chessPlayerDAO.getChessPlayerById(opponentMp.getChessPlayerId());
                        MatchPlayer matchPlayer = new MatchPlayer();
                        matchPlayer.setMatchId(mp.getMatchId());
                        matchPlayer.setOpponentName(opponent != null ? opponent.getName() : "N/A");
                        matchPlayer.setEloChange(mp.getEloChange());
                        matches.add(matchPlayer);
                        break;
                    }
                }
            }
        }
        return matches;
    }
}