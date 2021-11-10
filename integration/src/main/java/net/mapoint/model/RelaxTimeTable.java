package net.mapoint.model;

import java.util.List;

public class RelaxTimeTable {

    private List<RelaxPeriod> dates;
    private IdHolder place;

    public List<RelaxPeriod> getDates() {
        return dates;
    }

    public void setDates(List<RelaxPeriod> dates) {
        this.dates = dates;
    }

    public IdHolder getPlace() {
        return place;
    }

    public void setPlace(IdHolder place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "RelaxTimeTable{" +
            "dates=" + dates +
            ", place=" + place +
            '}';
    }
}