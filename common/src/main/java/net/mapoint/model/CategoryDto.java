package net.mapoint.model;

import java.util.List;

public class CategoryDto implements Comparable<CategoryDto> {

    private int id;
    private String name;
    private String color;
    private String icon;
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

    public String getColor() {
        return color;
    }

    public CategoryDto setColor(String color) {
        this.color = color;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public CategoryDto setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public List<SubcategoryDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryDto> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public int compareTo(CategoryDto o) {
        return name.compareTo(o.getName());
    }
}
