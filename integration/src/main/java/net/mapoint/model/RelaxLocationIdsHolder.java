package net.mapoint.model;

import java.util.List;

public class RelaxLocationIdsHolder {

    private List<IdHolder> places;

    public List<IdHolder> getPlaces() {
        return places;
    }

    public void setPlaces(List<IdHolder> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "RelaxLocationIdsHolder{" +
            "places=" + places +
            '}';
    }
}