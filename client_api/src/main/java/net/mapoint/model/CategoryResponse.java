package net.mapoint.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {

    private int id;
    private String name;
    private String color;
    private List<SubcategoryDto> subcategories;

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

    public List<SubcategoryDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryDto> subcategories) {
        this.subcategories = subcategories;
    }

    public String getColor() {
        return color;
    }

    public CategoryResponse setColor(String color) {
        this.color = color;
        return this;
    }
}
