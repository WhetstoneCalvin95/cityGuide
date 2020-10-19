package edu.txstate.sl20.cityguide;

import org.json.JSONObject;

public class Attraction {
    private int id;
    private String name;
    private double cost;
    private String url;
    private int image;

    public Attraction() {
    }

    public Attraction (JSONObject object){
        try {
            this.id = object.getInt("Id");
            this.cost = object.getDouble("Cost");
            this.name = object.getString("Name");
            this.url = object.getString("Url");
        }catch (Exception ex){ex.printStackTrace();}


    }

    public Attraction(int id, String name, double cost, String url, int image) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.url = url;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return this.name + ", " + this.id;
    }
}
