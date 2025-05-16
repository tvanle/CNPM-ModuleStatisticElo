package org.example;

public class MatchPlayer {
    private String id, matchId, chessPlayerId, role, note;
    private int eloChange;

    public MatchPlayer(String id, String matchId, String chessPlayerId, String role, int eloChange) {
        this(id, matchId, chessPlayerId, role, eloChange, null);
    }

    public MatchPlayer(String id, String matchId, String chessPlayerId, String role, int eloChange, String note) {
        this.id = id;
        this.matchId = matchId;
        this.chessPlayerId = chessPlayerId;
        this.role = role;
        this.eloChange = eloChange;
        this.note = note;
    }

    public String getMatchId() { return matchId; }
    public String getChessPlayerId() { return chessPlayerId; }
    public int getEloChange() { return eloChange; }
    public String getNote() { return note; }
}