package net.mapoint.model;

public class RelaxOfferHolder {

    private RelaxOffer event;

    public RelaxOffer getEvent() {
        return event;
    }

    public void setEvent(RelaxOffer event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "RelaxOfferHolder{" +
            "event=" + event +
            '}';
    }
}