package com.westerdals.dako.pokemon.model;

public class Pokemon {
    private String _id;
    private String id;
    private String name;
    private String imageUrl;

    public Pokemon(String _id, String id, String name, String imageUrl) {
        setLocationId(_id);
        setId(id);
        setName(name);
        setImageUrl(imageUrl);
    }

    public Pokemon() {}

    public void setLocationId(String _id) { this._id = _id; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getLocationId() { return _id; }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getImageUrl() { return imageUrl; }

    public String toString() {
        return "Name: " + getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Pokemon))return false;
        Pokemon pokemon = (Pokemon)o;
        return pokemon.name.equals(this.name);
    }
}
