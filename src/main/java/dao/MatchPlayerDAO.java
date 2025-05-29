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
        ChessPlayerDAO chessPlayerDAO = new ChessPlayerDAO();
        try {
            // First get all matches for this player
            String sql = "SELECT m.* FROM MatchPlayer mp JOIN `Match` m ON mp.matchId = m.id WHERE mp.chessPlayerId = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chessPlayerId);
            var rs = pstmt.executeQuery();

            while (rs.next()) {
                String matchId = rs.getString("id");
                Match match = new Match(
                    matchId,
                    rs.getString("roundId"),
                    rs.getString("date")
                );

                // Now get all players for this match
                String playerSql = "SELECT mp.*, cp.* FROM MatchPlayer mp " +
                                 "JOIN ChessPlayer cp ON mp.chessPlayerId = cp.id " +
                                 "WHERE mp.matchId = ?";
                var playerStmt = con.prepareStatement(playerSql);
                playerStmt.setString(1, matchId);
                var playerRs = playerStmt.executeQuery();

                while (playerRs.next()) {
                    ChessPlayer player = new ChessPlayer(
                        playerRs.getString("chessPlayerId"),
                        playerRs.getString("name"),
                        playerRs.getString("nationality"),
                        playerRs.getInt("birthYear"),
                        playerRs.getString("icard"),
                        playerRs.getInt("initialElo")
                    );

                    MatchPlayer matchPlayer = new MatchPlayer(
                        playerRs.getString("mp.id"),
                        player,
                        playerRs.getInt("eloChange"),
                        playerRs.getString("result")
                    );

                    match.addPlayer(matchPlayer);
                }

                playerRs.close();
                playerStmt.close();

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
        ChessPlayerDAO chessPlayerDAO = new ChessPlayerDAO();
        try {
            String sql;
            if (matchId == null) {
                sql = "SELECT mp.*, cp.* FROM MatchPlayer mp " +
                     "JOIN ChessPlayer cp ON mp.chessPlayerId = cp.id";
            } else {
                sql = "SELECT mp.*, cp.* FROM MatchPlayer mp " +
                     "JOIN ChessPlayer cp ON mp.chessPlayerId = cp.id " +
                     "WHERE mp.matchId = ?";
            }

            var pstmt = con.prepareStatement(sql);
            if (matchId != null) pstmt.setString(1, matchId);
            var rs = pstmt.executeQuery();

            while (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("chessPlayerId"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birthYear"),
                    rs.getString("icard"),
                    rs.getInt("initialElo")
                );

                MatchPlayer mp = new MatchPlayer(
                    rs.getString("mp.id"),
                    player,
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
        List<MatchPlayer> matchPlayers = new ArrayList<>();
        try {
            String sql = "SELECT mp.*, cp.* FROM MatchPlayer mp " +
                         "JOIN ChessPlayer cp ON mp.chessPlayerId = cp.id " +
                         "WHERE mp.chessPlayerId = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, playerId);
            var rs = pstmt.executeQuery();

            while (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("chessPlayerId"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birthYear"),
                    rs.getString("icard"),
                    rs.getInt("initialElo")
                );

                MatchPlayer mp = new MatchPlayer(
                    rs.getString("mp.id"),
                    player,
                    rs.getInt("eloChange"),
                    rs.getString("result")
                );

                // Find the opponent for this match
                String opponentSql = "SELECT mp.*, cp.* FROM MatchPlayer mp " +
                                    "JOIN ChessPlayer cp ON mp.chessPlayerId = cp.id " +
                                    "WHERE mp.matchId = ? AND mp.chessPlayerId != ?";
                var opponentStmt = con.prepareStatement(opponentSql);
                opponentStmt.setString(1, rs.getString("mp.matchId"));
                opponentStmt.setString(2, playerId);
                var opponentRs = opponentStmt.executeQuery();

                matchPlayers.add(mp);
                opponentRs.close();
                opponentStmt.close();
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchPlayers;
    }
}

