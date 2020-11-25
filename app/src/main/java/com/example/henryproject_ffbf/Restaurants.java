package com.example.henryproject_ffbf;

public class Restaurants {
    private String name, description, url, summary;

    public Restaurants() {}

    public Restaurants(String Name, String Description, String URL) {
        this.name = Name;
        this.description = Description;
        this.url = URL;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
