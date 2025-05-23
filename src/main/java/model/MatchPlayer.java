package model;

import java.io.Serializable;

public class MatchPlayer implements Serializable {
    private String id;
    private String matchId;
    private String chessPlayerId;
    private String role;
    private int eloChange;
    private String note;
    private String result;
    private String opponentName; // Added field

    public MatchPlayer() {
        super();
    }

    public MatchPlayer(String id, String matchId, String chessPlayerId, String role, int eloChange) {
        this(id, matchId, chessPlayerId, role, eloChange, null, null);
    }

    public MatchPlayer(String id, String matchId, String chessPlayerId, String role, int eloChange, String note) {
        this(id, matchId, chessPlayerId, role, eloChange, note, null);
    }

    public MatchPlayer(String id, String matchId, String chessPlayerId, String role, int eloChange, String note, String result) {
        this.id = id;
        this.matchId = matchId;
        this.chessPlayerId = chessPlayerId;
        this.role = role;
        this.eloChange = eloChange;
        this.note = note;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getChessPlayerId() {
        return chessPlayerId;
    }

    public void setChessPlayerId(String chessPlayerId) {
        this.chessPlayerId = chessPlayerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEloChange() {
        return eloChange;
    }

    public void setEloChange(int eloChange) {
        this.eloChange = eloChange;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // Added getter and setter for opponentName
    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }
}