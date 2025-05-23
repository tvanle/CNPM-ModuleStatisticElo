package model;

import java.io.Serializable;

public class Match implements Serializable {
    private String id;
    private String roundId;
    private String date;

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
}