package net.mapoint.payload;


import java.util.List;


public class AddOfferPayload {

    private Integer locationId;
    private String name;
    private String text;
    private String link;
    private String fullText;
    private List<Integer> categories;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public AddOfferPayload setName(String name) {
        this.name = name;
        return this;
    }
}
