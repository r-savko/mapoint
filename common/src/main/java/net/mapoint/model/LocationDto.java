package net.mapoint.model;

import java.util.Set;

public class LocationDto implements Comparable<LocationDto> {

    private int id;
    private Double lat;
    private Double lng;
    private String address;
    private String name;
    private String type;
    private Set<FactDto> facts;
    private Set<OfferDto> offers;
    private Set<String> phones;
    private Set<WorkingTimeDto> workingTimes;
    private Double distance;

    public LocationDto() {
    }

    public LocationDto(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public LocationDto setId(int id) {
        this.id = id;
        return this;
    }

    public Double getLat() {
        return lat;
    }

    public LocationDto setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Double getLng() {
        return lng;
    }

    public LocationDto setLng(Double lng) {
        this.lng = lng;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LocationDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public LocationDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public LocationDto setType(String type) {
        this.type = type;
        return this;
    }

    public Set<FactDto> getFacts() {
        return facts;
    }

    public LocationDto setFacts(Set<FactDto> facts) {
        this.facts = facts;
        return this;
    }

    public Set<OfferDto> getOffers() {
        return offers;
    }

    public LocationDto setOffers(Set<OfferDto> offers) {
        this.offers = offers;
        return this;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public LocationDto setPhones(Set<String> phones) {
        this.phones = phones;
        return this;
    }

    public Set<WorkingTimeDto> getWorkingTimes() {
        return workingTimes;
    }

    public LocationDto setWorkingTimes(Set<WorkingTimeDto> workingTimes) {
        this.workingTimes = workingTimes;
        return this;
    }

    public Double getDistance() {
        return distance;
    }

    public LocationDto setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public int compareTo(LocationDto o) {
        return id - o.getId();
    }
}
