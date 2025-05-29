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
        List<Match> matches = new ArrayList<>();
        try {
            String sql = "SELECT m.* FROM MatchPlayer mp JOIN `Match` m ON mp.matchId = m.id WHERE mp.chessPlayerId = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chessPlayerId);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                Match match = new Match(
                    rs.getString("id"),
                    rs.getString("roundId"),
                    rs.getString("date")
                );
                matches.add(match);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    public List<MatchPlayer> getMatchPlayersByMatch(String matchId) {
        List<MatchPlayer> result = new ArrayList<>();
        try {
            String sql = matchId == null ? "SELECT * FROM MatchPlayer" : "SELECT * FROM MatchPlayer WHERE matchId = ?";
            var pstmt = matchId == null ? con.prepareStatement(sql) : con.prepareStatement(sql);
            if (matchId != null) pstmt.setString(1, matchId);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                MatchPlayer mp = new MatchPlayer(
                    rs.getString("id"),
                    rs.getString("matchId"),
                    rs.getString("chessPlayerId"),
                    rs.getInt("eloChange"),
                    rs.getString("result")
                );
                result.add(mp);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
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

