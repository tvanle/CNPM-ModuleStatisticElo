package org.example;

public class Match {
    private String id, roundId, date, result;

    public Match(String id, String roundId, String date, String result) {
        this.id = id;
        this.roundId = roundId;
        this.date = date;
        this.result = result;
    }

    public String getId() { return id; }
    public String getRoundId() { return roundId; }
    public String getDate() { return date; }
    public String getResult() { return result; }
}