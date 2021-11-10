package net.mapoint.model;

public class RelaxLocationHolder {

    private RelaxLocation place;

    public RelaxLocation getPlace() {
        return place;
    }

    public void setPlace(RelaxLocation place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "RelaxLocationHolder{" +
            "place=" + place +
            '}';
    }
}