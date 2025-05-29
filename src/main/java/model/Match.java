package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {
    private String id;
    private String roundId;
    private String date;
    private List<MatchPlayer> players = new ArrayList<>();

    public Match() {
        super();
    }

    public Match(String id, String roundId, String date) {
        this.id = id;
        this.roundId = roundId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<MatchPlayer> players) {
        this.players = players;
    }

    public void addPlayer(MatchPlayer player) {
        this.players.add(player);
    }
}