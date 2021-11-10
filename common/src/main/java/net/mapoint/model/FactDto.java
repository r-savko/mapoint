package net.mapoint.model;

import net.mapoint.dao.entity.Type;

public class FactDto implements Comparable<FactDto> {

    private int id;
    private String text;
    private String link;
    private LocationDto location;
    private boolean approved;
    private int likes;
    private Type type = Type.FACT;

    public int getId() {
        return id;
    }

    public FactDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public FactDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getLink() {
        return link;
    }

    public FactDto setLink(String link) {
        this.link = link;
        return this;
    }

    public LocationDto getLocation() {
        return location;
    }

    public FactDto setLocation(LocationDto location) {
        this.location = location;
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public FactDto setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public FactDto setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public Type getType() {
        return type;
    }

    public FactDto setType(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public int compareTo(FactDto o) {
        return id - o.getId();
    }
}
