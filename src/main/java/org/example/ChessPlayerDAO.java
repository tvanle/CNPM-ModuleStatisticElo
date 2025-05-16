package org.example;

import java.util.ArrayList;
import java.util.List;

public class ChessPlayerDAO {
    public List<ChessPlayer> getChessPlayersByTournament(String tournamentId) {
        List<ChessPlayer> players = new ArrayList<>();
        if ("T1".equals(tournamentId)) {
            players.add(new ChessPlayer("P1", "Alice", "USA", 1990, "ID001", 1500, 1550));
            players.add(new ChessPlayer("P2", "Bob", "UK", 1985, "ID002", 1600, 1580));
            players.add(new ChessPlayer("P5", "Eve", "USA", 1995, "ID005", 1400, 1400));
        } else if ("T2".equals(tournamentId)) {
            players.add(new ChessPlayer("P3", "Charlie", "France", 1992, "ID003", 1700, 1720));
            players.add(new ChessPlayer("P4", "David", "Germany", 1988, "ID004", 1800, 1770));
        }
        return players;
    }

    public ChessPlayer getChessPlayerById(String chessPlayerId) {
        List<ChessPlayer> allPlayers = new ArrayList<>();
        allPlayers.add(new ChessPlayer("P1", "Alice", "USA", 1990, "ID001", 1500, 1550));
        allPlayers.add(new ChessPlayer("P2", "Bob", "UK", 1985, "ID002", 1600, 1580));
        allPlayers.add(new ChessPlayer("P3", "Charlie", "France", 1992, "ID003", 1700, 1720));
        allPlayers.add(new ChessPlayer("P4", "David", "Germany", 1988, "ID004", 1800, 1770));
        allPlayers.add(new ChessPlayer("P5", "Eve", "USA", 1995, "ID005", 1400, 1400));

        for (ChessPlayer player : allPlayers) {
            if (player.getId().equals(chessPlayerId)) {
                return player;
            }
        }
        return null;
    }
}