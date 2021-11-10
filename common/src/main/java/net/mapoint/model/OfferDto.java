package net.mapoint.model;

import java.util.List;
import java.util.Set;
import net.mapoint.dao.entity.Type;

public class OfferDto implements Comparable<OfferDto> {

    private int id;
    private String text;
    private Set<OfferDateDto> dates;
    private String link;
    private LocationDto location;
    private String fullText;
    private List<SubcategoryDto> subcategories;
    private boolean approved;
    private int likes;
    private Type type = Type.OFFER;

    public int getId() {
        return id;
    }

    public OfferDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public OfferDto setText(String text) {
        this.text = text;
        return this;
    }

    public Set<OfferDateDto> getDates() {
        return dates;
    }

    public OfferDto setDates(Set<OfferDateDto> dates) {
        this.dates = dates;
        return this;
    }

    public String getLink() {
        return link;
    }

    public OfferDto setLink(String link) {
        this.link = link;
        return this;
    }

    public LocationDto getLocation() {
        return location;
    }

    public OfferDto setLocation(LocationDto location) {
        this.location = location;
        return this;
    }

    public String getFullText() {
        return fullText;
    }

    public OfferDto setFullText(String fullText) {
        this.fullText = fullText;
        return this;
    }

    public List<SubcategoryDto> getSubcategories() {
        return subcategories;
    }

    public OfferDto setSubcategories(List<SubcategoryDto> subcategories) {
        this.subcategories = subcategories;
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public OfferDto setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public OfferDto setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public Type getType() {
        return type;
    }

    public OfferDto setType(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public int compareTo(OfferDto o) {
        return id - o.id;
    }
}
