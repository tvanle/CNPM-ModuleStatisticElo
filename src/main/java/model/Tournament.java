package model;

import java.io.Serializable;

public class Tournament implements Serializable {
    private String id;
    private String name;
    private int year;
    private int edition;
    private String location;
    private String description;

    public Tournament() {
        super();
    }

    public Tournament(String id, String name, int year, int edition, String location, String description) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.edition = edition;
        this.location = location;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}