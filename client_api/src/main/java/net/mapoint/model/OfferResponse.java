package net.mapoint.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Set;
import net.mapoint.dao.entity.Type;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferResponse implements Comparable<OfferResponse> {

    private int id;
    private String text;
    private Set<OfferDateResponse> dates;
    private String link;
    private LocationResponse location;
    private String fullText;
    private List<String> subcategories;
    private int likes;
    private Type type = Type.OFFER;


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

    public Set<OfferDateResponse> getDates() {
        return dates;
    }

    public OfferResponse setDates(Set<OfferDateResponse> dates) {
        this.dates = dates;
        return this;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int compareTo(OfferResponse o) {
        return id - o.id;
    }
}
