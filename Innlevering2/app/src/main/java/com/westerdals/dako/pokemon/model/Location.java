package com.westerdals.dako.pokemon.model;

public class Location {
    private String _id, name, hint;
    private double lat, lng;

    public Location() {
    }

    public Location(String _id, String name, double lat, double lng, String hint) {
        this._id = _id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.hint = hint;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return lng;
    }

    public void setLongitude(double lng) {
        this.lng = lng;
    }

    public double getLatitude() {
        return lat;
    }

    public void setLatitude(double lat) {
        this.lat = lat;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint){
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "ID#" + _id + "\nPokemon: " + name +
                "\nCords: " + lat + " - " + lng;
    }
}
