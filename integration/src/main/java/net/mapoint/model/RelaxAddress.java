package net.mapoint.model;

public class RelaxAddress {

    private String text;
    private double longitude;
    private double latitude;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "RelaxAddress{" +
            "text='" + text + '\'' +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            '}';
    }
}