package com.example.henryproject_ffbf;

public class StreetFood {
    private String name, description, location, summary;

    public StreetFood() {}

    public StreetFood(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
