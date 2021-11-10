package net.mapoint.model;


import java.util.List;

public class RelaxOfferIdsHolder {

    private List<IdHolder> events;

    public List<IdHolder> getEvents() {
        return events;
    }

    public void setEvents(List<IdHolder> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "RelaxOfferIdsHolder{" +
            "events=" + events +
            '}';
    }
}
