package model;

import java.io.Serializable;

public class MatchPlayer implements Serializable {
    private String id;
    private String matchId;
    private String chessPlayerId;
    private int eloChange;
    private String result;
    private String opponentName; // Added field

    public MatchPlayer() {
        super();
    }

    public MatchPlayer(String id, String matchId, String chessPlayerId, int eloChange) {
        this(id, matchId, chessPlayerId, eloChange, null);
    }

    public MatchPlayer(String id, String matchId, String chessPlayerId, int eloChange, String result) {
        this.id = id;
        this.matchId = matchId;
        this.chessPlayerId = chessPlayerId;
        this.eloChange = eloChange;
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

    public int getEloChange() {
        return eloChange;
    }

    public void setEloChange(int eloChange) {
        this.eloChange = eloChange;
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

