package net.mapoint.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.mapoint.dao.entity.Type;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FactResponse implements Comparable<FactResponse> {

    private int id;
    private String text;
    private String link;
    private LocationResponse location;
    private int likes;
    private Type type = Type.FACT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public int compareTo(FactResponse o) {
        return id - o.getId();
    }
}
