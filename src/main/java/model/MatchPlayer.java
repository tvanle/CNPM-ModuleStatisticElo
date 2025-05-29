package model;

import java.io.Serializable;

public class MatchPlayer implements Serializable {
    private String id;
    private ChessPlayer player;
    private int eloChange;
    private String result;

    public MatchPlayer() {
        super();
    }

    public MatchPlayer(String id, ChessPlayer player, int eloChange) {
        this(id, player, eloChange, null);
    }

    public MatchPlayer(String id, ChessPlayer player, int eloChange, String result) {
        this.id = id;
        this.player = player;
        this.eloChange = eloChange;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChessPlayer getPlayer() {
        return player;
    }

    public void setPlayer(ChessPlayer player) {
        this.player = player;
    }

    public String getChessPlayerId() {
        return player != null ? player.getId() : null;
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

    public String getPlayerName() {
        return player != null ? player.getName() : "Unknown";
    }

    public String getPlayerNationality() {
        return player != null ? player.getNationality() : "Unknown";
    }
}

